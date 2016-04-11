package org.montclairrobotics.sprocket.drive;

import org.montclairrobotics.sprocket.control.Control;
import org.montclairrobotics.sprocket.drive.DriveMotor.M_TYPE;
import org.montclairrobotics.sprocket.utils.Dashboard;
import org.montclairrobotics.sprocket.utils.PID;
import org.montclairrobotics.sprocket.utils.Updatable;
import org.montclairrobotics.sprocket.utils.Update;

/*
 * Please note this is for a traditional chasis with wheels on one side and wheels on another
 */

public abstract class DriveTrain implements Updatable
{
	
	//constants
	protected DriveMotor[][] wheels;
	
	//variables
	protected boolean forward = true;
	protected static boolean shutdown = false;
	
	/*
	 * leftPorts = the ports for the left motors
	 * rightPorts = the ports for the right motors
	 * encoders = two dimensional array; collection of 2 ports per encoder. 
	 * 				if there is only one encoder or less than the entire length, 
	 * 				the remainder will be filled with encoder[0]
	 * motorType = the type of the motor
	 * p,i,d	= the p,i, and d values for the PID controller.
	 */
	
	public DriveTrain(int[][]ports,M_TYPE motorType){
		this(ports,motorType,null);
	}
	public DriveTrain(int[][]ports,M_TYPE motorType,int[][][]encoders){
		this(ports,motorType,encoders,null);
	}
	public DriveTrain(int[][]ports,M_TYPE motorType,int[][][]encoders,PID encPID)
	{
		wheels = new DriveMotor[2][ports[0].length];
		
		for(int c=0; c<wheels.length; c++)
		{
			for(int r=0;r<wheels[0].length;r++)
			{
				if(encoders==null)
				{
					wheels [c][r]= new DriveMotor(ports[c][r],motorType);
				}
				else if(r>=encoders[0].length)
				{
					wheels [c][r]= new DriveMotor(ports[c][r],motorType,encoders[c][0],encPID);
				}
				else
				{
					wheels [c][r]= new DriveMotor(ports[c][r],motorType,encoders[c][r],encPID);
				}
				if(c==1)
				{
					wheels[c][r].setInverted(true);
				}
			}
		}
		Update.add(this);
	}
	public abstract boolean isStraight();
	public abstract void setSpeedPolar(double speed,double angle,double rotation);
	public abstract void correct(double correction);
	
	public double getAvgEncoderClicks() {
		double sum = 0;
		
		for (int c = 0; c < wheels.length;c++)
		{
			for(int r=0;r<wheels[0].length;r++)
			{
				sum+=wheels[c][r].getDistance();
			}
		}
		
		return sum / (wheels.length*wheels[0].length);
	}
}
