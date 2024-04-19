//Aufgabe 2
package blatt1;

import java.util.HashSet;

public class MSS {
    private int[] sequence;

    public MSS(int[] sequence) {
        this.sequence = sequence;
    }

    public HashSet<int[]> allMSS(){
        /**
        * Calculate all maximum sum subsequences of the given sequence using the optimal solution,
         * including overlaps.
         */
        HashSet<int[]> mss = new HashSet<>();
        int max = 0;
        int rmax = 0;
        int rstart = 0;

        for (int i = 0; i < sequence.length; i++) {
            if (rmax >0) {
                rmax += sequence[i];
            }
            else {
                rmax = sequence[i];
                rstart = i;
            }

            if (rmax > max) {
                max = rmax;
                mss.clear();
                mss.add(new int[]{rstart, i, rmax});
            }
            else if (rmax == max) {
                mss.add(new int[]{rstart, i, rmax});
            }
        }
        return mss;
    }

    public HashSet<int[]> calcSMSS(){
        /**
         * Calculate shortest maximum sum subsequences of the given sequence.
         */
        HashSet<int[]> smss = new HashSet<>();
        int max = 0;
        int rmax = 0;
        int rstart = 0;
        int minlength = 0;

        for (int i = 0; i < sequence.length; i++) {
            if (rmax >0) {
                rmax += sequence[i];
            }
            else {
                rmax = sequence[i];
                rstart = i;
            }

            if (rmax > max) {
                max = rmax;
                minlength = i - rstart + 1;
                smss.clear();
                smss.add(new int[]{rstart, i, rmax});
            }
            else if (rmax == max) {
                int length = i - rstart + 1;
                if (length < minlength) {
                    minlength = length;
                    smss.clear();
                    smss.add(new int[]{rstart, i, rmax});
                }
                else if (length == minlength) {
                    smss.add(new int[]{rstart, i, rmax});
                }
            }
        }
        return smss;
    }

    public HashSet<int[]> NaiveMSS(){
        //TODO
        return null;
    }

    public HashSet<int[]> NaiveRecursiveMSS(){
        //TODO
        return null;
    }

    public HashSet<int[]> DynamicProgrammingMSS(){
        //TODO
        return null;
    }

    public HashSet<int[]> DivideAndConquerMSS(){
        //TODO
        return null;
    }

    public HashSet<int[]> OptimalMSS(){
        //TODO
        return null;
    }
}
