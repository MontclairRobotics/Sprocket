package org.montclairrobotics.sprocket.drive;

import org.montclairrobotics.sprocket.utils.Angle;
import org.montclairrobotics.sprocket.utils.Dashboard;
import org.montclairrobotics.sprocket.utils.Degree;
import org.montclairrobotics.sprocket.utils.PID;
import org.montclairrobotics.sprocket.utils.Polar;
import org.montclairrobotics.sprocket.utils.Updatable;
import org.montclairrobotics.sprocket.utils.Update;
import org.montclairrobotics.sprocket.utils.Vector;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.CANTalon.TalonControlMode;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.VictorSP;

/**
 * An all purpose DriveMotor class supporting everything from Mecanum to Kiwi
 * @author Jack Hymowitz
 *
 */

public class DriveMotor implements Updatable{
	
	//constants
	private SpeedController motor;
	private Encoder encoder;
	private PID pid;
	private Vector offset;
	private Angle forceAngle;
	
	//variables
	private static boolean shutdown=false;
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
	public DriveMotor(SpeedController motor,Vector offset,Encoder encoder,PID encPID,Angle forceAngle)
	{
		this.motor=motor;
		if(motor instanceof CANTalon)
		{
			CANTalon talon = (CANTalon)motor;
			talon.setControlMode(TalonControlMode.PercentVbus.value);
			talon.reset();
			talon.enable();
			talon.enableControl();
		}
		this.encoder=encoder;
		this.pid=encPID.copy();
		this.offset=offset;
		this.forceAngle=forceAngle;
		if(forceAngle==null)
			this.forceAngle=new Degree(0);
		Update.add(this);
	}
	/**
	 * Sets the speed as directly as possible
	 * @param spd the speed
	 */
	public void setSpeed(double spd)
	{
		setVelocity(new Polar(spd,0));
	}
	/**
	 * Sets the velocity vector of the robot with no rotation
	 * @param direction The velocity Vector of the robot
	 */
	public void setVelocity(Vector direction)
	{
		setVelocity(direction,0.0);
	}
	/**
	 * Sets the velocity Vector of the robot with a rotation value
	 * Calculates the goal Vector for this one wheel and saves it to goal.
	 * @param direction The velocity Vector of the robot
	 * @param rotation The rotation value from [-1,1]
	 */
	public void setVelocity(Vector direction,double rotation)
	{
		goal=direction.add(offset.getRotationVector(rotation)).rotate(forceAngle.negative());
	}
	/**
	 * Calculates the speed of this wheel
	 * Overload this method for more complicated driveTrains
	 * @param goal The goal velocity vector for this wheel
	 * @return the speed as a double of this wheel
	 */
	public double calcSpeed(Vector goal)
	{
		return goal.getY();
	}
	/**
	 * The automatically called function to update the speed of this wheel
	 * Will attempt to use a PID controller if it is not null.
	 */
	public void update()
	{
		double tgtSpeed=calcSpeed(goal);
		double speed=0;
		if(shutdown)
		{
			speed = 0;
			motor.set(0);
			if(motor instanceof CANTalon) ((CANTalon)motor).disableControl();
			return;
		}
		if(encoder==null||pid==null)
		{
			speed=tgtSpeed;
		}
		else
		{
			pid.setTarget(tgtSpeed);
			pid.setCur(encoder.getRate());
			speed=pid.getAdjOut();
		}
		motor.set(speed);
	}
	
	public boolean isInverted() {
		return motor.getInverted();
	}
	
	public void setInverted(boolean invert) {
		motor.setInverted(invert);
	}
	
	public void toggleInvert() {
		motor.setInverted(!motor.getInverted());
	}
	
	public static void shutdown() {
		shutdown = true;
	}
	
	public double getDistance()
	{
		if(encoder==null)return 0.0;
		return encoder.getDistance();
	}
	
	public double getSpeed() {
		return getRate();
	}
	
	public double getRate() {
		if(encoder==null) return 0.0;
		return encoder.getRate();
	}
	
	public void resetDistance()
	{
		if(encoder==null)return;
		encoder.reset();
	}
}
