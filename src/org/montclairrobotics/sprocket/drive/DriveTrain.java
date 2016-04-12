package org.montclairrobotics.sprocket.drive;

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

public class DriveTrain implements Updatable
{
	public static enum M_TYPE{TALONSRX,VICTORSP,TALON};
	
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
	/**
	 * Drive with a simulated tank drive
	 * @param left left Joystick
	 * @param right right Joystick
	 */
	public void driveTank(double left,double right)
	{
		Vector netV=new Polar(Math.abs(left),((left>0)?45:135)).add(new Polar(Math.abs(right),((right>0)?-45:-135)));
		drive(netV.getY(),netV.getX());
	}
	/**
	 * Used to be setSpeedXY(),
	 * maintained for reverse compatibility and simplicity
	 * @param speed the forward speed
	 * @param rotation the rotation
	 */
	public void drive(double speed, double rotation)
	{
		drive(new Polar(speed,0),rotation);
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
	 * Gets the average distance covered by the encoders
	 * !!!!!! This method will not currently work as required 
	 * because right and left motors spin in oposite directions, 
	 * canceling each other out.
	 * @return the distance the robot has traveled
	 */
	public double getAvgEncoderClicks() {
		double sum = 0;

		for (int i = 0; i < wheels.length;i++)
		{
			sum+=wheels[i].getDistance();
		}
		
		return sum / (wheels.length);
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
			r[i]=new DriveMotor(makeMotor(leftPorts[j],type),leftOffset,makeEncoder(leftEncoders,j),encPID,null);
			i++;
		}
		for(int j=0;j<rightPorts.length;j++)
		{
			r[i]=new DriveMotor(makeMotor(rightPorts[j],type),rightOffset,makeEncoder(rightEncoders,j),encPID,null);
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
		r[0]=new DriveMotor(makeMotor(flPort,type),new XY(-1, 1),makeEncoder(flEncoder),encPID,new Degree( 45));
		r[1]=new DriveMotor(makeMotor(frPort,type),new XY( 1, 1),makeEncoder(frEncoder),encPID,new Degree(-45));
		r[2]=new DriveMotor(makeMotor(blPort,type),new XY(-1,-1),makeEncoder(blEncoder),encPID,new Degree(-45));
		r[3]=new DriveMotor(makeMotor(brPort,type),new XY( 1,-1),makeEncoder(brEncoder),encPID,new Degree( 45));
		return new DriveTrain(r);
	}
	
	/**
	 * Helper method to create a SpeedController of a given type
	 * @param port The motor port
	 * @param type The motor type
	 * @return The SpeedController
	 */
	public static SpeedController makeMotor(int port,M_TYPE type)
	{
		switch(type)
		{
		case TALONSRX:
			return new CANTalon(port);
		case VICTORSP:
			return new VictorSP(port);
		case TALON:
			return new Talon(port);
		default:
			return null;
		}
	}
	/**
	 * Helper method to create an Encoder from a two dimensional array
	 * @param ports the two dimensional array
	 * @param i the encoder id
	 * @return the Encoder
	 */
	public static Encoder makeEncoder(int[][] ports,int i)
	{
		if(ports==null||ports.length>=i)return null;
		return makeEncoder(ports[i]);
	}
	public static Encoder makeEncoder(int[] ports)
	{
		return new Encoder(ports[0],ports[1]);
	}
}
