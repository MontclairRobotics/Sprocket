package org.montclairrobotics.sprocket.pid;
/**
 * Write a description of class OrderedList here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
import java.util.ArrayList;
public class OrderedList<T extends Comparable<T>> extends ArrayList<T>
{
    public OrderedList()
    {
        super();
    }
    public boolean add(T obj)
    {
        super.add(find(obj),obj);
        return true;
    }
    public void add(int pos,T obj)
    {
        add(obj);
    }
    public void insert(int pos,T obj)
    {
        add(obj);
    }
    public T set(int pos,T obj)
    {
        add(obj);
        return obj;
    }
    public int find(T tgt)
    {
        int min=0;
        int max=size()-1;
        int mid=0;
        int compare=-1;
        int last=0;
        while(min<=max)
        {
            mid=(max+min)/2;
            compare=get(mid).compareTo(tgt);
            if(compare<0)
            {
                min=mid+1;
                last=1;
            }
            else if(compare>0)
            {
                max=mid-1;
                last=0;
            }
            else
                return mid;
        }
        return mid+last;
    }
}
