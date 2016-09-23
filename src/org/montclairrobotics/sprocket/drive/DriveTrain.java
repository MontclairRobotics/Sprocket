package org.montclairrobotics.sprocket.drive;

import org.montclairrobotics.sprocket.geometry.Angle;
import org.montclairrobotics.sprocket.geometry.Distance;
import org.montclairrobotics.sprocket.geometry.Radian;
import org.montclairrobotics.sprocket.geometry.Vector;
import org.montclairrobotics.sprocket.geometry.XY;
import org.montclairrobotics.sprocket.updater.Priority;
import org.montclairrobotics.sprocket.updater.Updatable;
import org.montclairrobotics.sprocket.updater.Updater;

/**
 * The main drivetrain class which holds a set of wheels
 * This is mainly a container for wheels; a lot of the calculations has been moved away from this class.
 * 
 */

public class DriveTrain implements Updatable{
	
	
	//constants
	private DriveModule[] wheels;
	private Distance maxSpeed=Distance.METER;
	
	//variables
	private Vector driveVector=Vector.ZERO;
	private Angle driveRotation=Angle.ZERO;

	/**
	 * Creates a DriveTrain with a list of wheels.
	 * Each wheel knows where it is on the robot.
	 * @param wheels a list of DriveMotors
	 * @see makeStandard
	 */
	public DriveTrain(DriveModule... wheels){
		this.wheels=wheels;
		Updater.add(this, Priority.DRIVE_CALC);
	}

	/**DRIVE HELPER METHODS**/
	
	/**
	 * Set the max speed for scaling the speed.
	 * At maxSpeed, the motors will run at full power.
	 * Otherwise, they will run at a percentage of max speed
	 * @param maxSpeed the input speed for maximum power
	 * @return this
	 */
	public DriveTrain updateMaxSpeed()
	{
		if(wheels.length>0)
		{
			double temp=0.0;
			for(DriveModule wheel:wheels)
				temp+=wheel.getForce().getMag().getMeters();
			maxSpeed=new Distance(temp/wheels.length,Distance.METER);
		}
		return this;
	}
	
	/**
	 * Drive with a Speed And Rotation
	 * @param speed the speed [-maxSpeed,maxSpeed]
	 * @param rotation the rotation [-1,1]
	 */
	public void driveSpeedRotation(Distance speed,Angle rotation)
	{
		drive(new XY(speed,Distance.ZERO),rotation);
	}
	
	/**
	 * Drive with a simulated tank drive
	 * @param left left Joystick
	 * @param right right Joystick
	 * @param gyroAngle the current heading
	 */
	public void driveTank(Distance left,Distance right)
	{
		driveSpeedRotation(left.add(right).divide(2),new Radian(left.divide(maxSpeed)-right.divide(maxSpeed)));
	}
	
	
	/**DRIVE METHODS**/
	
	/**
	 * Drive in a specific vector without any rotation
	 * Robot-centric
	 * @param direction The direction to translate along
	 */
	public void drive(Vector direction)
	{
		drive(direction,Angle.ZERO);
	}
	/**
	 * Drive in a specific vector with rotation
	 * Robot-centric
	 * @param direction The direction to translate along
	 * @param rotation A value from [-1,1] to rotate
	 */
	public void drive(Vector direction,Angle rotation)
	{
		this.driveVector=direction;
		this.driveRotation=rotation;
	}

	/**UPDATE METHODS**/
	
	/**
	 * Sets each wheel to the current translation vector and rotation vector,
	 * leaving the wheels to figure out how fast to spin.
	 */
	public void update()
	{
		for(DriveModule wheel:wheels)
		{
			wheel.set(driveVector,driveRotation);
		}
	}
}