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

/*
 * DriveTrain
 * 
 * USAGE:
 * construct with a list of DriveMotors containing all of the wheels
 * 
 * call drive() with a direction Vector, a rotation double, and a gyro Angle
 * The gyro Angle may be ommited for Robot-centric control, or included for Field-centric control
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
	
	public DriveTrain(DriveMotor[] wheels){
		this.wheels=wheels;
		Update.add(this);
	}
	public void drive(double speed, double rotation)
	{
		drive(new Polar(speed,0),rotation);
	}
	public void drive(Vector direction)
	{
		drive(direction,0.0);
	}
	public void drive(Vector direction,double rotation)
	{
		drive(direction,rotation,new Degree(0));
	}
	public void drive(Vector direction,double rotation,Angle gyroAngle)
	{
		this.driveVector=new Polar(direction.getMag(),gyroAngle.subtract(direction.getAngle()));
		this.driveRotation=rotation;
	}
	public boolean isStraight()
	{
		return driveRotation<MAX_STRAIGHT_ROTATION;
	}
	public void correct(double correction)
	{
		driveRotation+=correction;
	}
	
	public double getAvgEncoderClicks() {
		double sum = 0;

		for (int i = 0; i < wheels.length;i++)
		{
			sum+=wheels[i].getDistance();
		}
		
		return sum / (wheels.length);
	}
	
	public void update()
	{
		for(DriveMotor wheel:wheels)
		{
			wheel.setVelocity(driveVector,driveRotation);
		}
	}
	
	
	//STATIC METHODS TO HELP MAKE DRIVETRAIN
	public static DriveMotor[] makeStandardWheels(int[] leftPorts,int[] rightPorts,M_TYPE type,
			int[][]leftEncoders,int[][]rightEncoders,PID encPID)
	{
		Vector leftOffset=new XY(-1,0),rightOffset=new XY(1,0);
		
		DriveMotor[] r=new DriveMotor[leftPorts.length+rightPorts.length];
		int i=0;
		for(int j=0;j<leftPorts.length;j++)
		{
			r[i]=new DriveMotor(makeMotor(leftPorts[j],type),makeEncoder(leftEncoders,j),encPID,leftOffset);
			i++;
		}
		for(int j=0;j<rightPorts.length;j++)
		{
			r[i]=new DriveMotor(makeMotor(rightPorts[j],type),makeEncoder(rightEncoders,j),encPID,rightOffset);
			i++;
		}
		return r;
	}
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
	public static Encoder makeEncoder(int[][] ports,int i)
	{
		if(ports==null||ports.length>=i)return null;
		return new Encoder(ports[i][0],ports[i][1]);
	}
}
