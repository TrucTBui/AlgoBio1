package blatt2;
import org.apache.commons.cli.*;
import java.util.ArrayList;

import static blatt2.Aufgabe1.*;

public class Aufgabe3 {
    private Aufgabe1 graph;
    private double sumOfEdgeWeights;
    private ArrayList<Edge> mstEdges;
    private int totalEdges;
    private StringBuilder backtracking;

    public Aufgabe3(Aufgabe1 graph) {
        this.graph = graph;
        sumOfEdgeWeights = 0;
        mstEdges = new ArrayList<>();
        totalEdges = graph.cities.size() - 1;
        backtracking = new StringBuilder();
    }

    public void kruskal() {
        /**
         * Calculate the minimum spanning tree of the given graph using Kruskal's algorithm
         */
        sortEdges(graph.edges);

        for (Edge e: graph.edges) {
            if (mstEdges.size() == totalEdges) { //finish when the number of edges in the MST is equal to the number of cities - 1
                //System.out.println("MST complete");
                break;
            }

            if (!isCycle(e)) {
                mstEdges.add(e);
                backtracking.append(e.getStartID() + "\t" + e.getEndID() + "\n");
                sumOfEdgeWeights += e.getDistance();
            }
        }
    }

    public boolean isCycle(Edge e) {
        /**
         * Check if adding the edge e to the MST would create a cycle
         */
        int startID = e.getStartID();
        int endID = e.getEndID();

        //TODO: implement this method
        return false;
    }

    public void printMST() {
        System.out.println(backtracking.toString());
    }

    public static void main(String[] args) {
        Options options = new Options();
        options.addOption("f", "file", true, "input tsv file");
        CommandLineParser parser = new BasicParser();

        String filename;

        try {
            CommandLine cmd = parser.parse(options, args);
            if (cmd.hasOption("f")) {
                filename = cmd.getOptionValue("f");
            }
            else {
                filename = "src/blatt2/cities.250.tsv";
            }
        } catch (ParseException e) {
            System.out.println("Error parsing command line arguments");
            return;
        }

        Aufgabe1 a1 = new Aufgabe1("src/blatt2/demoCities.tsv");
        Aufgabe3 a3 = new Aufgabe3(a1);
        long startTime3 = System.nanoTime();
        a3.kruskal();
        long endTime3 = System.nanoTime();
        System.out.println("done creating mst with " + a3.totalEdges + " egdes in " + (endTime3 - startTime3) / 1000000 + " ms");
        System.out.println("sum of edge weights: " + a3.sumOfEdgeWeights);
        a3.printMST();
    }
}
