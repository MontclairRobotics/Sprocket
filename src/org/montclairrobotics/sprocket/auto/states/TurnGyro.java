package org.montclairrobotics.sprocket.auto.states;

import org.montclairrobotics.sprocket.auto.AutoState;
import org.montclairrobotics.sprocket.drive.steps.GyroCorrection;
import org.montclairrobotics.sprocket.geometry.Angle;
import org.montclairrobotics.sprocket.geometry.Degrees;
import org.montclairrobotics.sprocket.loop.Updater;
import org.montclairrobotics.sprocket.utils.Debug;
import org.montclairrobotics.sprocket.utils.Input;

/**
 * Turn gyro uses an input from a gyroscope to accurately turn.
 * The angle can be set as a relative angle to the robots current
 * heading or as an absolute angle.
 */
public class TurnGyro extends AutoState {
	
	/**
	 * The angle to turn
	 */
	private Angle tgt;
	
	/**
	 * gets turn information from the smart dashboard
	 */
	private Input<Double> dashInput;
	
	/**
	 * @return the target turn angle
	 */
	public Angle getTgt() {
		return tgt;
	}
	
	/**
	 * Set the target turn angle
	 * @param tgt target turn angle
	 */
	public void setTgt(Angle tgt) {
		this.tgt = tgt;
	}
	
	/**
	 * The gyro correct to be used for correction
	 */
	private GyroCorrection gyro;
	
	/**
	 * Time that the robot is not in the correct position
	 */
	private double incorrectTime;
	
	/**
	 * True if the angle is relative to the current heading
	 */
	private boolean relative;
	
	
	/**
	 * The tolerance of the turn accuracy, for example,
	 * if the tolerance is 5 degrees than the turn would stop
	 * when it within 5 degrees of the target turn angle
	 */
	private static final Angle tolerance=new Degrees(4);
	
	/**
	 * Amount of time that has to be spent at the target before
	 * the state is considered completed
	 */
	private static final double timeAtTarget=0.5;
	
	/**
	 * Creates a state that turns using the target angle and gyro correction
	 * @param tgt Target angle for turn
	 * @param gyro gyro correction to be used
	 * @param relative true if the angle is relative to the current heading
	 */
	public TurnGyro(Angle tgt,GyroCorrection gyro,boolean relative) 
	{
		this.tgt=tgt;
		this.gyro=gyro;
		this.relative=relative;
	}
	
	/**
	 * Create a state that turns using the target angle from the smart dashboard and a gyro correction
	 * @param dashInput angle input from the smart dashboard
	 * @param gyro gyro correction to be used
	 * @param relative true if the angle is relative to the current heading
	 */
	public TurnGyro(Input<Double> dashInput, GyroCorrection gyro, boolean relative) {
		this.dashInput = dashInput;
		this.gyro = gyro;
		this.relative = relative;
	}
	
	/**
	 * When the state is started, calculate the target angle based on the parameters
	 * and set the incorrect time to prevent the state from automatically stopping
	 */
	@Override
	public void userStart()
	{
		if(dashInput != null) {
			tgt = new Degrees(dashInput.get());
		}
		if(relative)
		{
			gyro.setTargetAngleRelative(tgt);
		}
		else
		{
			gyro.setTargetAngleReset(tgt);
		}
		gyro.setMinMaxOut(-0.5, 0.5);
		incorrectTime=Updater.getTime()-timeAtTarget+0.1;
	}
	
	/**
	 * When the state is updated, use the gyro correction to turn the robot and
	 * set the incorect time to the current time, as well as debug out information
	 */
	@Override
	public void stateUpdate() {
		gyro.use();
		if(Math.abs(gyro.getError().toDegrees())>tolerance.toDegrees())
		{
			incorrectTime=Updater.getTime();
		}
		Debug.msg("gyroError", gyro.getError().toDegrees());
		Debug.msg("incorrectTime", incorrectTime);
		Debug.msg("cur-time", Updater.getTime());
		Debug.msg("timeCorrect", Updater.getTime()-incorrectTime);
		Debug.msg("IS-DONE",isDone());
	}
	
	/**
	 * Reset the gyro correction min max
	 */
	@Override
	public void userStop()
	{
		gyro.resetMinMaxOut();
	}
	
	/**
	 * @return true if the robot is in the correct position for longer than the target time
	 */
	@Override
	public boolean isDone() {
		return (Updater.getTime()-incorrectTime>timeAtTarget);
	}

}
