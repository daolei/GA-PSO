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
 *	MainFrame: Design Main Frame of the application.
 */

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


public class MainFrame extends JFrame 
{
	// Declare gridPanel.
	private GridPanel gridPanel = new GridPanel();
	
	// Declare default population size and number of iterations.
    private final int POPULATION_SIZE = 50;
    private final int ITERATIONS = 100;
    
    
    /*
     * Constructor.
     */
    public MainFrame() 
    {
    	// Initialize main components of the frame.
        initComponents();
        
        // Set frame to be shown at the center of the screen. 
        setLocationRelativeTo(null);
        
        // Set settings of the algorithm at default values.
        Settings.setSizeOfPopulation(this.POPULATION_SIZE);
        Settings.setIterations(this.ITERATIONS);
    }
    

    /*
     * Called from within the constructor to initialize the frame.
     */
    private void initComponents() 
    {
    	// Initialize main components of the frame.
    	JButton executeButton = new JButton("Execute Algorithm");
    	JButton clearButton = new JButton("Clear Panel");
    	JButton settingsButton = new JButton("Settings");
    	JButton exitButton = new JButton("Exit Application");

    	// On frame closing, application closes. 
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // Frame title.
        setTitle("Genetic Algorithm - Particle Swarm Optimization for Path Planning");
        
        // Set background color of the frame as white.
        getContentPane().setBackground(Color.white);
        
        // Size of frame cannot be changed.
        setResizable(true);

        
        // Set layout of the frame as group layout. 
        GroupLayout layout = new GroupLayout(getContentPane());
        setLayout(layout);
        
        layout.setHorizontalGroup
        (
        	layout.createSequentialGroup()
        	.addGap(20)												// add 20px horizontal gap.
        	.addGroup
        	(
        		layout.createParallelGroup(GroupLayout.Alignment.LEADING)
        		.addComponent(this.gridPanel, 601, 601, 601)		// Add gridPanel with 601px width.
        		.addGroup
        		(
        			layout.createSequentialGroup()
        			.addComponent(executeButton, 140, 140, 140)		// add button with 140px width.
        			.addGap(20)										// add 20px horizontal gap.
        			.addComponent(clearButton, 100, 100, 100)		// add button with 100px width.
        			.addGap(80)										// add 80px horizontal gap.
        			.addComponent(settingsButton, 100, 100, 100)	// add button with 100px width.
        			.addGap(20)										// add 20px horizontal gap.
        			.addComponent(exitButton, 140, 140, 140)		// add button with 140px width.
        		)
        	)
        	.addGap(20)												// add 20px horizontal gap.
        );
        
        layout.setVerticalGroup
        (
        	layout.createSequentialGroup()
        	.addGap(20)											// add 20px vertical gap.
        	.addComponent(this.gridPanel, 401, 401, 401)		// Add gridPanel with 401px height.
        	.addGap(20)											// add 20px vertical gap.
        	.addGroup
        	(	
        		layout.createParallelGroup(GroupLayout.Alignment.LEADING)
        		.addComponent(executeButton, 50, 50, 50)		// Add button with 50px height.
        		.addComponent(clearButton, 50, 50, 50)			// Add button with 50px height.
        		.addComponent(settingsButton, 50, 50, 50)		// Add button with 50px height.
        		.addComponent(exitButton, 50, 50, 50)			// Add button with 50px height.
        	)
        	.addGap(20)											// add 20px vertical gap.
        );

        
       pack();
        

        // Create Event Handler for executeButton.
        executeButton.addActionListener
        (
        	new ActionListener() 
		    {
        		public void actionPerformed(ActionEvent e) 
		        {
        			executeButton_OnClick(e);
		        }
		    }
        );


        // Create Event Handler for clearButton.
        clearButton.addActionListener
        (
        	new java.awt.event.ActionListener() 
        	{
        		public void actionPerformed(ActionEvent e) 
        		{
        			clearButton_OnClick(e);
        		}
        	}
        );


        // Create Event Handler for settingsButton.
        settingsButton.addActionListener
        (
        	new ActionListener() 
        	{
	            public void actionPerformed(ActionEvent e) 
	            {
	            	settingsButton_OnClick(e);
	            }
	        }
        );
        

        // Create Event Handler for clearButton.
        exitButton.addActionListener
        (
        	new ActionListener() 
        	{
        		public void actionPerformed(ActionEvent e) 
        		{
        			exitButton_OnClick(e);
        		}
        	}
        );
        
    } // end of initComponents()

    
    /*
     * executeButton Event Handler.
     */
    private void executeButton_OnClick(ActionEvent e) 
    {
    	GAPSO GAPSO = new GAPSO();
        
        // Get algorithms solution.
        Particle solution = GAPSO.execute();
        
        // Reset - Clear the panel.
        this.gridPanel.reset();
        
        // Draw new solution.
        this.gridPanel.drawSolution(solution);
        
        // Set executed flag as true.
        this.gridPanel.setExecuteFlag(true);
    }

    
    /*
     * clearButton Event Handler.
     */
    private void clearButton_OnClick(ActionEvent e) 
    {
    	// Reset - Clear the panel.
    	this.gridPanel.reset();
        
        // Set executed flag as false.
    	this.gridPanel.setExecuteFlag(false);
    }

    
    /*
     * settingsButton Event Handler.
     */
    private void settingsButton_OnClick(ActionEvent e) 
    {
        // Create new Settings Frame.
    	SettingsFrame settingsFrame = new SettingsFrame(Settings.getSizeOfPopulation(), Settings.getIterations(), this);
        
        // Set Main Frame as disabled.
        this.setEnabled(false);
        
        // Show Settings Frame.
        settingsFrame.setVisible(true);
    }
    
    
    /*
     * exitButton Event Handler.
     */
    private void exitButton_OnClick(ActionEvent e) 
    {
    	// Exit application.
        System.exit(0);
    }
}

