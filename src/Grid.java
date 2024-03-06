package com.main;

import javax.swing.DebugGraphics;
import com.main.*;

public class Grid {
    private static int xCellCount;
    private static int yCellCount;
    private Subject[][] cells = new Subject[xCellCount][yCellCount];

    public Grid(int xSize, int ySize){
        xCellCount = xSize;
        yCellCount = ySize;
    }

    private boolean CheckPos(int xPos, int yPos)
    {
        return (xPos > 0 && xPos < xCellCount && yPos > 0 && yPos < yCellCount);
    }

    public Subject GetSubjectInPosition(Vector2 pos)
    {
        if (!CheckPos(pos.x, pos.y)){System.err.println("Position invalid"); return null;}
        return cells[pos.x][pos.y];
    }

    public void SetSubjectInPosition(Subject subject, Vector2 pos)
    {
        if (!CheckPos(pos.x, pos.y)){System.err.println("Position invalid");}
        cells[pos.x][pos.y] = subject;
    }

    public Vector2 GetSize()
    {
        return new Vector2(xCellCount, yCellCount);
    }

    public Vector2 GetPositionFromSubject(Subject subject)
    {
        Vector2 pos= new Vector2(-1,-1);
        for(int x = 0; x < xCellCount; x++){
            for(int y = 0; y< yCellCount; y++){
                if(cells[x][y] == subject){
                    pos = new Vector2(x, y);
                }
            }
        }
        return pos;
    }
    public void MoveSubjectToPosition(Subject subject, Vector2 targetPosition){
        Vector2 initialPosition = subject.position;
        Subject temp = GetSubjectInPosition(targetPosition);
        cells[targetPosition.x][targetPosition.y] = subject;
        cells[initialPosition.x][initialPosition.y] = temp;
    }
    
}