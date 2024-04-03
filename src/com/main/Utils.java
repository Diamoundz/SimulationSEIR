package com.main;

import java.text.DecimalFormat;

public class Utils {
    public enum DebugType{
        all,
        timeStamps,
        stepInfo,
        none
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

    public static void UpdateProgressBar(int completed, int total) {
        double progress = (double) completed / total;
        DecimalFormat df = new DecimalFormat("0.000");
        String progressStr = df.format(progress * 100);

        int barLength = 50;

        int numOfBars = (int) (progress * barLength);

        StringBuilder progressBar = new StringBuilder("Progress : [");
        for (int i = 0; i < barLength; i++) {
            if (i < numOfBars) {
                progressBar.append("=");
            } else {
                progressBar.append(" ");
            }
        }
        progressBar.append("] " + progressStr + "%");

        System.out.print("\r" + progressBar.toString());
        System.out.flush();
    }
}
