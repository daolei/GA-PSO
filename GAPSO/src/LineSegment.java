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
 * LineSegment: Define a line segment of a path. 
 * 				Consists of its first node, its last node
 * 				and the orientation in which they connect.  
 * 
 * Note: when referring to vertical and horizontal axis in
 * 		the comments, its about indexes of a two dimensions 
 * 		array.
 */


public class LineSegment 
{
    // First node of the segment.
    private Node firstNode;
    
    // Last node of the segment.
    private Node lastNode;
    
    // Orientation of the segment. The orientation can take values between 0 and 4.
    private int orientation;
    
    /*
     * Constants that represent the 4 possible directions. 
     */
    private static final int NO_CONNECTION = 0;
    private static final int NORTH = 1;
    private static final int EAST = 2;
    private static final int SOUTH = 3;
    private static final int WEST = 4;
    
    
    /*
     * Constructor.
     */
    public LineSegment(Node first, Node last, int orientation) 
    {
        this.firstNode = first;
        this.lastNode = last;
        this.orientation = orientation;
    }

    
    /*
     * Return last node of the segment. 
     */
    public Node getLastNode() 
    {
        return this.lastNode;
    }

    
    /*
     * Set last node of the segment. 
     */
    public void setLastNode(Node last) 
    {
        this.lastNode = last;
    }

    
    /*
     * Return the distance between the 2 ends
     * of the line segment.
     */
    public int distance() 
    { 
    	// Initialize distance.
        int distance = 0;
        
        switch (orientation) 
        { 
            case NORTH:
            	// If orientation = 1 the distance is equal to 
            	// the difference of the two nodes in the vertical axis.
                distance = this.firstNode.getY() - this.lastNode.getY();
                break;
            
            case EAST:
            	// If orientation = 2 the distance is equal to 
            	// the difference of the two nodes in the horizontal axis.
                distance = this.lastNode.getX() - this.firstNode.getX();
                break;
                
            case SOUTH:
            	// If orientation = 3 the distance is equal to 
            	// the difference of the two nodes in the vertical axis.
                distance = this.lastNode.getY() - this.firstNode.getY();
                break;
                
            case WEST:
            	// If orientation = 4 the distance is equal to 
            	// the difference of the two nodes in the horizontal axis.
                distance = this.firstNode.getX() - this.lastNode.getX();
                break;
        }
        
        // Return distance.
        return distance;
    }
    
    
    
    /*
     * Return the number of obstacles that
     * exist inside a line segment. 
     */
    public int numberOfObstacles() 
    { 
    	// Initialize new grid.
        Grid grid = new Grid();
        
        // Initialize number of obstacles.
        int number = 0;
        
        switch (orientation) 
        { 
            case NORTH:
            	// If orientation = 1 check if the nodes between the
            	// first and the last nodes, in the vertical axis, are obstacles.
            	// If they are increase number of obstacles by 1.
                for (int i = this.firstNode.getY(); i >= this.lastNode.getY(); i--) 
                { 
                     int j = this.firstNode.getX();
                     Node node = grid.getNode(i, j);
                     if (node.isObstacle())
                     {
                    	 number++;
                     }
                }
                break;
                
            case EAST:
            	// If orientation = 2 check if the nodes between the
            	// first and the last nodes, in the horizontal axis, are obstacles.
            	// If they are increase number of obstacles by 1.
                for (int j = this.firstNode.getX(); j <= this.lastNode.getX(); j++) 
                { 
                     int i = this.firstNode.getY();
                     Node node = grid.getNode(i, j);
                     if (node.isObstacle())
                     {
                    	 number++;
                     }
                }
                break;
                
            case SOUTH:
            	// If orientation = 3 check if the nodes between the
            	// first and the last nodes, in the vertical axis, are obstacles.
            	// If they are increase number of obstacles by 1.
                for (int i = this.firstNode.getY(); i <= this.lastNode.getY(); i++) 
                { 
                     int j = this.firstNode.getX();
                     Node node = grid.getNode(i, j);
                     if (node.isObstacle())
                     {
                    	 number++;
                     }
                }
                break;
                
            case WEST:
            	// If orientation = 4 check if the nodes between the
            	// first and the last nodes, in the horizontal axis, are obstacles.
            	// If they are increase number of obstacles by 1.
                for (int j = this.firstNode.getX(); j >= this.lastNode.getX(); j--) 
                { 
                     int i = this.firstNode.getY();
                     Node node = grid.getNode(i, j);
                     if (node.isObstacle())
                     {
                    	 number++;
                     }
                }
                break;
        }
        
        // Return number of obstacles in the line segment.
        return number;
    }
    
    
    /*
     * Return the orientation in which two nodes are connected.
     * The orientation can take values between 0 and 4.
     */
    public static int getConnection(Node center, Node current) 
    { 
    	// If the nodes are in the same point in the horizontal axis.
        if (center.getX() == current.getX()) 
        { 
        	// If center node is beneath current node in the vertical axis
            if (center.getY() - 1 == current.getY())
            {
                return NORTH;
            }
            // Else if center node is above current node in the vertical axis.
            else if (center.getY() + 1 == current.getY())
            {
                return SOUTH;
            }
        } 
        // Else if the nodes are in the same point in the vertical axis.
        else if (center.getY() == current.getY()) 
        { 
        	// If center node is at the right of current node in the horizontal axis.
            if (center.getX() + 1 == current.getX())
            {
                return EAST;
            }
            // Else if center node is at the left of current node in the horizontal axis.
            else if (center.getX() - 1 == current.getX())
            {
                return WEST;
            }
        }
        
        // Else there is no connection between the two nodes.
        return NO_CONNECTION;
    }
}

