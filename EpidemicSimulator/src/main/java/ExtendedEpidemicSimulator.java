import java.util.Random;

/**
 * @author Ylewi Adrong & Mark He
 * @class: CSC 407A - Network Analysis
 * @date: December 3, 2020
 * 
 *        To-do: 
 *        [X]: 1 [X]: 2 [X]: 3 [X]: 4 [ ]: 5 [X]: 6 [X]: 7 [ ]: 8 
 *      ------ ------------------------------------------------------
 *        [ ]: a [ ]: b [ ]: c [ ]: d [ ]: e [ ]: f
 * 
 */
public class ExtendedEpidemicSimulator {

    public static final int HEALTHY = 0, INFECTED = 1, EXPOSED = 2, RECOVERED = 3, IMMUNE = 4;;
    public static final String[] statusString = { "Healthy", "Infected", "Exposed", "Recovered" };

    // Total population N = 1000;
    public static int N = 1000;
    public static int[] infected = new int[N];

    public static int T = 100;
    public static int total_infected;
    public static double a = 1;
    public static double b = 1;

    /**
     * Contact Rate True if 0 <= a <= 1 False otherwise
     * 
     * @return
     */
    public static boolean isExposed() {
        // Probability of 1/(int) value
        return new Random().nextDouble() <= a;
    }

    /**
     * Infect Rate True if 0 <= b <= 1 False otherwise
     * 
     * @return
     */
    public static boolean isInfected() {
        // Probability of 1/(int) value
        return new Random().nextDouble() <= b;
    }

    public static void totalInfected(int[] infected) {
        int infectedCount = 0;
        int exposedCount = 0;
        int totalInfected = 0;
        int recoveredCount = 0;
        int recoveredTotal = 0;
        int immuneCount = 0;

        for (int i = 0; i < T; i++) {
            System.out.println("\n|--------------| ROUND {" + i + "} |--------------|");
            infectedCount = 0;
            exposedCount = 0;
            recoveredCount = 0;
            immuneCount++;

            System.out.println(immuneCount);
            for (int j = 0; j < infected.length; j++) {
                if (infected[j] == HEALTHY || infected[j] == EXPOSED) {
                    if (infected[j] == EXPOSED || isExposed()) {
                        infected[j] = EXPOSED;
                        exposedCount++;
                        if (isInfected()) {
                            infected[j] = INFECTED;
                            infectedCount++;
                            totalInfected++;
                        } else {
                            infected[j] = HEALTHY;
                        }
                    }
                }
                for (int k = 0; k <= j; k++) {
                    if (infected[k] == INFECTED && immuneCount % 5 == 0) {
                        infected[k] = RECOVERED;
                        recoveredCount++;
                        recoveredTotal++;
                        infectedCount--;
                        totalInfected--;
                    } else if (infected[k] == RECOVERED && immuneCount % 20 == 0) {
                        infected[k] = HEALTHY;
                        recoveredCount--;
                        recoveredTotal--;
                        infectedCount++;
                    }
                }
            }
            System.out.println("There have been " + exposedCount + " exposed in round " + i);

            if (infectedCount == 0 || infectedCount > 1) {
                System.out.println(infectedCount + " were infected in round " + i);
            } else {
                System.out.println(infectedCount + " was infected in round " + i);
            }
            System.out.println("There have been " + recoveredCount + " recovered patients in round " + i);
            System.out.println("Total recovered: " + recoveredTotal);
            System.out.println("Total infected: " + totalInfected);
        }
    }

    public static void main(String[] args) {

        // ExtendedEpidemicSimulator epidemic = new ExtendedEpidemicSimulator();
        // epidemic.totalInfected(infected);

        totalInfected(infected);
    }
}