package com.main;

import com.main.*;

public class Subject {

    public enum Status{
        S,
        E,
        I,
        R
    }

    private Vector2 position;

    private Status status;
    private float statusTime;

    private double dE;
    private double dI;
    private double dR;

    public Subject(){
        this.status = Status.S;
        this.dE = Utils.NegExp(3);
        this.dI = Utils.NegExp(7);
        this.dR = Utils.NegExp(365);
    }

    public Status GetStatus()
    {
        return status;
    }
}
