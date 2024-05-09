package blatt1;

import java.util.HashSet;

public class Runtime {
    public static void main(String[] args) {
        //could be developed further with a variable n how many iterations
        int iterations = 30;

        int[] sequence = {5, -2, 5, -2, 1, -9, 12, -2, 24, -5, 13, -12, 3, -13, 5, -3, 2, -1, 2};
        MSS seq = new MSS(sequence);

        // Naive algorithm
        System.out.println("Find all MSS using naive algorithm: ");
        double[] naiveRuntime = new double[iterations];
        for(int i = 0; i < iterations; i++){
            long startTime = System.nanoTime();
            HashSet<int[]> result = seq.NaiveMSS();
            long endTime   = System.nanoTime();
            long totalTime = endTime - startTime;
            naiveRuntime[i] = totalTime*0.001;
        }
        long sumNaive = 0;
        for(double time: naiveRuntime){
            sumNaive += time;
        }
        System.out.println("Average runtime for naive: " + Math.round(sumNaive/(iterations*1.0)));
        System.out.println();


        //Naive recursive algorithm:
        System.out.println("Find all MSS using naive recursive algorithm: ");
        double[] naiveRecursiveRuntime = new double[iterations];
        for(int i = 0; i < iterations; i++){
            long startTime = System.nanoTime();
            HashSet<int[]> result = seq.NaiveRecursiveMSS();
            long endTime   = System.nanoTime();
            long totalTime = endTime - startTime;
            naiveRecursiveRuntime[i] = totalTime*0.001;
        }
        long sumNaiveRecursive = 0;
        for(double time: naiveRecursiveRuntime){
            sumNaiveRecursive += time;
        }
        System.out.println("Average runtime for naive recursive: " + Math.round(sumNaiveRecursive/(1.0*iterations)));
        System.out.println();

        //Dynamic Programming algorithm:
        System.out.println("Find all MSS using dynamic programming: ");
        double[] DP = new double[iterations];
        for(int i = 0; i < iterations; i++){
            long startTime = System.nanoTime();
            HashSet<int[]> result = seq.DynamicProgrammingMSS();
            long endTime   = System.nanoTime();
            long totalTime = endTime - startTime;
            DP[i] = totalTime*0.001;
        }
        long sumDP = 0;
        for(double time: DP){
            sumDP += time;
        }
        System.out.println("Average runtime for dynamic programming: " + Math.round(sumDP/(iterations*1.0)));
        System.out.println();


        //Divide-and-Conquer algorithm:
        System.out.println("Find MSS using divide-and-conquer: ");
        double[] DC = new double[iterations];
        for(int i = 0; i < iterations; i++){
            long startTime = System.nanoTime();
            int[] result = seq.DivideAndConquerMSS();
            long endTime   = System.nanoTime();
            long totalTime = endTime - startTime;
            DC[i] = totalTime*0.001;
        }
        long sumDC = 0;
        for(double time: DC){
            sumDC += time;
        }
        System.out.println("Average runtime for divide-and-conquer: " + Math.round(sumDC/(iterations*1.0)));
        System.out.println();


        System.out.println("Find MSS using the optimal algorithm: ");
        double[] optimal = new double[iterations];
        for(int i = 0; i < iterations; i++){
            MSS optimalSeq = new MSS(sequence);
            long startTime = System.nanoTime();
            int[] result = optimalSeq.OptimalMSS();
            long endTime   = System.nanoTime();
            long totalTime = endTime - startTime;
            optimal[i] = totalTime*0.001;
        }
        long sumOptimal = 0;
        for(double time: optimal){
            sumOptimal += time;
        }
        System.out.println("Average runtime for optimal: " + Math.round(sumOptimal/(iterations*1.0)));
        System.out.println();
    }
}
