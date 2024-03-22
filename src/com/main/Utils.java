package com.main;

import com.main.Main;

public class Utils {
    public enum DebugType{
        all,
        timeStamps,
        stepInfo
    }

    public static Utils instance;
    public static void wait(int ms)
    {
        try
        {
            Thread.sleep(ms);
        }
        catch(InterruptedException ex)
        {
            Thread.currentThread().interrupt();
        }
    }
    public static void Debug(String message, DebugType debugType){
        if(debugType == Main.instance.debugType || Main.instance.debugType == DebugType.all || debugType == DebugType.all){
            int secconds = GetRunTime()/1000;
            int millisecconds = GetRunTime()%1000;
            System.out.println(secconds+"."+millisecconds+"s : "+message);
        }
    }
    public static void Debug(String message){
        int secconds = GetRunTime()/1000;
        int millisecconds = GetRunTime()%1000;
        System.out.println(secconds+"."+millisecconds+"s : "+message);
        
    }
    public static int GetRunTime(){
        return (int)((System.nanoTime()-Main.instance.startTime)/1000000);
    }
    public static double NegExp(double inMean){
        return -inMean*Math.log(1 - Main.instance.rand.nextFloat());
    }
    public static int RandomRange(int lowerBound, int upperBound){
        return Main.instance.rand.nextInt(upperBound-lowerBound+1)+lowerBound;
    }
}
