package org.montclairrobotics.sprocket.drive;

import org.montclairrobotics.sprocket.geometry.Angle;
import org.montclairrobotics.sprocket.geometry.Polar;
import org.montclairrobotics.sprocket.geometry.Vector;

/**
 * A module for the SwerveDrive
 * Equivelent to the DriveModule, except it also contains a SwivelMotor
 * for rotating the other motor
 * @author Hymowitz
 *
 */

public class SwerveModule extends DriveModule{

	private SwivelMotor swivelMotor;
	
	/**
	 * Creates a SwerveMotor like the DriveMotor, except it contains a SwivelMotor
	 * @param motor the SpeedController
	 * @param swivelMotor the SwivelMotor
	 * @param offset The vector pointing from the robot's center of rotation
	 * to this wheel
	 * @param encoder OPTIONAL The Encoder attached to this motor
	 * @param encPID OPTIONAL The PID for correcting the motor's speed
	 * @param forceAngle OPTIONAL The Angle describing the force when this wheel turns
	 * Use this as + or - 45 for Mecanum Wheels or the angle for Kiwi wheels
	 * @see DriveMotor
	 */
	public SwerveModule(Vector pos,Vector force,SwivelMotor swivel,IMotor... motors)
	{
		super(pos,force,motors);
		this.swivelMotor=swivel;
	}
	/**
	 * The method to set the speed to the swivel motor and the DriveMotor
	 */
	public Vector set(Vector velocity,Angle rotation)
	{
		super.setForce(new Polar(super.getForce().getMag(),swivelMotor.getAngle()));
		Vector tgt=super.set(velocity,rotation);
		swivelMotor.setAngle(tgt.getAngle());
		return tgt;
	}
}
