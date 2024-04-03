package com.main;

import java.util.ArrayList;
import java.util.HashMap;
import java.awt.*;

public class Grid {
    private int xCellCount;
    private int yCellCount;
    private ArrayList<Subject>[][] cells;

    private ArrayList<Subject> population = new ArrayList<Subject>();

    private int stepCount = 0;
    private int initialPopulationCount = 0;
    private int deadPopulation = 0;

    @SuppressWarnings("unchecked")
    public Grid(int xSize, int ySize){
        this.xCellCount = xSize;
        this.yCellCount = ySize;
        this.cells =  new ArrayList[xCellCount][yCellCount];
        for(int x = 0; x < xCellCount; x++){
            for(int y = 0; y < yCellCount; y++){
                this.cells[x][y] = new ArrayList<Subject>();
            }
        }
    }

    
    private Vector2 ClampPos(Vector2 pos)
    {
        Vector2 newPos = pos;
        if(pos.x >= xCellCount){
            newPos.x = -(xCellCount - pos.x);
        }
        if(pos.x < 0){
            newPos.x = xCellCount+pos.x;
        }
        if(pos.y >= yCellCount){
            newPos.y = -(yCellCount - pos.y);
        }
        if(pos.y < 0){
            newPos.y = yCellCount+pos.y;
        }
        return newPos;
    }

    private boolean GetExposed(Vector2 pos){
        int expositionCount = GetExposedCount(pos);
        double rand = Main.instance.rand.nextDouble();
        double p = 1-Math.exp(-Subject.INFECTION_FORCE*expositionCount);
        if(rand<p){
            return true;
        }
        else{
            return false;
        }
    }


    private int GetExposedCount(Vector2 pos){
        int infectedCount =0;
        for(int i = -1; i<2 ; i++){
            for(int j = -1; j<2 ; j++){
                Vector2 newPos = new Vector2(pos.x+i, pos.y + j);
                if(newPos.x>=0 && newPos.x<xCellCount 
                && newPos.y>=0 && newPos.y<yCellCount){
                    for(int k = 0; k<cells[newPos.x][newPos.y].size(); k++){
                        if(cells[newPos.x][newPos.y].get(k).GetStatus() == Subject.Status.I){
                            infectedCount+=1;
                        }
                    }
                }
            }
        }
        return infectedCount;
    }

    private Vector2 GetRandomAdjacentPosition(Vector2 pos){
        return ClampPos(pos.add(new Vector2(Utils.RandomRange(-1, 1),Utils.RandomRange(-1, 1))));
    }
    private Vector2 GetRandomPosition(){
        return (new Vector2(Utils.RandomRange(0, xCellCount-1),Utils.RandomRange(0, yCellCount-1)));
    }

    public Subject GetSubjectInPosition(Vector2 position){
        if(cells[position.x][position.y].size()>0){
            return (cells[position.x][position.y]).get(0);
        }
        else{
            return null;
        }
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

        int max = cells[coord.y][coord.x].size();

        if(max >0){
            double r = 0,g = 0,b = 0,a = 0;

            for(int i = 0;i<max;i++){
                r += cells[coord.y][coord.x].get(i).GetColor().getRed()/max;
                g += cells[coord.y][coord.x].get(i).GetColor().getGreen()/max;
                b += cells[coord.y][coord.x].get(i).GetColor().getBlue()/max;
                a += cells[coord.y][coord.x].get(i).GetColor().getAlpha()/max;
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

        while(temp.size()>0){
            int randIndex = Utils.RandomRange(0, temp.size()-1);
            Subject subj = temp.get(randIndex);
            Vector2 pos = subj.GetPosition();

            Vector2 newPos = null;

            if(Main.ENABLE_TELEPORT_MOVEMENT){
                newPos = GetRandomPosition();
            }
            else{
                newPos = GetRandomAdjacentPosition(pos);
            }

            MoveSubject(subj,pos,newPos);

            if(subj.NextTimeStep()){
                if(subj.GetStatus() == Subject.Status.S){
                    if(GetExposed(newPos)){
                        subj.SetExposed();
                    }
                }
            }
            else{
                population.remove(subj);
                cells[newPos.x][newPos.y].remove(subj);
                deadPopulation++;
            }
            temp.remove(subj);
        }
    }

    public void DebugPopulationInfo(){
        HashMap<Subject.Status,Integer> info = new HashMap<Subject.Status,Integer>();
        info.put(Subject.Status.S, 0);
        info.put(Subject.Status.E, 0);
        info.put(Subject.Status.I, 0);
        info.put(Subject.Status.R, 0);
        for(int i = 0; i<population.size();i++){
            info.replace(population.get(i).GetStatus(), info.get(population.get(i).GetStatus())+1);
        }

        System.out.println("=============================================================");
        System.out.println("Initial population : "+initialPopulationCount);
        for(Subject.Status status : Subject.Status.values()){
            System.out.println("Population count of status : "+status.name() +" : "+ info.get(status) + "(" + (info.get(status)/(float)initialPopulationCount)*100+ "%)" );
        }
        System.out.println("Population count of dead : " + deadPopulation + "(" +(deadPopulation/(float)initialPopulationCount)*100 + "%)" );
        System.out.println("=============================================================");
    }


    public void FillGrid(int popCount, int infectedCount){
        int remainingInfected = infectedCount;
        for(int x = 0 ; x < popCount; x++ ){

            Vector2 pos = new Vector2(Utils.RandomRange(0, xCellCount-1),Utils.RandomRange(0, yCellCount-1));
            Subject subject = new Subject(pos);
            population.add(subject);
            cells[pos.x][pos.y].add(subject);

            if(remainingInfected>0){
                subject.SetStatus(Subject.Status.I);
                remainingInfected--;
            }

        }
        initialPopulationCount = popCount;
    }   
    
}