package blatt3;
import blatt2.*;
import static blatt2.Aufgabe1.*;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class TSP {
    Aufgabe1 graph;
    Aufgabe2 dfs;
    ArrayList<Edge> TSPWalk;

    public TSP(String inputMST) {
        TSPWalk = new ArrayList<>();
        graph = readMST(inputMST);
        dfs = new Aufgabe2(graph, false);
    }

    public Aufgabe1 readMST(String filename) {
        Aufgabe1 graph = new Aufgabe1();
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line = br.readLine();
            while (line != null) {
                String[] fields = line.split("\t");
                int id1 = Integer.parseInt(fields[0]);
                int id2 = Integer.parseInt(fields[1]);
                double distance = Double.parseDouble(fields[2]);

                if (!graph.hasCity(id1)){
                    City c1 = new City(id1);
                    c1.addEdge(new Edge(id1, id2, distance));
                    graph.addCity(c1);
                }
                else { //city already in graph, only update edges
                    graph.getCities().get(id1).addEdge(new Edge(id1, id2, distance));
                }

                if (!graph.hasCity(id2)){
                    City c2 = new City(id2);
                    c2.addEdge(new Edge(id2, id1, distance));
                    graph.addCity(c2);
                }
                else { //city already in graph, only update edges
                    graph.getCities().get(id2).addEdge(new Edge(id2, id1, distance));
                }

                line = br.readLine();
            }
        }
        catch (FileNotFoundException e) {
            System.out.println("File not found: " + filename);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return graph;
    }

    public void approximate() {
        /**
         * Create a round tour from the dfs walk, starting at the city with the smallest id
         * Idea: change every edge, in which the startingCity has already been the start once
         */
        dfs.depthFirstSearch(dfs.getFirstID()); //dfs can start anywhere so we start at the city with the smallest id

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
                //TODO: idk how to get the distance since this new edge does not exists in the input edge list
                TSPWalk.add(new Edge(currentCity,to,0));
                currentCity = to;
            }
        }
        //Add the last edge that connects the last and the first cities
        //TODO: idk how to get the distance since this new edge does not exists in the input edge list
        TSPWalk.add(new Edge(currentCity,startingCities.get(0),0));
    }

    public static void main(String[] args) {
        TSP tour = new TSP("src/blatt3/cities.250.mst.edgelist");
        tour.approximate();
        for (Edge e: tour.TSPWalk) {
            System.out.println(e.getStartID()+"\t"+e.getEndID()+"\t"+e.getDistance());
        }
    }


}
