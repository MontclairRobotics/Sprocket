package org.montclairrobotics.sprocket.pid;
/**
 * Write a description of class OrderedSet here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class OrderedSet extends OrderedList<Double>
{
    private double sum;
    public Double get(int pos)
    {
        Double val=super.get(pos);
        if(val==null)return 0.0;
        return val;
    }
    public double getQ(int q)
    {
        if(q<0||q>4)return 0.0;
        return get(size()*q/4);
    }
    public boolean add(Double obj)
    {
        if(obj==null||obj.isNaN()||obj.isInfinite())return false;
        super.add(obj);
        sum+=obj;
        return true;
    }
    public OrderedSet getNonOutlier()
    {
        double min=getQ(1)*3-getQ(3)*2;
        double max=getQ(3)*3-getQ(1)*2;
        OrderedSet r=new OrderedSet();
        for(double x:this)
        {
            r.add(x);
        }
        return r;
    }
    public double getAvg()
    {
        if(size()==0)return 0.0;
        return sum/size();
    }
}
