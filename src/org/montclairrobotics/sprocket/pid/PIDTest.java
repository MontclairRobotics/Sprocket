package org.montclairrobotics.sprocket.pid;
/**
 * Write a description of class CCTuner here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public abstract class PIDTest extends ISAPID
{
    //private enum States {WAIT,TEST,DONE};
    private double LOOPS_PER_SEC=50;
    private int STEPS=6;
    private int LOOPS_TO_RUN=2;
    private double waitTime=1;
    
    private Signal pv,co;//process variable,controller output
    
    private String name;
    private double in,out;
    private int startI;
    private long startMillis;
    private int timesRan=0;
    private boolean started=false;
    public PIDTest(String name)
    {
        this(name,1);
    }
    public PIDTest(String name,double waitTime)
    {
        this.name=name;
        this.waitTime=waitTime;
        setup();
    }
    public PIDTest(double kc,double ti,double td)
    {
        super(kc,ti,td);
        setup();
        timesRan=getTimesToRun();
    }
    private void setup()
    {
        startMillis=System.currentTimeMillis();
        
        pv=new Signal((int)(waitTime*LOOPS_PER_SEC*4),5);
        co=new Signal((int)(waitTime*LOOPS_PER_SEC*4));
    }
    public double calculate(double val,double timeChg)
    {
        in=val;
        if(isDone() || !started || !isSetMinMaxIn() || !isSetMinMaxOut())
            return super.calculate(val,timeChg);
        out=calcOut(timesRan,STEPS);
        return out;
    }
    public void startTest()
    {
        startI=pv.getI();
        startMillis=System.currentTimeMillis();  
    }
    public void stopTest()
    {
        runCalcPID(startI-pv.getI(),(double)(System.currentTimeMillis()-startMillis)/1000,pv.getAvgs(),co);
        timesRan++;
        if(isDone())
        {
            setPIDFromCalc();
        }
        else
        {
            startTest();
        }
    }
    public void update()
    {
        super.update();
        updateRecords(in,out);
        if(!isDone()&&System.currentTimeMillis()-startMillis>waitTime*1000)
        {
            if(started)
            {
                stopTest();
            }
            else
            {
                startTest();
                started=true;
            }
        }
    }
    public abstract void setPIDFromCalc();
    public abstract double calcOut(int timesRan,int STEPS);
    public abstract void runCalcPID(int testStart,double testTime,Signal pv,Signal co);
    public void updateRecords(double in,double out)
    {
        pv.add(in);
        co.add(out);
    }
    public int getTimesToRun()
    {
        return STEPS*LOOPS_TO_RUN*2;
    }
    public boolean isDone()
    {
        return timesRan>=getTimesToRun();
    }
    public Signal getPV()
    {
        return pv;
    }
    public Signal getCO()
    {
        return co;
    }
    
}
