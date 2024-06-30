package blatt4;

import org.apache.commons.cli.*;

public class KMP {
    public static int KnuthMorrisPratt(String t, String p) {
        char[] text = t.toCharArray();
        char[] pattern = p.toCharArray();
        int[] border = new int[p.length()+1];
        computeBorder(border, pattern);

        int i = 0, j = 0;
        while(i < text.length-pattern.length+1) {
            while (text[i+j] == pattern[j]) {
                j++;
                if (j == pattern.length) return i;
            }
            i += j-border[j];
            j = Math.max(0, border[j]);
        }
        return -1;
    }

    public static void computeBorder(int[]border, char[]s) {
        border[0] = -1;
        border[1] = 0;

        int i = border[1];
        for (int j=2; j<=s.length;j++) {
            while ((i>=0) && (s[i] != s[j-1])) {
                i = border[i];
            }
            i++;
            border[j]=i;
        }
    }

    public static void main(String[] args) {
        Options options = new Options();
        options.addOption("t", "text", true, "input text");
        options.addOption("p", "pattern", true, "input pattern");
        CommandLineParser parser = new BasicParser();

        System.out.println("String Matching with KMP algorithm");
        String t;
        String p;

        try {
            CommandLine cmd = parser.parse(options, args);
            if (cmd.hasOption("t") && cmd.hasOption("p")) {
                t = cmd.getOptionValue("t");
                p = cmd.getOptionValue("p");
            }
            else { //Random strings
                System.out.println("Text and pattern will be randomly generated from the alphabet {A,T,G,C}");
                System.out.println("If you want to use your own input, please use the -t and -p options");
                t = Utils.generateRandomString(80,Utils.ALPHABET2);
                p = Utils.generateRandomString(5,Utils.ALPHABET2);
            }
        } catch (ParseException e) {
            System.out.println("Error parsing command line arguments");
            return;
        }

        System.out.println("Text: " + t);
        System.out.println("Pattern: " + p);
        long startTime = System.nanoTime();
        int result = KnuthMorrisPratt(t, p);
        long endTime = System.nanoTime();
        if (result != -1) {
            System.out.println("Pattern found at index " + result);
        }
        else {
            System.out.println("Pattern does not occur in text");
        }
        System.out.println("Time taken: " + 1.0*(endTime - startTime)/ 1000000 + " ms");
    }
}
