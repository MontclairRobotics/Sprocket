package org.montclairrobotics.sprocket.pid;

import org.montclairrobotics.sprocket.dataconstructs.Line;
import org.montclairrobotics.sprocket.dataconstructs.Point;
import org.montclairrobotics.sprocket.utils.Dashboard;

/**
 * Write a description of class PZLevelTuner here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class PZLevelTuner extends SlopeTest
{
    private static final double SM=3;
    private OrderedSet kc,ti,td;
    public PZLevelTuner(String name)
    {
        super(name);
        setup();
    }
    public PZLevelTuner(String name,double waitTime)
    {
        super(name,waitTime);
        setup();
    }
    public PZLevelTuner(double kc,double ti,double td)
    {
        super(kc,ti,td);
        setup();
    }
    public PZLevelTuner copy()
    {
    	return (PZLevelTuner)copy(new PZLevelTuner(getName()+" 2"));
    }
    private void setup()
    {
        kc=new OrderedSet();
        ti=new OrderedSet();
        td=new OrderedSet();
    }
    public void calcPID(int lastTestStart,int testStart,double testTime,Signal pv,Signal co)
    {
        //http://http://blog.opticontrols.com/archives/260
        int[] xs={(lastTestStart+testStart)/2,testStart,testStart/2,0};
        Line pvA=new Line(new Point(xs[0],pv.get(xs[0])),new Point(xs[1],pv.get(xs[1])));
        Line pvB=new Line(new Point(xs[2],pv.get(xs[2])),new Point(xs[3],pv.get(xs[3])));
        double coA=co.get(xs[1]);
        double coB=co.get(xs[3]);
        
        double intersect=pvA.intersect(pvB);
        double tdead=(intersect-testStart)/-testStart*testTime;
        double ds=(pvB.m-pvA.m)/(maxIn()-minIn())*100*-testStart/testTime;
        double dco=(coB-coA)/(maxOut()-minOut());
        double ri=ds/dco;
        
        double kc=1.2/(SM*ri*tdead);
        double ti=2*SM*tdead;
        double td=tdead/2;
       
        //System.out.println(kc+","+ti+","+td);
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
        Dashboard.putString("PID",kc+"\n"+ti+"\n"+td);
    }
}
