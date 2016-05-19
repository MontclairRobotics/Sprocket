package org.montclairrobotics.sprocket.drive;

import org.montclairrobotics.sprocket.utils.Angle;
import org.montclairrobotics.sprocket.utils.Dashboard;
import org.montclairrobotics.sprocket.utils.Degree;
import org.montclairrobotics.sprocket.utils.PID;
import org.montclairrobotics.sprocket.utils.Polar;
import org.montclairrobotics.sprocket.utils.Updatable;
import org.montclairrobotics.sprocket.utils.UpdateClass;
import org.montclairrobotics.sprocket.utils.Updater;
import org.montclairrobotics.sprocket.utils.Vector;
import org.montclairrobotics.sprocket.utils.XY;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.CANTalon.TalonControlMode;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.VictorSP;

/**
 * An all purpose DriveMotor class supporting everything from Mecanum to Kiwi
 * @author Jack Hymowitz
 *
 */

public class DriveMotor extends Motor{
	
	private static final double WHEEL_CIRCUMFRANCE = 8;//TODO
	//constants
	private Vector offset;
	private Angle forceAngle;
	private Vector totDistance=new XY(0,0);
	private double lastLoops=Timer.getFPGATimestamp();	
	
	private Vector goal;
	
	/**
	 * Creates a DriveMotor
	 * Any optional field can be left as null
	 * @param motor The SpeedController
	 * @param offset The vector pointing from the robot's center of rotation
	 * to this wheel
	 * @param encoder OPTIONAL The Encoder attached to this motor
	 * @param encPID OPTIONAL The PID for correcting the motor's speed
	 * @param forceAngle OPTIONAL The Angle describing the force when this wheel turns
	 * Use this as + or - 45 for Mecanum Wheels or the angle for Kiwi wheels
	 */
	public DriveMotor(SpeedController motor,String name,Vector offset,Angle forceAngle)
	{
		super(motor,name);
		this.offset=offset;
		this.forceAngle=forceAngle;
		if(forceAngle==null)
			this.forceAngle=new Degree(0);
	}
	public DriveMotor setEncoder(Encoder e)
	{
		return (DriveMotor)super.setEncoder(e);
	}
	public DriveMotor setPID(PID a)
	{
		return (DriveMotor)super.setPID(a);
	}
	/**
	 * Sets the velocity Vector of the robot with a rotation value
	 * Calculates the goal Vector for this one wheel and saves it to goal.
	 * @param direction The velocity Vector of the robot
	 * @param rotation The rotation value from [-1,1]
	 */
	public void setVelocity(Vector direction,double rotation)
	{
		Vector v=direction.add(offset.getRotationVector(rotation)).rotate(getForceAngle().negative());
		setVelocity(v);
		Dashboard.putString("V", v.getX()+","+v.getY());
	}
	/**
	 * Sets the velocity vector of this wheel with no rotation
	 * @param v The velocity Vector of the robot
	 */
	public void setVelocity(Vector v)
	{
		goal=v;
	}
	/**
	 * Calculates the speed of this wheel
	 * Overload this method for more complicated driveTrains
	 * @param goal The goal velocity vector for this wheel
	 * @return the speed as a double of this wheel
	 */
	public double calcSpeed()
	{
		return goal.getY();
	}
	public void update()
	{
		super.update();
		double loops=Timer.getFPGATimestamp();
		double diff=loops-lastLoops;
		lastLoops=loops;
		
		totDistance=totDistance.add(new Polar(super.getRate()*diff,forceAngle));
	}
	public Vector getDirectionDistance() {
		// TODO Auto-generated method stub
		return totDistance;
	}
	public Angle getForceAngle()
	{
		return forceAngle;
	}
	public Vector getGoal()
	{
		return goal;
	}
}
