package com.visual;

import javax.swing.*;
import java.awt.*;

public class ProgressBarWindow extends JFrame {
    private JProgressBar progressBar;

    public ProgressBarWindow() {
        setTitle("Progress");
        setSize(300, 100);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Close only this window when closed
        setLocationRelativeTo(null); // Center the window

        progressBar = new JProgressBar();
        progressBar.setStringPainted(true); // Show percentage text on the progress bar

        JPanel panel = new JPanel();
        panel.add(progressBar);

        getContentPane().add(panel);
    }

    // Method to update the progress of the progress bar
    public void setProgress(int progress) {
        progressBar.setValue(progress);
    }

    // Method to destroy (dispose) the progress bar window
    public void destroy() {
        dispose(); // Close the window
    }
}