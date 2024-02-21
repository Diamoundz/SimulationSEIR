package com.main;

import com.main.Main;

public class Utils {
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
    public static void Debug(String message){
        int secconds = GetRunTime()/1000;
        int millisecconds = GetRunTime()%1000;
        System.out.println(secconds+"."+millisecconds+"s : "+message);
    }
    public static int GetRunTime(){
        return (int)((System.nanoTime()-Main.instance.startTime)/1000000);
    }
}
