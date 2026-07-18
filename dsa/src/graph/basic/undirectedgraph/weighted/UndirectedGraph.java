package graph.basic.undirectedgraph.weighted;

import java.util.*;

// undirected weighted graph
// using hashmap
public class UndirectedGraph {
    // node -> { neighbor -> weight }
    private HashMap<Integer, HashMap<Integer, Integer>> graph;

    public UndirectedGraph(int v){
        graph = new HashMap<>();
        for (int i = 1; i <= v; i++) {
            graph.put(i,new HashMap<>());
        }
    }

    // Add edge bw nodes
    public void addEdge(int v1, int v2, int weight) {

        graph.putIfAbsent(v1, new HashMap<>());
        graph.putIfAbsent(v2, new HashMap<>());
        graph.get(v1).put(v2, weight);
        graph.get(v2).put(v1, weight);
    }

    public boolean containsEdge(int v1, int v2) {

        // 1 -> {2=5, 3=10}
        // 2 -> {1=5, 4=3}
        // Check if node v1 exists
        if (!graph.containsKey(v1)) {
            return false;
        }
        // Check if v2 is a neighbor of v1
        return graph.get(v1).containsKey(v2);
    }

    public boolean containsVertex(int v) {
        return graph.containsKey(v);
    }

    public void removeEdge(int v1, int v2) {
        if (!graph.containsKey(v1) || !graph.containsKey(v2)) {
            return;
        }
        graph.get(v1).remove(v2);
        graph.get(v2).remove(v1); // undirected
    }

    public void removeVertex(int v) {
        if (!graph.containsKey(v)) return;

        // remove this vertex from all neighbors
        for (int neighbor : graph.get(v).keySet()) {
            graph.get(neighbor).remove(v);
        }
        // remove vertex itself
        graph.remove(v);
    }

    public int getDegree(int v) {
        if (!graph.containsKey(v)) return -1;
        return graph.get(v).size();
    } // Degree = number of neighbors

    // Total edges = (sum of all degrees) / 2
    // In an undirected graph, the number of edges is calculated by summing the size of all
    // adjacency lists and dividing by 2, since each edge is stored twice.
    public int getNumberOfEdges() {
        int count = 0;

        for (int node : graph.keySet()) {
            count += graph.get(node).size(); // number of neighbors
        }
        return count / 2;
    }

    public boolean hasPath(int src, int dest, Map<Integer, Boolean> visited) {
        if (src == dest) return true;

        visited.put(src, true);

        for (int neighbor : graph.get(src).keySet()) {
            if (!visited.containsKey(neighbor)) {
                boolean found = hasPath(neighbor, dest, visited);
                if (found) return true;
            }
        }
        return false;
    }

    public Set<Integer> getNeighbors(int node) {
        if (!graph.containsKey(node)) return new HashSet<>();
        return graph.get(node).keySet();
    }

    public void printAllPaths(int src, int dest,
                              Set<Integer> visited,
                              List<Integer> path) {

        // Add node to path
        //Mark visited
        //If destination → print
        //Otherwise → go to neighbors
        //Undo changes (backtrack)

        // add current node to path
        path.add(src);
        visited.add(src);

        // base case: reached destination
        if (src == dest) {
            System.out.println(path);
        } else {
            // explore neighbors
            for (int neighbor : graph.get(src).keySet()) {
                if (!visited.contains(neighbor)) {
                    printAllPaths(neighbor, dest, visited, path);
                }
            }
        }

        // backtracking
        path.remove(path.size() - 1);
        visited.remove(src);
    }

    public void printAllPaths(int src, int dest,
                              Set<Integer> visited,
                              String path) {

        // add current node to path
        path = path + src + " ";
        visited.add(src);

        // base case
        if (src == dest) {
            System.out.println(path);
        } else {
            for (int neighbor : graph.get(src).keySet()) {
                if (!visited.contains(neighbor)) {
                    printAllPaths(neighbor, dest, visited, path);
                }
            }
        }

        // backtrack
        visited.remove(src);
    }

    public boolean bfs(int src, int dest){
        HashSet<Integer> visited = new HashSet<>();
        Queue<Integer> q = new LinkedList<>();
        q.add(src);
        while(!q.isEmpty()){
            // remove
            int rv = q.poll();
            // ignore if already visited
            if(visited.contains(rv)){
                continue;
            }

            // mark visited
            visited.add(rv);

            // self work
            if(rv == dest){
                return true;
            }

            // add nbrs(unvisited)
            for(int nbrs : graph.get(rv).keySet()){
                if(!visited.contains(nbrs)){
                    q.add(nbrs);
                }
            }
        }
        return false;
    }

    public boolean bfsOptimize(int src, int dest) {

        HashSet<Integer> visited = new HashSet<>();
        Queue<Integer> q = new LinkedList<>();

        q.add(src);
        visited.add(src); // mark early

        while (!q.isEmpty()) {

            int rv = q.poll();

            if (rv == dest) {
                return true;
            }

            for (int nbr : graph.get(rv).keySet()) {
                if (!visited.contains(nbr)) {
                    visited.add(nbr);
                    q.add(nbr);
                }
            }
        }

        return false;
    }

    public boolean dfsIterative(int src, int dest) {

        Set<Integer> visited = new HashSet<>();
        Stack<Integer> stack = new Stack<>();
// add src
        stack.push(src);

        while (!stack.isEmpty()) {
            // remove
            int node = stack.pop();
     // // ignore if already visited
            if (visited.contains(node)) continue;
            // mark visited
            visited.add(node);
            // self work
            if (node == dest) return true;
// add nbrs (unvisited)
            for (int nbr : graph.get(node).keySet()) {
                if (!visited.contains(nbr)) {
                    stack.push(nbr);
                }
            }
        }

        return false;
    }

    public void bft() {

        HashSet<Integer> visited = new HashSet<>();
        Queue<Integer> q = new LinkedList<>();

        // go through all nodes
        for (int src : graph.keySet()) {

            if(visited.contains(src)) continue;
            q.add(src);
            while(!q.isEmpty()){
                // remove
                int rv = q.poll();
                // ignore if already visited
                if(visited.contains(rv)){
                    continue;
                }

                // mark visited
                visited.add(rv);

                // self work
                System.out.print(rv+" ");

                // add nbrs(unvisited)
                for(int nbrs : graph.get(rv).keySet()){
                    if(!visited.contains(nbrs)){
                        q.add(nbrs);
                    }
                }
            }
            System.out.println();
        }
    }

    // Print graph
    public void printGraph() {
        for (int node : graph.keySet()) {
            System.out.print(node + " -> ");
            for (Map.Entry<Integer, Integer> entry : graph.get(node).entrySet()) {
                System.out.print("(" + entry.getKey() + ", w=" + entry.getValue() + ") ");
            }
            System.out.println();
        }
    }

}

// Outer map → node
//Inner map → neighbors + weights

/*
An undirected weighted graph can be implemented using either a Map<Integer, List<Edge>>
or a Map<Integer, Map<Integer, Integer>>. The list-based adjacency list is more memory efficient
and commonly used, while the map-of-maps approach provides O(1) edge lookup and easier
updates but uses more space.
 */

