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
 * Particle: Define a single particle of the population. 
 */

public class Particle 
{
    
	// The path of the particle. 
    private Path path;
    
    // The velocity of the particle.
    private double[] velocity = new double[100];
    
    // selection probability of the particle.
    private double selectionProbability;
    
    // cumulative probability of the particle.
    private double cumulativeProbability;
    
    // Best position in which the particle has been until now.
    private Path pbest;
    
    // Best position of the population. 
    private Path gbest;
    
    
    /*
     * Constructor.
     */
    public Particle(boolean randomFlag) 
    { 
        this.path = new Path();
        
        // If the flag is true create a random path for the particle
        // and set pbest and gbest equal to this path.
        if (randomFlag) 
        { 
            this.path.randomCreation();
            this.pbest = this.path;
            this.gbest = this.path;
        }
    }
    
    
    /*
     * Return particle's path.
     */
    public Path getPath() 
    {
        return this.path;
    }

    
    /*
     * Set particle's path.
     */
    public void setPath(Path path) 
    { 
        this.path = path;
        
        // If the fitness function of the new path is
        // less than the fitness function of the pbest,
        // then set the particle's pbest as the new path.
        if (this.path.fitnessFunction() < this.pbest.fitnessFunction())
        {
            this.pbest = this.path;
        }
    }
    
       
    /*
     * Return selection probability.
     */
    public double getSelectionProb() 
    {
        return this.selectionProbability;
    }


    /*
     * Set selection probability.
     */
    public void setSelectionProb(double selectionProb) 
    {
        this.selectionProbability = selectionProb;
    }

    
    /*
     * Return cumulative probability.
     */
    public double getCumulativeProb() 
    {
        return this.cumulativeProbability;
    }

    
    /*
     * Set cumulative probability.
     */
    public void setCumulativeProb(double cumulativeProb) 
    {
        this.cumulativeProbability = cumulativeProb;
    }
    
    
    /*
     * Set best position of the population.
     */
    public void setGbest(Path gbest) 
    { 
        this.gbest = gbest;
    }
    
    
    /*
     * Return a clone of the current particle.
     * (Overrides clone() method of Object Class).
     * 
     * (non-Javadoc)
     * @see java.lang.Object#clone()
     */
    public Particle clone() 
    { 
        Particle newParticle = new Particle(false);
        
        newParticle.path = this.path;
        
        for (int i = 0; i < newParticle.velocity.length; i++)
        {
             newParticle.velocity[i] = velocity[i];
        }
        
        newParticle.selectionProbability = selectionProbability;
        newParticle.cumulativeProbability = cumulativeProbability;
        
        newParticle.pbest = this.pbest;
        newParticle.gbest = this.gbest;
        
        return newParticle;
    }
    
    
    /*
     * Update position - velocity of the particle.
     */
    public void update(double w, double c1, double c2, double f1, double f2) 
    { 
        Grid grid = new Grid();
        
        // Loop through the velocity array of the particle and update it.
        for (int i = 0; i < this.velocity.length; i++) 
        {
             if ( (this.pbest.getSize() > i) && (this.gbest.getSize() > i) &&
            		 (this.path.getSize() > i) ) 
             { 
                 this.velocity[i] = w * this.velocity[i] + 
                        c1 * f1 * 
                        (this.pbest.getNodeNumber(i) - this.path.getNodeNumber(i)) + 
                        c2 * f2 * 
                        (this.gbest.getNodeNumber(i) - this.path.getNodeNumber(i));
             }
        }
        
        
        // Update the position of the particle.
        Path newPath = new Path();
        for (int i = 0; i < this.path.getSize(); i++) 
        { 
        	Node node = grid.getNodeByNumber((int) Math.floor(this.path.getNodeNumber(i) + this.velocity[i]));
             if (node == null) 
             { 
                 return;
             }
             newPath.getPath().add(node);
        }
        
        // If the new path is valid, continuous and without double nodes, then set it as the new path.
        if (newPath.isValid() && !newPath.hasDoubleNodes() && newPath.isContinuous())
        {
            this.setPath(newPath);
        }
    }
}

