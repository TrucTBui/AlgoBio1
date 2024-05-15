package blatt2;

import org.apache.commons.cli.*;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;

public class Aufgabe2 {

    private Aufgabe1 graph;
    private HashSet<Integer> toBeVisited;
    private int firstID;
    private int step;
    private StringBuilder backtracking;

    public Aufgabe2(Aufgabe1 graph) {
        this.graph = graph;
        toBeVisited = new HashSet<>();
        firstID = Integer.MAX_VALUE;
        step =0;
        backtracking = new StringBuilder();

        for (int id : graph.cities.keySet()) {
            Aufgabe1.City city = graph.cities.get(id);
            city.sortEdges(); //sort the edges by distance
            city.removeEdgesWithDistanceGreaterThan(20.24); //remove all edges with distance greater than 20.24

            if (!city.edges.isEmpty()) {
                toBeVisited.add(id);  //create a set of all cities. DFS will end when this set is empty
            }
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

        //begin with the egde with the smallest distance. The edges arraylist is already sorted
        Aufgabe1.City currentCity = graph.cities.get(currentID);
        for (int i = 0; i < currentCity.edges.size(); i++) {
            if (toBeVisited.isEmpty()) {
                //System.out.println("Done");
                return;
            }
            int nextID = currentCity.edges.get(i).getId2(); //get the id of the connecting city
            if (toBeVisited.contains(nextID)) {
                backtracking.append(currentID + "\t" + nextID + "\t" + String.format("%.2f",currentCity.edges.get(i).getDistance()) + "\n");
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

        Aufgabe1 graph = new Aufgabe1(filename);
        Aufgabe2 dfs = new Aufgabe2(graph);
        long startTime = System.nanoTime();
        dfs.depthFirstSearch(dfs.firstID);
        long endTime = System.nanoTime();
        dfs.writeOutput2TSV("src/blatt2/cities.250.bfs.tsv");
        System.out.println("traversed graph in " + dfs.step + " steps in " + (endTime - startTime)/1000000 + " ms");
    }
}
