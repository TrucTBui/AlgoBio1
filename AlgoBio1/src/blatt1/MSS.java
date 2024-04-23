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

    /**
     * 5 versions of the MSS algorithm, from the lectures
     */
    public HashSet<int[]> NaiveMSS(){
        //a hashset to store all max scoring subsequences
        HashSet<int[]> allMSS = new HashSet<>();

        //all variables, defining one subsequence
        int max = 0;
        int sum;
        for(int i = 0; i < sequence.length; i++){
            for(int j = 1; j < sequence.length; j++){
                sum = 0;

                for(int k = i; k <= j; k++){
                    sum += sequence[k];
                }

                if(sum > max){
                    allMSS.clear(); //clear the whole HashMap, as there are subsequences with a higher sum
                    max = sum;
                    allMSS.add(new int[]{i, j, max});
                } else if (sum == max){
                    allMSS.add(new int[]{i, j, max});
                }
            }
        }

        return allMSS;
    }

    public HashSet<int[]> NaiveRecursiveMSS(){
        //a hashset to store all max scoring subsequences
        HashSet<int[]> allMSS = new HashSet<>();

        //all variables, defining one subsequence
        int max = 0;

        int sum;
        for(int i = 0; i < sequence.length; i++){
            for(int j = 1; j < sequence.length; j++){
                sum = 0;

                sum = recursiveSum(i, j);

                if(sum > max){
                    allMSS.clear(); //clear the whole HashMap, as there are subsequences with a higher sum
                    max = sum;
                    allMSS.add(new int[]{i, j, max});
                } else if (sum == max){
                    allMSS.add(new int[]{i, j, max});
                }
            }
        }

        return allMSS;
    }

    public HashSet<int[]> DynamicProgrammingMSS(){
        HashSet<int[]> allMSS = new HashSet<>();

        //build a matrix to save the sums
        int[][] matrix = new int[sequence.length][sequence.length];

        int max = 0;
        for(int i = 0; i < sequence.length; i++){
            for(int j = i; j < sequence.length; j++){

                if(j == 0){
                    matrix[i][j] = sequence[j];
                } else {
                    matrix[i][j] = matrix[i][j-1] + sequence[j];
                }

                if(matrix[i][j] > max){
                    allMSS.clear(); //clear the whole HashMap, as there are subsequences with a higher sum
                    max = matrix[i][j];
                    allMSS.add(new int[]{i, j, max});
                } else if(matrix[i][j] == max){
                    allMSS.add(new int[]{i, j, max});
                }
            }
        }

        return allMSS;
    }
/*
    public HashSet<int[]> DivideAndConquerMSS(){
        //TODO
        DCRecurse(0, sequence.length-1);
        return allMSSDC;
    }
    private int[] DCRecurse(int start, int end){
        if(start == end){
            if(this.sequence[start] > 0) return new int[]{start, start, this.sequence[start]};
            else return new int[]{start, start-1, 0};
        } else{
            int[] maxSum;

            //find the middle index for split
            int middle = ((start+end-1)/2);
            System.out.println("Middle index: " + middle);

            int[] lowerPartMaxSubsequence = DCRecurse(start, middle);
            System.out.println("Lower part: ");
            print(lowerPartMaxSubsequence);
            int[] higherPartMaxSubsequence = DCRecurse(middle+1, end);
            System.out.println("Upper part: ");
            print(higherPartMaxSubsequence);

            int middleStart = returnMaxValue(start, middle);
            int middleEnd =returnMinValue(middle+1, end);
            System.out.println("Over the middle part has indexes: " + middleStart + ", " + middleEnd);
            int middleSum = recursiveSum(middleStart, middleEnd);

            if(Math.max(middleSum, Math.max(lowerPartMaxSubsequence[2], higherPartMaxSubsequence[2])) == middleSum){
                maxSum = new int[]{middleStart, middleEnd, middleSum};
            } else if(Math.max(middleSum, Math.max(lowerPartMaxSubsequence[2], higherPartMaxSubsequence[2])) == higherPartMaxSubsequence[0]){
                maxSum =higherPartMaxSubsequence;
            } else maxSum = lowerPartMaxSubsequence;

            System.out.println("Adding: " + maxSum[0] + ", " + maxSum[1] + " with value: " + maxSum[2]);
            allMSSDC.add(maxSum);

            return maxSum;
        }
    }


 */
    public int[] DCVersion2(int l, int r) {
        if (l == r) {
            if (sequence[l] > 0) {
                return new int[]{l, l, sequence[l]};
            } else {
                return new int[]{l, l-1, 0};
            }
        } else {
            int m = (l + r - 1) / 2;
            int[] leftSum = DCVersion2(l, m);
            int[] rightSum = DCVersion2(m+1, r);
            int i = maxCrossIndexLeft(l,m);
            int j = maxCrossIndexRight(m+1,r);
            int cross = iterativeSum(i,j);
            if (leftSum[2] >= rightSum[2] && leftSum[2] >= cross) {
                return leftSum;
            } else if (rightSum[2] >= leftSum[2] && rightSum[2] >= cross) {
                return rightSum;
            } else {
                return new int[]{i, j, cross};
            }
        }
    }

    public int maxCrossIndexLeft(int l, int r) {
        int maxSum = sequence[r];
        int sum = maxSum;
        int index = r;
        for (int k = r-1; k >= l; k--) {
            sum += sequence[k];
            if (sum > maxSum) {
                maxSum = sum;
                index = k;
            }
        }
        return index;
    }

    public int maxCrossIndexRight(int l, int r) {
        int maxSum = sequence[l];
        int sum = maxSum;
        int index = l;
        for (int k = l+1; k <= r; k++) {
            sum += sequence[k];
            if (sum > maxSum) {
                maxSum = sum;
                index = k;
            }
        }
        return index;
    }



    public HashSet<int[]> OptimalMSS(){
        //TODO
        return null;
    }

    private int returnMaxValue(int start, int end){
        int maxValue = 0;
        int toRet = 0;

        int sum;
        for(int i = start; i <= end; i++){
            for(int j = i; j <= end; j++){
                sum = recursiveSum(i, j);

                if(sum >= maxValue){
                    maxValue = sum;
                    toRet = i;
                }

            }
        }
        return toRet;
    }
    private int returnMinValue(int start, int end){
        int maxValue = 0;
        int toRet = 0;

        int sum;
        for(int i = start; i <= end; i++){
            for(int j = i; j <= end; j++){
                sum = recursiveSum(i, j);

                if(sum >= maxValue){
                    maxValue = sum;
                    toRet = j;
                }

            }
        }
        return toRet;
    }
    private int recursiveSum(int start, int end){
        if(start > end) return 0;
        else if(start == end) return sequence[start];
        else return sequence[start] + recursiveSum(start+1, end);
    }

    private int iterativeSum(int start, int end) {
        int sum = 0;
        for (int i = start; i <= end; i++) {
            sum += sequence[i];
        }
        return sum;
    }
    private void print(int[] a){
        System.out.print("[[");
        for (Integer i: a){
            System.out.print(""+i+", ");
        }
        System.out.println("]]");

    }
}
