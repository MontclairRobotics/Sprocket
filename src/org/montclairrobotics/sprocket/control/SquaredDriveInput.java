package org.montclairrobotics.sprocket.control;

import edu.wpi.first.wpilibj.Joystick;

/**
 * SquaredDriveInput is an extension of ArcadeDriveInput. The main difference is that the
 * Y-axis output is squared. The intention of this is to allow the robot driver to drive at
 * lower speeds more easily as the squared power curve makes it so joystick inputs closer
 * to the center are counted less than movements far away.
 */
public class SquaredDriveInput extends ArcadeDriveInput{
	
	private double xSensitivity = 1.0;
	private double ySensitivity = 1.0;

    /**
     * Instantiates a SquaredDriveInput with the specified joystick.
     * @param stick The joystick to instantiate the input on.
     */
    public SquaredDriveInput(Joystick stick) {
		super(stick);
		// TODO Auto-generated constructor stub
	}

    /**
     * @return The X value of the joystick.
     */
    public double getX()
    {
    	double x=super.getX();
    	return x*/*Math.abs(x)**/xSensitivity;
    }

    /**
     * @return The Y value of the joystick after being squared.
     */
    public double getY()
    {
    	double y=super.getY();
    	return y*Math.abs(y)*ySensitivity;
    }

    /**
     * Sets a factor to scale the output of the X-axis by a specified factor.
     * @param s The X axis scaling factor.
     */
    public void setXSensitivity(double s) {
    	xSensitivity = s;
    }

    /**
     * Sets a factor to scale the output of the Y-axis by a specified factor.
     * @param s The Y axis scaling factor.
     */
    public void setYSensitivity(double s) {
    	ySensitivity = s;
    }
    
}
