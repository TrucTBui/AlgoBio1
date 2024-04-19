package blatt1;

import java.util.HashSet;

public class MSSOptimal {
    private int[] sequence;

    public MSSOptimal(int[] sequence) {
        this.sequence = sequence;
    }

    public HashSet<int[]> calMSS(){
        /**
        * Calculate all maximum sum subsequences of the given sequence, including overlaps.
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
}
