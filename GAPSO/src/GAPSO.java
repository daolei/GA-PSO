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
 * GAPSO: Defines the hybrid algorithm Generic Algorithm - Particle Swarm Optimization.
 * 
 * 		Methods:
 * 			- Selection(): defines the selection operator.
 * 			- Crossover(): defines the crossover operator.
 * 			- Mutate():	defines the mutation operator.
 * 			- Execute(): executes previous methods is one iteration.
 */

import java.util.*;

public class GAPSO 
{    
    // Particle population.
    private Particle[] population;
    
    // Constants of the algorithm.
    private final double w = 0.8;
    private final double c1 = 1.5;
    private final double c2 = 1.5;
    private final double f1 = randValue();
    private final double f2 = randValue();
    private final double crossoverProbability = 0.7;
    private final double mutationProbability = 0.1;
    
    
    /*
     * Constructor.
     */
    public GAPSO() 
    { 
    	// Set the size of particles population.
        this.population = new Particle[ Settings.getSizeOfPopulation() ];
        
        // Loop through the population and create random particles (randomFlag = true).
        for (int i = 0; i < this.population.length; i++)
        {
             this.population[i] = new Particle(true);
        }
    }
    

    /*
     * Return a random value between (0, 1).
     */
    private static double randValue() 
    { 
        double value;
        
        // Get random value between [0, 1).
        value = Math.random();
        
        // While value equals 0.0, get a new random value between [0, 1).
        while (value == 0.0)
        {
        	value = Math.random();
        }
        
        return value;
    }
    
    
    /*
     * Selection operator:
     * 			Calculate selection and accumulated probability of every
     * 			particle of the population, and then create a new population
     * 			according to those probabilities.
     */
    private void selection() 
    { 
        /*
         * Calculate total value of every particle's path fitness function.
         */
        double totalValue = 0.0;
        
        // Loop through every particle of the population.
        for (Particle p : this.population)
        {
        	totalValue += p.getPath().fitnessFunction();
        }
        
        
        /*
         * Calculate selection probability of every particle.
         */
        
        // Loop through every particle of the population.
        for (Particle p : this.population) 
        { 
        	 // Selection probability of a particle = 
        	 //  		its path fitness function / total value of every 
        	 //										particle's path fitness function.
             double selectionProbability = p.getPath().fitnessFunction() / totalValue;
             
             // Set selection probability of each particle. 
             p.setSelectionProb(selectionProbability);
        }
        
        
        /*
         * Calculate accumulated probability of each particle.
         */
        
        // Calculate and set cumulative probability of the first (0) particle of the population. 
        double probability =  this.population[0].getSelectionProb();
        this.population[0].setCumulativeProb(probability);

        // Loop through every particle of the population, except the first one.
        for (int i = 1; i < this.population.length; i++) 
        { 
        	// Calculate and set cumulative probability of each particle.
        	probability += this.population[i].getSelectionProb();
            this.population[i].setCumulativeProb(probability);
        }
        
        
        /*
         * Create new population.
         */
        
        // Create new population of particles with the same population size. 
        Particle[] newPopulation = new Particle[Settings.getSizeOfPopulation()];
        
        // Loop through the OLD particle population.
        for (int i = 0; i < this.population.length; i++) 
        { 
             // New random number between (0, 1). 
             double rand = randValue();
             
             // Create new null particle.
             Particle newParticle = null;

             // If the random number is less or equal to
             // the OLD populations cumulative probability 
             // of the first (0) particle. 
             if (rand <= this.population[0].getCumulativeProb()) 
             { 
                 // then the new particle becomes equal
            	 // to the first (0) particle of the OLD population. 
            	 newParticle = this.population[0];
             } 
             else 
             { 
            	 // Else loop through every particle j of the population, except the first one.
                 for (int j = 1; j < this.population.length; j++) 
                 { 
                	 // rand number is between the cumulative probabilities of particles
                	 // j - 1 and j, then the new particle becomes equal
                	 // to the particle j of the OLD population.
                      if (this.population[j - 1].getCumulativeProb() < rand &&
                    		  rand <= this.population[j].getCumulativeProb()) 
                      { 
                    	  newParticle = this.population[j];
                      }
                 }
             }
             
             // Clone the new Particle.
             Particle newselectedParticle = newParticle.clone();
             
             // Add the particle into the new population.
             newPopulation[i] = newselectedParticle;
        }
        
        // Set OLD population equal to the new one.
        this.population = newPopulation;
    }
    
    
    /*
     * Return the position of population's best particle,
     * meaning the particle with the biggest fitness function.
     * 
     * If all population positions are null -1 is returned.
     */
    private int particleBestPosition() 
    { 
    	// Initialize position.
        int position = -1;
        
        // Initialize maximum fitness function value.
        double maxFitness = 0;
        
        // Loop through every particle of the population.
        for (int i = 0; i < this.population.length; i++) 
        { 
        	// If the particle is not null AND its fitness function is greater that the maximum.
             if (this.population[i] != null && this.population[i].getPath().fitnessFunction() > maxFitness) 
             { 
            	 // Set new maximum value.
            	 maxFitness = this.population[i].getPath().fitnessFunction();
            	 
            	 // Set new position.
            	 position = i;
             }
        }
        
        // Return the position.
        return position;
    }
    
    
    /*
     * Return an ArrayList with all nodes that two particles
     * have in common. 
     */
    private ArrayList<Node> getCrossoverNodes(Particle p1, Particle p2) 
    { 
    	// Initialize nodes' ArrayList.
        ArrayList<Node> crossoverNodes = new ArrayList<>();
        
        // Loop through every node of the first particle's Path.
        for (Node node : p1.getPath().getPath()) 
        { 
        	// If the node exists in the second particle's Path too.
             if (p2.getPath().getPath().contains(node))
             {
            	 // Add the node into the ArrayList.
            	 crossoverNodes.add(node);
             }
        }
        
        // Delete the first and last node of the ArrayList,
        // because they are node number 0 and node number 99,
        // which exist in every particle.
        crossoverNodes.remove(0);
        crossoverNodes.remove(crossoverNodes.size() - 1);
        
        // Return ArrayList of the nodes.
        return crossoverNodes;
    }
    
    
    
    /*
     * Create two new offsprings (paths) from two particles.
     * 
     * The offsprings will be created according to
     * a random crossover node of the two particles.
     */
    private void crossoverOffsprings(Particle p1, Particle p2) 
    { 
        // Random number generator.
        Random rand = new Random();
        
        // Create an ArrayList with all common nodes of particle p1 and p2 paths.
        ArrayList<Node> crossoverNodes = this.getCrossoverNodes(p1,p2);
        
        // Initialize new offspring paths.
        Path firstOffspring = new Path();
        Path secondOffspring = new Path();
        
        // While the ArrayList with the common nodes is not empty.
        while (!crossoverNodes.isEmpty()) 
        { 
            // Get a random node from the crossoverNodes ArrayList.
        	Node crossNode = crossoverNodes.get(rand.nextInt(crossoverNodes.size()));
            
            // Get the position of the common node in both p1 and p2.
            int pos1 = p1.getPath().getNodePosition(crossNode);
            int pos2 = p2.getPath().getNodePosition(crossNode);
            
            /*
             * Creation of the two possible offsprings.
             */

            // Initialize a node ArrayList for the first possible offspring.
            ArrayList<Node> firstOffspringNodeList = new ArrayList<>();
            
            
            // Loop through all nodes of the path of particle p1,
            // from position 0 to position pos1 (position of common node in p1),
            // and add them to the node ArrayList of the first offspring.
            for (int i = 0; i <= pos1; i++)
            {
            	firstOffspringNodeList.add(p1.getPath().getNode(i));
            }
            
            // Loop through all nodes of the path of particle p2,
            // from position pos2 (position of common node in p2) to the last position,
            // and add them to the node ArrayList of the first offspring.
            for (int i = pos2+1; i < p2.getPath().getSize(); i++)
            {
            	firstOffspringNodeList.add(p2.getPath().getNode(i));
            }
            
            // Create first offspring from the node ArrayList.
            firstOffspring.setPath(firstOffspringNodeList);
            
            
            // If the first offspring that was created has double nodes in it.
            if (firstOffspring.hasDoubleNodes()) 
            { 
            	// The node that was randomly selected from the
            	// common node list of the two particles
            	// is now removed and the process of the creation 
            	// of the offsprings starts from the beginning. 
            	// (while loop starts over)
            	crossoverNodes.remove(crossNode);
                continue;
            }
            
            
            // Initialize a node ArrayList for the second possible offspring.
            ArrayList<Node> secondOffspringNodeList = new ArrayList<>();
            
            
            // Loop through all nodes of the path of particle p2,
            // from position 0 to position pos2 (position of common node in p2),
            // and add them to the node ArrayList of the second offspring.
            for (int i = 0; i <= pos2; i++)
            {
            	secondOffspringNodeList.add(p2.getPath().getNode(i));
            }
            
            // Loop through all nodes of the path of particle p1,
            // from position pos1 (position of common node in p1) to the last position,
            // and add them to the node ArrayList of the second offspring.
            for (int i = pos1+1; i < p1.getPath().getSize(); i++)
            {
            	secondOffspringNodeList.add(p1.getPath().getNode(i));
            }
            
            // Create second offspring from the node ArrayList.
            secondOffspring.setPath(secondOffspringNodeList);
            
            // If the second offspring that was created has double nodes in it.
            if (secondOffspring.hasDoubleNodes())
            	
            	// The node that was randomly selected from the
            	// common node list of the two particles
            	// is now removed and the process of the creation 
            	// of the offsprings starts from the beginning. 
            	// (while loop starts over)
            	crossoverNodes.remove(crossNode);
            else 
            { 
            	// Else the paths of the two particles p1 and p2
            	// are replaced from the new offsprings and the 
            	// process of their creation stops. (end of while loop).
            	// (when the else statement is reached, the two
            	// offsprings are created without problems).
                p1.setPath(firstOffspring);
                p2.setPath(secondOffspring);
                break;
            }
        }
    }
    
    
    /*
     * Crossover operator:
     * 				Get all the particles of the population that will be crossed,
     * 				for every couple of those particles get their offspring paths,
     * 				and change them with the originals,
     * 				then create a new population with those particles.
     */
    private void crossover() 
    {
        // Initialize ArrayList of the particles that will get crossed.
        ArrayList<Particle> crossoverParticles = new ArrayList<>();
        
        // Loop through every particle of the population.
        for (int i = 0; i < this.population.length; i++) 
        { 
             // Get a random number between (0, 1).
             double rand = randValue();
             
             // If the random number is less than the crossover probability. 
             if (rand < this.crossoverProbability) 
             { 
            	 // The particle will get crossed so it is added 
            	 // in the crossover particle ArrayList.
            	 crossoverParticles.add(this.population[i]);
            	 
            	 // and it gets "removed" from the particle population.
                 this.population[i] = null;
             }
        }
        
        
        // If the number of the particles in the crossover ArrayList is odd.
        if (crossoverParticles.size() % 2 == 1) 
        {
            // Get the position of the best particle of the population. 
            int bestParticlePos = this.particleBestPosition();
            
            // If the position is -1, there are no more particles
            // in the population, so every one of them has been
            // added in the crossover ArrayList.
            if (bestParticlePos == -1) 
            { 
            	// Remove the first particle of the crossover ArrayList and add it
            	// to the particle population.
                this.population[0] = crossoverParticles.get(0);
                crossoverParticles.remove(0);
            } 
            else 
            { 
            	// Else add the best particle of the population in the
            	// crossover ArrayList and "remove" it for the population.
            	crossoverParticles.add(this.population[bestParticlePos]);

                this.population[bestParticlePos] = null;
            }
        }
        
        
        // For every couple (i and i + 1) of particles in the crossover ArrayList.
        for (int i = 0; i < crossoverParticles.size(); i += 2) 
        { 
             // Get their offsprings, (create new paths)
        	crossoverOffsprings(crossoverParticles.get(i), crossoverParticles.get(i + 1));
        }
        
        // Loop through the particles population,
        // and add the particles of the crossover ArrayList
        // into the null positions of the population.
        for (int i = 0; i < this.population.length; i++) 
        { 
             if (this.population[i] == null) 
             { 
                 this.population[i] = crossoverParticles.get(0);
                 crossoverParticles.remove(0);
             }
        }
    }
    
    
    /*
     * Mutation operator:
     * 			Create a new path between two randomly selected
     * 			nodes of the existing path, of each particle of 
     * 			of the population.
     */
    private void mutate() 
    { 
    	// Random number generator.
        Random rand = new Random();
        
        // Loop through every particle of the population.
        for (Particle currentParticle : this.population) 
        { 
             // Get a random number between (0, 1).
             double randNumber = randValue();
             
             
             // If the random number is less that the mutation probability
             // then the particle will be mutated. 
             if (randNumber < mutationProbability) 
             { 
                 // Initialize new nodes as null.
            	 Node firstNode = null;
            	 Node secondNode = null;
                 
                 while (true) 
                 { 
                	 // Get two random nodes from the current particle of the population.
                	 firstNode = currentParticle.getPath().getNode(rand.
                                        nextInt(currentParticle.getPath().getSize()));
                	 secondNode = currentParticle.getPath().getNode(rand.
                                        nextInt(currentParticle.getPath().getSize()));
                	 
                	 // If the two nodes are equal or the absolute value of their difference is 1
                	 // get two new random nodes from the current particle of the population.
                	 // (start over the while loop).
                     if (firstNode == secondNode || Math.abs(firstNode.getNumber() - secondNode.getNumber()) == 1)
                     {
                         continue;
                     }
                     else	
                     {
                    	 // else keep those two nodes.
                    	 // (end the while loop).
                         break;
                     }
                 }
                 
                 // Get the positions of the two random nodes, in the path of the current particle.
                 int firstPos = currentParticle.getPath().getNodePosition(firstNode);
                 int secondPos = currentParticle.getPath().getNodePosition(secondNode);
                 
                 // If the position of the first node is greater than the position of the second one
                 // (the second node is before the first one in the path)
                 // then change the name of their variables.
                 if (firstPos > secondPos) 
                 { 
                	 Node temp = firstNode;
                	 firstNode = secondNode;
                	 secondNode = temp;
                     firstPos = currentParticle.getPath().getNodePosition(firstNode);
                     secondPos = currentParticle.getPath().getNodePosition(secondNode);
                 }
                 
                 
                 /*
                  * Create a new path from the first node to the second one. 
                  */
                 // Initialize the path.
                 Path offspringPath = new Path();
                 
                 // Copy the path from position 0 until the position of the first node 
                 // that was randomly selected.
                 for (int i = 0; i < firstPos; i++)
                 {
                      offspringPath.getPath().add(currentParticle.getPath().getNode(i));
                 }
                 
                 
                 // Create a new random path from the first node to the second one.
                 // If the new random path is null, meaning it could not be created,
                 // then mutate the next particle of the population. 
                 // (continue the "for each" loop)
                 if (!offspringPath.pathCreation(firstNode.getNumber(), secondNode.getNumber()))
                 {
                     continue;
                 }
                 
                 
                 // Copy the path from the position of the second node to the last position.
                 for (int i = secondPos + 1; i < currentParticle.getPath().getSize(); i++)
                 {
                      offspringPath.getPath().add(currentParticle.getPath().getNode(i));
                 }
                 
                 
                 // If the new path has not double nodes in it 
                 // then set the path of the current particle 
                 // as the new created path.
                 if (!offspringPath.hasDoubleNodes())
                 {
                	 currentParticle.setPath(offspringPath);
                 }
                 
             }
        } // end of for loop.
    }
    

    /*
     * Execute GA-PSO algorithm.
     */
    public Particle execute() 
    { 
        // For the number of iterations that has been set in the Settings Class.
        for (int i = 0; i < Settings.getIterations(); i++) 
        { 
        	 // Execute three main steps of the Generic Algorithm (GA).
             this.selection();
             this.crossover();
             this.mutate();
             
             
             /*
              * Execute PSO algorithm.
              * 
              * 
              * Note: the pbest of every particle was automatically
              * 	   updated through the crossover and mutation operators.
              */
             
             // Find the gbest (best position) of the population,
             // and save it in every particle of the population.
             Particle gbest = this.population[this.particleBestPosition()];
             for (Particle p : this.population)
             {
                  p.setGbest(gbest.getPath());
             }
             
             // Update the position - velocity of each particle of the population.
             for (Particle p : this.population)
             {
                  p.update(w,c1,c2,f1,f2);
             }
        }
        
        // After all iterations are done, return the best particle of the population.
        return this.population[this.particleBestPosition()];
    }
}


