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
 * Grid: Defines the grid of the algorithm.
 * 		The grid consists of Nodes. 
 */

import java.util.*;

public class Grid 
{
	// The nodes of the grid.
    private Node[][] grid;
    
    // Number of rows of the grid. 
    private final int ROW_NUMBER = 10;
    // Number of columns of the grid.
    private final int COLUMN_NUMBER = 10;

    // Array with the number of the nodes that
    // are obstacles.
    public static final int[] OBSTACLES = { 9, 19, 23, 24, 25, 33, 34, 50, 51,
                                          	52, 56, 57, 58, 59, 60, 61, 62, 68,
                                          	69, 74, 90, 91 };

    
    /*
	 * Constructor.
	 */
    public Grid() 
    { 
        // Initialize the grid.
    	grid = new Node[ROW_NUMBER][COLUMN_NUMBER];
        
        /*
		 *  Create nodes depending the position of the array. 
		 */
    	
		// Initialize number of node.
        int number = 0;
        
        // Loop through the positions of the array.
        // Start from the last element of the first column,
        // until the first element of the last column.
        for (int i = ROW_NUMBER - 1; i >= 0; i--) 
        { 
             for (int j = 0; j < COLUMN_NUMBER; j++) 
             { 
                  this.grid[i][j] = new Node(number, i, j);

                  // Set if the node is obstacle or not. 
                  this.setObstacle(grid[i][j]);
                  
                  // Increase number of node by 1.
                  number++;
             }
        }
        
    }
    
    
    /*
	 * Set a node as obstacle or not.  
	 */
    private void setObstacle(Node node) 
    { 
    	// Loop through obstacles array. 
        for (int i = 0; i < OBSTACLES.length; i++) 
        { 
        	// If the number of the node equals the element of
        	// obstacles array, then the node is an obstacle.
             if (node.getNumber() == OBSTACLES[i]) 
             { 
            	 node.setObstacle(true);
                 return;
             }
        }
    }
    
    
    /*
     * Return the node in position i, j
     */
    public Node getNode(int i, int j)
    {
    	return this.grid[i][j];
    }
    
    
    /*
	 * Return the node of the grid with a given number. 
	 */
    public Node getNodeByNumber(int nodeNumber) 
    { 
    	// Loop through the grid. 
        for (int i = 0; i < ROW_NUMBER; i++) 
        { 
             for (int j = 0; j < COLUMN_NUMBER; j++) 
             { 
            	 // If the number of the node equals with the 
            	 // given number, return the node.
                  if (this.grid[i][j].getNumber() == nodeNumber) 
                  { 
                      return grid[i][j];
                  }
             }
        }
        
        // Else return null (the node was not found).
        return null;
    }
    
    
    /*
     * Return an ArrayList with the neighbor nodes of a given node.
     */
    public ArrayList<Node> getNeighbors(Node node) 
    {
    	// Initialize neighbors ArrayList.
        ArrayList<Node> neighbors = new ArrayList<Node>();

        // Get coordinates of the given node.
        int x = node.getX();
        int y = node.getY();
        
        // Get north neighbor if the node is not at the edge of the grid.
        if (node.getY() != 0)
        {
        	neighbors.add(grid[x][y - 1]);
        }
        
        // Get east neighbor if the node is not at the edge of the grid.
        if (node.getX() != COLUMN_NUMBER - 1)
        {
        	neighbors.add(grid[x + 1][y]);
        }
        
        // Get south neighbor if the node is not at the edge of the grid.
        if (node.getY() != ROW_NUMBER - 1)
        {
        	neighbors.add(grid[x][y + 1]);
        }

        // Get west neighbor if the node is not at the edge of the grid.
        if (node.getX() != 0)
        {
        	neighbors.add(grid[x - 1][y]);
        }
        
        // Return neighbors ArrayList.
        return neighbors;
    }
}

