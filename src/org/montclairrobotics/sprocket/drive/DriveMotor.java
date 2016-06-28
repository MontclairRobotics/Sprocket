package org.montclairrobotics.sprocket.drive;

import org.montclairrobotics.sprocket.dataconstructs.Angle;
import org.montclairrobotics.sprocket.dataconstructs.Distance;
import org.montclairrobotics.sprocket.dataconstructs.Vector;

import edu.wpi.first.wpilibj.SpeedController;

/**
 * An all purpose DriveMotor class supporting everything from Mecanum to Kiwi
 *
 */

public class DriveMotor extends Motor{
	
	private Vector offset;
	private Angle forceAngle;	
	
	private Vector goal=Vector.zero;
	private Distance scale; 
	
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
	}
	/**
	 * Sets the velocity Vector of the robot with a rotation value
	 * Calculates the goal Vector for this one wheel and saves it to goal.
	 * @param direction The velocity Vector of the robot
	 * @param rotation The rotation value from [-1,1]
	 */
	public void setVelocity(Vector direction,Angle rotation)
	{
		Vector v=direction.add(offset.getRotationVector(rotation)).rotate(getForceAngle());
		setVelocity(v);
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
	public Distance calcSpeed()
	{
		
		return goal.getY();
	}
	public Angle getForceAngle()
	{
		return forceAngle;
	}
	public Vector getGoal()
	{
		return goal;
	}
	public Vector getOffset() {
		return offset;
	}
}
