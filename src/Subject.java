package com.main;

import com.main.*;
import java.awt.*;


public class Subject {

    public static Color S_COLOR = new Color(255,255,255,255);
    public static Color E_COLOR = new Color(0,0,255,255);
    public static Color I_COLOR = new Color(255,0,0,255);
    public static Color R_COLOR = new Color(0,255,0,255);
    public static double INFECTION_FORCE = 0.5;// default is 0.5

    public enum Status{
        S,
        E,
        I,
        R
    }

    private Vector2 position;

    private Status status;

    private int statusTime;
    private int dE;
    private int dI;
    private int dR;

    public Subject(Vector2 position){
        this.status = Status.S;
        this.dE = (int)Utils.NegExp((double)3);
        this.dI = (int)Utils.NegExp((double)7);
        this.dR = (int)Utils.NegExp((double)100);//Default is 365
        this.position = position;
    }

    public void SetExposed(){
        if(status == Status.S){
            this.status = Status.E;
            statusTime = 0;
        }
    }

    public void SetStatus(Status status){
        statusTime = 0;
        this.status =status;
    }

    public void NextTimeStep(){
        statusTime++;
        switch (status) {
            case E:
                if(statusTime>dE) {CycleNextState();}
            case I:
                if(statusTime>dI) {CycleNextState();}
            case R:
                if(statusTime>dR) {CycleNextState();}                               
            default:
                break;
        }
    }

    public Status GetStatus()
    {
        return status;
    }

    public Color GetColor(){
        switch (status) {
            case S:
                return S_COLOR;
            case E:
                return E_COLOR;
            case I:
                return I_COLOR;
            case R:
                return R_COLOR;                                     
            default:
                return null;
        }
    }

    private void CycleNextState(){
        statusTime = 0;
        status =Status.values()[(status.ordinal()+1)%4];
    }

    public void SetPosition(Vector2 pos){
        position = pos;
    }
    public Vector2 GetPosition(){
        return position;
    }

}
