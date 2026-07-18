package springboot_deep_drive.caching.distributed;

/*
 * SIMPLE DEFINITION:
 * Consistent Hashing is a technique to DISTRIBUTE data across multiple
 * cache servers so that adding/removing a server only requires moving
 * a MINIMAL amount of data (not rehashing everything).
 *
 * REAL-LIFE ANALOGY:
 * A phonebook has 26 sections (A-Z). You add a new section "AB" for
 * names starting with "Aa-Ab". Instead of rewriting the ENTIRE phonebook,
 * you only move a few names from section "A". Minimal reshuffling.
 *
 * ============================================================================
 *  CONSISTENT HASHING — Distributed Cache Sharding
 * ============================================================================
 *
 *  THE PROBLEM (NAIVE HASHING):
 *    cacheServer = hash(key) % N
 *    If N changes (server added/removed), ALL keys remap → ALL cache misses!
 *    This is called "cache stampede" — everything hits DB at once.
 *
 *  DIAGRAM (Naive):
 *    Server Count: 3 → hash(key) % 3 = 0, 1, or 2
 *    Add Server:   4 → hash(key) % 4 = completely different mapping
 *    Result:       ~75% of cache keys miss → DB overload!
 *
 *  THE SOLUTION (CONSISTENT HASHING):
 *    1. Create a HASH RING (circle) from 0 to 2^32 - 1
 *    2. Place servers on the ring using hash(serverIP)
 *    3. For each key: hash(key) → find nearest server clockwise
 *    4. Add server: only reassign keys between new server and its neighbor
 *    5. Remove server: only reassign keys from that server to next
 *
 *  DIAGRAM:
 *                   0
 *           ┌──────────────┐
 *           │              │
 *   2^32-1  │    Ring      │   Server A (hash(A))
 *           │              │
 *           │   Key X ─────┼── Find next server clockwise → Server C
 *           │              │
 *           │   Server B   │
 *           └──────────────┘
 *
 *  VIRTUAL NODES:
 *    Each physical server gets MULTIPLE virtual nodes on the ring.
 *    This ensures better load distribution (avoids "hot spots").
 *    Example: Server A → vnode_A1, vnode_A2, vnode_A3... (200 virtual nodes)
 *
 * ============================================================================
 *  ADVANTAGES
 * ============================================================================
 *  - MINIMAL reshuffling when scaling (only 1/N keys move)
 *  - Servers can be added/removed without full cache flush
 *  - Each server handles roughly equal load (with virtual nodes)
 *
 * ============================================================================
 *  REAL-WORLD USES
 * ============================================================================
 *  - Redis Cluster (16384 hash slots — not exactly consistent hashing,
 *    but similar concept with pre-defined slots)
 *  - Amazon DynamoDB (uses consistent hashing for partition)
 *  - Discord (consistent hashing for sharding)
 *  - Cassandra (consistent hashing for data distribution)
 *  - Akamai CDN (consistent hashing for edge server selection)
 *
 * ============================================================================
 *  CONSISTENT HASHING vs REDIS CLUSTER
 * ============================================================================
 *  ┌────────────────────┬────────────────────────────────────────────┐
 *  │ Consistent Hashing  │ Redis Cluster                            │
 *  ├────────────────────┼────────────────────────────────────────────┤
 *  │ Ring-based         │ Fixed 16384 hash slots                    │
 *  │ Any server count   │ Min 3 master nodes                        │
 *  │ Virtual nodes      │ Slot migration                            │
 *  │ Client-side routing│ Cluster nodes route transparently         │
 *  └────────────────────┴────────────────────────────────────────────┘
 *
 * ============================================================================
 *  INTERVIEW Q&A — Consistent Hashing
 * ============================================================================
 *
 *  Q1: How does consistent hashing minimize rehashing?
 *  A1: Only keys in the range between the new/removed server and its
 *      clockwise neighbor need to move. On average, only K/N keys move
 *      (where K = total keys, N = number of servers).
 *
 *  Q2: Why do we need virtual nodes?
 *  A2: Without virtual nodes, if servers hash close together on the ring,
 *      some servers get much more load. Virtual nodes spread each server's
 *      load across the ring → balanced distribution. Typically 100-200
 *      virtual nodes per physical server.
 *
 *  Q3: How does Redis Cluster handle sharding?
 *  A3: Redis Cluster uses 16384 fixed hash slots (not a ring).
 *      hash(key) % 16384 → assigned to server. Slots are migratable.
 *      Client can connect to any node; node redirects to correct server.
 *
 *  Q4: What is "hot spotting" and how do you solve it?
 *  A4: A single key gets massive traffic (e.g., Taylor Swift on Spotify).
 *      Solution: Hash-based sharding + local cache + application-level
 *      replication of hot keys across multiple servers.
 *
 *  Q5: How does DynamoDB use consistent hashing?
 *  A5: Each partition is a range on the consistent hash ring.
 *      When a partition grows too large, it splits into two.
 *      Data is replicated to N nodes (typically 3) for durability.
 */

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

public class ConsistentHashing {

    // ================================================================
    //  CONSISTENT HASH RING IMPLEMENTATION
    // ================================================================
    static class HashRing {
        private final TreeMap<Integer, String> ring = new TreeMap<>();
        private final int virtualNodesPerServer;
        private final MessageDigest md;

        HashRing(int virtualNodesPerServer) {
            this.virtualNodesPerServer = virtualNodesPerServer;
            try {
                this.md = MessageDigest.getInstance("MD5");
            } catch (NoSuchAlgorithmException e) {
                throw new RuntimeException(e);
            }
        }

        // Add a physical server with its virtual nodes
        void addServer(String server) {
            for (int i = 0; i < virtualNodesPerServer; i++) {
                String vnode = server + "#" + i;
                int hash = hash(vnode);
                ring.put(hash, server);
            }
            System.out.println("   [Ring] Added server: " + server
                + " (" + virtualNodesPerServer + " virtual nodes)");
        }

        // Remove a physical server and all its virtual nodes
        void removeServer(String server) {
            ring.entrySet().removeIf(e -> e.getValue().equals(server));
            System.out.println("   [Ring] Removed server: " + server);
        }

        // Find which server handles a given key
        String getServer(String key) {
            if (ring.isEmpty()) return null;
            int hash = hash(key);
            // Find the next server clockwise (ceiling entry)
            Map.Entry<Integer, String> entry = ring.ceilingEntry(hash);
            if (entry == null) {
                // Wrap around to first server (circle)
                entry = ring.firstEntry();
            }
            return entry.getValue();
        }

        // MD5 hash to an integer (simplified — real impl uses full 2^32 range)
        private int hash(String key) {
            byte[] digest = md.digest(key.getBytes(StandardCharsets.UTF_8));
            return ((digest[0] & 0xFF) << 24)
                 | ((digest[1] & 0xFF) << 16)
                 | ((digest[2] & 0xFF) << 8)
                 | (digest[3] & 0xFF);
        }

        int size() { return ring.size(); }

        void display() {
            System.out.println("   [Ring] Virtual nodes: " + ring.size());
            ring.forEach((hash, server) ->
                System.out.println("      hash=" + hash + " → " + server));
        }
    }

    // ================================================================
    //  MAIN — Demo
    // ================================================================
    public static void main(String[] args) {
        System.out.println("============================================");
        System.out.println("   CONSISTENT HASHING — Distributed Cache");
        System.out.println("============================================");

        // Create ring with 3 virtual nodes per server (small for demo)
        HashRing ring = new HashRing(3);

        System.out.println("\n=== Step 1: Add 3 cache servers ===");
        ring.addServer("Cache-A");
        ring.addServer("Cache-B");
        ring.addServer("Cache-C");
        ring.display();

        System.out.println("\n=== Step 2: Map some keys to servers ===");
        String[] keys = {"user:101", "user:102", "user:103",
                         "product:shoes", "product:phone",
                         "session:abc123", "session:xyz789"};
        Map<String, Integer> serverCount = new HashMap<>();

        for (String key : keys) {
            String server = ring.getServer(key);
            serverCount.merge(server, 1, Integer::sum);
            System.out.println("   Key: " + key + " → " + server);
        }

        System.out.println("\n   Distribution:");
        serverCount.forEach((s, c) ->
            System.out.println("      " + s + ": " + c + " keys"));

        System.out.println("\n=== Step 3: Add a new server (Cache-D) ===");
        System.out.println("  ONLY keys between Cache-D and its neighbor move!");
        ring.addServer("Cache-D");

        int movedKeys = 0;
        for (String key : keys) {
            String newServer = ring.getServer(key);
            String oldServer = serverCount.entrySet().stream()
                .filter(e -> e.getValue() > 0)
                .map(Map.Entry::getKey)
                .findFirst().orElse("");
            // Simplified check: just show if mapping changed
            Map<String, String> oldMapping = new HashMap<>();
            for (String k : keys) {
                oldMapping.putIfAbsent(k, ring.getServer(k));
            }
        }

        // Proper remapping check
        Map<String, String> beforeAdd = new HashMap<>();
        for (String key : keys) {
            beforeAdd.put(key, ring.getServer(key));
        }
        // Remove D to get original state
        ring.removeServer("Cache-D");

        Map<String, String> afterAdd = new HashMap<>();
        for (String key : keys) {
            afterAdd.put(key, ring.getServer(key));
        }
        ring.addServer("Cache-D");

        for (String key : keys) {
            String before = ring.getServer(key);
            // We need to compare before/after adding D
            // Actually simpler: just show the new mapping
            String now = ring.getServer(key);
            System.out.println("   " + key + " → " + now);
            if (!now.equals(beforeAdd.get(key))) {
                movedKeys++;
            }
        }

        // Simpler approach: show remapping count
        System.out.println("\n  Now let's see which keys moved when Cache-D joined:");
        // Re-create clean demo
        HashRing demoRing = new HashRing(100); // 100 virtual nodes for realism
        demoRing.addServer("A");
        demoRing.addServer("B");
        demoRing.addServer("C");

        Map<String, String> mappingBefore = new HashMap<>();
        for (String key : keys) {
            mappingBefore.put(key, demoRing.getServer(key));
        }

        demoRing.addServer("D");

        int moved = 0;
        for (String key : keys) {
            String after = demoRing.getServer(key);
            if (!after.equals(mappingBefore.get(key))) {
                moved++;
                System.out.println("   MOVED: " + key + " " + mappingBefore.get(key)
                    + " → " + after);
            }
        }
        System.out.println("   Keys moved: " + moved + "/" + keys.length
            + " (only ~1/" + (4) + " of keys = " + (100/4) + "%)");

        System.out.println("\n=== Step 4: Benefits demonstration ===");
        System.out.println("  With naive hashing (hash % N):");
        System.out.println("    Add 1 server → ~" + ((3*100)/4) + "% keys remapped → cache stampede!");
        System.out.println("  With consistent hashing:");
        System.out.println("    Add 1 server → ~" + (100/4) + "% keys remapped → minimal impact");

        System.out.println("\n=== Step 5: Virtual nodes importance ===");
        System.out.println("  Without virtual nodes:");
        System.out.println("    Servers might cluster on ring → unbalanced load");
        System.out.println("  With virtual nodes (100 per server):");
        System.out.println("    Evenly distributed across ring → balanced load");
        System.out.println("    One server handles ~1/N of total keys");

        System.out.println("\n============================================");
        System.out.println("  Consistent Hashing Demo Complete!");
        System.out.println("============================================");
    }
}
