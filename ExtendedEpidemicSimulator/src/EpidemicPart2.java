import java.util.Random;
import java.util.ArrayList;

public class EpidemicPart2 {
	//infection status
    public static final int HEALTHY = 0, INFECTED = 1, EXPOSED = 2, RECOVERED = 3;
    public static final String[] statusString = { "Healthy", "Infected", "Exposed", "Recovered" };

    // Total population
    public static int N = 100;
    // Number of 'communities'
    public static int communities = 2;
    // Array of infected
    public static int[][] infected = new int[communities][N];
    //Temp array to track numbers of days infected
    public static int[][] temp = new int[communities][N];
    //Keeps track of rate of infection
    public static double[][] bArray = new double[communities][N];

    //Rounds
    public static int T = 101;
    
    public static int total_infected;
    
    //Rate of coming in contact
    public static double a = 0.25;
    
    //Rate of being infected
    public static double b = 0.25;
    
    //Rate of contact in social circle
    public static double y = 0.25;
    
    //Effective rate of 1
    public static double v1 = 0.01;
    //Effective rate of 2
    public static double v2 = 0.90;
    
    //Stores different vaccine rates for each community
    public static double[] vaccineRate = new double[communities];
    
    //Rate of how much the vaccine wears off
    public static double wearOff = 0.05;

    /**
     * Contact Rate True if 0 <= a <= 1 False otherwise
     * 
     * @return
     */
    public static boolean isExposed() {
        // Probability of 1/(int) value
        return new Random().nextDouble() <= y;
    }

    /**
     * Has a % chance of returning true based on the specified infection rate (if value is .2 it has 20% chance)
     * 
     * @return
     */
    public static boolean isInfected(double[][] bArray, int i, int j) {
        // Probability of 1/(int) value
        return new Random().nextDouble() <= bArray[i][j];
    }
    
    /**
     * Returns random value between 0 and specified vaccine rate
     * @param v upper bound
     * @return
     */
    public static double vaccine(double v) {
    	// Vaccine rates
    	int min = 0;
    	Random r = new Random();
    	double randomValue = min + (v - min) * r.nextDouble();
        return randomValue;
    }
    
    /**
     * Returns if one has been in contact with another person
     * @param y chance of being in contact with someone else
     * @return
     */
    public static boolean inContact() {
    	return new Random().nextDouble() <= y;
    }
    
    /**
     * Simulates vaccine wearing off
     * @param wearOff rate at which the vaccine starts to wear off
     * @return
     */
    public static double wearOff() {
    	int min = 0;
    	Random r = new Random();
    	double randomValue = min + (wearOff - min) * r.nextDouble();
        return randomValue;
    }
    
    public static boolean isCured(double v) {
    	return new Random().nextDouble() <= v;
    }
    
    /**
     * Simulates spread of the infection
     * @param infected array with status of infected
     * @param temp a temp array to keep track of days infected
     */
    public static void totalInfected(int[][] infected, int[][] temp, double[][] bArray) {
    	//Tracks specified values for each community/social group
        int[] infectedCount = new int[infected.length];
        int[] exposedCount = new int[infected.length];
        int[] totalInfected = new int[infected.length];
        int[] recoveredCount = new int[infected.length];
        int[] inContact = new int[infected.length];
        
        //Assigns a different success rate for each vaccine
        double[] avgRateInfection = new double[infected.length];
        vaccineRate[0] = v1;
        vaccineRate[1] = v2;
        
        //Initialize bArray
        for (int i = 0; i < bArray.length; i++) {
        	for (int j = 1; j < bArray[i].length; j++) {
        		bArray[i][j] = b;
        	}
        }
        
      //Tracks the rounds
        for (int i = 1; i < T; i++) {
        	System.out.println("\n|--------------| ROUND {" + i + "} |--------------|");
        	
        	//Resets the counter for the next round
        	for (int x = 0; x < infected.length; x++) {
        		infectedCount[x] = 0;
            	exposedCount[x] = 0;
            	recoveredCount[x] = 0;
            	inContact[x] = 0;
        	}
        	
        	//Administer vaccines at round 3
        	if (i == 3) {
				System.out.println("==VACCINES HAVE BEEN ADMINISTERED==");
				System.out.println("Community 1's vaccine has a " + (v1 * 100) + "% effective rate");
				System.out.println("Community 2's vaccine has a " + (v2 * 100) + "% effective rate");
				System.out.println("\n|-----------------------------------------|");
			}
        	
        	for (int j = 0; j < infected.length; j++) {
        		System.out.println("Community " + (j + 1) + " results of population " + infected[j].length);
        		for (int k = 1; k < infected[j].length; k++) {
        			
        			//Rate of infection does not change until round 3
        			if (i < 3) {
        				avgRateInfection[j] = b;
        			}
        			
        			//If round 3, apple vaccine. Rate of infection is decreased for each individual
        			if (i == 3) {
        				//Decrease rate of infection from vaccine
        				bArray[j][k] -= vaccine(vaccineRate[j]);
        				if (bArray[j][k] < 0) {
    						bArray[j][k] = 0;
    					}
        				avgRateInfection[j] = (avgRateInfection[j] + bArray[j][k]) / 2;
        			}
        			
        			//If past round 3, start the wear off effect of vaccine
        			if (i > 3) {
        				//If not infected
        				if (infected[j][k] != INFECTED) {
        					//Checks if person was successfully vaccinated or not
        					if (bArray[j][k] != b) {
        						//If person was vaccinated, incraese infection rate by wearoff value
        						//to simulate wearing off of vaccine
            					bArray[j][k] += wearOff();
            					if (bArray[j][k] > b) {
            						bArray[j][k] = b;
            					}
            					avgRateInfection[j] = (avgRateInfection[j] + bArray[j][k]) / 2;
            				}
        				} else {
        					//If they are already infected, has a % chance of becoming healthy from vaccine based on success rate
        					if (isCured(vaccineRate[j])) {
        						infected[j][k] = RECOVERED;
    							recoveredCount[j]++;
        					}
        				}
        			}
            		
            		//Checks if not infected also excludes recovered (AKA immune)
            		if (!(infected[j][k] == INFECTED) && !(infected[j][k] == RECOVERED)) {
            			if (inContact()) {
            				inContact[j]++;
            				if (isExposed() || infected[j][k] == EXPOSED) {
            					infected[j][k] = EXPOSED;
                				exposedCount[j]++;
                				if (isInfected(bArray, j, k)) {
                					infected[j][k] = INFECTED;
                        			infectedCount[j]++;
                        			totalInfected[j]++;
                        			temp[j][k]++;
                				}
            				}
            			}
            		}
        		}
        		System.out.println("Average rate of infection is: " + ((int)(avgRateInfection[j] * 100)) + "%");
        		System.out.println(inContact[j] + " people have been in contact");
        		System.out.println("There have been " + exposedCount[j] + " exposed out of the " + inContact[j] + " in contact");
            	
        	    if (infectedCount[j] == 0 || infectedCount[j] > 1) {
        	    	System.out.println(infectedCount[j] + " were infected");
        	    } else {
        	        System.out.println(infectedCount[j] + " was infected");
        	    }
        	    	System.out.println("There have been " + recoveredCount[j] + " recovered patients");
        	    	System.out.println("Total infected: " + totalInfected[j]);
        	    	System.out.println("");
        	}
        	
        }
        	
    }

    public static void main(String[] args) {
        totalInfected(infected, temp, bArray); 
    }
    
    public static String toString(int[] infected, int k) {
    	return statusString[infected[k]];
    }

}