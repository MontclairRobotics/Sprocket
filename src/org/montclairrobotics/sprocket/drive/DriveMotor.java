package org.montclairrobotics.sprocket.drive;

import org.montclairrobotics.sprocket.pid.PID;
import org.montclairrobotics.sprocket.utils.Angle;
import org.montclairrobotics.sprocket.utils.Dashboard;
import org.montclairrobotics.sprocket.utils.Degree;
import org.montclairrobotics.sprocket.utils.Distance;
import org.montclairrobotics.sprocket.utils.Polar;
import org.montclairrobotics.sprocket.utils.Vector;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.SpeedController;

/**
 * An all purpose DriveMotor class supporting everything from Mecanum to Kiwi
 * @author Jack Hymowitz
 *
 */

public class DriveMotor extends Motor{
	
	//constants
	private Vector offset;
	private Angle forceAngle;
	//private double lastLoops=Timer.getFPGATimestamp();	
	
	private Vector goal=Vector.zero; 
	
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
	/**
	 * Sets the encoder to use with the PID settings
	 * @param e the Encoder
	 * @return this
	 */	
	public DriveMotor setEncoder(Encoder e,Distance encoderScale)
	{
		super.setEncoder(e,encoderScale);
		return this;
	}
	/**
	 * Sets the PID values for this motor
	 * @param a the PID values
	 * @return this
	 */
	public DriveMotor setPID(PID a)
	{
		super.setPID(a);
		return this;
	}
	public void setVelocity(Vector direction,double rotation)
	{
		setVelocity(direction,rotation,maxSpeed());
	}
	/**
	 * Sets the velocity Vector of the robot with a rotation value
	 * Calculates the goal Vector for this one wheel and saves it to goal.
	 * @param direction The velocity Vector of the robot
	 * @param rotation The rotation value from [-1,1]
	 */
	public void setVelocity(Vector direction,double rotation,Distance d)
	{
		Vector v=direction.add(offset.getRotationVector(rotation)).rotate(getForceAngle());
		setVelocity(v,d);
		Dashboard.putString("V", v.getX()+","+v.getY());
	}
	public void setVelocity(Vector v)
	{
		setVelocity(v,maxSpeed());
	}
	/**
	 * Sets the velocity vector of this wheel with no rotation
	 * @param v The velocity Vector of the robot
	 */
	public void setVelocity(Vector v,Distance d)
	{
		if(d==null)d=maxSpeed();
		goal=new Polar(new Distance(v.getMag(),d).to(Distance.METERS),v.getAngle());
	}
	/**
	 * Calculates the speed of this wheel
	 * Overload this method for more complicated driveTrains
	 * @param goal The goal velocity vector for this wheel
	 * @return the speed as a double of this wheel
	 */
	public Distance calcSpeed()
	{
		return new Distance(goal.getY(),Distance.METERS);
	}
	/*
	public void update()
	{
		super.update();
		
		double loops=Timer.getFPGATimestamp();
		double diff=loops-lastLoops;
		lastLoops=loops;
		totDistance=totDistance.add(new Polar(super.getRate()*diff,forceAngle));
	}
	public Vector getDirectionDistance() {
		return totDistance;
	}*/
	public Angle getForceAngle()
	{
		return forceAngle;
	}
	public Vector getGoal()
	{
		return goal;
	}
}
