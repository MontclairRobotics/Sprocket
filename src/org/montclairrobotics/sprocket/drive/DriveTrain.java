package org.montclairrobotics.sprocket.drive;

import org.montclairrobotics.sprocket.dataconstructs.Angle;
import org.montclairrobotics.sprocket.dataconstructs.Distance;
import org.montclairrobotics.sprocket.dataconstructs.Vector;
import org.montclairrobotics.sprocket.input.Input;
import org.montclairrobotics.sprocket.pid.PID;
import org.montclairrobotics.sprocket.updater.Priority;
import org.montclairrobotics.sprocket.updater.Updatable;
import org.montclairrobotics.sprocket.updater.Updater;
import org.montclairrobotics.sprocket.utils.Utils;

/**
 * The main drivetrain class which holds a set of wheels
 * This is mainly a container for wheels; a lot of the calculations has been moved away from this class.
 * 
 */

public class DriveTrain implements Updatable{
	
	private double deadZone = 0.15;
	
	//constants
	private DriveMotor[] wheels;
	private PID rotationPID;
	
	//variables
	private Vector driveVector;
	private Angle driveRotation;

	private Distance maxSpeed,maxOffset;
	/**
	 * Creates a DriveTrain with a list of wheels.
	 * Each wheel knows where it is on the robot.
	 * @param wheels a list of DriveMotors
	 * @see makeStandard
	 */
	public DriveTrain(DriveMotor[] wheels){
		this.wheels=wheels;
		Updater.add(this, Priority.DRIVE_CALC);
		driveVector=Vector.zero;
		driveRotation=Angle.zero;
		setMaxAuto();
	}
	
	/**
	 * Setup a PID to ensure you are driving at the correct angle
	 * @param pid the PID controller to use
	 * @param heading the input heading (from a gyroscope)
	 * @return this
	 */
	public DriveTrain setGyro(PID pid,Input heading)
	{
		this.rotationPID=pid.copy().setInput(heading).setMinMaxOut(-1,1);
		return this;
	}

	/**
	 * Sets the dead zone for the driveArcade and driveTank modes
	 * @param deadZone the deadZone
	 * @return this
	 */
	public DriveTrain setDeadZone(double deadZone)
	{
		this.deadZone=deadZone;
		return this;
	}
	
	public DriveTrain setMaxAuto()
	{
		maxSpeed=null;
		maxOffset=Distance.zero;
		for(DriveMotor wheel:wheels)
		{
			if(maxSpeed==null||wheel.getMaxSpeed().compareTo(maxSpeed)<0)
			{
				maxSpeed=wheel.getMaxSpeed();
			}
			if(wheel.getOffset().getMag().compareTo(maxOffset)>0)
			{
				maxOffset=wheel.getOffset().getMag();
			}
		}
		return this;
	}
	public DriveTrain setMaxSpeed(Distance maxSpeed)
	{
		this.maxSpeed=maxSpeed;
		return this;
	}
	
	/**DRIVE HELPER METHODS**/
	
	/**
	 * Drive in Arcade mode with a joystick
	 * @param joyX the rotation
	 * @param joyY the speed
	 */
	public void driveArcade(double joyX,double joyY)
	{
		driveSpeedRotation(Utils.deadZone(joyY,deadZone),Utils.deadZone(joyX,deadZone));
	}
	public void driveSpeedRotation(double speed,double rotation)
	{
		driveSpeedRotation(new Distance(speed),rotation);
	}
	/**
	 * Drive with a Speed And Rotation
	 * @param speed the speed [-maxSpeed,maxSpeed]
	 * @param rotation the rotation [-1,1]
	 */
	public void driveSpeedRotation(Distance speed,double rotation)
	{
		drive(new Vector(Distance.zero,speed),new Angle(rotation*maxSpeed.divide(maxOffset),Angle.Unit.RADIANS));
	}
	
	/**
	 * Drive with a simulated tank drive
	 * @param left left Joystick
	 * @param right right Joystick
	 * @param gyroAngle the current heading
	 */
	public void driveTank(double left,double right)
	{
		Vector netV=Vector.getAvg(
				new Vector(-1,Utils.deadZone(left,deadZone)),
				new Vector(1,Utils.deadZone(right,deadZone)));
		driveSpeedRotation(netV.getRawY(),netV.getRawX());
	}
	
	
	/**DRIVE METHODS**/
	/**
	 * Drive in a specific vector without any rotation
	 * Robot-centric
	 * @param direction The direction to translate along
	 */
	public void drive(Vector direction)
	{
		drive(direction,Angle.zero);
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

	
	
	/**
	 * Sets each wheel to the current translation vector and rotation vector,
	 * leaving the wheels to figure out how fast to spin.
	 */
	public void update()
	{
		for(DriveMotor wheel:wheels)
		{
			wheel.setVelocity(driveVector,driveRotation);
		}
	}

	public Angle getDriveRotation() {
		return driveRotation;
	}
	public void setDriveRotation(Angle driveRotation)
	{
		this.driveRotation=driveRotation;
	}
	public Vector getDriveVector()
	{
		return driveVector;
	}
	public void setDriveVector(Vector driveVector)
	{
		this.driveVector=driveVector;
	}

	public double getDeadZone() {
		return deadZone;
	}
}
