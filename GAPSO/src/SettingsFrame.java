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
 * SettingsFrame: Define a Frame where the user can change
 * 					the main settings of the algorithm 
 * 					(population size and number of iterations).
 * 					
 * 					The frame is shown when the user clicks
 * 					"Settings" button from the main Frame.
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class SettingsFrame extends javax.swing.JFrame 
{
	// Declare main Frame.
    private MainFrame mainFrame;

    // Declare text fields.
    private JTextField populationSizeTextField = new JTextField();;
    private JTextField iterationsTextField = new JTextField();;
    
    // Declare default population size and number of iterations.
    private final int POPULATION_SIZE = 50;
    private final int ITERATIONS = 100;
    
    
    /*
     * Constructor.
     */
    public SettingsFrame(int popSize, int iterations, MainFrame main) 
    {
    	// Set settings of the algorithm at given values.
    	Settings.setSizeOfPopulation(popSize);
        Settings.setIterations(iterations);
        
        // Get data of main frame.
        this.mainFrame = main;
        
        // Initialize main components of the frame.
        initComponents();
        
        // Set frame to be shown at the center of the screen. 
        setLocationRelativeTo(null);
        
        // Set text of TextFields with the corresponding settings value.
        populationSizeTextField.setText( String.valueOf(Settings.getSizeOfPopulation()) );
        iterationsTextField.setText( String.valueOf(Settings.getIterations()) );
    }

    
    /*
     * Called from within the constructor to initialize the frame.
     */
    private void initComponents() 
    {
    	// Initialize main components of the frame.
    	JLabel populationSizeLabel = new JLabel("Population Size:");
    	JLabel iterationsLabel = new JLabel("Number of Iterations:");
    	JButton okButton = new JButton("OK");
    	JButton cancelButton = new JButton("Cancel");

        // On frame closing, application does nothing. 
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        
        // Frame title.
        setTitle("Change Settings");
        
        // Set background color of the frame as white.
        getContentPane().setBackground(Color.white);
        
        // Size of frame cannot be changed.
        setResizable(false);

        // Set alignment of text fields.
        populationSizeTextField.setHorizontalAlignment(JTextField.RIGHT);
        iterationsTextField.setHorizontalAlignment(JTextField.RIGHT);
        
        // Set shown columns of text fields.
        populationSizeTextField.setColumns(5);
        populationSizeTextField.setColumns(5);
        
        // Set default values of text fields.
        //populationSizeTextField.setText(String.valueOf(this.POPULATION_SIZE));
        //iterationsTextField.setText(String.valueOf(this.ITERATIONS));
        
        
        // Set layout of the frame as group layout. 
        GroupLayout layout = new GroupLayout(getContentPane());
        setLayout(layout);

        layout.setHorizontalGroup
        (
        	layout.createSequentialGroup()
        	.addGap(20)										// add 20px horizontal gap.
        	.addGroup
        	(
        		layout.createParallelGroup(GroupLayout.Alignment.LEADING)
        		.addGroup
        		(
        			layout.createSequentialGroup()
        			.addComponent(populationSizeLabel)		// add label.
        			.addGap(70)								// add 70px horizontal gap.
        			.addComponent(populationSizeTextField)	// add text field.
        		)
        		.addGroup
        		(
        			layout.createSequentialGroup()
        			.addComponent(iterationsLabel)			// add label.
        			.addGap(40)								// add 40px horizontal gap.
        			.addComponent(iterationsTextField)		// add text field.
        		)
        		.addGroup
        		(
        			layout.createSequentialGroup()
        			.addGap(75)								// add 75px horizontal gap.
        			.addComponent(okButton, 80, 80, 80)		// add button with 80px width.
        			.addGap(20)								// add 20px horizontal gap.
        			.addComponent(cancelButton, 80, 80, 80)	// add button with 80px width.
        		)
        	)
        	.addGap(20)										// add 20px horizontal gap.
        );
        
        
        layout.setVerticalGroup
        (
        	layout.createSequentialGroup()
        	.addGap(20)										// add 20px vertical gap.
        	.addGroup
        	(
        		layout.createParallelGroup(GroupLayout.Alignment.LEADING)
        		.addComponent(populationSizeLabel)			// add label.
        		.addComponent(populationSizeTextField)		// add text field.
        	)
        	.addGap(10)										// add 10px vertical gap.
        	.addGroup
        	(
        		layout.createParallelGroup(GroupLayout.Alignment.LEADING)
        		.addComponent(iterationsLabel)				// add label.
        		.addComponent(iterationsTextField)			// add text field.
        	)
        	.addGap(30)										// add 30px vertical gap.
        	.addGroup
        	(
        		layout.createParallelGroup(GroupLayout.Alignment.LEADING)
        		.addComponent(okButton, 35, 35, 35)			// Add button with 35px height.
        		.addComponent(cancelButton, 35, 35, 35)		// Add button with 35px height.
        	)
        	.addGap(20)										// add 20px vertical gap.
        );
        
        
        pack();
        
        
        // Create Event Handler for okButton.
        okButton.addActionListener
        (
        	new ActionListener() 
        	{
        		public void actionPerformed(ActionEvent e) 
        		{
        			okButton_OnClick(e);
        		}
        	}
        );

        
        // Create Event Handler for cancelButton.
        cancelButton.addActionListener
        (
        	new ActionListener() 
        	{
        		public void actionPerformed(ActionEvent e) 
        		{
        			cancelButton_OnClick(e);
        		}
        	}
        );
        
        
        // Create Event Handler for frame closing.
        this.addWindowListener
        (
        	new WindowAdapter() 
        	{
        		public void windowClosing(WindowEvent e) 
        		{
        			settingsFrame_OnClosing(e);
        		}
        	}
        );
        
    } // end of initComponents().

    
    /*
     * OnClosing Event Handler.
     */
    private void settingsFrame_OnClosing(WindowEvent e) 
    {
    	// Enable main frame and dispose it.
    	mainFrame.setEnabled(true);
        this.dispose();
    }

    
    /*
     * cancelButton Event Handler.
     */
    private void cancelButton_OnClick(ActionEvent e) 
    {
    	// Close the frame.
        this.settingsFrame_OnClosing(null);
    }

    
    /*
     * okButton Event Handler.
     */
    private void okButton_OnClick(ActionEvent e) 
    {
        // Declare variables.
        int populationSize;
        int iterations;
        
        
        /*
         * Try to get population size.
         */
        try 
        { 
        	// Get text from text field and convert it to integer,
        	// if possible.
        	populationSize = Integer.parseInt(populationSizeTextField.getText());
        }
        catch(NumberFormatException ex) 
        { 
        	// Show error message if text could not convert into integer.
            JOptionPane.showMessageDialog(	
            								this, 			
                    						"Population Size: Invalid Integer.", 
                    						"Error", 
                    						JOptionPane.WARNING_MESSAGE
                    					 );
            return;
        }
        
        if (populationSize < 30 || populationSize > 200) 
        { 
        	// Show error message if value does not exist in the specified interval.
            JOptionPane.showMessageDialog(
            								this, 
            								"Population Size: valid values between 30 and 200", 
            								"Error", 
            								JOptionPane.WARNING_MESSAGE
            							 );
            return;
        }
        
        
        /*
         * Try to get number of iterations.
         */
        try 
        { 
        	// Get text from text field and convert it to integer,
        	// if possible.
            iterations = Integer.parseInt(iterationsTextField.getText());
        }
        catch(NumberFormatException ex) 
        { 
        	// Show error message if text could not convert into integer.
            JOptionPane.showMessageDialog(
            								this, 
            								"Number of Iterations: Invalid Integer.", 
            								"Error", 
            								JOptionPane.WARNING_MESSAGE
            							 );
            return;
        }
        
        if (iterations < 50 || iterations > 300) 
        { 
        	// Show error message if value does not exist in the specified interval.
            JOptionPane.showMessageDialog(	
            								this, 
            								"Αριθμός Επαναλήψεων: valid values ​​between 50 and 300", 
            								"Error", 
            								JOptionPane.WARNING_MESSAGE
            							 );
            return;
        }
        
        
        // If there where no exception set new population size and iterations.
        Settings.setSizeOfPopulation(populationSize);
        Settings.setIterations(iterations);
                
        
        // Then enable main frame and dispose it.
        mainFrame.setEnabled(true);
        this.dispose();
    }

}

