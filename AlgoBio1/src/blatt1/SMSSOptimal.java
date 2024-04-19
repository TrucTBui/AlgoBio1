//Aufgabe 2
package blatt1;

import java.util.HashSet;

public class SMSSOptimal {
    private int[] sequence;

    public SMSSOptimal(int[] sequence) {
        this.sequence = sequence;
    }

    public HashSet<int[]> calSMSS(){
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

}
