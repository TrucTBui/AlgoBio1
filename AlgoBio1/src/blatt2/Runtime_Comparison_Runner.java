package blatt2;

public class Runtime_Comparison_Runner {
    public static void main(String[] args) {
        String[] fileNames = {"cities.50", "cities.100", "cities.150", "cities.200", "cities.250"};

        for(int i = 0; i < fileNames.length; i++){
            //for Antoaneta
            //String filename = "C:/Users/antoa/IdeaProjects/AlgoBio1/AlgoBio1/src/blatt2/" + fileNames[i] + ".tsv";
            //String outputFilenameAufgabe2 = "C:/Users/antoa/IdeaProjects/AlgoBio1/AlgoBio1/src/blatt2/" + fileNames[i] + ".bfs.tsv";
            //String outputFilenameAufgabe3 = "C:/Users/antoa/IdeaProjects/AlgoBio1/AlgoBio1/src/blatt2/" + fileNames[i] + ".mst.edgelist";

            String filename = "src/blatt2/" + fileNames[i] + ".tsv";
            String outputFilenameAufgabe2 = "src/blatt2/" + fileNames[i] + ".bfs.tsv";
            String outputFilenameAufgabe3 = "src/blatt2//" + fileNames[i] + ".mst.edgelist";

            //Aufgabe1:
            Aufgabe1 a1 = new Aufgabe1();
            long startTime1 = System.nanoTime();
            a1.cities = a1.readTSV(filename);
            long endTime1 = System.nanoTime();
            System.out.println("read " +(i+1)*50 +  " cities in " + (endTime1 - startTime1) / 1000000 + " ms");

            long startTime2 = System.nanoTime();
            a1.createEdges();
            long endTime2 = System.nanoTime();

            int numEdges = 0;
            for (Aufgabe1.City c : a1.cities.values()) {
                numEdges += c.edges.size();
            }

            System.out.println("created " + numEdges + " edges in " + (endTime2 - startTime2) / 1000000 + " ms");

            //Aufgabe2:
            Aufgabe1 graph = new Aufgabe1(filename);
            Aufgabe2 dfs = new Aufgabe2(graph, true);
            long startTime = System.nanoTime();
            dfs.depthFirstSearch(dfs.firstID);
            long endTime = System.nanoTime();
            dfs.writeOutput2TSV(outputFilenameAufgabe2);
            System.out.println("traversed graph in " + dfs.step + " steps in " + (endTime - startTime)/1000000 + " ms");

            //Aufgabe3:
            Aufgabe1 a1_3 = new Aufgabe1(filename);
            Aufgabe3 a3 = new Aufgabe3(a1_3);
            long startTime3 = System.nanoTime();
            a3.kruskal();
            long endTime3 = System.nanoTime();
            System.out.println("done creating mst with " + (a3.mstCities.size()-1) + " egdes in " + (endTime3 - startTime3) / 1000000 + " ms");
            System.out.println("sum of edge weights: " + a3.sumOfEdgeWeights);
            a3.writeMST(outputFilenameAufgabe3);

            System.out.println();
            System.out.println("End of iteration " + (i+1));
            System.out.println();
        }
    }
}
