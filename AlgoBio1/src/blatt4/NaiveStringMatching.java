package blatt4;

import org.apache.commons.cli.*;

public class NaiveStringMatching {
    public static int naiveSearching(String t, String p) {
        int i=0,j=0;
        while (i<= t.length()-p.length()) {
            while (t.charAt(i+j) == p.charAt(j)) {
                j++;
                if (j==p.length()) return i;
            }
            i++;
            j=0;
        }
        return -1; //Pattern not found in text
    }

    public static void main(String[] args) {
        Options options = new Options();
        options.addOption("t", "text", true, "input text");
        options.addOption("p", "pattern", true, "input pattern");
        CommandLineParser parser = new BasicParser();

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
                t = Utils.generateRandomString(20,Utils.ALPHABET2);
                p = Utils.generateRandomString(5,Utils.ALPHABET2);
            }
        } catch (ParseException e) {
            System.out.println("Error parsing command line arguments");
            return;
        }

        System.out.println("Text: " + t);
        System.out.println("Pattern: " + p);
        long startTime = System.nanoTime();
        int result = naiveSearching(t, p);
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
