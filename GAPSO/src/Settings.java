/*
	*********************************************
	*			Benetatou Persefoni			 	*
	*				Athens 2014				 	*
	*									 		*
	*	Exercise for Artificial Intelligence 	*
	*			University of Piraeus		 	*
	*********************************************
*/

/*
 * Settings: Save the main settings that the algorithm uses.
 * 			(Population size and number of iterations.
 */

public class Settings 
{
    private static int sizeOfPopulation;
    private static int Iterations;

    
    /*
     * Return population size.
     */
    public static int getSizeOfPopulation() 
    {
        return sizeOfPopulation;
    }

    
    /*
     * Set population size.
     */
    public static void setSizeOfPopulation(int populationSize) 
    {
    	sizeOfPopulation = populationSize;
    }

    
    /*
     * Return number of iterations.
     */
    public static int getIterations() 
    {
        return Iterations;
    }

    
    /*
     * Set number of iterations.
     */
    public static void setIterations(int iterations) 
    {
        Iterations = iterations;
    }
}

