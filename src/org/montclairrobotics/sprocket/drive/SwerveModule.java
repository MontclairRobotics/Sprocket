package org.montclairrobotics.sprocket.drive;

import org.montclairrobotics.sprocket.utils.Angle;
import org.montclairrobotics.sprocket.utils.PID;
import org.montclairrobotics.sprocket.utils.Vector;

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
	/**
	 * Creates a SwerveMotor like the DriveMotor, except it contains a SwivelMotor
	 * @param swivelMotor the SwivelMotor
	 * @see DriveMotor
	 */
	public SwerveModule(SpeedController motor, SwivelMotor swivelMotor,Vector offset, Encoder encoder,
			PID encPID, Angle forceAngle) {
		super(motor, offset, encoder, encPID, forceAngle);
		this.swivelMotor=swivelMotor;
	}
	
	/**
	 * The method to set the speed to the swivel motor and the DriveMotor
	 */
	public double calcSpeed(Vector goal)
	{
		swivelMotor.setAngle(goal.getAngle());
		return goal.getMag();
	}
	
}
