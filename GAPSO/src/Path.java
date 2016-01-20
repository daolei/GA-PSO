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
 * Path: Define a path into the grid.
 * 		The path is consists of nodes.
 */

import java.util.*;

public class Path 
{
	// node ArrayList of the path.
    private ArrayList<Node> path;
    
    // Constant T that is used for the fitness function.
    private final double T = 5.0;
    
    
    /*
     * Constructor.
     */
    public Path() 
    { 
    	// Initialize node ArrayList.
        this.path = new ArrayList<>();
    }
    
    
    /*
     * Return node ArrayList.
     */
    public ArrayList<Node> getPath() 
    { 
        return this.path;
    }
    
    
    /*
     * Set node ArrayList.
     */
    public void setPath(ArrayList<Node> path) 
    { 
        this.path = path;
    }
    
    
    /*
     * Return paths size.
     */
    public int getSize()
    {
    	return this.path.size();
    }
    
    
    /*
     * Return the node in a specific position of the path.
     */
    public Node getNode(int i)
    {
    	return this.path.get(i);
    }
    
    
    /*
     * Return number of a node in a specific position
     * of the path.
     */
    public int getNodeNumber(int i)
    {
    	return this.getNode(i).getNumber();
    }

    
    /* 
     * Return the position of a specific node in the path.
     * Return -1 if the node does not exist in the path.
     */
    public int getNodePosition(Node node) 
    { 
    	// Loop through the nodes of the path.
        for (int i = 0; i < this.path.size(); i++) 
        { 
        	// If the number of the current node is the same as
        	// the number of the node that was given,
        	// return nodes position.
             if (this.getNodeNumber(i) == node.getNumber())
             {
                 return i;
             }
        }
        
        // else return -1.
        return -1;
    }
    
    
    /*
     * Return true if the path is valid  (if it meets all the criteria).
     * Criteria:
     * 			- The path must start with the node number 0.
     * 			- The path must finish with the node number 99.
     */
    public boolean isValid() 
    { 
        int index = this.getSize() - 1;
        
        // If first node is number 0 and last node is number 99, 
        // return true, else return false.
        if (this.getNodeNumber(0) == 0 && this.getNodeNumber(index) == 99)
        {
            return true;
        }
        else
        {
            return false;
        }
    }
    

    /*
     * Return true if the path is accessible,
     * if it is valid and it has not obstacles.
     */
    private boolean isAccessible() 
    { 
    	// If the path is not valid return false.
        if (!this.isValid())
        {
            return false;
        }
        
        // Loop through the path's nodes.
        for (Node node : this.path) 
        { 
        	// If the current node is an obstacle return false.
             if (node.isObstacle())
             {
                 return false;
             }
        }
        
        // else return true.
        return true;
    }
    
    
    
    /* 
     *For the fastest convergence of the algorithm, the following assumption is made:
     *		When a random path is made, then 5 out of 10 paths are acceptable only 
     *		if they have no obstacles. But 5 out of 10 paths are acceptable even 
     *		if they have obstacles.
     *		In both cases the path must be valid. 
     */
    private boolean acceptablePath(double probability) 
    { 
        if (probability < 0.3)
        {
            return this.isAccessible();
        }
        else
        {
            return this.isValid();
        }
    }
    
    
    /*
     * Return true if the path has a node more than one time.
	 */
    public boolean hasDoubleNodes() 
    { 
    	
        for (int i = 0; i < this.getSize(); i++) 
        { 
             for (int j = i + 1; j < this.getSize(); j++) 
             { 
            	 // If the node in position i equals the node in position j
            	 // then return true.
                  if (this.getNode(i).equals(this.getNode(j)))
                  {
                      return true;
                  }
             }
        }
        
        // Else return false.
        return false;
    }
    

    /*
     * Return true if the path is continuous,
     * if every node in position i is neighbor 
     * with the node in position i + 1.
     */
    public boolean isContinuous() 
    { 
    	// Loop through every node of the path. 
        for (int i = 0; i < this.getSize() - 1; i++) 
        { 
        	// If nodes i and i + 1 are not connected return false. 
             if (LineSegment.getConnection(this.getNode(i), this.getNode(i + 1)) != 0)
             {
                 return false;
             }
        }
        
        // Else return true.
        return true;
    }
    
    
    /*
     * Create a random path from node with number "first", 
     * to the node with number "last". 
     */
    public boolean pathCreation(int first, int last) 
    { 
    	// Initialize grid.
        Grid grid = new Grid();

        // Random number generator.
        Random rand = new Random();
        
        // Add first node in the path and make it the current node.
        this.path.add(grid.getNodeByNumber(first));
        Node current = this.getNode(this.getSize() - 1);
        
        while(true) 
        {
        	// Get current node's neighbors.
            ArrayList<Node> neighbors = grid.getNeighbors(current);

            // While neighbors ArrayList is not empty.
            while (!neighbors.isEmpty()) 
            { 
            	// Get the index of a random neighbor.
                int randIndex = rand.nextInt(neighbors.size());

                // If the random neighbor node is already in the path remove it 
                // from the neighbor ArrayList. 
                if (this.path.contains(neighbors.get(randIndex)))
                {
                    neighbors.remove(randIndex);
                }
                else 
                { 
                	// Else add the random neighbor node in the path and make it the current node.
                    this.path.add(neighbors.get(randIndex));
                    current = neighbors.get(randIndex);

                    // If the current node is the same with the node with number "last"
                    // end the random creation of the path and return true. 
                    if (current.getNumber() == last)
                    {
                        return true;
                    }
                    else
                    {
                    	// Else continue with the random creation of the path.
                        break;
                    }
                }
            } // end of second while.

            // If neighbor ArrayList is empty return false. 
            if (neighbors.isEmpty())
            {
                return false;
            }
        } // end of first while. 
    }
    
    
    /*
     * Create a random path that starts from node number 0
     * and ends with node number 99. 
     * 
     * The assumption from acceptablePath() method is used.
     */
    public void randomCreation() 
    { 
    	// Get a random number between [0, 1).
        double rand = Math.random();
        
        do { 	// Loop until the path is acceptable. 
        	
        	// Clear the nodes of the path. 
            this.path.clear();

            // Create the random path between nodes 0 and 99.
            this.pathCreation(0, 99);
            
        } while (!this.acceptablePath(rand));
    }
    

    /*
     * Return an ArrayList with the line segments of the path.
     */
    private ArrayList<LineSegment> getLineSegments() 
    { 
    	// If the path is not valid return an empty ArrayList. 
        if (!isValid())
        {
            return null;
        }
        
        // Initialize segments ArrayList.
        ArrayList<LineSegment> segments = new ArrayList<>();
        
        // Get the orientation of the two first nodes of the path. 
        int orientation = LineSegment.getConnection(this.getNode(0), this.getNode(1));
        
        // Create first line segment and add it in the segments ArrayList.
        LineSegment segm = new LineSegment(this.getNode(0), this.getNode(1), orientation);
        segments.add(segm);

        // For every node in the path.
        for (int i = 2; i < this.getSize(); i++) 
        { 
        	// Get current node
        	Node current = this.getNode(i);
            
            // Get the last node from the last line segment that was created. 
        	Node last = segm.getLastNode();
            
            // If the current node does not connect with the last node
            // with the same orientation as the orientation of the line segment.
            if (LineSegment.getConnection(last, current) != orientation) 
            {
            	// Create a new line segment with orientation the
            	// one between current and last node,
            	// and add it in the segments ArrayList. 
                orientation = LineSegment.getConnection(last, current);
                segm = new LineSegment(last, current, orientation);
                segments.add(segm);
            } 
            else 
            { 
            	// Else (if current and last node connect with the same orientation as the line segment)
            	// Add current node into the line segment. 
                segm.setLastNode(current);
            }
        }
        
        // Return segments ArrayList.
        return segments;
    }
    
    
    /*
     * Calculate and return fitness function of the path. 
     */
    public double fitnessFunction() 
    { 
        double result = 0.0;
        
        // Get line segments of the path. 
        ArrayList<LineSegment> segments = this.getLineSegments();

        // Loop through the line segments in the segments ArrayList.
        for (LineSegment segm : segments) 
        { 
        	// Calculate the distance between the current segment's ends.
             double d = segm.distance();

        	 // Calculate a.
             double a;
             
             // If the segment has no obstacles then a = 0.
             if (segm.numberOfObstacles() == 0)
             {
                 a = 0;
             }
             else 
             { 
            	 // else a = the number of obstacle in the line segment.
                 a = segm.numberOfObstacles();
             }

             // Add the fitness function of the current segment,
             // into the total result.
             result += d + a*T;
        }

        // Return result of the function.
        return Math.floor(((1.0 / result) * 1000) * 100) * 100;
    }
}
