package graph.basic.undirectedgraph.weighted;

public class UndirectedWeightedGraphImplementation {
    public static void main(String[] args) {
        UndirectedGraph g = new UndirectedGraph(7);

        g.addEdge(1, 2, 5);
        g.addEdge(1, 3, 10);
        g.addEdge(2, 4, 3);

        g.printGraph();
    }
}
