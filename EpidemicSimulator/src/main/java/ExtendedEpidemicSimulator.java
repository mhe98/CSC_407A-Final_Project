
import java.util.Random;

/**
 * @author Ylewi Adrong & Mark He
 * @class: CSC 407A - Network Analysis
 * @date: December 3, 2020
 */

/*
    To-do:
    [X]: 1
    [X]: 2
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

    // Total population n = 1000;
    public static int[] N = new int[1000];
    public static int[] exposed;

    public static int T = 2000;
    public static int total_infected;
    public static double a = 0.005;
//    public static double b = 0.01;

    public static int min = 0;
    public static int max = N.length - 1;
    public static int range = max - min + 1;
    public static int rand;
    public static int new_rand;

    /**
     * Contact Rate
     *
     * @param _a
     * @return positive
     *
     * public static int contactRate(int _ratio) { double contact = 0; double
     * lowRate = 0; double highRate = 1; Random chance = new Random(); contact =
     * ((highRate - lowRate) * chance.nextDouble()); _ratio = (int) (contact *
     * (_ratio/T)* 100); //0.3 * (500/ 1000) = 0.3 * 1/2 *100 = 0.4 *
     * System.out.println(" Rate of Contact = " + _ratio); return _ratio; }
     */
    /**
     * Calculate the probability of infection
     *
     * @param _a
     * @return
     */
    public static boolean probability(double _a) {
        //Probability of 1/(int) value
        double _b = 0;
        double lowRate = 0; 
        double highRate = 1; 
        Random b = new Random(); 
        _b = ((highRate - lowRate) * b.nextDouble()); //
        System.out.println("\n>>>>>>>> Chance of infection = " + _b);
        return _b <= _a;
    }

    /**
     * Infection Rate
     *
     * @param _b
     * @return false
     *
     * public static boolean infectRate() { // double infected = 0; // double
     * lowRate = 0; // double highRate = 1; // Random chance = new Random(); //
     * infected = ((highRate - lowRate) * chance.nextDouble()) * 100; //
     * System.out.println("\n>>>>>>>> Chance of infection = " + (int) infected +
     * "%");
     *
     * //if percent is more than 50%, they are infected if (probability(a)) {
     * return true; } return false; }
     */
    /**
     * Infection Status
     *
     * @param _person
     * @param _infectedCount
     * @return isInfected
     */
    public static boolean isInfected(int _person[], int _exposed[], int _infectedCount) {
        boolean isInfected = false;
        int sample = _person.length - 1;

        //infected status
        int infected = 1;
        int uninfected;

        int contact;
        int ratio;
        boolean infect;
//        boolean infect = false;

        //initial random infection
        rand = (int) (Math.random() * range);
        _person[rand] += infected;
        if (_person[rand] >= infected) {
            _infectedCount++;
        }
        //iterate the number of rounds T=2000
        for (int i = 0; i < T; i++) {
            System.out.println("\n/-----------| ROUND {" + i + "} |-----------/");

            for (int j = 0; j < sample; j++) {

                //check which person is infected
                if (_person[j] >= 1) {
                    new_rand = (int) (Math.random() * range);
                    isInfected = true;
                    _person[rand] += infected;
                    //[RI] -> Random Infected
                    System.out.printf("%10s %1s %-5s %9s\n", "[RI] infected[", rand, "] =", isInfected);
                    _person[rand]--;

                    //infection rate      
                    if (probability(a) && new_rand != rand && _person[new_rand] != _person[rand]) {
                        isInfected = true;
                        _person[new_rand] += infected;
                        //[CI] -> Contact Infected
                        System.out.printf("%10s %1s %-5s %9s\n", "[CI] infected[", new_rand, "] =", isInfected);
                        _infectedCount++;
                        _person[new_rand]--;
                    } //end if
                } else {
                    isInfected = false;
//                    System.out.printf("%10s %1s %-5s %9s\n", "healthy[", j, "] =", isInfected);
                }
            }

            System.out.println("\nTotal infected: " + _infectedCount);
        }

        return isInfected;
    }

    public static void main(String[] args) {

        ExtendedEpidemicSimulator epidemic = new ExtendedEpidemicSimulator();

        epidemic.isInfected(N, exposed, total_infected);
    }
}