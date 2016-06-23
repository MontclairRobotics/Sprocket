package org.montclairrobotics.sprocket.utils;
/**
 * Write a description of class Line here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Line
{
    public final double m,b;
    public Line(double m,double b)
    {
        this.m=m;
        this.b=b;
    }
    public Line(Point p,double m)
    {
        this(m,p.y-m*p.x);
    }
    public Line(Point a,Point b)
    {
        this(a,(b.y-a.y)/(b.x-a.x));
    }
    public double intersect(Line other)
    {
        if(m==other.m)return 0.0;
        return (other.b-b)/(m-other.m);
    }
}
