package org.montclairrobotics.sprocket.control;

import edu.wpi.first.wpilibj.Joystick;

/**
 * This class scales the coordinates of the Joystick inputs exponentially to a specified
 * power, defaulting to a square root power curve.
 */
public class ExponentDriveInput extends ArcadeDriveInput
{

	private double p;

	/**
	 * Instantiates the drive input with a default square root power curve.
	 * @param stick The joystick to instantiate the input on.
	 */
	public ExponentDriveInput(Joystick stick) {
		this(stick,0.5);
		// TODO Auto-generated constructor stub
	}

	/**
	 * Instantiates the drive input with a the specified exponential power curve.
	 * @param stick The joystick to instantiate the input on.
	 * @param p The power to scale the joystick's axises by.
	 */
	public ExponentDriveInput(Joystick stick,double p) {
		super(stick);
		this.p=p;
		// TODO Auto-generated constructor stub
	}

	/**
	 * @return The X coordinate of the joystick after scaling has been applied.
	 */
	public double getX()
    {
    	double x=super.getX();
    	return p*Math.pow(x, 3)+(1-p)*x;
    }

	/**
	 * @return The Y coordinate of the joystick after scaling has been applied.
	 */
    public double getY()
    {
    	double y=super.getY();
    	return p*Math.pow(y, 3)+(1-p)*y;
    }
}
