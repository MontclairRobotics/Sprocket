package org.montclairrobotics.sprocket.pid;
/**
 * Write a description of class IRAPID here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class ParallelPID extends PID
{
    private double p,i,d;
    private double totalError, prevError, error,out;
    public ParallelPID()
    {
    }
    public ParallelPID(double p,double i,double d)
    {
        this();
        setPID(p,i,d);
    }
    public ParallelPID copy()
    {
        return (ParallelPID)copy(new ParallelPID().setPID(p,i,d));
    }
    public ParallelPID setPID(double p,double i,double d)
    {
        this.p=p;
        this.i=i;
        this.d=d;
        return this;
    }
    public double calculate(double in,double timeChg)
    {
        error=wrapIn(getTarget()-in);
        //totalError+=error*timeChg; MOVED THIS LINE DOWN
        if (i != 0) 
        {
            double potentialIGain = (error+totalError) * i;
            if (potentialIGain < maxOut()) {
                if (potentialIGain > minOut()) {
                    totalError += error*timeChg;
                } else {
                    totalError = minOut() /i;
                }
            } else {
                totalError = maxOut() /i;
            }
            out=p*error+i*totalError+d*(error-prevError)/timeChg;
        }
        else
        {
            out=p*error+d*(error-prevError)/timeChg;
        }
        prevError = error;
        return out;
    }
}
