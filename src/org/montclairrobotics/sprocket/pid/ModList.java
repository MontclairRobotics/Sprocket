package org.montclairrobotics.sprocket.pid;
/**
 * Write a description of class ModList here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */

public class ModList<T>
{
    private T[] list;
    private int i;
    public ModList(int len)
    {
        list=(T[])(new Object[len]);
        i=0;
    }
    public ModList<T> add(T obj)
    {
        i++;
        list[mod(i)]=obj;
        return this;
    }
    public T get(int pos)
    {
        return list[mod(i+pos)];
    }
    public int size()
    {
        return list.length;
    }
    public int mod(int i)
    {
        return ((i%list.length)+list.length)%list.length;
    }
    public boolean isFull()
    {
        return i>=size();
    }
    public int getFullAmount()
    {
        if(isFull())return size();
        return i;
    }
    public int getI()
    {
        return i;
    }
}
