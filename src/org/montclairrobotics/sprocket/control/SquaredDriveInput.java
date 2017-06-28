package org.montclairrobotics.sprocket.control;

import org.montclairrobotics.sprocket.core.IJoystick;


public class SquaredDriveInput extends ArcadeDriveInput{
	
	private double xSensitivity = 1.0;
	private double ySensitivity = 1.0;
	
    public SquaredDriveInput(IJoystick stick) {
		super(stick);
		// TODO Auto-generated constructor stub
	}
    
    public double getX()
    {
    	double x=super.getX();
    	return x*/*Math.abs(x)**/xSensitivity;
    }
    public double getY()
    {
    	double y=super.getY();
    	return y*Math.abs(y)*ySensitivity;
    }
    
    public void setXSensitivity(double s) {
    	xSensitivity = s;
    }
    
    public void setYSensitivity(double s) {
    	ySensitivity = s;
    }
    
}
