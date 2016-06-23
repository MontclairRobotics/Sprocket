package org.montclairrobotics.sprocket.pid;
/**
 * Write a description of class IRAPID here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class ISAPID extends PID
{
    private double kc,ti,td;
    private double totalError, prevError, error,out;
    public ISAPID()
    {
    }
    public ISAPID(double kc,double ti,double td)
    {
        this();
        setPID(kc,ti,td);
    }
    public ISAPID copy()
    {
        return (ISAPID)copy(new ISAPID().setPID(kc,ti,td));
    }
    public ISAPID setPID(double kc,double ti,double td)
    {
        this.kc=kc;
        this.ti=ti;
        this.td=td;
        return this;
    }
    public double calculate(double in,double timeChg)
    {
        error=wrapIn(getTarget()-in);
        //totalError+=error*timeChg; MOVED THIS LINE DOWN
        if (ti != 0) 
        {
            double potentialIGain = kc*(error+totalError) / ti;
            if (potentialIGain < maxOut()) {
                if (potentialIGain > minOut()) {
                    totalError += error*timeChg;
                } else {
                    totalError = minOut() *ti;
                }
            } else {
                totalError = maxOut() *ti;
            }
            out=kc*(error+totalError/ti+td*(error-prevError)/timeChg);
        }
        else
        {
            out=kc*(error+td*(error-prevError)/timeChg);
        }
        prevError = error;
        return out;
    }
}
