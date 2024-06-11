package blatt3;

import org.apache.commons.cli.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class TFNetwork_SAT_Runner {
    public static void main(String[] args) {
        Options options = new Options();
        options.addOption("network",  true, "input regulatory network file");
        options.addOption("actives",  true, "input active genes file");
        options.addOption("cnf",  true, "input cnf file in dimacs format");
        options.addOption("sol",  true, "output SAT solution file");
        options.addOption("all",  false, "if set, then all tasks will be executed");
        CommandLineParser parser = new BasicParser();

        String regulatory;
        String active;
        String cnf;
        String sol;

        try {
            CommandLine cmd = parser.parse(options, args);
            if (cmd.hasOption("network") && cmd.hasOption("actives") && cmd.hasOption("all")) {
                //TODO: finish this
            }
            else{
                if (cmd.hasOption("cnf")) {
                    cnf = cmd.getOptionValue("cnf");
                    SATsolving(cnf);
                }
                System.out.println("=======================================================================================");

                if (cmd.hasOption("network") && cmd.hasOption("sol")) {
                    //TODO: review this part. The Assignment said when -sol is set then map the results to the TF IDs. But without a network we do not know the IDs.
                    regulatory = cmd.getOptionValue("network");

                    TFNetwork network = new TFNetwork();
                    network.readNetwork(regulatory);

                    sol = cmd.getOptionValue("sol");

                    try (BufferedReader br = new BufferedReader(new FileReader(sol))) {
                        String line;
                        while ((line = br.readLine()) != null) {
                            if (line.startsWith("v")) { //The result variable
                                String[] results = line.split(" ");
                                for (int i = 1; i < results.length; i++) {  //the first element is "v". Ignore it
                                    if (results[i].startsWith("-")) continue; //ignore inactive TFs
                                    int result = Integer.parseInt(results[i]);
                                    System.out.println(network.reversedTFMap.get(result));
                                }
                            }
                        }
                    }
                    catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println("=======================================================================================");


                if (cmd.hasOption("network") && cmd.hasOption("actives")) {
                    regulatory = cmd.getOptionValue("network");
                    active = cmd.getOptionValue("actives");

                    TFNetwork network = new TFNetwork();
                    network.readNetwork(regulatory);
                    network.readActiveGenes(active);
                    network.make_CNF_SAT();
                    if (!network.genesMap.isEmpty()) {
                        System.out.println("p cnf " + network.TFMap.size() + " " + network.genesMap.size());
                        for (TFNetwork.Gene gene : network.genesMap.values()) {
                            System.out.println(gene.SATClause);
                        }
                    }
                }
            }
        } catch (ParseException e) {
            System.err.println("Error parsing command line arguments");
            return;
        }
    }

    public static void SATsolving(String cnfPath) {
        try {
            //TODO: make the path the the SAT-Solver dynamic
            String cmd = "/home/trucbui/GitHub/AlgoBio1Git/AlgoBio1/src/blatt3/akmaxsat_1.1/akmaxsat " + cnfPath;
            Process p = Runtime.getRuntime().exec(cmd);
            BufferedReader result = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String line;
            while ((line = result.readLine())!=null) {
                System.out.println(line);
            }
            result.close();
        }
        catch (IOException e) {
            System.err.println("Something wrong with the SAT-Solver");
        }
    }

}
