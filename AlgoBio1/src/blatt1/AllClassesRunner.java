package blatt1;

public class AllClassesRunner {
    public static void main(String[] args) {
        System.out.println("Find all MSS:");
        AllMSSRunner.main(new String[]{});
        System.out.println("");
        System.out.println("Find SMSS:");
        SMSSRunner.main(new String[]{});
        System.out.println();
        System.out.println("Find minimal MSS:");
        MinimalSMSSRunner.main(new String[]{});
        System.out.println();
        System.out.println("Find all MSS using naive algorithm: ");
        NaiveRunner.main(new String[]{});
        System.out.println();
        System.out.println("Find all MSS using naive recursive algorithm: ");
        NaiveRecursiveRunner.main(new String[]{});
        System.out.println();
        System.out.println("Find all MSS using dynamic programming: ");
        DPRunner.main(new String[]{});
        System.out.println();
        System.out.println("Find MSS using divide-and-conquer: ");
        DCRunner.main(new String[]{});
        System.out.println();
        System.out.println("Find MSS using the optimal algorithm: ");
        OptimalRunner.main(new String[]{});
    }
}
