package org.montclairrobotics.sprocket.drive;

import org.montclairrobotics.sprocket.drive.Motor.M_TYPE;
import org.montclairrobotics.sprocket.utils.Angle;
import org.montclairrobotics.sprocket.utils.Degree;
import org.montclairrobotics.sprocket.utils.PID;
import org.montclairrobotics.sprocket.utils.Polar;
import org.montclairrobotics.sprocket.utils.Updatable;
import org.montclairrobotics.sprocket.utils.UpdateClass;
import org.montclairrobotics.sprocket.utils.Updater;
import org.montclairrobotics.sprocket.utils.Vector;
import org.montclairrobotics.sprocket.utils.XY;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.VictorSP;

/**
 * The main drivetrain class which holds a set of wheels
 * This is mainly a container for wheels; a lot of the calculations has been moved away from this class.
 * 
 */

public class DriveTrain implements Updatable{
	
	public static final double MIN_SPEED=0.1;
	public static final double MAX_STRAIGHT_ROTATION=0.1;
	public static final double ROT_FACTOR=0.75;
	
	//constants
	protected DriveMotor[] wheels;
	
	//variables
	protected static boolean shutdown = false;
	private Vector driveVector;
	private double driveRotation;
	/**
	 * Creates a DriveTrain with a list of wheels.
	 * Each wheel knows where it is on the robot.
	 * @param wheels a list of DriveMotors
	 * @see makeStandardWheels
	 */
	public DriveTrain(DriveMotor[] wheels){
		this.wheels=wheels;
		Updater.add(this, UpdateClass.DriveTrain);
	}
	public void driveTank(double left,double right)
	{
		driveTank(left,right,new Degree(0));
	}
	/**
	 * Drive with a simulated tank drive
	 * @param left left Joystick
	 * @param right right Joystick
	 * @param gyroAngle the current heading
	 */
	public void driveTank(double left,double right,Angle gyroAngle)
	{
		Vector netV=new Polar(Math.abs(left),45).makeFractionOfSquare().add(new Polar(Math.abs(right),-45).makeFractionOfSquare());
		driveSpeedRotation(netV.getY(),netV.getX(),gyroAngle);
	}
	public void driveSpeedRotation(double speed,double rotation)
	{
		driveSpeedRotation(speed,rotation,new Degree(0));
	}
	/**
	 * Used to be setSpeedXY(),
	 * maintained for reverse compatibility and simplicity
	 * @param speed the forward speed
	 * @param rotation the rotation
	 * @param gyroAngle the current heading
	 */
	public void driveSpeedRotation(double speed, double rotation,Angle gyroAngle)
	{
		drive(new XY(0,speed),rotation,gyroAngle);
	}
	/**
	 * Drive in a specific vector without any rotation
	 * Robot-centric
	 * @param direction The direction to translate along
	 */
	public void drive(Vector direction)
	{
		drive(direction,0.0);
	}
	/**
	 * Drive in a specific vector with rotation
	 * Robot-centric
	 * @param direction The direction to translate along
	 * @param rotation A value from [-1,1] to rotate
	 */
	public void drive(Vector direction,double rotation)
	{
		drive(direction,rotation,new Degree(0));
	}
	/**
	 * Drive in a specific vector with rotation
	 * Field-centric
	 * @param direction The direction to translate along
	 * @param rotation A value from [-1,1] to rotate
	 * @param gyroAngle The current heading from a gyroscope
	 */
	public void drive(Vector direction,double rotation,Angle gyroAngle)
	{
		this.driveVector=new Polar(direction.getMag(),gyroAngle.subtract(direction.getAngle()).negative());
		this.driveRotation=rotation;
	}
	/**
	 * Is the robot driving straight?
	 * Used to enable AutoLock
	 * @return Is the robot driving straight?
	 */
	public boolean isStraight()
	{
		return driveRotation<MAX_STRAIGHT_ROTATION;
	}
	/**
	 * Add or subtract some rotation
	 * Used as the output of AutoLock
	 * @param correction the correction value
	 */
	public void correct(double correction)
	{
		driveRotation+=correction;
	}
	/**
	 * Gets the total distance the robot has traveled
	 * @return the distance the robot has traveled
	 */
	public Vector getAvgDirectionDistance() {
		Vector r=new XY(0,0);
		for(DriveMotor wheel:wheels)
		{
			r.add(wheel.getDirectionDistance());
		}
		return new Polar(r.getMag()/wheels.length,r.getAngle());
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
	
	
	//STATIC METHODS
	
	public static DriveTrain makeStandard(int[] leftPorts,int[] rightPorts,M_TYPE type)
	{
		return makeStandard(leftPorts,rightPorts,type,null,null,null);
	}
	
	/**
	 * A helper, optional method to create a DriveTrain in a standard configuration
	 * @param leftPorts The ports of the left motors
	 * @param rightPorts The ports of the right motors
	 * @param type The type of the motors
	 * @param leftEncoders OPTIONAL A two dimensional array of encoder ports for the left motors; each encoder requires 2 ports
	 * @param rightEncoders OPTIONAL A two dimensional array of encoder ports for the right motors
	 * @param encPID OPTIONAL The PID controller for the encoders to correct each DriveMotor
	 * @return a new DriveTrain with these settings
	 */
	public static DriveTrain makeStandard(int[] leftPorts,int[] rightPorts,M_TYPE type,
			int[][]leftEncoders,int[][]rightEncoders,PID encPID)
	{
		Vector leftOffset=new XY(-1,0),rightOffset=new XY(1,0);
		
		DriveMotor[] r=new DriveMotor[leftPorts.length+rightPorts.length];
		int i=0;
		for(int j=0;j<leftPorts.length;j++)
		{
			r[i]=new DriveMotor(Motor.makeMotor(leftPorts[j],type),leftOffset,Motor.makeEncoder(leftEncoders,j),encPID,null);
			i++;
		}
		for(int j=0;j<rightPorts.length;j++)
		{
			r[i]=new DriveMotor(Motor.makeMotor(rightPorts[j],type),rightOffset,Motor.makeEncoder(rightEncoders,j),encPID,null);
			i++;
		}
		return new DriveTrain(r);
	}
	
	public static DriveTrain makeMecanum(int flPort,int frPort,int blPort,int brPort,M_TYPE type)
	{
		return makeMecanum(flPort,frPort,blPort,brPort,type,null,null,null,null,null);
	}
	
	/**
	 * Helper method to make a standard Mecanum DriveTrain
	 * @param flPort Front Left motor port
	 * @param frPort Front Right motor port
	 * @param blPort Back Left motor port
	 * @param brPort Back Right motor port
	 * @param type Motor type
	 * @param flEncoder Front Left encoder port
	 * @param frEncoder Front Right encoder port
	 * @param blEncoder Back Left encoder port
	 * @param brEncoder Back Right encoder port
	 * @param encPID Encoder PID values
	 * @return a new DriveTrain with these settings.
	 */
	public static DriveTrain makeMecanum(int flPort,int frPort,int blPort,int brPort,M_TYPE type,
			int[] flEncoder,int[] frEncoder,int[] blEncoder,int[] brEncoder,PID encPID)
	{
		DriveMotor[] r= new DriveMotor[4];
		r[0]=new DriveMotor(Motor.makeMotor(flPort,type),new XY(-1, 1),Motor.makeEncoder(flEncoder),encPID,new Degree( 45));
		r[1]=new DriveMotor(Motor.makeMotor(frPort,type),new XY( 1, 1),Motor.makeEncoder(frEncoder),encPID,new Degree(-45));
		r[2]=new DriveMotor(Motor.makeMotor(blPort,type),new XY(-1,-1),Motor.makeEncoder(blEncoder),encPID,new Degree(-45));
		r[3]=new DriveMotor(Motor.makeMotor(brPort,type),new XY( 1,-1),Motor.makeEncoder(brEncoder),encPID,new Degree( 45));
		return new DriveTrain(r);
	}
	
	
	
}
