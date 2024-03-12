package com.main;

import com.main.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.Vector;
import java.awt.*;

public class Grid {
    private int xCellCount;
    private int yCellCount;
    private ArrayList<Subject>[][] cells;

    @SuppressWarnings("unchecked")
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

    public Subject GetSubjectInPosition(Vector2 position){
        if(cells[position.x][position.y].size()>0){
            return (cells[position.x][position.y]).get(0);
        }
        else{
            return null;
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

    public Color GetCellColor(Vector2 coord){
        double r = 0,g = 0,b = 0,a = 0;
        int max = cells[coord.x][coord.y].size();
        for(int i = 0;i<max;i++){
            r += cells[coord.x][coord.y].get(i).GetColor().getRed()/max;
            g += cells[coord.x][coord.y].get(i).GetColor().getGreen()/max;
            b += cells[coord.x][coord.y].get(i).GetColor().getBlue()/max;
            a += cells[coord.x][coord.y].get(i).GetColor().getAlpha()/max;
        }
        return new Color((int)r,(int)g,(int)b,(int)a);
    }

    public void NextStep(){
        
    }


    public void FillGrid(int popCount){
        for(int x = 0 ; x < popCount; x++ ){
            Subject subject = new Subject();
            cells[Utils.RandomRange(0, xCellCount)][Utils.RandomRange(0, yCellCount)].add(subject);
        }
    }   
    
}