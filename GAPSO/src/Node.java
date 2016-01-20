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
 * Node: Defines a Node of the grid.
 * 		Consists of the number of the node,
 * 		its coordinates and if it is obstacle or not.
 */


public class Node 
{
    
    // Number of the node in the grid.
    private int number;
    
    // Coordinates of the node
    private int x;
    private int y;

    // True if the node is an obstacle.
    private boolean obstacle;
    
    /*
	 * Constructor.
	 */
    public Node(int number, int x, int y) 
    { 
        this.number = number;
        this.x = x;
        this.y = y;
    }
    
    
    /*
	 * Return number of the node. 
	 */
    public int getNumber() 
    { 
        return this.number; 
    }

    
    /*
	 * Return coordinate X of the node in the grid. 
	 */
    public int getX() 
    {
        return this.x;
    }

    
    /*
	 * Return coordinate Y of the node in the grid. 
	 */
    public int getY() 
    {
        return this.y;
    }
    
    
    /*
	 * Return true if the node is an obstacle. 
	 */
    public boolean isObstacle() 
    {
        return this.obstacle;
    }
    
    
    /*
	 * Set the node as an obstacle (true) or not. 
	 */
    public void setObstacle(boolean obstacle) 
    {
        this.obstacle = obstacle;
    }

    
    /*
	 * Check if two nodes are equal or not. 
	 * (Overrides equals() method of Object Class).
	 * 
	 * (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
    public boolean equals(Object obj) 
    { 
    	// If obj is null return false.
    	if (obj == null)
        {
            return false;
        }
    	
    	// If obj cannot be cast to Node object return false.
        if (!(obj instanceof Node))
        {
            return false;
        }
        
        // Return true if the fields match.
        return (this.number == ((Node)obj).getNumber()) &&
        		(this.x == ((Node)obj).getX()) &&
        		(this.y == ((Node)obj).getY()) &&
        		(this.obstacle == ((Node)obj).isObstacle());
    }
}
