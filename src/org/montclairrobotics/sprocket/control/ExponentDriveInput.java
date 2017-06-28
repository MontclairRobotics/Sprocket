package org.montclairrobotics.sprocket.control;

import org.montclairrobotics.sprocket.core.IJoystick;


public class ExponentDriveInput extends ArcadeDriveInput
{

	private double p;
	
	public ExponentDriveInput(IJoystick stick) {
		this(stick,0.5);
		// TODO Auto-generated constructor stub
	}
	public ExponentDriveInput(IJoystick stick,double p) {
		super(stick);
		this.p=p;
		// TODO Auto-generated constructor stub
	}
	public double getX()
    {
    	double x=super.getX();
    	return p*Math.pow(x, 3)+(1-p)*x;
    }
    public double getY()
    {
    	double y=super.getY();
    	return p*Math.pow(y, 3)+(1-p)*y;
    }
}
