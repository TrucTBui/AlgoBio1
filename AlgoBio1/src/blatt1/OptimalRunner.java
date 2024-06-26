package blatt1;

public class OptimalRunner {
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
        int [] result = seq.OptimalMSS();
        long endTime   = System.nanoTime();
        long totalTime = endTime - startTime;

        System.out.print("Ausgabe: ");

        System.out.println("[" + result[0] + ", " + result[1] + "] mit Score " + result[2]);

        System.out.println("Laufzeit: " + Math.round(totalTime*0.001) + " μs");
    }
}


