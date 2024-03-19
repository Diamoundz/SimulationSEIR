/*
 * FIND FILES TO COMPILE : find ./src/ -type f -name "*.java" > sources.txt
 * COMPILE : javac -d ./build/ @sources.txt
 * RUN : java -cp ./build/ com.main.Main
 */

package com.main;
import com.main.*;
import com.visual.*;
import com.main.MersenneTwister;
import java.util.Scanner;


public class Main{

    // Logic constants

    public static Main instance;
    private static double clockRunSpeed = (1f/60f)*1000f;
    public static boolean USE_GUI = true;
    public static boolean WAIT_FOR_USER_INPUT = false;

    public Utils.DebugType debugType = null;
    private boolean isRunning = false;

    public long startTime;
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
        Utils.Debug("Program ended",Utils.DebugType.timeStamps);
        System.exit(0);
    }

    private void Awake(){
        Utils.Debug("Program awake",Utils.DebugType.timeStamps);
    }
    
    private void Start(){
        Utils.Debug("Program start");
        
        Main.instance.grid = new Grid(100,100);
        Main.instance.grid.FillGrid(20000);
        
        if(USE_GUI){
            Main.instance.gui = new Interface();
            Main.instance.gui.CreateWindow();
            Main.instance.gui.DisplayGrid(grid);
        }

    }

    private void Update(){
        if(WAIT_FOR_USER_INPUT){
            String userInput = scanner.nextLine();
            Utils.Debug("Current step initialized : " + grid.GetStepCount(),Utils.DebugType.timeStamps);
        }

        grid.NextStep();
        if(USE_GUI){
            if(gui.IsActive()){
                gui.DisplayGrid(grid);
            }
            else{
                isRunning = false;
            }
        }
        Utils.Debug("Current step completed",Utils.DebugType.timeStamps);
    }

}
