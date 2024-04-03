package com.visual;

import javax.swing.*;
import javax.swing.border.LineBorder;

import java.awt.*;
import java.awt.event.*;

import com.main.*;
import com.main.Utils.DebugType;

public class Interface {

    private int width;
    private int height;

    private int numRows;
    private int numCols;

    private JFrame mainFrame;
    private JPanel displayPanel;
    private JPanel gridPanel;

    private boolean windowOpen = false;
    private boolean loaded = false;

    public Interface(int width, int height, int numRows, int numCols) {
        this.width = width;
        this.height = height;
        this.numRows = numRows;
        this.numCols = numCols;
        SwingUtilities.invokeLater(this::createAndShowGUI);
    }

    private void createAndShowGUI() {
        mainFrame = new JFrame("SEIR SIMULATION");
        //mainFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        mainFrame.setLayout(new BorderLayout());
    
        // Create the display panel
        displayPanel = createPanel(Color.DARK_GRAY, width); // Default background color
        mainFrame.add(displayPanel, BorderLayout.CENTER); // Add to the center
    
        // Set minimum size for the main frame
        mainFrame.setMinimumSize(new Dimension(600, 300));
    
        // Set mainFrame size
        mainFrame.setSize(1600, 1000); // Set to your desired size
    
        // Set mainFrame location to the center of the screen
        mainFrame.setLocationRelativeTo(null);


        mainFrame.setVisible(true);
        windowOpen = true;

        createGridPanel(numRows, numCols);
    }

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

    private JPanel createPanel(Color color, int width) {
        JPanel panel = new JPanel();
        panel.setBackground(color);
        panel.setPreferredSize(new Dimension(width, mainFrame.getHeight())); // Set preferred size with height of mainFrame
        return panel;
    }

    private JPanel createPanel(Color backgroundColor) {
        JPanel panel = new JPanel();
        panel.setBackground(backgroundColor);
        return panel;
    }

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

    public boolean isActive() {
        return windowOpen && mainFrame != null && mainFrame.isVisible();
    }

    public void displayGrid(Grid grid) {
        Vector2 size = grid.GetSize();
    
        if (!loaded){
            System.out.println("\n");
            while (gridPanel == null) {
                try {
                    Thread.sleep(100); // Add a short delay to reduce CPU usage
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt(); // Restore interrupted status
                }
            }
            System.out.println("Grid initialized successfully.");
        
            // Ensure renderPanel has the correct number of components
            int expectedComponents = size.x * size.y;
            while (gridPanel.getComponentCount() != expectedComponents) {
                try {
                    System.out.print("\rProgress: " + (int) ((double) gridPanel.getComponentCount() / expectedComponents * 100) + "%");
                    Thread.sleep(30); // Add a short delay to reduce CPU usage
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt(); // Restore interrupted status
                }
            }
            System.out.println("Grid elements loaded.");
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
    }
}