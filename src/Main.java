/*
 * FIND FILES TO COMPILE : find ./src/ -type f -name "*.java" > sources.txt
 * COMPILE : javac -d ./build/ @sources.txt
 * RUN : java -cp ./build/ com.main.Main
 */

package com.main;

public class Main{

    private double clockRunSpeed = 1f/60;
    private boolean isRunning = false;

    public static void main(String[] args){
        Main mainProgram = new Main();

        mainProgram.InitProgram();
        mainProgram.isRunning = true;
        while (mainProgram.isRunning) {
            mainProgram.Update();
        }
        
    }
    private void InitProgram(){
        System.out.println("Initialized Program");
        Start();
    }
    
    private void Start(){
        
    }
    private void Update(){

    }

}