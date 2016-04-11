package org.montclairrobotics.sprocket.drive;

import org.montclairrobotics.sprocket.utils.Angle;
import org.montclairrobotics.sprocket.utils.Degree;
import org.montclairrobotics.sprocket.utils.PID;
import org.montclairrobotics.sprocket.utils.Polar;
import org.montclairrobotics.sprocket.utils.Updatable;
import org.montclairrobotics.sprocket.utils.Update;
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
		Update.add(this);
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
		this.driveVector=new Polar(direction.getMag(),gyroAngle.subtract(direction.getAngle()));
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
	
	/**
	 * A helper, optional method to create a list of DriveMotors in a standard,
	 * rover position.
	 * @param leftPorts The ports of the left motors
	 * @param rightPorts The ports of the right motors
	 * @param type The type of the motors
	 * @param leftEncoders OPTIONAL A two dimensional array of encoder ports for the left motors; each encoder requires 2 ports
	 * @param rightEncoders OPTIONAL A two dimensional array of encoder ports for the right motors
	 * @param encPID OPTIONAL The PID controller for the encoders to correct each DriveMotor
	 * @return List of DriveMotors for DriveTrain
	 */
	public static DriveMotor[] makeStandardWheels(int[] leftPorts,int[] rightPorts,M_TYPE type,
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
		return r;
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
		return new Encoder(ports[i][0],ports[i][1]);
	}
}
