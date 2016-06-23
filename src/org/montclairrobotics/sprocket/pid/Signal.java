package org.montclairrobotics.sprocket.pid;
/**
 * Write a description of class ModListDouble here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */

public class Signal extends ModList<Double>
{
    private Signal myDer=null,
        myAvgs=null;
    private double sum=0;
    private int lookBack;
    public Signal(int len,int lookBack)
    {
        super(len);
        if(lookBack>0&&lookBack<len)
        {
            myAvgs=new Signal(len);
        }
        this.lookBack=lookBack;
    }
    public Signal(int len)
    {
        this(len,0);
    }
    
    //Set and Get
    public Signal add(Double val)
    {
        if(val==null)val=0.0;
        super.add(val);
        
        if(myDer!=null)
        {
            myDer.add(get(0)-get(-1));
        }
        
        if(myAvgs!=null)
        {
            sum+=get(0)-get(-lookBack);
            myAvgs.add(sum/getFullAmount());
        }
        return this;
    }
    public Double get(int pos)
    {
        Double r=super.get(pos);
        if(r==null)return 0.0;
        return r;
    }
    
    //Derivatives
    public Signal derive()
    {
        if(myDer==null);
        {
            myDer=new Signal(size(),lookBack);
            for(int i=-size()+2;i<=0;i++)
            {
                myDer.add(get(i)-get(i-1));
            }
        }
        return myDer;
    }
    public Signal derive(int order)
    {
        if(order<1)return this;
        return derive().derive(order-1);
    }
    
    //Avgs
    public Signal getAvgs()
    {
        if(myAvgs!=null)return myAvgs;
        return this;
    }
    public int getOrder(double tolerance,int max)
    {
        if(myAvgs==null)
            return -1;//BAD
        if(max<1||Math.abs(Math.abs((myAvgs.get(0)-myAvgs.get(lookBack))/myAvgs.get(0))-1)<tolerance)
            return 0;
        return 1+derive().getOrder(tolerance,max-1);
    }
    
    //Other Functions
    public int firstAcrossPos(int start,double val)
    {
        for(int i=start;i<=0;i++)
        {
            if(get(start)>val != get(i)>val)return i;
        }
        return 0;
    }
    public int maxAbsPos(int start)
    {
        double max=Math.abs(get(start));
        int pos=start;
        for(int j=start;j<=0;j++)
        {
            if(Math.abs(get(j))>max)
            {
                pos=j;
                max=Math.abs(get(j));
            }
        }
        return pos;
    }
    
    public double getAvg(int startAt)
    {
        return getAvg(startAt,0);
    }
    public double getAvg(int startAt,int endAt)
    {
        if(startAt>=endAt)return 0.0;
        double sum=0;
        for(int i=startAt;i<=endAt;i++)
        {
            sum+=get(i);
        }
        return sum/(endAt-startAt);
    }
    /*
    //Painting signal
    private int[] toIntListForPaint(int startAt,int scale)
    {
        int[] r=new int[size()];
        for(int i=0;i<r.length;i++)
        {
            r[i]=startAt-(int)(scale*(get(i-size()+1))+0.5);
        }
        return r;
    }
    public void draw(Graphics g,int x,int y,int scale,Color c)
    {
        g.setColor(Color.WHITE);
        g.fillRect(x,y,size(),200);
        g.setColor(Color.BLACK);
        g.drawLine(x,y+100,x+size(),y+100);
        g.setColor(c);
        g.drawPolyline(Utils.range(50,size()+50),toIntListForPaint(y+100,scale),size());
    }*/
}
