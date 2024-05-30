package blatt2;
import org.apache.commons.cli.*;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static blatt2.Aufgabe1.*;

public class Aufgabe3 {
    protected Aufgabe1 graph;  //original input graph
    protected double sumOfEdgeWeights;
    protected HashMap<Integer,City> mstCities;
    protected Aufgabe2 dfs;
    private StringBuilder backtracking;

    // Union-Find data structure
    private int[] parent;
    private int[] rank;
    private Map<Integer, Integer> cityIDToIndex; // Maps city IDs to Union-Find indices
    private Map<Integer, Integer> indexToCityID; // Maps Union-Find indices to city IDs


    public Aufgabe3(Aufgabe1 graph) {
        this.graph = graph;
        sumOfEdgeWeights = 0;
        mstCities = new HashMap<>();

        // Initialize Union-Find structure
        cityIDToIndex = new HashMap<>();
        indexToCityID = new HashMap<>();
        int n = graph.cities.size();
        parent = new int[n];
        rank = new int[n];
        int index = 0;

        for (City c: graph.cities.values()) {
            //create a new graph with cities without any edges to initialize the mst
            mstCities.put(c.getId(),c.cloneCity());

            //fill the Union-data data structures
            cityIDToIndex.put(c.getId(), index);
            indexToCityID.put(index, c.getId());
            parent[index] = index;
            rank[index] = 0;
            index++;
        }

        dfs = new Aufgabe2(new Aufgabe1(mstCities), false);
        backtracking = new StringBuilder();
    }

    public void kruskal() {
        /** Calculate the minimum spanning tree of the given graph using Kruskal's algorithm **/

        ArrayList<Edge> allEdges = new ArrayList<>();
        for (City c : graph.cities.values()) {
            allEdges.addAll(c.edges);
        }
        sortEdges(allEdges);

        int mstEdgeSize = 0;
        for (Edge e: allEdges) {
            if (mstEdgeSize == mstCities.size()-1) { //finish when the number of edges in the MST is equal to the number of cities - 1
                //System.out.println("MST complete");
                break;
            }

            if (!isCycle(e)) {
                //Add edges to cities at two ends
                mstCities.get(e.getStartID()).edges.add(e);
                mstCities.get(e.getEndID()).edges.add(new Edge(e.getEndID(),e.getStartID(),e.getDistance()));
                //backtracking
                backtracking.append(e.getStartID() + "\t" + e.getEndID() + "\n");
                //increase sum of the edge weights
                sumOfEdgeWeights += e.getDistance();
                mstEdgeSize++;
            }
        }

        if ((mstEdgeSize+1) != mstCities.size()) {
            throw new RuntimeException("Something is wrong with the creation of the mst!");
        }


    }

    /**
     *Another algorithm to check for cycles, which uses the DFS algorithm from Aufgabe2
     * not used, because of big runtime
     */
    //public boolean isCycle(Edge e) {
    //    /**
    //     * Check if adding the edge e to the MST would create a cycle
    //     * Idea: Run DFS on the current graph. If we can go from startCity to endCity of e, then there has been a connection between them.
    //     * By adding e we would create a cycle
    //     */
//
    //    dfs.resetToBeVisited();
    //    dfs.depthFirstSearch(e.getStartID());
//
    //    String[] remainingCities = dfs.getToBeVisited().split(" ");
    //    for (String c: remainingCities) {
    //        if (c.equals(String.valueOf(e.getEndID()))) {
    //            return false;
    //        }
    //    }
    //    return true;
    //}

    /**
     * Implementation of the Union-Find algorithm to check for loops with much better runtime
     * than the usage of DFS algorithm
     */
    public boolean isCycle(Edge e){
        //get the index, associated to the city ID
        int startCityIndex = cityIDToIndex.get(e.getStartID());
        int endCityIndex = cityIDToIndex.get(e.getEndID());

        //get the root of the
        int rootStart = find(startCityIndex);
        int rootEnd = find(endCityIndex);

        if(rootStart == rootEnd){
            return true;
        } else {
            //the roots are not equal, so we combine the 2 trees, because of the new edge
            union(rootStart, rootEnd);
            return false;
        }
    }

    private int find(int x){
        if(parent[x] != x){
            parent[x] = find(parent[x]); //Path compression
        }
        return parent[x];
    }

    private void union(int x, int y){
        //get the roots of the 2 elements
        int rootX = find(x);
        int rootY = find(y);

        if(rootX != rootY){
            if(rank[rootX] > rank[rootY]){
                parent[rootY] = rootX;
            } else if(rank[rootX] < rank[rootY]){
                parent[rootX] = rootY;
            } else {
                parent[rootY] = rootX;
                rank[rootX]++;
            }
        }
    }

    public void writeMST(String outputFile) {
        try(FileWriter writer = new FileWriter(outputFile)) {
            writer.write(backtracking.toString());
        } catch (IOException e) {
            throw new RuntimeException("Error writing to file: " + outputFile);
        }
    }

    public static void main(String[] args) {
        Options options = new Options();
        options.addOption("f", "file", true, "input tsv file");
        options.addOption("o", "output", true, "output tsv file");
        CommandLineParser parser = new BasicParser();

        String filename;
        String outputFilename;

        try {
            CommandLine cmd = parser.parse(options, args);
            if (cmd.hasOption("f")) {
                filename = cmd.getOptionValue("f");
            }
            else {
                //Antoaneta file path:
                //filename = "C:/Users/antoa/IdeaProjects/AlgoBio1/AlgoBio1/src/blatt2/cities.250.tsv";
                filename = "src/blatt2/cities.250.tsv";
            }

            if (cmd.hasOption("o")) {
                outputFilename = cmd.getOptionValue("o");
            }
            else {
                outputFilename = "C:/Users/antoa/IdeaProjects/AlgoBio1/AlgoBio1/src/blatt2/cities.250.mst.edgelist";
                //outputFilename = "src/blatt2/cities.250.mst.edgelist";
            }
        } catch (ParseException e) {
            System.out.println("Error parsing command line arguments");
            return;
        }

        Aufgabe1 a1 = new Aufgabe1(filename);
        Aufgabe3 a3 = new Aufgabe3(a1);
        long startTime3 = System.nanoTime();
        a3.kruskal();
        long endTime3 = System.nanoTime();
        System.out.println("done creating mst with " + (a3.mstCities.size()-1) + " egdes in " + (endTime3 - startTime3) / 1000000 + " ms");
        System.out.println("sum of edge weights: " + a3.sumOfEdgeWeights);
        a3.writeMST(outputFilename);
    }
}
