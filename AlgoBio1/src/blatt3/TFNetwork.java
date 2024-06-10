package blatt3;

import org.apache.commons.cli.*;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;

public class TFNetwork {
    HashMap<String, Gene> genesMap;
    HashMap<String, Integer> TFMap;

    public TFNetwork() {
        genesMap = new HashMap<>();
        TFMap = new HashMap<>();
    }

    public static void main(String[] args) {
        Options options = new Options();
        options.addOption("r", "regulatory", true, "input regulatory network file");
        options.addOption("a", "active", true, "input active genes file");
        CommandLineParser parser = new BasicParser();

        String regulatory;
        String active;

        try {
            CommandLine cmd = parser.parse(options, args);
            if (cmd.hasOption("r")) {
                regulatory = cmd.getOptionValue("r");
            }
            else {
                regulatory = "src/blatt3/regulatory-network.edgelist.tsv";
            }

            if (cmd.hasOption("a")) {
                active = cmd.getOptionValue("a");
            }
            else {
                active = "src/blatt3/active-genes.txt";
            }
        } catch (ParseException e) {
            System.err.println("Error parsing command line arguments");
            return;
        }

        TFNetwork network = new TFNetwork();
        network.readNetwork(regulatory);
        network.readActiveGenes(active);

        network.make_CNF_SAT();
        if (!network.genesMap.isEmpty()) {
            System.out.println("p cnf " + network.TFMap.size() + " " + network.genesMap.size());
            for (Gene gene : network.genesMap.values()) {
                System.out.println(gene.SATClause);
            }
        }
    }

    public void readNetwork(String filename) {
        int counter = 1;
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line = br.readLine();
            while (line != null) {
                if (line.startsWith("#")) {
                    line = br.readLine();
                }
                String[] field = line.split("\t");
                String TF = field[0];
                String target = field[1];

                if (!TFMap.containsKey(TF)) {
                    TFMap.put(TF, counter);
                    counter++;
                }

                if (!genesMap.containsKey(target)) {
                    genesMap.put(target, new Gene(target));
                }
                genesMap.get(target).addTF(TFMap.get(TF));

                line = br.readLine();
            }
        }
        catch (FileNotFoundException e) {
            System.err.println("File path incorrect: " + filename + "\n" + "Please use the option -r or --regulatory for input regulatory network file");
        }
        catch (IOException e) {
            System.err.println("Error reading file: " + filename);
        }
    }

    public void readActiveGenes(String filename) {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line = br.readLine();
            while (line!=null) {
                line = line.trim();
                if (genesMap.containsKey(line)) {
                    genesMap.get(line).setActive();
                }
                line = br.readLine();
            }
        }
        catch (FileNotFoundException e) {
            System.err.println("File path incorrect: " + filename + "\n" + "Please use the option -a or --active for input active genes file");
        }
        catch (IOException e) {
            System.err.println("Error reading file: " + filename);
        }
    }

    public void make_CNF_SAT(){
        StringBuilder sb;
        for (Gene gene : genesMap.values()) {
            sb = new StringBuilder();
            if (gene.active) {
                for (int TF : gene.regulatedTFs) {
                    sb.append(TF);
                    sb.append(" ");
                }
                sb.append(0);
            }
            else {
                for (int TF : gene.regulatedTFs) {
                    sb.append(-TF);
                    sb.append(" ");
                }
                sb.append(0);
            }
            gene.setSATClause(sb.toString());
        }
    }

    public static class Gene {
        String id;
        HashSet<Integer> regulatedTFs;  //Why HashSet? Because there are some duplicated TF-target lines in the input
        boolean active=false;
        String SATClause;

        public Gene(String id){
            this.id = id;
            regulatedTFs = new HashSet<>();
        }
        public void addTF(int tf){
            regulatedTFs.add(tf);
        }

        public void setActive(){
            active = true;
        }

        public void setSATClause(String SAT){
            SATClause = SAT;
        }
    }
}
