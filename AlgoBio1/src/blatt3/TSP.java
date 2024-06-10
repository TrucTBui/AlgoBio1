/**
 * Solving the traveling salesman problem approximately using the minimal spanning tree
 */
package blatt3;
import blatt2.*;
import org.apache.commons.cli.*;

import static blatt2.Aufgabe1.*;

import java.io.*;
import java.util.ArrayList;

public class TSP {
    Aufgabe1 mst;
    Aufgabe2 dfs;
    ArrayList<Edge> TSPWalk;

    public TSP(String inputMST, String inputGraph) {
        TSPWalk = new ArrayList<>();
        mst = new Aufgabe1(inputGraph);
        loadEdgesFromMST(mst, inputMST);
        dfs = new Aufgabe2(mst, false);
    }

    public static void loadEdgesFromMST(Aufgabe1 graph, String inputMST) {
        try (BufferedReader br = new BufferedReader(new FileReader(inputMST))) {
            String line = br.readLine();
            while (line != null) {
                String[] fields = line.split("\t");
                int id1 = Integer.parseInt(fields[0]);
                int id2 = Integer.parseInt(fields[1]);
                double distance = Double.parseDouble(fields[2]);

                graph.getCities().get(id1).addEdge(new Edge(id1, id2, distance));
                graph.getCities().get(id2).addEdge(new Edge(id2, id1, distance));

                line = br.readLine();
            }
        }
        catch (FileNotFoundException e) {
            System.err.println("The input mst file path is not correct. Please use the option -m for the input mst file");
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void approximate() {
        /**
         * Create a round tour from the dfs walk, starting at the city with the smallest id
         * Idea: change every edge, in which the startingCity has already been the start once
         */
        if (mst.getCities().isEmpty()) {
            return;
        }

        dfs.depthFirstSearch(dfs.getFirstID()); //dfs can start anywhere so we start at the city with the smallest id

        if (dfs.getWalk().isEmpty()) return;

        ArrayList<Integer> startingCities = new ArrayList<>();
        int currentCity = dfs.getFirstID();

        for (Edge e: dfs.getWalk()) {
            int from = e.getStartID();
            int to = e.getEndID();
            if (!startingCities.contains(from) ) { //The city has not been visited as a starting city
                TSPWalk.add(e);
                startingCities.add(from);
                currentCity = to;
            }
            else { //change this edge, because we do not want to go back to that city
                TSPWalk.add(new Edge(currentCity,to,Aufgabe1.distance(mst.getCities().get(from), mst.getCities().get(to))));
                currentCity = to;
            }
        }
        //Add the last edge that connects the last and the first cities
        TSPWalk.add(new Edge(currentCity,startingCities.get(0),Aufgabe1.distance(mst.getCities().get(startingCities.get(0)), mst.getCities().get(currentCity))));
    }

    public void writeTSPWalk(String outputFile) {
        try (FileWriter writer = new FileWriter(outputFile)) {
            for (Edge e: TSPWalk) {
                writer.write(e.toString() + "\n");
            }
        }
        catch (IOException e) {
            System.out.println("Error when writing to " + outputFile + "file");;
        }
    }

    public static void main(String[] args) {
        Options options = new Options();
        options.addOption("f", "graph", true, "input tsv graph file");
        options.addOption("m", "mst", true, "input mst file");
        CommandLineParser parser = new BasicParser();

        String graph;
        String mst;

        try {
            CommandLine cmd = parser.parse(options, args);
            if (cmd.hasOption("f")) {
                graph = cmd.getOptionValue("f");
            }
            else {
                graph = "src/blatt2/cities.250.tsv";
            }

            if (cmd.hasOption("m")) {
                mst = cmd.getOptionValue("m");
            }
            else {
                mst = "src/blatt3/cities.250.mst.edgelist";
            }
        } catch (ParseException e) {
            System.err.println("Error parsing command line arguments");
            return;
        }

        TSP tour = new TSP(mst, graph);
        tour.approximate();
        for (Edge e: tour.TSPWalk) {
            System.out.println(e.toString());
        }
    }

}
