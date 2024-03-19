package com.visual;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class PreLoadScreen extends JDialog {
    private JTextField heightField;
    private JTextField gridSizeField;
    private JTextField startPopulationField;
    private boolean submitted = false;

    public PreLoadScreen(JFrame parent) {
        super(parent, "Preload Screen", true); // Set dialog modal
        setSize(400, 200);
        setLayout(new GridLayout(4, 2));

        JLabel heightLabel = new JLabel("Enter height of the main screen:");
        heightField = new JTextField();
        JLabel gridSizeLabel = new JLabel("Enter grid size:");
        gridSizeField = new JTextField();
        JLabel startPopulationLabel = new JLabel("Enter start population:");
        startPopulationField = new JTextField();

        JButton submitButton = new JButton("Submit");
        submitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                submitted = true;
                dispose(); // Close the dialog
            }
        });

        add(heightLabel);
        add(heightField);
        add(gridSizeLabel);
        add(gridSizeField);
        add(startPopulationLabel);
        add(startPopulationField);
        add(new JLabel()); // Empty cell for alignment
        add(submitButton);

        setLocationRelativeTo(parent); // Center dialog relative to parent frame
        setVisible(true);
    }

    public boolean isSubmitted() {
        return submitted;
    }

    public String getHeightInput() {
        return heightField.getText();
    }

    public String getGridSizeInput() {
        return gridSizeField.getText();
    }

    public String getStartPopulationInput() {
        return startPopulationField.getText();
    }
}
