package com.visual;

import javax.swing.*;
import java.awt.*;

public class Interface extends JFrame {

    private int cellWidth;
    private int cellHeight;

    public Interface(int cellXN, int cellYN) {
        setTitle("Test Frame");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Set the size of each cell based on the number of cells in X and Y directions
        int cellSize = 1000 / Math.max(cellXN, cellYN);
        this.cellWidth = cellXN * cellSize;
        this.cellHeight = cellYN * cellSize;

        // Width of the side bar
        int sidebarWidth = 400;

        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.setColor(Color.BLACK); // Set background color
                g.fillRect(0, 0, getWidth(), getHeight()); // Fill the background

                g.setColor(Color.WHITE); // Set grid color

                // Draw vertical lines excluding the sidebar area
                for (int x = sidebarWidth; x < getWidth(); x += cellSize) {
                    g.drawLine(x, 0, x, getHeight());
                }

                // Draw horizontal lines
                for (int y = 0; y < getHeight(); y += cellSize) {
                    g.drawLine(sidebarWidth, y, getWidth(), y);
                }

                // Draw gray side bar on the left
                g.setColor(Color.GRAY);
                g.fillRect(0, 0, sidebarWidth, getHeight());
            }
        };

        setContentPane(panel);
        int offsetx = 1;
        int offsety = 38;
        setSize(cellWidth + offsetx + sidebarWidth, cellHeight + offsety); // Set JFrame size to match the calculated cell size with the additional side bar width
        setVisible(true);
    }

    public static void main(String[] args) {
        // Ask the user for the number of cells in the X direction
        String inputX = JOptionPane.showInputDialog(null, "Enter number of cells in X direction:");
        int cellWidth = Integer.parseInt(inputX);

        // Ask the user for the number of cells in the Y direction
        String inputY = JOptionPane.showInputDialog(null, "Enter number of cells in Y direction:");
        int cellHeight = Integer.parseInt(inputY);

        // Create the Interface instance with the specified number of cells in X and Y directions
        new Interface(cellWidth, cellHeight);
    }
}
