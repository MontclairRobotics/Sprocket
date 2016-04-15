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
	private Vector totDistance;
	private double lastLoops=Timer.getFPGATimestamp();	
	
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
	public DriveMotor(SpeedController motor,Vector offset,Encoder encoder,PID encPID,Angle forceAngle)
	{
		super(motor, encoder, encPID);
		this.offset=offset;
		this.forceAngle=forceAngle;
		if(forceAngle==null)
			this.forceAngle=new Degree(0);
		Updater.add(this, UpdateClass.MotorController);
	}
	/**
	 * Sets the velocity Vector of the robot with a rotation value
	 * Calculates the goal Vector for this one wheel and saves it to goal.
	 * @param direction The velocity Vector of the robot
	 * @param rotation The rotation value from [-1,1]
	 */
	public void setVelocity(Vector direction,double rotation)
	{
		setVelocity(direction.add(offset.getRotationVector(rotation)).rotate(forceAngle.negative()));
	}
	
	public void update()
	{
		super.update();
		double loops=Timer.getFPGATimestamp();
		double diff=loops-lastLoops;
		lastLoops=loops;
		
		Vector actual=super.getActual();
		totDistance.add(new Polar(super.getRate()*diff*WHEEL_CIRCUMFRANCE/360,actual.getAngle()));
	}
	public Vector getDirectionDistance() {
		// TODO Auto-generated method stub
		return totDistance;
	}
}
