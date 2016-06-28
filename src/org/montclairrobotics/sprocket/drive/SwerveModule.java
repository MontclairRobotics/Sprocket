package org.montclairrobotics.sprocket.drive;

import org.montclairrobotics.sprocket.dataconstructs.Angle;
import org.montclairrobotics.sprocket.dataconstructs.Vector;
import org.montclairrobotics.sprocket.pid.PID;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.SpeedController;

/**
 * A module for the SwerveDrive
 * Equivelent to the DriveModule, except it also contains a SwivelMotor
 * for rotating the other motor
 * @author Hymowitz
 *
 */

public class SwerveModule extends DriveMotor{

	private SwivelMotor swivelMotor;
	
	public SwerveModule(SpeedController motor,String name,SpeedController swivelMotor,Encoder rotEnc,PID rotPID,Vector offset,Angle initialAngle)
	{
		this(motor,name,new SwivelMotor(swivelMotor,name+" SWIVEL",rotEnc,rotPID),offset,initialAngle);
	}
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
	public SwerveModule(SpeedController motor, String name,SwivelMotor swivelMotor,Vector offset,Angle initialAngle) {
		super(motor, name,offset, Angle.zero);
		this.swivelMotor=swivelMotor;
		swivelMotor.setInitialAngle(initialAngle);
	}
	/**
	 * The method to set the speed to the swivel motor and the DriveMotor
	 */
	public void setVelocity(Vector v)
	{
		super.setVelocity(v);
		swivelMotor.setAngle(v.getAngle().add(getForceAngle()));
	}
	public Angle getForceAngle()
	{
		return swivelMotor.getAngle();
	}
}
