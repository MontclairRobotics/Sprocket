package org.montclairrobotics.sprocket.drive;

import org.montclairrobotics.sprocket.drive.Motor.M_TYPE;
import org.montclairrobotics.sprocket.updater.Priority;
import org.montclairrobotics.sprocket.updater.Updatable;
import org.montclairrobotics.sprocket.updater.Updater;
import org.montclairrobotics.sprocket.utils.Angle;
import org.montclairrobotics.sprocket.utils.Degree;
import org.montclairrobotics.sprocket.utils.Input;
import org.montclairrobotics.sprocket.utils.PID;
import org.montclairrobotics.sprocket.utils.Polar;
import org.montclairrobotics.sprocket.utils.Vector;
import org.montclairrobotics.sprocket.utils.XY;

/**
 * The main drivetrain class which holds a set of wheels
 * This is mainly a container for wheels; a lot of the calculations has been moved away from this class.
 * 
 */

public class DriveTrain implements Updatable{
	
	public static final double DEGREES_PER_LOOP=90;
	public static final double MIN_SPEED=0.1;
	public static final double MAX_STRAIGHT_ROTATION=0.1;
	public static final double ROT_FACTOR=0.75;
	
	//constants
	private DriveMotor[] wheels;
	private PID lockPID;
	
	//variables
	private static boolean shutdown = false;
	private Vector driveVector;
	private double driveRotation;
	/**
	 * Creates a DriveTrain with a list of wheels.
	 * Each wheel knows where it is on the robot.
	 * @param wheels a list of DriveMotors
	 * @see makeStandard
	 */
	public DriveTrain(DriveMotor[] wheels){
		this.wheels=wheels;
		Updater.add(this, Priority.DRIVE_CALC);
		driveVector=new XY(0,0);
	}
	public DriveTrain setLockPID(PID pid)
	{
		this.lockPID=pid.setMinMax(-180,180,-1,1);
		return this;
	}
	
	/**
	 * Sets each wheel to the current translation vector and rotation vector,
	 * leaving the wheels to figure out how fast to spin.
	 */
	public void update()
	{
		correct();
		for(DriveMotor wheel:wheels)
		{
			wheel.setVelocity(driveVector,driveRotation);
		}
	}
	
	/**DRIVE METHODS**/
	
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
		this.driveVector=direction;
		this.driveRotation=rotation;
	}
	
	public void correct()
	{
		if(lockPID==null) return;
		driveRotation=lockPID.setTarget(lockPID.getInput()+driveRotation*DEGREES_PER_LOOP).get();
	}
	
	public Angle getHeading()
	{
		if(lockPID==null)return null;
		return new Degree(lockPID.getInput());
	}
	
	/**DRIVE HELPER METHODS**/
	
	/**
	 * Drive with a simulated tank drive
	 * @param left left Joystick
	 * @param right right Joystick
	 * @param gyroAngle the current heading
	 */
	public void driveTank(double left,double right)
	{
		Vector netV=new Polar(Math.abs(left),45).makeFractionOfSquare().add(new Polar(Math.abs(right),-45).makeFractionOfSquare());
		driveSpeedRotation(netV.getY(),netV.getX());
	}
	/**
	 * Drive with a Speed And Rotation
	 * May input the joystick x and y values respectively
	 * @param speed Joystick Y
	 * @param rotation Joystick X
	 */
	public void driveSpeedRotation(double speed,double rotation)
	{
		drive(new XY(0,speed),rotation);
	}
	
	/**OTHER METHODS**/
	public Angle rotateTo(Angle target)
	{
		return rotateTo(target,true);
	}
	public Angle rotateTo(Angle target,boolean relativeToRobot)
	{
		if(lockPID==null)return null;
		if(relativeToRobot)
		{
			target=new Degree(target.toDegrees()-lockPID.getInput());
		}
		lockPID.setTarget(target.toDegrees());
		return target;
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
			r[i]=new DriveMotor(Motor.makeMotor(leftPorts[j],type),"LEFT "+j+":"+leftPorts[j],leftOffset,new Degree(0));
			if(leftEncoders!=null)
				r[i].setEncoder(Motor.makeEncoder(leftEncoders,j)).setPID(encPID);
			i++;
		}
		for(int j=0;j<rightPorts.length;j++)
		{
			r[i]=new DriveMotor(Motor.makeMotor(rightPorts[j],type),"RIGHT "+j+":"+leftPorts[j],rightOffset,new Degree(0));
			if(rightEncoders!=null)
				r[i].setEncoder(Motor.makeEncoder(rightEncoders,j)).setPID(encPID);
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
		r[0]=new DriveMotor(Motor.makeMotor(flPort,type),"FL",new XY(-1, 1),new Degree(  45)).setEncoder(Motor.makeEncoder(flEncoder)).setPID(encPID);
		r[1]=new DriveMotor(Motor.makeMotor(frPort,type),"FR",new XY( 1, 1),new Degree( 135)).setEncoder(Motor.makeEncoder(frEncoder)).setPID(encPID);
		r[2]=new DriveMotor(Motor.makeMotor(blPort,type),"BL",new XY(-1,-1),new Degree( -45)).setEncoder(Motor.makeEncoder(blEncoder)).setPID(encPID);
		r[3]=new DriveMotor(Motor.makeMotor(brPort,type),"BR",new XY( 1,-1),new Degree(-135)).setEncoder(Motor.makeEncoder(brEncoder)).setPID(encPID);
		return new DriveTrain(r);
	}
}
