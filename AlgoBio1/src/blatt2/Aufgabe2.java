package blatt2;

import java.util.HashSet;

public class Aufgabe2 {

    private Aufgabe1 graph;
    private HashSet<Integer> toBeVisited;
    private int firstID;
    private int step;

    public Aufgabe2(Aufgabe1 graph) {
        this.graph = graph;
        toBeVisited = new HashSet<>();
        firstID = Integer.MAX_VALUE;
        step =0;

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
        for (Aufgabe1.Edge e : graph.cities.get(currentID).edges) {
            int nextID = e.getId2(); //get the id of the connecting city
            if (toBeVisited.contains(nextID)) {
                System.out.println("from " + currentID + " to " + nextID + " with distance " + e.getDistance());
                depthFirstSearch(nextID);
            }
        }

    }

    public static void main(String[] args) {
        Aufgabe1 graph = new Aufgabe1("src/blatt2/cities.250.tsv");
        Aufgabe2 dfs = new Aufgabe2(graph);
        dfs.depthFirstSearch(dfs.firstID);
        System.out.println("traversed graph in " + dfs.step + " steps");
    }
}
