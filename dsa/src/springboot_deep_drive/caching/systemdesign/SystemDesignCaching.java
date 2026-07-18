package springboot_deep_drive.caching.systemdesign;

/*
 * SIMPLE DEFINITION:
 * System Design with Caching means strategically placing cache layers
 * in large-scale systems to handle MILLIONS of users while keeping
 * response times under 100ms and DB load manageable.
 *
 * REAL-LIFE ANALOGY:
 * A library with 1 million books. Instead of going to the basement
 * archive (DB) every time, the librarian keeps popular books on a
 * front desk shelf (cache). Bestsellers stay on the shelf. Rarely
 * requested books stay in the archive.
 *
 * ============================================================================
 *  SYSTEM DESIGN: TWITTER / X NEWS FEED
 * ============================================================================
 *
 *  REQUIREMENTS:
 *  - 500M+ users, 200M+ daily active
 *  - Each user sees feed of Tweets from people they follow
 *  - Feed should load in < 200ms
 *  - Celebrities have millions of followers (fan-out problem)
 *
 *  CACHING STRATEGY: Fan-out on Write
 *  ┌─────────────────────────────────────────────────────────────────┐
 *  │  When Elon tweets:                                              │
 *  │  1. Store tweet in DB (permanent)                               │
 *  │  2. Pre-compute: Insert tweet ID into cache of ALL followers    │
 *  │  3. Each follower's feed cache gets the new tweet               │
 *  │  4. When follower loads feed: READ from cache (Redis list)      │
 *  │                                                                 │
 *  │  Cache Structure:                                               │
 *  │  Redis: feed:{userId} -> List of tweet IDs (capped at 800)      │
 *  │  TTL: 24 hours (feed regenerated if expired)                    │
 *  │                                                                 │
 *  │  WHY THIS WORKS:                                                │
 *  │  - Writes are RARE (few tweets), reads are FREQUENT (feed)      │
 *  │  - Fan-out on write = O(N) work per tweet, but reads are O(1)   │
 *  └─────────────────────────────────────────────────────────────────┘
 *
 *  CELEBRITY FIX (Elon has 150M followers):
 *  - Don't fan-out to ALL followers for celebrities
 *  - Instead: Fan-out on READ for celebrities
 *  - Merge: Regular tweets (from cache) + Celebrity tweets (fetched live)
 *  - Celebrities have a separate "celebrity_tweets" cache
 *
 *  CACHE LAYERS:
 *  ┌────────────┐  ┌──────────────┐  ┌────────────┐  ┌────────┐
 *  │ CDN        │→ │ API Gateway  │→ │ Application│→ │ Redis  │→ DB
 *  │ (static)   │  │ Cache        │  │ Cache      │  │ Cache  │
 *  │            │  │ (rate-limits)│  │ (L1: local)│  │ (L2)   │
 *  └────────────┘  └──────────────┘  └────────────┘  └────────┘
 *
 *  CACHE KEY EXAMPLES:
 *  - feed:{userId}:{page}           → List of tweet IDs
 *  - tweet:{tweetId}                → Tweet content object
 *  - user:{userId}:profile          → User profile metadata
 *  - celebrity_tweets:{userId}:page → Tweets for celebrities
 *
 * ============================================================================
 *  SYSTEM DESIGN: YOUTUBE / NETFLIX
 * ============================================================================
 *
 *  REQUIREMENTS:
 *  - 1B+ users, 500+ hours uploaded every minute
 *  - Video metadata & recommendations must load instantly
 *  - Video content served via CDN (not app cache)
 *
 *  WHAT WE CACHE:
 *  ┌─────────────────────┬──────────────────────┬─────────────────┐
 *  │ DATA                │ CACHE                │ TTL             │
 *  ├─────────────────────┼──────────────────────┼─────────────────┤
 *  │ Video metadata      │ Redis (L2) + Local   │ 1 hour          │
 *  │ (title, desc, tags) │ (L1)                 │ (sliding)       │
 *  ├─────────────────────┼──────────────────────┼─────────────────┤
 *  │ Trending videos     │ Redis sorted set     │ 5 minutes       │
 *  │                     │ (score = views/hr)   │ (recalculated)  │
 *  ├─────────────────────┼──────────────────────┼─────────────────┤
 *  │ User recommendations│ Redis (pre-computed) │ 1 hour          │
 *  ├─────────────────────┼──────────────────────┼─────────────────┤
 *  │ Watch history       │ Redis list (capped)  │ 7 days          │
 *  ├─────────────────────┼──────────────────────┼─────────────────┤
 *  │ Video content       │ CDN (CloudFront)     │ Long (popular)  │
 *  │ (actual video file) │                      │ Short (unpopular)│
 *  └─────────────────────┴──────────────────────┴─────────────────┘
 *
 *  HOT DATA:
 *  - Trending videos: cached at CDN edge (most requested)
 *  - Recommendations: pre-computed for logged-in users
 *  - Search results: cached for popular queries
 *
 *  CACHE STRATEGY: Cache-Aside + Write-Through
 *  - On upload: Write-Through (metadata goes to cache + DB)
 *  - On view: Cache-Aside (check cache, miss → load from DB)
 *  - Trending: Refresh-Ahead (recompute every 5 min, cache pre-warmed)
 *
 * ============================================================================
 *  SYSTEM DESIGN: UBER / LYFT
 * ============================================================================
 *
 *  REQUIREMENTS:
 *  - 15M+ trips per day, 5M+ drivers online
 *  - Driver location updates every 3-4 seconds
 *  - Riders need to see nearby available drivers in < 1 second
 *
 *  WHY CACHING IS CRITICAL:
 *  - 5M drivers × 1 update/3s = 1.6M writes/sec to DB (impossible)
 *  - Need to query "which drivers are within 1km of (lat,lng)?"
 *
 *  CACHE SOLUTION: Redis Geo + Write-Behind
 *
 *  ┌──────────┐   WebSocket   ┌──────────┐   Batch    ┌──────────┐
 *  │ Driver   │ ────────────> │  Redis   │ ──────────> │   DB     │
 *  │ App      │  GeoADD       │  Geo Set │  Every 5s   │ (history)│
 *  └──────────┘               └──────────┘             └──────────┘
 *
 *  CACHE STRUCTURE:
 *  - Redis GEOADD drivers:location {lng} {lat} {driverId}
 *  - GEORADIUS drivers:location {lng} {lat} 1km → nearby drivers
 *  - TTL: NO expiry (always live), but driver removed if no update in 10s
 *
 *  WHY THIS WORKS:
 *  - Redis Geo queries are O(log N) — sub-millisecond
 *  - DB writes are batched (Write-Behind) — 5s batches reduce 1.6M to ~300K writes/sec
 *  - If Redis crashes, we lose recent locations (acceptable — drivers reconnect)
 *
 *  CACHE LAYERS:
 *  ┌──────────┐  ┌──────────────┐  ┌────────────┐  ┌──────────┐
 *  │ Client   │→ │ API Gateway  │→ │ App Server │→ │  Redis   │
 *  │ App      │  │ (auth cache) │  │ (trip cache)│  │ (Geo +   │
 *  │          │  │              │  │            │  │  Session)│
 *  └──────────┘  └──────────────┘  └────────────┘  └──────────┘
 *
 * ============================================================================
 *  SYSTEM DESIGN: URL SHORTENER (TinyURL)
 * ============================================================================
 *
 *  REQUIREMENTS:
 *  - 100M+ URLs created, 1B+ redirects per month
 *  - Redirect must be < 10ms (every ms matters for UX + SEO)
 *  - Short URL (7 chars) must be unique
 *
 *  CACHING STRATEGY: Cache-Aside with Local Cache
 *  ┌──────────┐  GET tinyurl.com/abc1234 ┌──────────┐
 *  │ Browser  │ ───────────────────────> │  L1      │
 *  │          │                          │ (Caffeine│
 *  │          │                          │  local)  │
 *  │          │     MISS ───> ┌────────┐ │          │
 *  │          │              │  L2     │ │          │
 *  │          │              │ (Redis) │ │          │
 *  │          │     MISS ──> │         │ │          │
 *  │          │              └────┬────┘ │          │
 *  │          │                   v      │          │
 *  │          │              ┌──────────┐│          │
 *  │          │              │   DB     ││          │
 *  │          │ <─────────── │          ││          │
 *  └──────────┘   Redirect   └──────────┘└──────────┘
 *
 *  WHY TWO CACHE LAYERS?
 *  - L1 (Caffeine): ~10μs access, survives high QPS
 *  - L2 (Redis): ~1ms, shared across app instances
 *  - L1 TT < L2 TTL (L1 expires first → refreshes from L2, not DB)
 *
 * ============================================================================
 *  GENERAL SYSTEM DESIGN CACHING PRINCIPLES
 * ============================================================================
 *
 *  PRINCIPLE 1: Cache at the RIGHT level
 *    - Client (browser) → CDN → API Gateway → App Server → Redis → DB
 *    - Cache as close to the user as possible
 *
 *  PRINCIPLE 2: Choose the RIGHT eviction policy
 *    - LRU for temporal patterns (news feed, sessions)
 *    - LFU for popularity patterns (viral videos, trending)
 *    - TTL for time-sensitive data (sessions, OTP)
 *
 *  PRINCIPLE 3: Choose the RIGHT write strategy
 *    - Cache-Aside for most reads (simple, flexible)
 *    - Write-Through for consistency (banking, profiles)
 *    - Write-Behind for write-heavy (analytics, locations)
 *
 *  PRINCIPLE 4: Plan for cache failure
 *    - What happens when Redis goes down? (Circuit breaker)
 *    - What happens when cache is cold? (Warm-up strategy)
 *    - What happens under heavy load? (Rate limiting, degradation)
 *
 *  PRINCIPLE 5: Monitor cache health
 *    - Hit ratio (target > 90%)
 *    - Eviction rate (high eviction = need more memory)
 *    - Latency (p99 cache read time)
 */

public class SystemDesignCaching {

    // ================================================================
    //  DEMO: Twitter Feed Cache
    // ================================================================
    static class TwitterFeedDesign {

        /*
         * Simulate fan-out on write:
         * When Elon tweets, push to all followers' feed caches.
         */
        static void demo() {
            System.out.println("\n─────── TWITTER FEED CACHE ────────────");

            java.util.Map<String, java.util.List<String>> feedCache = new java.util.HashMap<>();
            java.util.Map<String, java.util.Set<String>> followers = new java.util.HashMap<>();

            // Setup followers
            followers.put("elon", new java.util.HashSet<>(java.util.Arrays.asList("user1", "user2", "user3")));
            followers.put("user1", new java.util.HashSet<>(java.util.Arrays.asList("user2")));

            // Fan-out on write
            String tweetId = "tweet:1001";
            String author = "elon";

            System.out.println("  " + author + " tweets: " + tweetId);
            System.out.println("  Fanning out to " + followers.get(author).size() + " followers...");

            for (String follower : followers.get(author)) {
                feedCache.computeIfAbsent(follower, k -> new java.util.ArrayList<>())
                    .add(0, tweetId); // Insert at front (newest first)
                System.out.println("    Added to " + follower + "'s feed cache");
            }

            // Show feed
            System.out.println("\n  user1's feed cache:");
            feedCache.get("user1").forEach(t ->
                System.out.println("    - " + t));

            System.out.println("\n  Celebrity fix: Fan-out on READ for 150M followers");
            System.out.println("  (Don't copy to all — fetch live + merge with cached feed)");
        }
    }

    // ================================================================
    //  DEMO: YouTube Trending Cache (Redis Sorted Set)
    // ================================================================
    static class YouTubeTrendingDesign {

        static void demo() {
            System.out.println("\n─────── YOUTUBE TRENDING CACHE ────────");

            // Redis sorted set: key=trending, score=views, value=videoId
            java.util.Map<String, Double> trendingCache = new java.util.TreeMap<>();

            // Simulate view counts
            trendingCache.put("video:cat", 1000000.0);
            trendingCache.put("video:music", 5000000.0);
            trendingCache.put("video:news", 2000000.0);
            trendingCache.put("video:sports", 3000000.0);

            System.out.println("  Trending cache (sorted by views):");
            trendingCache.entrySet().stream()
                .sorted(java.util.Map.Entry.<String, Double>comparingByValue().reversed())
                .forEach(e -> System.out.println("    " + e.getKey()
                    + " — " + String.format("%,.0f", e.getValue()) + " views"));

            System.out.println("\n  TTL: 5 minutes (recomputed every 5m)");
            System.out.println("  Cache strategy: Refresh-Ahead");
            System.out.println("  Video content: served via CDN (not app cache)");
        }
    }

    // ================================================================
    //  DEMO: Uber Driver Location Cache (Redis Geo)
    // ================================================================
    static class UberGeoCacheDesign {

        static void demo() {
            System.out.println("\n─────── UBER DRIVER LOCATION CACHE ────");

            System.out.println("  Redis GEOADD drivers:location -73.99 40.73 driver:101");
            System.out.println("  Redis GEOADD drivers:location -73.98 40.74 driver:102");
            System.out.println("  Redis GEOADD drivers:location -74.01 40.72 driver:103");

            // Simulate geo query
            double userLat = 40.73;
            double userLng = -73.99;
            double radiusKm = 1.0;

            // Simulated nearby drivers
            String[][] nearbyDrivers = {
                {"driver:101", "0.2km", "Available"},
                {"driver:102", "0.5km", "On Trip"},
                {"driver:103", "0.8km", "Available"}
            };

            System.out.println("\n  Rider at (" + userLat + ", " + userLng + ")");
            System.out.println("  GEORADIUS drivers:location " + userLng + " "
                + userLat + " " + radiusKm + "km");
            System.out.println("  Nearby drivers:");
            for (String[] d : nearbyDrivers) {
                System.out.println("    " + d[0] + " — " + d[1] + " (" + d[2] + ")");
            }

            System.out.println("\n  Write strategy: Write-Behind");
            System.out.println("  Location updates every 3s → Redis");
            System.out.println("  Batch flush to DB every 5s (reduces 1.6M → 300K writes/sec)");
            System.out.println("  If Redis crashes: drivers reconnect → acceptable loss");
        }
    }

    // ================================================================
    //  DEMO: URL Shortener Cache (Multi-Level)
    // ================================================================
    static class UrlShortenerDesign {

        static void demo() {
            System.out.println("\n─────── URL SHORTENER CACHE ───────────");

            java.util.Map<String, String> l1Cache = new java.util.HashMap<>(); // Caffeine (local)
            java.util.Map<String, String> l2Cache = new java.util.HashMap<>(); // Redis

            // Populate L2 (Redis)
            l2Cache.put("abc123", "https://example.com/long-url");
            l1Cache.put("abc123", l2Cache.get("abc123")); // L1 copies from L2

            System.out.println("  Redirect: GET short.url/abc123");
            System.out.println("  L1 (Caffeine): ~10μs — " + l1Cache.get("abc123"));
            System.out.println("  L2 (Redis): ~1ms — " + l2Cache.get("abc123"));
            System.out.println("  Cache strategy: Cache-Aside (L1 + L2)");

            System.out.println("\n  Cache hierarchy:");
            System.out.println("    L1 TTL: 5 min (shorter) — expires first");
            System.out.println("    L2 TTL: 1 hour (longer) — survives L1 expiry");
            System.out.println("    On L1 miss → check L2 → if L2 miss → DB");
            System.out.println("  This minimizes DB hits even when");
            System.out.println("  individual app caches are cold.");
        }
    }

    // ================================================================
    //  MAIN
    // ================================================================
    public static void main(String[] args) {
        System.out.println("============================================");
        System.out.println("   SYSTEM DESIGN WITH CACHING");
        System.out.println("============================================");

        TwitterFeedDesign.demo();
        YouTubeTrendingDesign.demo();
        UberGeoCacheDesign.demo();
        UrlShortenerDesign.demo();

        System.out.println("\n============================================");
        System.out.println("   CACHING PRINCIPLES SUMMARY");
        System.out.println("============================================");
        System.out.println("  1. Cache at the RIGHT level (close to user)");
        System.out.println("  2. Choose RIGHT eviction (LRU/LFU/TTL/FIFO)");
        System.out.println("  3. Choose RIGHT write strategy");
        System.out.println("  4. Plan for cache FAILURE");
        System.out.println("  5. MONITOR: hit ratio, evictions, latency");
        System.out.println("");
        System.out.println("  TWITTER: Fan-out on write + Redis lists");
        System.out.println("  YOUTUBE: CDN content + Redis trending + TTL");
        System.out.println("  UBER:    Redis Geo + Write-Behind batching");
        System.out.println("  TINYURL: Multi-level Cache (L1 Caffeine + L2 Redis)");
        System.out.println("============================================");
    }
}
