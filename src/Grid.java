package com.main;

import com.main.*;
import java.util.ArrayList;
import java.util.Random;

public class Grid {
    private static int xCellCount;
    private static int yCellCount;
    private ArrayList<Subject>[][] cells;

    public Grid(int xSize, int ySize){
        this.xCellCount = xSize;
        this.yCellCount = ySize;
        this.cells =  new ArrayList[xCellCount][yCellCount];
        for(int x = 0; x < xCellCount; x++){
            for(int y = 0; y < xCellCount; y++){
                this.cells[x][y] = new ArrayList<Subject>();
            }
        }
    }

    private Vector2 ClampPos(Vector2 pos)
    {
        Vector2 newPos = pos;
        if(pos.x > xCellCount){
            newPos.x = -(xCellCount - pos.x);
        }
        if(pos.x < 0){
            newPos.x = xCellCount*pos.x;
        }
        if(pos.y > yCellCount){
            newPos.y = -(yCellCount - pos.y);
        }
        if(pos.y < 0){
            newPos.y = yCellCount*pos.y;
        }
        return newPos;
    }

    public Vector2 GetSize()
    {
        return new Vector2(xCellCount, yCellCount);
    }


    public void FillGrid(int popCount){
        for(int x = 0 ; x < popCount; x++ ){
            Subject subject = new Subject();
            cells[Utils.RandomRange(0, xCellCount)][Utils.RandomRange(0, yCellCount)].add(subject);
        }
    }   
    
}