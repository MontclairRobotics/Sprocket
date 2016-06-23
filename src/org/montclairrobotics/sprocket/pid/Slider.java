package org.montclairrobotics.sprocket.pid;

import org.montclairrobotics.sprocket.utils.Input;

/**
 * Write a description of class Slider here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Slider implements Input
{
    private double loc;
    private double spd;
    public void set(double chg)
    {
        spd+=chg+Math.random()/100;
    }
    public void update()
    {
        loc+=spd+Math.random()/100;
    }
    public double get()
    {
        return loc;
    }
}
