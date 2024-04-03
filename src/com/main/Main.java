package com.main;
import com.visual.*;

import java.io.File;
import java.text.DecimalFormat;
import java.util.Scanner;


public class Main{

    // Logic constants

    public static Main instance;
    private static double clockRunSpeed = (1f/60f)*1000f;

    public static boolean USE_GUI = false; // Default : false;
    public static boolean WAIT_FOR_USER_INPUT = false; // Default : false
    public static boolean ENABLE_TELEPORT_MOVEMENT = true; // Default : true
    public static boolean ENABLE_SUBJECT_DEATH = false; // Default : false

    public static int SIMULATION_ITERATIONS = 25; // Default : 730
    public static int SIMULATION_COUNT = 3; // Default : 100

    public static int GRID_SIZE_X = 300; // Default : 300
    public static int GRID_SIZE_Y = 300; // Default : 300

    public static int INITIAL_POP_COUNT = 20000; // Default : 20000
    public static int INITIAL_INFECTED_COUNT = 20; //Default : 20

    public Utils.DebugType debugType = Utils.DebugType.none;
    public double countedFps;
    public int countedFpsIterations;
    public long startTime;
    public boolean isPaused;
    public int currentSimulationStep;

    private boolean isRunning = false;
    
    public MersenneTwister rand;
    public Scanner scanner = new Scanner(System.in);
    public Interface gui;
    public Grid grid;


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
            if(!mainProgram.isPaused){
                mainProgram.Update();
                Utils.wait((int)clockRunSpeed);
            }
        }
        mainProgram.End();
    }

    private void Awake(){
        Utils.Debug("Program awake");
    }
    
    private void Start(){
        Utils.Debug("Program start");

        CSVGenerator.ClearData("./data/");
        
        Main.instance.grid = new Grid(GRID_SIZE_X, GRID_SIZE_Y);
        Main.instance.grid.FillGrid(INITIAL_POP_COUNT,INITIAL_INFECTED_COUNT);
        
        if(USE_GUI){
            gui = new Interface(1000, 600, GRID_SIZE_X, GRID_SIZE_Y);
            gui.displayGrid(grid);
        }

    }

    private void Update(){
        int startTime =Utils.GetRunTime();

        if(WAIT_FOR_USER_INPUT){
            scanner.nextLine();
            Utils.Debug("Current step initialized : " + grid.GetStepCount(),Utils.DebugType.timeStamps);
        }

        grid.NextStep();
        currentSimulationStep++;

        if(USE_GUI){
            if (gui.isActive()){gui.displayGrid(grid);}
            else {isRunning = false;}
        }
        else{
            Utils.UpdateProgressBar(currentSimulationStep, SIMULATION_COUNT*SIMULATION_ITERATIONS);
        }

        int endTime =Utils.GetRunTime();
        int frameTime = endTime-startTime;

        Main.instance.countedFps += frameTime;
        Main.instance.countedFpsIterations++;

        if(debugType == Utils.DebugType.stepInfo ||debugType==Utils.DebugType.all)
        {
            grid.DebugPopulationInfo();
        }

        if(currentSimulationStep == SIMULATION_COUNT *SIMULATION_ITERATIONS){
            if(!USE_GUI){
                Utils.UpdateProgressBar(1,1);
                System.out.print("\n");
            }
            Utils.Debug("Completed "+SIMULATION_COUNT*SIMULATION_ITERATIONS +" iterations, Shutting down ...");
            isRunning = false;
        }
        if(currentSimulationStep%SIMULATION_ITERATIONS ==0){
            Main.instance.grid = new Grid(GRID_SIZE_X, GRID_SIZE_Y);
            Main.instance.grid.FillGrid(INITIAL_POP_COUNT,INITIAL_INFECTED_COUNT);
        }

        CSVGenerator.RecordStep(grid, "./data/data"+((currentSimulationStep-1)/SIMULATION_ITERATIONS)+".csv");
    

        Utils.Debug("Current step completed in "+frameTime +" ms.",Utils.DebugType.timeStamps);
    }

    private void End(){
        Utils.Debug("Average FPS at runtime: "+ 1/((float)(Main.instance.countedFps/Main.instance.countedFpsIterations)/1000));
        Utils.Debug("Program ended");
        System.exit(0);
    }



}
