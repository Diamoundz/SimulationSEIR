package com.visual;

import com.main.*;
import javax.swing.*;
import java.awt.*;

public class Interface {
    private JFrame frame;
    private boolean isActive = false;
    private int controlPanelX = 300;

    private JPanel controlPanel;
    private JPanel displayPanel;

    public void CreateWindow(int width, int height, boolean isFullscreen) {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        double screenWidth = screenSize.getWidth();
        frame = new JFrame();
        if (isFullscreen) {
            frame.setSize(screenSize);
        } 
        else {
            if (width + controlPanelX < screenWidth)
            {
                frame.setSize(width + controlPanelX, height);
            } 
        }
        frame.setVisible(true);
        isActive = true;

        SplitScreen();
    }

    public void SplitScreen() {
        if (!isActive) {
            System.err.println("Error: Window not created yet.");
            return;
        }
    
        // Create two panels for split screen
        JPanel controlPanel = new JPanel();
        JPanel displayPanel = new JPanel();
    
        // Set background colors
        controlPanel.setBackground(Color.WHITE);
        displayPanel.setBackground(Color.BLACK);
    
        // Calculate width for control panel and display panel
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        double screenWidth = screenSize.getWidth();
        int displayPanelWidth = (int) screenWidth - controlPanelX;
    
        // Set layout for the frame's content pane
        frame.getContentPane().setLayout(null); // Set layout to null for direct control
    
        // Set bounds for control panel and display panel
        controlPanel.setBounds(0, 0, controlPanelX, frame.getHeight());
        displayPanel.setBounds(controlPanelX, 0, displayPanelWidth, frame.getHeight());
    
        // Add panels to the content pane
        frame.getContentPane().add(controlPanel);
        frame.getContentPane().add(displayPanel);
    
        // Refresh the frame to display changes
        frame.revalidate();
    }    
    
    public void DisplayGrid(Grid grid) {
        if (frame == null || !isActive) {
            System.err.println("Error: Window not created yet.");
            return;
        }
    
        // Clear existing components from displayPanel
        displayPanel.removeAll();
    
        // Get grid size
        Vector2 size = grid.GetSize();
        int maxX = size.x;
        int maxY = size.y;
    
        // Calculate cell size based on displayPanel size and grid size
        int cellWidth = displayPanel.getWidth() / maxX;
        int cellHeight = displayPanel.getHeight() / maxY;
    
        // Create components for grid elements
        for (int y = 0; y < maxY; y++) {
            for (int x = 0; x < maxX; x++) {
                // Assuming you have a method to get grid element at position (x, y)
                Subject subject = grid.GetSubjectInPosition(new Vector2(x, y));
                
                // Determine color based on subject status
                Color color;
                switch (subject.GetStatus()) {
                    case S:
                        color = Color.BLUE; // Susceptible - Blue
                        break;
                    case E:
                        color = Color.YELLOW; // Exposed - Yellow
                        break;
                    case I:
                        color = Color.RED; // Infected - Red
                        break;
                    case R:
                        color = Color.GREEN; // Recovered - Green
                        break;
                    default:
                        color = Color.WHITE; // Default color
                        break;
                }
    
                // Create and add a colored rectangle representing the subject state
                JPanel cellPanel = new JPanel() {
                    @Override
                    protected void paintComponent(Graphics g) {
                        super.paintComponent(g);
                        g.setColor(color);
                        g.fillRect(0, 0, getWidth(), getHeight());
                    }
                };
                cellPanel.setBounds(x * cellWidth, y * cellHeight, cellWidth, cellHeight);
                displayPanel.add(cellPanel);
            }
        }
    
        // Refresh the displayPanel
        displayPanel.revalidate();
        displayPanel.repaint();
    }
    
}