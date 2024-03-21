package com.visual;

import com.main.*;
import com.visual.*;
import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class Interface 
{
    private JFrame frame;
    private boolean isActive = false;
    private int controlPanelX = 300;

    private JPanel controlPanel;
    private JPanel displayPanel;
    private JPanel renderPanel;

    private int width;
    public int height;

    public void CreateWindow()
    {
        OnInit();

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        frame = new JFrame();
        
        if (height < screenSize.getHeight())
        {
            frame.setSize(width, height);
        }
        else
        {
            height = (int) screenSize.getHeight();
            this.width = height + controlPanelX;
            frame.setSize(width, height);
        }

        // Make the frame not resizable
        frame.setResizable(false);


        isActive = true;

        SplitScreen();
    }

    private void OnInit() {
        JFrame frame = new JFrame("Main Screen");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 500);
        frame.setVisible(false);
    
        PreLoadScreen preloadScreen = new PreLoadScreen(frame);
        while (!preloadScreen.isSubmitted()) {
            try {
                Thread.sleep(100); // Sleep to avoid busy waiting
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        frame.dispose();
    
        // Once the preload screen is closed, continue with the rest of the code
        String heightInput = preloadScreen.getHeightInput();
        String gridSizeInput = preloadScreen.getGridSizeInput();
        String startPopulationInput = preloadScreen.getStartPopulationInput();

        if(!heightInput.equals("") && !gridSizeInput.equals(gridSizeInput) && !startPopulationInput.equals(startPopulationInput)){
            // Convert strings into integers
            int height = Integer.parseInt(heightInput);
            int gridSize = Integer.parseInt(gridSizeInput);
            int startPopulation = Integer.parseInt(startPopulationInput);

            // Now you can process the input values
            Main.instance.grid = new Grid(gridSize, gridSize);
            Main.instance.grid.FillGrid(startPopulation,20);

            this.height = height;
            this.width = height + controlPanelX;
        }
        else{
            this.height = 900;
            this.width = 900 + controlPanelX;
        }


    }    

    public boolean IsActive() {
        return frame != null && frame.isVisible();
    }

    private void SplitScreen() 
    {
        if (!isActive)
        {
            System.err.println("Error: Window not created yet.");
            return;
        }
    
        // Create two panels for split screen
        JPanel controlPanel = new JPanel();
        JPanel displayPanel = new JPanel();
    
        // Set background colors
        controlPanel.setBackground(Color.DARK_GRAY);
        displayPanel.setBackground(Color.BLACK);
    
        // Calculate width for control panel and display panel
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    
        // Set layout for the frame's content pane
        frame.getContentPane().setLayout(null); // Set layout to null for direct control
    
        // Set bounds for control panel and display panel
        controlPanel.setBounds(0, 0, controlPanelX, frame.getHeight());
        displayPanel.setBounds(controlPanelX, 0, frame.getHeight(), frame.getHeight());
        MakeGrid(displayPanel, Main.instance.grid.GetSize().x);
        renderPanel.setBackground(Color.DARK_GRAY);
    
        // Add panels to the content pane
        frame.getContentPane().add(controlPanel);
        frame.getContentPane().add(displayPanel);
    
        // Refresh the frame to display changes
        frame.revalidate();
        frame.setSize(width, height + 37);
    }

    private void MakeGrid(JPanel panel, int size) {
        // Create a new JPanel for rendering
        renderPanel = new JPanel();
        int caseborder = 5;
        renderPanel.setBorder(BorderFactory.createEmptyBorder(caseborder, caseborder, caseborder, caseborder)); // Adding padding for aesthetic purpose
        renderPanel.setLayout(new GridBagLayout()); // Use GridBagLayout for centering the square panel
        
        // Get the height of the provided panel
        int panelHeight = panel.getHeight();
        
        // Set the size of the square panel
        Dimension squareDimension = new Dimension(panelHeight, panelHeight);
        renderPanel.setPreferredSize(squareDimension);
        
        // Create cells for the grid and add them to the renderPanel
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                JPanel cell = new JPanel();
                cell.setPreferredSize(new Dimension(squareDimension.width / size, squareDimension.height / size));
                cell.setBorder(BorderFactory.createLineBorder(new Color(64, 64, 64))); // Dark grey color for grid lines
                renderPanel.add(cell, new GridBagConstraints(j, i, 1, 1, 1.0, 1.0,
                                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                                new Insets(0, 0, 0, 0), 0, 0));
            }
        }
        
        // Center the square panel
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = GridBagConstraints.CENTER; // Center horizontally
        gbc.gridy = GridBagConstraints.CENTER; // Center vertically
        
        // Add the square panel to the renderPanel
        panel.setLayout(new GridBagLayout());
        panel.add(renderPanel, gbc);
    }    
    
    public void DisplayGrid(Grid grid) {
        Vector2 size = grid.GetSize();
    
        // Ensure renderPanel exists
        if (renderPanel == null) {
            System.err.println("Error: renderPanel is null.");
            return;
        }
    
        // Ensure renderPanel has the correct number of components
        int expectedComponents = size.x * size.y;
        if (renderPanel.getComponentCount() != expectedComponents) {
            System.err.println("Error: renderPanel has incorrect number of components.");
            return;
        }
    
        for (int i = 0; i < size.y; i++) {
            for (int j = 0; j < size.x; j++) {
                // Get the cell at position (i, j)
                Vector2 pos = new Vector2(i, j);
                Color cellColor = grid.GetCellColor(pos);
    
                // Calculate the index of the cell in the renderPanel
                int index = i * size.x + j;
    
                // Retrieve the corresponding JPanel within the renderPanel
                JPanel cellPanel = (JPanel) renderPanel.getComponent(index);
    
                // Set the background color of the cell panel based on the cell's content
                cellPanel.setBackground(cellColor);
            }
        }
    }
}