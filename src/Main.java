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
    }

    private void Awake(){
        Utils.Debug("Program awake");
    }
    
    private void Start(){
        Utils.Debug("Program start");

        grid = new Grid(50,50);
        grid.FillGrid(1);

        if(USE_GUI){
            gui =new Interface();
            gui.CreateWindow(900, false);
            Main.instance.gui.DisplayGrid(grid);
        }
    }

    private void Update(){
        String userInput = scanner.nextLine();
        Utils.Debug("Current step initialized : " + grid.GetStepCount());
        grid.NextStep();
        if(USE_GUI){
            Main.instance.gui.DisplayGrid(grid);
        }
        Utils.Debug("Current step completed");
    }

}
