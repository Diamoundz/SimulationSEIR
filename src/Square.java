package com.visual;

import javax.swing.*;
import java.awt.*;

public class Square extends JComponent {
    private Color color;

    public Square(Color color, int dimX, int dimY) {
        this.color = color;
        setPreferredSize(new Dimension(dimX, dimY)); // Set preferred size for the square
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(color);
        g.fillRect(0, 0, getWidth(), getHeight()); // Fill the square with the specified color
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
        repaint(); // Repaint the component to reflect the color change
    }
}