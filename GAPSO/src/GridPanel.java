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
 * GridPanel: Draw panel for main Frame.
 */

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import javax.swing.JPanel;

public class GridPanel extends JPanel 
{
    // Graphical nodes of the Panel.
    private Rectangle2D.Double[][] rectangles = new Rectangle2D.Double[10][10];
    
    // Solution of the algorithm that is going to be drawn.
    private Particle solution;
    
    // Flag that confirms if a solution is given or not.
    private boolean executeFlag;
    
    // nodes that are obstacles. 
    private final int[] OBSTACLES = Grid.OBSTACLES;
    
    // Set width and height.
    private final int RWIDTH = 60;
    private final int RHEIGHT = 40;
    
    // Set font.
    private final Font FONT = new Font("Serif", Font.PLAIN, 20);
    
    /*
     * Set execute flag.
     */
    public void setExecuteFlag(boolean executed) 
    {
        this.executeFlag = executed;
    }
    
    
    /*
     * Returns true if the given number of a node, corresponds to an obstacle.
     */
    private boolean isObstacle(int nodeNumber) 
    { 
    	// Loop through obstacles array. 
        for (int x : OBSTACLES) 
        { 
             if (nodeNumber == x)
             {
                 return true;
             }
        }
        
        return false;
    }
    
    
    /*
     * Draw the grid panel when no solution exists. 
     * Also Resets - Clears the panel when a solution was already drawn.
     */
    public void reset() 
    {
    	this.reset(this.getGraphics());
    }
    
    public void reset(Graphics g) 
    { 
    	// Convert Graphics to Graphics2D.
        Graphics2D g2d = (Graphics2D) g;

        // Set font for the numbers of the nodes.	
        g2d.setFont(FONT);
        
        
        /*
         * Draw rectangles and their numbers.
         */
        
        // Initialize node number to zero.
        int nodeNumber = 0;
        
        // Loop through rows and columns of the panel.
        for (int i = 9; i >= 0; i--) 
        { 
             for (int j = 0; j < 10; j++) 
             {
            	 // Create the rectangle.
            	 // Set its position according to which row and column
            	 // it is at.
            	 this.rectangles[i][j] = new Rectangle2D.Double(j * RWIDTH, i * RHEIGHT, RWIDTH, RHEIGHT);
                  
                  /*
                   * Set rectangles color and fill it with it.
                   */
                  // If it's an obstacle.
                  if (this.isObstacle(nodeNumber)) 
                  { 
                      g2d.setColor(Color.lightGray);
                      g2d.fill(this.rectangles[i][j]);
                  } 
                  // If it's the first node.
                  else if (nodeNumber == 0) 
                  { 
                      g2d.setColor(Color.green);
                      g2d.fill(this.rectangles[i][j]);
                  } 
                  // If it's the last node.
                  else if (nodeNumber == 99) 
                  { 
                      g2d.setColor(Color.red);
                      g2d.fill(this.rectangles[i][j]);
                  } 
                  // Any other node.
                  else 
                  { 
                	  // Set color same as the background.
                      g2d.setColor(this.getParent().getBackground());
                      g2d.fill(this.rectangles[i][j]);
                  }
                  
                  // Set color of rectangle lines and Strings and draw them.
                  g2d.setColor(Color.black);
                  g2d.draw(this.rectangles[i][j]);
                  
                  // Draw strings inside the rectangles.
                  // Set its position according to which row and column
                  // it is at.
                  g2d.drawString( String.valueOf(nodeNumber), j * RWIDTH + 20, i * RHEIGHT + 26 );

                  // Get next node number.
                  nodeNumber++;
             }
        }
        
    }
    
    
    /*
     * Draw the Panel Component.
     * (Overrides paintComponent method of Object Class).
     * 
     * (non-Javadoc)
     * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
     */
    public void paintComponent(Graphics g) 
    {
    	// Use reset method to draw Grid Panel.
        this.reset(g);
        
        // If a solution is given, draw the Panel using drawSolution method.
        if (this.executeFlag)
        {
            this.drawSolution(this.solution, g);
        }
    }
    
    
    /*
     * Get the rectangle in which a specific node is drawn.
     */
    private Rectangle2D.Double getRectangle(Node currentNode) 
    { 
        for (int i = 0; i < this.rectangles.length; i++) 
        { 
             for (int j = 0; j < this.rectangles[i].length; j++) 
             { 
                  if (i == currentNode.getX() && j == currentNode.getY())
                  {
                      return this.rectangles[i][j];
                  }
             }
        }
        
        return null;
    }
    
    
    /*
     * Draw a given solution into the panel.
     */
    public void drawSolution(Particle solution) 
    {
    	this.drawSolution(solution, this.getGraphics());
    }
    
    public void drawSolution(Particle solution, Graphics g) 
    { 
    	// Get new solution.
        this.solution = solution;
        
        // Convert Graphics to Graphics2D.
        Graphics2D g2d = (Graphics2D) g;

        // Set font for the numbers of the nodes.
        g2d.setFont(FONT);
        
        // Loop through nodes of the Path of the Solution - Particle.
        for (Node currentNode : solution.getPath().getPath()) 
        { 
        	// If the node is not the first one or the last one.
             if (currentNode.getNumber() != 0 && currentNode.getNumber() != 99) 
             { 
            	 // Get the rectangle of the node.
                 Rectangle2D.Double rect = this.getRectangle(currentNode);
                 
                 // Set rectangles color and fill it with it.
                 g2d.setColor(Color.orange);
                 g2d.fill(rect);
                 
                 // Set color of rectangle lines and Strings and draw them.
                 g2d.setColor(Color.black);
                 g2d.draw(rect);
                 
                 
                 // Get current nodes coordinates and number.
                 int nodeNumber = currentNode.getNumber();
                 int x = currentNode.getX();
                 int y = currentNode.getY();
                 
                 // Draw strings inside the rectangles.
                 // Set its position according to which row and column
                 // it is at.
                 g2d.drawString( String.valueOf(nodeNumber), y * RWIDTH + 20, x * RHEIGHT + 27 );
             }
        }
        
    } // end of drawSolution()
}
