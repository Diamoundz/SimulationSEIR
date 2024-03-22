package com.visual;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import com.main.*;
import com.main.Utils.DebugType;

public class Interface {

    private int CONTROL_PANEL_X = 300;

    private int width;
    private int height;

    private int numRows;
    private int numCols;

    private JFrame mainFrame;
    private JPanel controlPanel;
    private JPanel displayPanel;
    private JPanel gridPanel;

    private boolean windowOpen = false;

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
    
        // Create control panel
        controlPanel = createPanel(Color.DARK_GRAY, CONTROL_PANEL_X); // Fixed width of 300 pixels
        controlPanel.setBorder(new LineBorder(Color.BLACK, 1));
        mainFrame.add(controlPanel, BorderLayout.WEST);
    
        // Create the display panel
        displayPanel = createPanel(Color.DARK_GRAY, width - CONTROL_PANEL_X); // Default background color
        controlPanel.setBorder(new LineBorder(Color.BLACK, 1));
        mainFrame.add(displayPanel, BorderLayout.CENTER); // Add to the center
    
        // Set minimum size for the main frame
        mainFrame.setMinimumSize(new Dimension(600, 300));
    
        // Set mainFrame size
        mainFrame.setSize(1600, 1000); // Set to your desired size
    
        // Set mainFrame location to the center of the screen
        mainFrame.setLocationRelativeTo(null);
    
        // Create buttons for Next Step and Previous Step
        JPanel nextPrevPanel = createButtonPanel("Previous Step","Next Step",
                 "Previous Step clicked!","Next Step clicked!");
    
        // Create buttons for Play auto and Pause
        JPanel playPausePanel = createButtonPanel("Play", "Pause",
                "Play clicked!", "Pause clicked!");
    
        // Center button panels vertically with space between them
        controlPanel.setLayout(new BoxLayout(controlPanel, BoxLayout.Y_AXIS));
        controlPanel.add(Box.createVerticalGlue());
        controlPanel.add(nextPrevPanel);
        controlPanel.add(Box.createRigidArea(new Dimension(0, 10))); // Add spacing between button panels
        controlPanel.add(playPausePanel);
        controlPanel.add(Box.createVerticalGlue());

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
        for (int i = 0; i < numRows * numCols; i++) {
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

    private JPanel createButtonPanel(String buttonText1, String buttonText2, String action1, String action2) {
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.DARK_GRAY); // Set background color to dark grey

        JButton button1 = createButton(buttonText1, e -> {
            JOptionPane.showMessageDialog(mainFrame, action1);
        });
        JButton button2 = createButton(buttonText2, e -> {
            JOptionPane.showMessageDialog(mainFrame, action2);
        });

        // Set button alignment to center
        button1.setAlignmentX(Component.CENTER_ALIGNMENT);
        button2.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Set preferred size of buttons to match
        button1.setPreferredSize(new Dimension(120, button1.getPreferredSize().height));
        button2.setPreferredSize(new Dimension(120, button2.getPreferredSize().height));

        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
        buttonPanel.add(Box.createHorizontalGlue());
        buttonPanel.add(button1);
        buttonPanel.add(Box.createRigidArea(new Dimension(10, 0))); // Add spacing between buttons
        buttonPanel.add(button2);
        buttonPanel.add(Box.createHorizontalGlue());

        return buttonPanel;
    }

    private JButton createButton(String text, ActionListener actionListener) {
        JButton button = new JButton(text);
        button.addActionListener(actionListener);
        return button;
    }

    public JFrame getMainFrame() {
        return mainFrame;
    }

    public JPanel getControlPanel() {
        return controlPanel;
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
                Thread.sleep(100); // Add a short delay to reduce CPU usage
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt(); // Restore interrupted status
            }
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