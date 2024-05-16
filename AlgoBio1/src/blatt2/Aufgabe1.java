package blatt2;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import org.apache.commons.cli.*;

public class Aufgabe1 {
    HashMap<Integer,City> cities; //Nodes
    ArrayList<Edge> edges; //Edges

    public Aufgabe1(String tsvFile) {
        cities = readTSV(tsvFile);
        edges = new ArrayList<>();
        createEdges();
    }

    public Aufgabe1() {
        cities = new HashMap<>();
        edges = new ArrayList<>();
    }

    // readTSV reads a tsv file and returns a hashmap with the cities and its id as key
    public HashMap<Integer,City> readTSV(String filename) {
        HashMap<Integer,City> map = new HashMap<>();
        try {
            BufferedReader br = new BufferedReader(new FileReader(filename));
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split("\t");
                int id = Integer.parseInt(parts[0]);
                String name = parts[1];
                double phi = Double.parseDouble(parts[2]);
                double lambda = Double.parseDouble(parts[3]);
                map.put(id, new City(id, name, phi, lambda));
            }
            br.close();
        } catch (IOException e) {
            System.out.println("Error reading file " + filename);
            return null;
        }
        return map;
    }

    public static double distance(City city1, City city2) {
        return Math.sqrt(Math.pow(city1.phi - city2.phi, 2) + Math.pow(city1.lambda - city2.lambda, 2));
    }

    public void createEdges() {
        for (int startID : cities.keySet()) {
            for (int endID : cities.keySet()) {
                if (startID != endID) {
                    City startCity = cities.get(startID);
                    City endCity = cities.get(endID);
                    double distance = distance(startCity, endCity);
                    startCity.edges.add(new Edge(startID, endID, distance)); //the first city of the edge is itself
                    edges.add(new Edge(startID, endID, distance)); //add the edge to the list of all edges
                }
            }
        }
    }

    public static void sortEdges(ArrayList<Edge> edges) {
        edges.sort((e1, e2) -> Double.compare(e1.distance, e2.distance));
    }

    public static void removeEdgesWithDistanceGreaterThan(ArrayList<Edge> edges, double distance) {
        edges.removeIf(e -> e.distance >= distance);
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


        Aufgabe1 a1 = new Aufgabe1();
        long startTime1 = System.nanoTime();
        a1.cities = a1.readTSV(filename);
        long endTime1 = System.nanoTime();
        System.out.println("read 250 cities in " + (endTime1 - startTime1) / 1000000 + " ms");

        long startTime2 = System.nanoTime();
        a1.createEdges();
        long endTime2 = System.nanoTime();

        int numEdges = 0;
        for (City c : a1.cities.values()) {
            numEdges += c.edges.size();
        }

        System.out.println("created " + numEdges + " edges in " + (endTime2 - startTime2) / 1000000 + " ms");
    }

    public static class City {
        private int id;
        private String name;
        private double phi;
        private double lambda;
        protected ArrayList<Edge> edges;

        public City(int id, String name, double phi, double lambda) {
            this.id = id;
            this.name = name;
            this.phi = phi;
            this.lambda = lambda;
            this.edges = new ArrayList<>();
        }

        public int getId() {
            return id;
        }
    }

    public static class Edge {
        private int startID;
        private int endID;
        private double distance;

        public Edge(int startID, int id2, double distance) {
            this.startID = startID;
            this.endID = id2;
            this.distance = distance;
        }

        public int getStartID() {
            return startID;
        }

        public int getEndID() {
            return endID;
        }
        public double getDistance() {
            return distance;
        }
    }

}
