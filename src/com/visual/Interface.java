package com.visual;

import javax.swing.*;
import javax.swing.border.LineBorder;

import java.awt.*;
import com.main.*;

public class Interface {

    private int width;
    private int height;

    private int numRows;
    private int numCols;

    private JFrame mainFrame;
    private JPanel displayPanel;
    private JPanel gridPanel;
    private JProgressBar progressBar; // Progress bar for displaying loading progress

    private boolean windowOpen = false;
    private boolean loaded = false;

/*--------------------------
Function: Interface constructor
Input: 
  - int width: width of the main frame
  - int height: height of the main frame
  - int numRows: number of rows in the grid
  - int numCols: number of columns in the grid
Output: None
Actions:
  - Initializes the Interface object with the specified dimensions and grid size.
  - Invokes the createAndShowGUI method to create and display the graphical user interface.
  - Sets the windowOpen and loaded flags to false initially.
  - Uses SwingUtilities.invokeLater to ensure GUI-related operations are performed on the Event Dispatch Thread (EDT).
--------------------------*/

    public Interface(int width, int height, int numRows, int numCols) {
        this.width = width;
        this.height = height;
        this.numRows = numRows;
        this.numCols = numCols;
        SwingUtilities.invokeLater(this::createAndShowGUI);
    }

/*--------------------------
Function: createAndShowGUI
Input: None
Output: None
Actions:
  - Creates and configures the main frame, display panel, progress bar, and grid panel.
  - Sets the size, title, and layout of the main frame.
  - Initializes the progress bar with string painting enabled.
  - Sets the windowOpen flag to true and makes the main frame visible.
  - Calls the createGridPanel method to create the grid panel.
--------------------------*/

    private void createAndShowGUI() {
        mainFrame = new JFrame("SEIR SIMULATION");
        mainFrame.setLayout(new BorderLayout());

        displayPanel = createPanel(Color.DARK_GRAY, width);
        mainFrame.add(displayPanel, BorderLayout.CENTER);

        mainFrame.setMinimumSize(new Dimension(600, 300));
        mainFrame.setSize(1600, 1000);
        mainFrame.setLocationRelativeTo(null);

        progressBar = new JProgressBar();
        progressBar.setStringPainted(true);
        mainFrame.add(progressBar, BorderLayout.SOUTH);

        windowOpen = true;
        mainFrame.setVisible(true);

        createGridPanel(numRows, numCols);
    }

/*--------------------------
Function: createGridPanel
Input:
  - int numRows: number of rows in the grid
  - int numCols: number of columns in the grid
Output: None
Actions:
  - Calculates cell size, position, and dimensions based on the number of rows and columns.
  - Creates a panel to contain the grid.
  - Sets the layout for the gridPanel to GridLayout with specified rows and columns.
  - Adds cells to the gridPanel and sets their background color.
--------------------------*/

    private void createGridPanel(int numRows, int numCols) {
        // Get the dimensions of the display panel
        int displayWidth = displayPanel.getWidth();
        int displayHeight = displayPanel.getHeight();
    
        // Calculate the cell size based on the number of rows and columns
        int cellSize = Math.min(displayWidth / numCols, displayHeight / numRows);
    
        // Calculate the new width and height for the grid panel
        int newWidth = numCols * cellSize;
        int newHeight = numRows * cellSize - (cellSize / 10);
    
        // Calculate the position to center the grid panel within the display panel
        int posX = (displayWidth - newWidth) / 2;
        int posY = (displayHeight - newHeight) / 2;
    
        // Create a panel to contain the grid
        gridPanel = new JPanel();
        gridPanel.setBackground(Color.BLACK);
    
        // Set the layout for the gridPanel to GridLayout with specified rows and columns
        gridPanel.setLayout(new GridLayout(numRows, numCols));
    
        // Add the container panel to the display panel
        displayPanel.setLayout(null); // Ensure that displayPanel uses null layout to allow manual positioning
        displayPanel.add(gridPanel);
    
        // Set the bounds of the gridPanel to center it within the displayPanel
        gridPanel.setBounds(posX, posY, newWidth, newHeight);
        
        boolean borderActive = true;
        if (cellSize == 1){borderActive = false;}
    
        // Add cells to the gridPanel
        int total = numRows * numCols;
        for (int i = 0; i < total; i++) {
            JPanel cell = new JPanel();
            cell.setBackground(Color.BLACK); // Set cell background color
            if (borderActive){
                cell.setBorder(new LineBorder(Color.DARK_GRAY, 1)); // Set cell border color
            }
            gridPanel.add(cell); // Add cell to the gridPanel
        }
    }

/*--------------------------
Function: createPanel (overloaded)
Input:
  - Color color: background color of the panel
  - int width: width of the panel
Output: JPanel object
Actions:
  - Creates a JPanel with the specified background color and preferred width.
--------------------------*/

    private JPanel createPanel(Color color, int width) {
        JPanel panel = new JPanel();
        panel.setBackground(color);
        panel.setPreferredSize(new Dimension(width, mainFrame.getHeight())); // Set preferred size with height of mainFrame
        return panel;
    }

/*--------------------------
Function: createPanel (overloaded)
Input:
  - Color backgroundColor: background color of the panel
Output: JPanel object
Actions:
  - Creates a JPanel with the specified background color.
--------------------------*/

    private JPanel createPanel(Color backgroundColor) {
        JPanel panel = new JPanel();
        panel.setBackground(backgroundColor);
        return panel;
    }

/*--------------------------
Function: createButtonPanel
Input:
  - String buttonText1: text for the first button
  - String buttonText2: text for the second button
Output: JPanel object
Actions:
  - Creates a panel with a dark gray background.
  - Creates buttons with the specified text and aligns them to the center.
  - Adds spacing between buttons and aligns them horizontally.
  - Returns the button panel.
--------------------------*/

    private JPanel createButtonPanel(String buttonText1, String buttonText2) {
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.DARK_GRAY); // Set background color to dark grey

        JButton button1 = createButton(buttonText1);

        // Set button alignment to center
        button1.setAlignmentX(Component.CENTER_ALIGNMENT);
        // Set preferred size of buttons to match
        button1.setPreferredSize(new Dimension(120, button1.getPreferredSize().height));
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
        buttonPanel.add(Box.createHorizontalGlue());
        buttonPanel.add(button1);

        if(buttonText2 !=""){
            JButton button2 = createButton(buttonText2);
            button2.setAlignmentX(Component.CENTER_ALIGNMENT);
            button2.setPreferredSize(new Dimension(120, button2.getPreferredSize().height));
            buttonPanel.add(Box.createRigidArea(new Dimension(10, 0))); // Add spacing between buttons
            buttonPanel.add(button2);
            buttonPanel.add(Box.createHorizontalGlue());
        }


        return buttonPanel;
    }
    private JButton createButton(String text) {
        JButton button = new JButton(text);
        return button;
    }

    public JFrame getMainFrame() {
        return mainFrame;
    }

    public JPanel getDisplayPanel() {
        return displayPanel;
    }

    public JPanel getGridPanel() {
        return gridPanel;
    }

/*--------------------------
Function: isActive
Input: None
Output: boolean value
Actions:
  - Checks if the window is open, the main frame is not null, visible, and loaded.
  - Returns true if all conditions are met; otherwise, returns false.
--------------------------*/

    public boolean isActive() {
        return windowOpen && mainFrame != null && mainFrame.isVisible() && loaded;
    }

/*--------------------------
Function: displayGrid
Input:
  - Grid grid: grid object containing simulation data
Output: None
Actions:
  - Displays the grid by setting the background color of each cell panel based on the corresponding cell color in the grid object.
  - Updates the progress bar to reflect the simulation progress.
--------------------------*/

    public void displayGrid(Grid grid) {
        Vector2 size = grid.GetSize();
    
        if (!loaded){
            while (gridPanel == null) {
                try {
                    Thread.sleep(100); // Add a short delay to reduce CPU usage
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt(); // Restore interrupted status
                }
            }
        
            // Ensure renderPanel has the correct number of components
            int expectedComponents = size.x * size.y;
            while (gridPanel.getComponentCount() != expectedComponents) {
                try {
                    System.out.print("\rGUI loading progress: " + (int) ((double) gridPanel.getComponentCount() / expectedComponents * 100) + "%");
                    Thread.sleep(30); // Add a short delay to reduce CPU usage
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt(); // Restore interrupted status
                }
            }
            System.out.println("\nGUI elements loaded.\n");
            loaded = true;
        }
    
        for (int i = 0; i < size.y; i++) {
            for (int j = 0; j < size.x; j++) {
                // Get the cell at position (i, j)
                Vector2 pos = new Vector2(i, j);
                Color cellColor = grid.GetCellColor(pos);
    
                // Calculate the index of the cell in the renderPanel
                int index = i * size.x + j;
    
                // Retrieve the corresponding JPanel within the renderPanel
                JPanel cellPanel = (JPanel) gridPanel.getComponent(index);
    
                // Set the background color of the cell panel based on the cell's content
                cellPanel.setBackground(cellColor);
            }
        }
        int maxIter = Main.SIMULATION_COUNT * Main.SIMULATION_ITERATIONS;
        int percentage = (int)((double) Main.instance.currentSimulationStep / maxIter * 100);
        progressBar.setValue(percentage);
        System.out.println(percentage);
    }
}