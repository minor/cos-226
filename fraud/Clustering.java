import edu.princeton.cs.algs4.CC;
import edu.princeton.cs.algs4.Edge;
import edu.princeton.cs.algs4.EdgeWeightedGraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.KruskalMST;
import edu.princeton.cs.algs4.Point2D;

public class Clustering {
    private int[] clusters; // array for the clusters
    private int k; // int to hold number of clusters

    // run the clustering algorithm and create the clusters
    public Clustering(Point2D[] locations, int k) {
        if (locations == null || k < 1 || k > locations.length
                || containsNull(locations)) {
            throw new IllegalArgumentException("Invalid input");
        }

        int m = locations.length;
        this.k = k;

        // create complete edge weighted graph
        EdgeWeightedGraph graph = createWeightedGraph(locations);

        // find mst
        KruskalMST mst = new KruskalMST(graph);

        // create cluster graph with m-k edges of lowest weight from MST
        EdgeWeightedGraph clusterGraph = createClusterGraph(mst, m, k);

        // Determine clusters using Connected Components
        CC cc = new CC(clusterGraph);
        clusters = new int[m];
        for (int i = 0; i < m; i++) {
            clusters[i] = cc.id(i);
        }
    }

    // private method to create the edge weighted graph
    private EdgeWeightedGraph createWeightedGraph(Point2D[] locations) {
        int m = locations.length;
        // create empty edge weighted graph with m vertices
        EdgeWeightedGraph graph = new EdgeWeightedGraph(m);

        // find Euclidean distance between each location and add edges
        for (int i = 0; i < m; i++) {
            for (int j = i + 1; j < m; j++) {
                double distance = locations[i].distanceTo(locations[j]);
                Edge edge = new Edge(i, j, distance);
                graph.addEdge(edge);
            }
        }

        return graph;
    }

    // private method to create the cluster graph
    private EdgeWeightedGraph createClusterGraph(KruskalMST mst, int m,
                                                 int kVal) {
        EdgeWeightedGraph clusterGraph = new EdgeWeightedGraph(m);
        int count = 0;

        // iterate through the edges in the MST in ascending order of weight
        for (Edge edge : mst.edges()) {
            if (count >= m - kVal) {
                break;
            }
            clusterGraph.addEdge(edge);
            count++;
        }
        return clusterGraph;
    }

    // private method to check if array contains nulls
    private boolean containsNull(Object[] array) {
        for (Object obj : array) {
            if (obj == null) {
                return true;
            }
        }
        return false;
    }

    // return the cluster of the ith point
    public int clusterOf(int i) {
        if (i < 0 || i >= clusters.length) {
            throw new IllegalArgumentException("Invalid argument.");
        }
        return clusters[i];
    }

    // use the clusters to reduce the dimensions of an input
    public double[] reduceDimensions(double[] input) {
        if (input == null || input.length != clusters.length) {
            throw new IllegalArgumentException("Invalid input.");
        }

        double[] reduced = new double[k];
        for (int i = 0; i < input.length; i++) {
            reduced[clusters[i]] += input[i];
        }
        return reduced;
    }

    // unit testing (required)
    public static void main(String[] args) {
        In in = new In("princeton_locations.txt");
        int numLocations = in.readInt();
        Point2D[] locations = new Point2D[numLocations];
        for (int i = 0; i < numLocations; i++) {
            double x = in.readDouble();
            double y = in.readDouble();
            locations[i] = new Point2D(x, y);
        }

        // create a new clustering with k=5
        Clustering clustering = new Clustering(locations, 5);

        // check if each location is in the right cluster
        for (int i = 0; i < locations.length; i++) {
            System.out.println(clustering.clusterOf(i));
        }

        // check reduce dimensions
        double[] sampleInput = new double[locations.length];
        // test data
        for (int i = 0; i < locations.length; i++) {
            sampleInput[i] = i * 2.5; // arbitrary test data
        }

        // reduce dimensions using clusters
        double[] reduced = clustering.reduceDimensions(sampleInput);

        // display original and reduced dimensions
        System.out.println("Original dimensions: ");
        for (double value : sampleInput) {
            System.out.print(value + " ");
        }
        System.out.println("\nReduced dimensions: ");
        for (double value : reduced) {
            System.out.print(value + " ");
        }
    }
}
