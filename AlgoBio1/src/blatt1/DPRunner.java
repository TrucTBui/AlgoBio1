package blatt1;

import java.util.HashSet;

public class DPRunner {
    public static void main(String[] args) {
        int[] sequence = {5, -2, 5, -2, 1, -9, 12, -2, 24, -5, 13, -12, 3, -13, 5, -3, 2, -1, 2};

        MSS seq = new MSS(sequence);
        System.out.print("Eingabe: [");
        for (int i = 0; i < sequence.length; i++) {
            System.out.print(sequence[i]);
            if (i < sequence.length - 1) {
                System.out.print(", ");
            }
        }
        System.out.println("]");
        long startTime = System.nanoTime();
        HashSet<int[]> result = seq.DynamicProgrammingMSS();
        long endTime   = System.nanoTime();
        long totalTime = endTime - startTime;

        System.out.print("Ausgabe: ");
        for (int[] r : result) {
            System.out.println("[" + r[0] + ", " + r[1] + "] mit Score " + r[2]);
        }

        System.out.println("Laufzeit: " + Math.round(totalTime*0.001) + " μs");
    }

}
