package graph.basic.undirectedgraph.weighted.questions;

import graph.basic.undirectedgraph.weighted.UndirectedGraph;

import java.util.Set;

public class HasPath {

    private UndirectedGraph graph;

    public HasPath(UndirectedGraph graph) {
        this.graph = graph;
    }

    public boolean hasPath(int src, int dest, Set<Integer> visited) {
        // base case
        if (src == dest) return true;

        // mark current node as visited
        visited.add(src);

        // explore neighbors
        for (int neighbor : graph.getNeighbors(src)) {
            if (!visited.contains(neighbor)) {
                boolean found = hasPath(neighbor, dest, visited);
                if (found) return true;
            }
        }
        return false;
    }
}

/*

void dfs(int node, Set<Integer> visited) {
    visited.add(node);

    for (int neighbor : graph.get(node).keySet()) {
        if (!visited.contains(neighbor)) {
            dfs(neighbor, visited);
        }
    }
}

dfs advance
void allPaths(int src, int dest, List<Integer> path, Set<Integer> visited) {
    path.add(src);
    visited.add(src);

    if (src == dest) {
        System.out.println(path);
    } else {
        for (int neighbor : graph.get(src).keySet()) {
            if (!visited.contains(neighbor)) {
                allPaths(neighbor, dest, path, visited);
            }
        }
    }

    path.remove(path.size() - 1); // backtrack
    visited.remove(src);
}
 */
