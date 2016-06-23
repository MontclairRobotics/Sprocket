package org.montclairrobotics.sprocket.pid;


/**
 * Write a description of class CCTuner here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class CCTuner extends StepTest
{
    
    private OrderedSet kc,ti,td;
    public CCTuner(String name)
    {
        super(name);
        setup();
    }
    public CCTuner(String name,double waitTime)
    {
        super(name,waitTime);
        setup();
    }
    public CCTuner(double kc,double ti,double td)
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
        //http://www.controleng.com/single-article/tuning-pid-control-loops-for-fast-response/495b3c78823d6ccfa58f2f83d58dc85c.html
        //http://blog.opticontrols.com/archives/383
        double pvChg=pv.get(0)-pv.get(testStart);
        double coChg=co.get(0)-co.get(testStart);
        double gp=(pvChg/coChg)/((maxIn()-minIn())/(maxOut()-minOut()));
        Signal dPV=pv.derive();
        int maxDPVpos=dPV.maxAbsPos(testStart);
        double intersect=(pv.get(testStart)-pv.get(maxDPVpos))/dPV.get(maxDPVpos)+maxDPVpos;
        double tdead=(intersect-testStart)*testTime/-testStart;
        double partPVchg=pvChg*(1-1/Math.E);
        double tau=(pv.firstAcrossPos(testStart,pv.get(testStart)+partPVchg)-intersect)*testTime/-testStart;
        double kc=1.35/gp*(tau/tdead+0.185)/2;
        double ti=2.5*tdead*(tau+0.185*tdead)/(tau+0.611*tdead);
        double td=0.37*tdead*tau/(tau+0.185*tdead);
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
