/*
 * FIND FILES TO COMPILE : find ./src/ -type f -name "*.java" > sources.txt
 * COMPILE : javac -d ./build/ @sources.txt
 * RUN : java -cp ./build/ com.main.Main
 */

package com.main;
import com.main.*;
import com.visual.*;
import com.main.MersenneTwister;

public class Main{

    // Logic constants
    public static Main instance;
    private static double clockRunSpeed = (1f/60f)*1000f;
    private boolean isRunning = false;

    public long startTime;
    public MersenneTwister rand;

    public Interface visual;

    public Main(){
        instance = this;
    }

    public static void main(String[] args){
        Main mainProgram = new Main();
        mainProgram.startTime = System.nanoTime();

        mainProgram.rand = new MersenneTwister(new int[]{0x123, 0x234, 0x345, 0x456});

        mainProgram.isRunning = true;
        mainProgram.Awake();
        mainProgram.Start();

        while (mainProgram.isRunning) {
            mainProgram.Update();
            Utils.wait((int)clockRunSpeed);
        }
    }

    private void Awake(){
        Utils.Debug("Program awake");
    }
    
    private void Start(){
        Utils.Debug("Program start");

        Grid grid = new Grid(100,100);
        grid.FillGrid(10);

        VisualTest1();
    }

    private void Update(){

    }

    private void VisualTest1()
    {
        visual = new Interface();
        visual.CreateWindow(1500,600, false);
    }



}