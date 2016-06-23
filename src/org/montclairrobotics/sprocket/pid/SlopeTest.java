package org.montclairrobotics.sprocket.pid;
/**
 * Write a description of class CCTuner here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public abstract class SlopeTest extends PIDTest
{
    private int lastTestStart;
    public SlopeTest(String name)
    {
        super(name);
    }
    public SlopeTest(String name,double waitTime)
    {
        super(name,waitTime);
    }
    public SlopeTest(double kc,double ti,double td)
    {
        super(kc,ti,td);
    }
    public double calcOut(int timesRan,int STEPS)
    {
        return (timesRan/2%STEPS+1)*(maxOut()-minOut())/STEPS*((timesRan%2)*2-1)/-3;
    }
    public void runCalcPID(int testStart,double testTime,Signal pv,Signal co)
    {
        if(lastTestStart!=0)
            calcPID(lastTestStart+testStart,testStart,testTime,pv,co);
        lastTestStart=testStart;
    }
    public abstract void calcPID(int lastTestStart,int thisTestStart,double testTime,Signal pv,Signal co);
}
