
import java.util.Random;

/**
 * @author Ylewi Adrong & Mark He
 * @class: CSC 407A - Network Analysis
 * @date: December 3, 2020
 */

/*
    To-do:
    [X]: 1
    [ ]: 2
    [X]: 3
    [X]: 4
    [ ]: 5
    [ ]: 6
    [ ]: 7
    [ ]: 8
    ------
    [ ]: a
    [ ]: b
    [ ]: c
    [ ]: d
    [ ]: e
    [ ]: f
    
 */
public class ExtendedEpidemicSimulator {

    public static final int HEALTHY=0, INFECTED=1, EXPOSED=2, RECOVERED=3, IMMUNE = 4;;
    public static final String[] statusString = { "Healthy", "Infected", "Exposed", "Recovered" };

    // Total population n = 1000;
    public static int N = 1000;
    public static int[] infected = new int[N];

    public static int T = 100;
    public static int total_infected;
    public static double a = 1;
    public static double b = 1;

//    public static int min = 0;
//    public static int max = N.length - 1;
//    public static int range = max - min + 1;
//    public static int rand;
//    public static int new_rand;

    public static boolean isExposed() {
        //Probability of 1/(int) value
        return new Random().nextDouble() <= a;
    }

    public static boolean isInfected() {
        //Probability of 1/(int) value
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
            System.out.println("\n/-----------| ROUND {" + i + "} |-----------/");
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
            		if (immuneCount > 5) {
            			immuneCount = 0;
            		}
            		if (immuneCount == 5) {
            			if (infected[k] == INFECTED) {
            				infected[k] = RECOVERED;
                        	recoveredCount++;
                        	recoveredTotal++;
                        	infectedCount--;
                        	totalInfected--;
            			}
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



//    /**
//     * Contact Rate
//     *
//     * @param _a
//     * @return positive
//     */
//    public static int contactRate() {
//        double positive = 0;
//        double lowRate = 0;
//        double highRate = 1;
//        Random chance = new Random();
//        positive = ((highRate - lowRate) * chance.nextDouble()) * 100;
//        System.out.println(" Rate of Contact = " + (int) positive + "%");
//        return (int) positive;
//    }
//
//    /**
//     * Infection Rate
//     *
//     * @param _b
//     * @return false
//     */
//    public static boolean infectRate() {
//        double infected = 0;
//        double lowRate = 0;
//        double highRate = 1;
//        Random chance = new Random();
//        infected = ((highRate - lowRate) * chance.nextDouble()) * 100;
//        System.out.println("\n>>>>>>>> Chance of infection = " + (int) infected + "%");
//
//        if (probability(chance)) {
//            return true;
//        }
//
////        //if percent is more than 50%, they are infected
////        if (infected > 50) {
////            return true;
////        }
//        return false;
//    }
//
//    /**
//     * Infection Status
//     *
//     * @param _person
//     * @param _infectedCount
//     * @return isInfected
//     */
//    public static boolean isInfected(int _person[], int _infectedCount) {
//        boolean isInfected = false;
//        int sample = _person.length - 1;
//
//        //infected status
//        int infected = 1;
//        int contact = contactRate();
//        boolean infect;
////        boolean infect = false;
//
//        //initial random infection
//        rand = (int) (Math.random() * range);
//        _person[rand] += infected;
//        if (_person[rand] >= infected) {
//            _infectedCount++;
//        }
//        //iterate the number of rounds T=2000
//        for (int i = 0; i < T; i++) {
//            System.out.println("\n/-----------| ROUND {" + i + "} |-----------/");
//
//            for (int j = 0; j < sample; j++) {
//
//                //check which person is infected
//                if (_person[j] >= 1) {
//                    new_rand = (int) (Math.random() * range);
//                    isInfected = true;
//                    _person[rand] += infected;
//                    //[RI] -> Random Infected
//                    System.out.printf("%10s %1s %-5s %9s\n", "[RI] infected[", rand, "] =", isInfected);
//                    _person[rand]--;
//
//                    //contact infection
//                    //infection rate
//                    infect = infectRate();
//                    if (infect && new_rand != rand && _person[new_rand] != _person[rand]) {
//                        isInfected = true;
//                        _person[new_rand] += infected;
//                        //[CI] -> Contact Infected
//                        System.out.printf("%10s %1s %-5s %9s\n", "[CI] infected[", new_rand, "] =", isInfected);
//                        _infectedCount++;
//                        _person[new_rand]--;
//                    }
//                } else {
//                    isInfected = false;
////                    System.out.printf("%10s %1s %-5s %9s\n", "healthy[", j, "] =", isInfected);
//                }
//            }
//
//            System.out.println("\nTotal infected: " + _infectedCount);
//        }
//
//        return isInfected;
//    }s

    public static void main(String[] args) {

        ExtendedEpidemicSimulator epidemic = new ExtendedEpidemicSimulator();

        epidemic.totalInfected(infected);
    }
}
