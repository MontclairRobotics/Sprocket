package org.montclairrobotics.sprocket.pid;

import org.montclairrobotics.sprocket.updater.Priority;
import org.montclairrobotics.sprocket.updater.Updatable;
import org.montclairrobotics.sprocket.updater.Updater;
import org.montclairrobotics.sprocket.utils.Input;


/**
 * A PID controller
 * Create with P,I,D values
 * Call setTarget() to set the target
 * Call get() to get the correction
 * 
 * It just uses the math from the WPI PIDController class.
 * 
 */

public abstract class PID implements Updatable{

    private Input input;
    private double minIn,maxIn,minOut,maxOut;
    private boolean wrapIn=false;
    private long lastUpdate;
    private boolean totOut=false;
    
    private double out;
    private double target;
    private double totalError, prevError, error;
    /**
     * 
     * @param P the Propotional Constant
     * @param I the Integral Constant
     * @param D the Derivitive Constant
     * @param minIn OPTIONAL the minimum input, or 0 to ignore. Use with maxIn to "wrap" the values, 
     * eg. so the error between 5 degrees and 355 degrees is 10 degrees
     * @param maxIn OPTIONAL the maximum input, or 0 to ignore
     * @param minOut OPTIONAL the minimum output to constrain to, or 0 to ignore
     * @param maxOut OPTIONAL the maximum output to constrain to, or 0 to ignore
     */
    public PID()
    {
        this.input=null;
        this.minOut=0.0;
        this.maxOut=0.0;
        this.minIn=0.0;
        this.maxIn=0.0;
        lastUpdate=System.currentTimeMillis();
        setTarget();
        Updater.add(this, Priority.INPUT_PID);
    }
    public PID setInput(Input i)
    {
        this.input=i;
        return this;
    }
    public abstract PID setPID(double P, double I, double D);
    public PID setTotOutMode(boolean totOut)
    {
        this.totOut=totOut;
        return this;
    }
    public PID setMinMaxIn(double minIn,double maxIn)
    {
        setMinMaxIn(minIn,maxIn,false);
        return this;
    }
    public PID setMinMaxIn(double minIn, double maxIn,boolean wrap)
    {
        this.minIn=minIn;
        this.maxIn=maxIn;
        this.wrapIn=wrap;
        return this;
    }
    public PID setMinMaxOut(double minOut, double maxOut)
    {
        this.minOut=minOut;
        this.maxOut=maxOut;
        return this;
    }
    /**
     * Copy constructor so you can copy PID controllers
     * @return a copy of this PID controller
     */
    public PID copy(PID newPID)
    {
        return newPID.setInput(input).setMinMaxIn(minIn,maxIn,wrapIn).setMinMaxOut(minOut,maxOut);
    }
    public abstract PID copy();
    public PID setTarget()
    {
        return setTarget(0.0,false);
    }
    public PID setTarget(double t)
    {
        return setTarget(t,false);
    }
    /**
     * Sets the setpoint
     * @param t the target/setpoint
     * @param reset true if the PID should reset, false otherwise
     */
    public PID setTarget(double t,boolean reset)
    {
        target=t;
        if(reset)
        {
            error=0.0;
            prevError=0.0;
            totalError=0.0;
        }
        return this;
    }
    public double getTarget()
    {
        return target;
    }
    public boolean isSetMinMaxIn()
    {
        return minIn!=0||maxIn!=0;
    }
    public double wrapIn(double in)
    {
        if(wrapIn&&isSetMinMaxIn())
        {
            double diff=maxIn-minIn;
            in=((in-minIn)%diff+diff)%diff+in;
        }
        return in;
    }
    public boolean isSetMinMaxOut()
    {
        return minOut!=0 || maxOut!=0;
    }
    public double constrainOut(double out)
    {
        if(isSetMinMaxOut())
        {
            if (out > maxOut) out=maxOut;
            else if (out < minOut) out=minOut;
        }
        return out;
    }
    /**
     * Get the output value
     * @return the output
     */
    public double get()
    {
        return out;
    }
    /*
    public double get(boolean calculate)
    {
        if(calculate)
            runCalculate();
        return out;
    }*/
    public void resetTotOut()
    {
        out=0;
        lastUpdate=System.currentTimeMillis();
    }
    public abstract double calculate(double val,double timeChg);
    
    public void runCalculate()
    {
        if(input==null)return;
        double timeChg=(double)(System.currentTimeMillis()-lastUpdate)/1000.0;
        lastUpdate=System.currentTimeMillis();
        if(timeChg<0.001)timeChg=0.001;
        double stepOut=calculate(input.get(),timeChg);
        if(totOut)
        {
            out=constrainOut(out+stepOut*timeChg);
        }
        else
        {
            out=stepOut;
        }
    }
    
    
    public Input getInput()
    {
        return input;
    }

    public void update()
    {
        runCalculate();
    }
    public boolean isTotOut()
    {
        return totOut;
    }
    public double maxIn()
    {
        return maxIn;
    }
    public double minIn()
    {
        return minIn;
    }
    public double maxOut()
    {
        return maxOut;
    }
    public double minOut()
    {
        return minOut;
    }
}
