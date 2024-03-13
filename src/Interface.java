package com.visual;

import com.main.*;
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

    public void CreateWindow(int height, boolean isFullscreen)
    {
        this.height = height;
        this.width = height + controlPanelX;
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        double screenWidth = screenSize.getWidth();
        frame = new JFrame();
        
        if (isFullscreen) 
        {
            frame.setSize(screenSize);
        } 
        else 
        {
            if (width + controlPanelX < screenWidth)
            {
                frame.setSize(width, height);
            } 
        }
        // Make the frame not resizable
        frame.setResizable(false);

        frame.setVisible(true);
        isActive = true;

        SplitScreen();
    }

    public void SplitScreen() 
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
        MakeGrid(displayPanel, 20);
        renderPanel.setBackground(Color.DARK_GRAY);
    
        // Add panels to the content pane
        frame.getContentPane().add(controlPanel);
        frame.getContentPane().add(displayPanel);
    
        // Refresh the frame to display changes
        frame.revalidate();
        frame.setSize(height + controlPanelX, height + 37);
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