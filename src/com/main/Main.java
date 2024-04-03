package com.main;
import com.visual.*;
import java.util.Scanner;


public class Main{

    // Logic constants

    public static Main instance;
    private static double clockRunSpeed = (1f/60f)*1000f;

    public static boolean USE_GUI = false; // Default : false;
    public static boolean WAIT_FOR_USER_INPUT = false; // Default : false
    public static boolean ENABLE_TELEPORT_MOVEMENT = true; // Default : true
    public static boolean ENABLE_SUBJECT_DEATH = false; // Default : false

    public static int SIMULATION_ITERATIONS = 730;
    public static int SIMULATION_COUNT = 100;

    public Utils.DebugType debugType = Utils.DebugType.none;
    public double countedFps;
    public int countedFpsIterations;
    public long startTime;

    private boolean isRunning = false;
    private int currentSimulationCount = 0;
    private double currentProgressFraction = 0;
    
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
            mainProgram.Update();
            Utils.wait((int)clockRunSpeed);
        }
        mainProgram.End();
    }

    private void Awake(){
        Utils.Debug("Program awake");
    }
    
    private void Start(){
        Utils.Debug("Program start");
        
        Vector2 gridSize = new Vector2(300, 300);

        Main.instance.grid = new Grid(gridSize.x, gridSize.y);
        Main.instance.grid.FillGrid(20000,20);
        


        if(USE_GUI){
            gui = new Interface(1000, 600, gridSize.x, gridSize.y);
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
        if(USE_GUI){
            if (gui.isActive()){gui.displayGrid(grid);}
            else {isRunning = false;}
        }
        else{
            updateProgressBar(currentSimulationCount, SIMULATION_COUNT*SIMULATION_ITERATIONS);
        }

        int endTime =Utils.GetRunTime();
        int frameTime = endTime-startTime;

        Main.instance.countedFps += frameTime;
        Main.instance.countedFpsIterations++;

        if(debugType == Utils.DebugType.stepInfo ||debugType==Utils.DebugType.all)
        {
            grid.DebugPopulationInfo();
        }

        if(currentSimulationCount == SIMULATION_COUNT *SIMULATION_ITERATIONS-1){
            Utils.Debug("Completed "+SIMULATION_COUNT*SIMULATION_ITERATIONS +" iterations, Shutting down ...");
            isRunning = false;
        }

        currentSimulationCount++;
        Utils.Debug("Current step completed in "+frameTime +" ms.",Utils.DebugType.timeStamps);
    }

    private void End(){
        grid.DebugPopulationInfo();
        Utils.Debug("Average FPS at runtime: "+ 1/((float)(Main.instance.countedFps/Main.instance.countedFpsIterations)/1000));
        Utils.Debug("Program ended");
        System.exit(0);
    }

    public static void updateProgressBar(int completed, int total) {
        // Calculate progress percentage
        int progress = (int) (100.0 * completed / total);

        // Define length of progress bar
        int barLength = 50;

        // Calculate number of '=' characters to represent progress
        int numOfBars = (int) (progress * barLength / 100.0);

        // Create progress bar string
        StringBuilder progressBar = new StringBuilder("[");
        for (int i = 0; i < barLength; i++) {
            if (i < numOfBars) {
                progressBar.append("=");
            } else {
                progressBar.append(" ");
            }
        }
        progressBar.append("] " + progress + "%");

        // Print progress bar
        System.out.print("\r" + progressBar.toString());
        System.out.flush();
    }

}
