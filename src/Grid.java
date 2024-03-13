package com.main;

import com.main.*;
import com.main.Utils.DebugType;

import java.util.ArrayList;
import java.util.Random;
import java.util.Vector;
import java.awt.*;

public class Grid {
    private int xCellCount;
    private int yCellCount;
    private ArrayList<Subject>[][] cells;

    private ArrayList<Subject> population = new ArrayList<Subject>();

    private int stepCount;

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

    public void MoveSubject(Subject subj,Vector2 pos1, Vector2 pos2){
        cells[pos1.x][pos1.y].remove(subj);
        cells[pos2.x][pos2.y].add(subj);
        subj.SetPosition(pos2);
    }

    public Vector2 GetSize()
    {
        return new Vector2(xCellCount, yCellCount);
    }

    public int GetStepCount(){
        return stepCount;
    }

    public Color GetCellColor(Vector2 coord){

        int max = cells[coord.x][coord.y].size();

        if(max >0){
            double r = 0,g = 0,b = 0,a = 0;

            for(int i = 0;i<max;i++){
                r += cells[coord.x][coord.y].get(i).GetColor().getRed()/max;
                g += cells[coord.x][coord.y].get(i).GetColor().getGreen()/max;
                b += cells[coord.x][coord.y].get(i).GetColor().getBlue()/max;
                a += cells[coord.x][coord.y].get(i).GetColor().getAlpha()/max;
            }
            return new Color((int)r,(int)g,(int)b,(int)a);
        }
        else{
            return new Color(0,0,0,255);
        }

    }

    public void NextStep(){

        stepCount ++;
        ArrayList<Subject> temp = new ArrayList<Subject>();
        temp.addAll(population);
        for(int i = 0; i<population.size();i++){
            int randIndex = Utils.RandomRange(0, temp.size());
            Subject subj = temp.get(randIndex);
            Vector2 pos = subj.GetPosition();
            MoveSubject(subj,pos,new Vector2(Utils.RandomRange(0, xCellCount),Utils.RandomRange(0, yCellCount)));
            temp.remove(subj);
        }
    }


    public void FillGrid(int popCount){
        for(int x = 0 ; x < popCount; x++ ){

            Vector2 pos = new Vector2(Utils.RandomRange(0, xCellCount),Utils.RandomRange(0, yCellCount));
            Subject subject = new Subject(pos);
            population.add(subject);
            cells[pos.x][pos.y].add(subject);
        }
    }   
    
}