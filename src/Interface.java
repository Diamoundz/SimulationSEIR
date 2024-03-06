package com.visual;
import javax.swing.*;
import com.main.*;

public class Interface{
    private JFrame frame;
    private boolean isActive = false;

    public void CreateWindow(int width, int height, boolean isFullscreen)
    {
        frame = new JFrame();
        if (isFullscreen)
        {
            frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        }
        else
        {
            frame.setSize(300, 300); 
        }
        frame.setVisible(true);
        isActive = true;
    }

    public void SplitScreen()
    {
        
    }

    public void DisplayTab(Grid grid)
    {
        int x; int y;
        Vector2 size = grid.GetSize();
        int maxX = size.x; int maxY = size.y;
        for (y = 0; y < maxY; y++)
        {
            for (x = 0; x < maxX; x++)
            {

            }
        }
    }

}
