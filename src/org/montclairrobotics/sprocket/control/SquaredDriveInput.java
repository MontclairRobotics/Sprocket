package org.montclairrobotics.sprocket.control;

import edu.wpi.first.wpilibj.Joystick;

public class SquaredDriveInput extends ArcadeDriveInput{
    public SquaredDriveInput(Joystick stick) {
		super(stick);
		// TODO Auto-generated constructor stub
	}
    
    public double getX()
    {
    	double x=super.getX();
    	return x*Math.abs(x);
    }
    public double getY()
    {
    	double y=super.getY();
    	return y*Math.abs(y);
    }
}
