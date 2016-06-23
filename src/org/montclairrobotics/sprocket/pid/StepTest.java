package org.montclairrobotics.sprocket.pid;
/**
 * Write a description of class CCTuner here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public abstract class StepTest extends PIDTest
{
    public StepTest(String name)
    {
        super(name);
    }
    public StepTest(String name,double waitTime)
    {
        super(name,waitTime);
    }
    public StepTest(double kc,double ti,double td)
    {
        super(kc,ti,td);
    }
    public double calcOut(int timesRan,int STEPS)
    {
        return (double)(STEPS-Math.abs(STEPS-timesRan%(2*STEPS)))*(maxOut()-minOut())/STEPS+minOut();
    }
    public void runCalcPID(int testStart,double testTime,Signal pv,Signal co)
    {
        calcPID(testStart,testTime,pv,co);
    }
    public abstract void calcPID(int testStart,double testTime,Signal pv,Signal co);
}
