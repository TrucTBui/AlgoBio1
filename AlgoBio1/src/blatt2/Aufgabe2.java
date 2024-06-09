package blatt2;

import org.apache.commons.cli.*;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;

import static blatt2.Aufgabe1.*;

public class Aufgabe2 {

    private Aufgabe1 graph;
    private HashSet<Integer> toBeVisited;
    protected int firstID;
    protected int step;
    private StringBuilder backtracking;
    protected ArrayList<Edge> walk; //for exercise 1 blatt 3

    public int getFirstID(){
        return firstID;
    }

    public ArrayList<Edge> getWalk(){
        return walk;
    }

    public String getToBeVisited() { //safe way to display without the risk of changing the HashSet
        StringBuilder sb = new StringBuilder();
        for (Integer i : toBeVisited) {
            sb.append(i + " ");
        }
        return sb.toString();
    }

    public void resetToBeVisited() { //only for exercise 3
        toBeVisited.clear();
        for (int id : graph.cities.keySet()) {
            Aufgabe1.City city = graph.cities.get(id);
            sortEdges(city.edges); //sort the edges by distance
            toBeVisited.add(id);
        }
    }

    public Aufgabe2(Aufgabe1 graph, boolean filter) {
        this.graph = graph;
        toBeVisited = new HashSet<>();
        firstID = Integer.MAX_VALUE;
        step = 0;
        backtracking = new StringBuilder();
        walk = new ArrayList<>();

        for (int id : graph.cities.keySet()) {
            Aufgabe1.City city = graph.cities.get(id);
            sortEdges(city.edges); //sort the edges by distance
            if (filter) {  //for the second exercise only
                removeEdgesWithDistanceGreaterThan(city.edges, 20.24); //remove all edges with distance greater than 20.24
            }

            toBeVisited.add(id);  //create a set of all cities. DFS will end when this set is empty

            if (id < firstID) firstID = id; //get the smallest id
        }
    }

    public void depthFirstSearch(int currentID) {
        toBeVisited.remove(currentID);
        step++;

        if (toBeVisited.isEmpty()) {
            //System.out.println("All cities have been visited");
            return; //if all cities have been visited, return
        }

        //begin with the edge with the smallest distance. The edges arraylist is already sorted
        Aufgabe1.City currentCity = graph.cities.get(currentID);
        for (int i = 0; i < currentCity.edges.size(); i++) {
            if (toBeVisited.isEmpty()) {
                //System.out.println("Done");
                return;
            }
            Edge e = currentCity.edges.get(i);
            int nextID = e.getEndID(); //get the id of the connecting city
            if (toBeVisited.contains(nextID)) {
                walk.add(e);
                backtracking.append(currentID + "\t" + nextID + "\t" + String.format("%.2f",e.getDistance()) + "\n");
                depthFirstSearch(nextID);
            }
        }
    }

    public void writeOutput2TSV(String filename) {
        /**
         * Write the output of the depthFirstSearch to a tsv file
         */
        try {
            FileWriter writer = new FileWriter(filename);
            writer.write("id (from)\tid(to)\tdistance\n");
            writer.write(backtracking.toString());
            writer.close();
        } catch (IOException e) {
            System.out.println("Error when writing to " + filename + "file");;
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
                //outputFilename = "C:/Users/antoa/IdeaProjects/AlgoBio1/AlgoBio1/src/blatt2/cities.250.bfs.tsv";
                outputFilename = "src/blatt2/cities.250.bfs.tsv";
            }
        } catch (ParseException e) {
            System.out.println("Error parsing command line arguments");
            return;
        }

        Aufgabe1 graph = new Aufgabe1(filename);
        graph.createEdges();
        Aufgabe2 dfs = new Aufgabe2(graph, true);
        long startTime = System.nanoTime();
        dfs.depthFirstSearch(dfs.firstID);
        long endTime = System.nanoTime();
        dfs.writeOutput2TSV(outputFilename);
        System.out.println("traversed graph in " + dfs.step + " steps in " + (endTime - startTime)/1000000 + " ms");
    }
}
