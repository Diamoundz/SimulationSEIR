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
    public Subject GetSubjectInPosition(Vector2 pos){
        return cells[pos.x][pos.y];
    }
    public Vector2 GetPositionFromSubject(Subject subject){
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