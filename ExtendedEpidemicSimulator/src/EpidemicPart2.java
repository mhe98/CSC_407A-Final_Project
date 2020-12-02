import java.util.Random;

public class EpidemicPart2 {
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
    public static double o1 = 0.25;
    public static double o2 = 0.40;

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
    
    public static boolean isCured(double o) {
    	// Probability of 1/(int) value
        return new Random().nextDouble() <= o;
    }
    
    /**
     * Simulates spread of the infection
     * @param infected array with status of infected
     * @param temp a temp array to keep track of days infected
     */
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
        		break;
        	}
        }
    }

    public static void main(String[] args) {
        totalInfected(infected, temp);
    }
    
    public static String toString(int[] infected, int k) {
    	return statusString[infected[k]];
    }

}
