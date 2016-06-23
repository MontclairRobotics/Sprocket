package org.montclairrobotics.sprocket.pid;
/**
 * Write a description of class LTuner here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class LTuner extends StepTest
{
    private OrderedSet kc,ti,td;
    private double lambda=3;//1-3
    public LTuner(String name)
    {
        super(name);
        setup();
    }
    public LTuner(String name,double waitTime)
    {
        super(name,waitTime);
        setup();
    }
    public LTuner(double kc,double ti,double td)
    {
        super(kc,ti,td);
        setup();
    }
    private void setup()
    {
        kc=new OrderedSet();
        ti=new OrderedSet();
        td=new OrderedSet();
    }
    public void calcPID(int testStart,double testTime,Signal pv,Signal co)
    {
        //http://http://blog.opticontrols.com/archives/260
        double pvChg=pv.get(0)-pv.get(testStart);
        double coChg=co.get(0)-co.get(testStart);
        double gp=(pvChg/coChg)/((maxIn()-minIn())/(maxOut()-minOut()));
        Signal dPV=pv.derive();
        int maxDPVpos=dPV.maxAbsPos(testStart);
        double intersect=(pv.get(testStart)-pv.get(maxDPVpos))/dPV.get(maxDPVpos)+maxDPVpos;
        double tdead=(intersect-testStart)*testTime/-testStart;
        double partPVchg=pvChg*(1-1/Math.E);
        double tau=(pv.firstAcrossPos(testStart,pv.get(testStart)+partPVchg)-intersect)*testTime/-testStart;
        double taucl=tau*lambda;
        double kc=tau/(gp*(taucl+tdead));
        double ti=tau;
        double td=0;
        System.out.println(kc+","+ti+","+td);
        this.kc.add(kc);
        this.ti.add(ti);
        this.td.add(td);
    }
    public void setPIDFromCalc()
    {
        double kc=this.kc.getNonOutlier().getAvg();
        double ti=this.ti.getNonOutlier().getAvg();
        double td=this.td.getNonOutlier().getAvg();
        super.setPID(kc,ti,td);
        super.resetTotOut();
        System.out.println(kc+"\n"+ti+"\n"+td);
    }
}
