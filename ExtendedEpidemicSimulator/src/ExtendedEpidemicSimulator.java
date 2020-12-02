import java.util.Random;


/**
 * @author Ylewi Adrong & Mark He
 * @class: CSC 407A - Network Analysis
 * @date: December 3, 2020
 * 
 *     To-do: 
 *      [X]: 1 [X]: 2 [X]: 3 [X]: 4 [ ]: 5 [X]: 6 [X]: 7 [ ]: 8 
 *    ------------------------------------------------------------ 
 *      [ ]: a [ ]: b []: c [ ]: d [ ]: e [ ]: f
 * 
 */
public class ExtendedEpidemicSimulator {

    //infection status
    public static final int HEALTHY = 0, INFECTED = 1, EXPOSED = 2, RECOVERED = 3, IMMUNE = 4;
    public static final String[] statusString = { "Healthy", "Infected", "Exposed", "Recovered" };

    // Total population N = 1000;
    public static int N = 1001;
    public static int[] infected = new int[N];
    public static int[] temp = new int[N];

    public static int T = 101;
    public static int total_infected;
    public static double a = 0.25;
    public static double b = 0.25;

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

    public static void totalInfected(int[] infected, int[] temp) {
        int infectedCount = 0;
        int exposedCount = 0;
        int totalInfected = 0;
        int recoveredCount = 0;

        //Tracks the rounds
        for (int i = 1; i < T; i++) {
        	System.out.println("\n|--------------| ROUND {" + i + "} |--------------|");
        	//Resets the counter for the next round
        	infectedCount = 0;
        	exposedCount = 0;
        	recoveredCount = 0;
        	
        	//Traverses Array
        	for (int j = 1; j < infected.length; j++) {
        		//Checks how long one has been infected and moves to immune
        		if (temp[j] > 0) {
        			temp[j]++;
        			if (temp[j] == 5) {
        				infected[j] = RECOVERED;
            			recoveredCount++;
            			totalInfected--;
            		// Checks how long they have been immune and moves back to healthy status
        			} else if (temp[j] == 20) {
        				infected[j] = HEALTHY;
            			temp[j] = 0;
        			}
        		}
        		
        		//Checks if not infected also excludes recovered (AKA immune)
        		if (!(infected[j] == INFECTED) && !(infected[j] == RECOVERED)) {
        			if (infected[j] == EXPOSED || isExposed()) {
        				infected[j] = EXPOSED;
        				exposedCount++;
        				if (isInfected()) {
        					infected[j] = INFECTED;
                			infectedCount++;
                			totalInfected++;
                			temp[j]++;
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
        	    	System.out.println("Total infected: " + totalInfected);
        }
    }

    public static void main(String[] args) {

        // ExtendedEpidemicSimulator epidemic = new ExtendedEpidemicSimulator();
        // epidemic.totalInfected(infected);

        totalInfected(infected, temp);
//        launch(args);
    }
    
    public static String toString(int[] infected, int k) {
    	return statusString[infected[k]];
    }
}