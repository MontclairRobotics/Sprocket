package org.montclairrobotics.sprocket.auto.states;

import org.montclairrobotics.sprocket.SprocketRobot;
import org.montclairrobotics.sprocket.auto.AutoState;
import org.montclairrobotics.sprocket.drive.DriveTrain;
import org.montclairrobotics.sprocket.geometry.Distance;
import org.montclairrobotics.sprocket.geometry.XY;
import org.montclairrobotics.sprocket.utils.Debug;
import org.montclairrobotics.sprocket.utils.Input;
import org.montclairrobotics.sprocket.utils.Utils;

/**
 * DriveEncoders autonomously drives the robot a specified number of encoder
 * ticks as well as regulating the motors so they don't exceed certain values
 * specified in the constructor, like acceleration and speed
 */
public class DriveEncoders extends AutoState {
	/**
	 * The drive train of the robot
	 */
	private DriveTrain dt;
	
	/**
	 * Specified target distance that the robot should drive
	 */
	private Distance tgtDistance;
	
	/**
	 * Distance it takes to stop the robot
	 */
	private Distance stopDist;
	
	/**
	 * Limit on the max acceleration of the robot
	 */
	private double maxEncAccel;
	
	/**
	 * Limit on the max speed of the robot
	 */
	private double maxEncTicksPerSec;
	
	/**
	 * The speed that the robot should drive
	 */
	private double speed;
	
	/**
	 * True id f the robot is moving forwards
	 */
	private boolean forwards;
	
	
	/**
	 * Gets information about the distance from smart dashboard
	 */
	private Input<Double> dashInput;
	
	/**
	 * Gets information about the speed from smart dashboard
	 */
	private Input<Double> speedDashInput;
	
	
	/**
	 * Creates an auto state that drives a certain distance using the target distance and the speed
	 * @param tgtDistance target distance to drive, given as a Distance
	 * @param speed speed to drive at
	 * @param maxEncAccel max acceleration limiter
	 * @param maxEncTicksPerSec max speed limiter
	 */
	public DriveEncoders(Distance tgtDistance, double speed, double maxEncAccel, double maxEncTicksPerSec) {
		this.tgtDistance=tgtDistance;
		this.speed = speed;
		this.maxEncAccel=maxEncAccel;
		this.maxEncTicksPerSec = maxEncTicksPerSec;
	}
	
	/**
	 * Creates an auto state that drives a certain distance using the target distance and the speed
	 * @param tgtDistance target distance to drive, given as a double
	 * @param speed speed to drive at
	 * @param maxEncAccel max acceleration limiter
	 * @param maxEncTicksPerSec max speed limiter
	 */
	public DriveEncoders(double tgtDistance, double speed, double maxEncAccel, double maxEncTicksPerSec) {
		this(new Distance(tgtDistance), speed, maxEncAccel, maxEncTicksPerSec);
	}
	
	/**
	 *  Creates an auto state that drives a certain distance using the target distance and the speed
	 *  as inputs from the smart dashboard
	 * @param dashInput the distance to drive taken from the smart dashboard
	 * @param speed the speed to drive taken from the smart dasboard
	 * @param maxEncAccel max acceleration limiter
	 * @param maxEncTicksPerSec max speed limiter
	 */
	public DriveEncoders(Input<Double> dashInput, Input<Double> speed, double maxEncAccel, double maxEncTicksPerSec) {
		this.dashInput = dashInput;
		this.speedDashInput = speed;
		this.maxEncAccel = maxEncAccel;
		this.maxEncTicksPerSec = maxEncTicksPerSec;
	}
	
	/**
	 * when the state starts, calculate the target distance based on
	 * the parameters, set the drive train and stopping distance and
	 * calculate weather the robot is moving forwards or not
	 */
	@Override
	public void userStart() {
		if(dashInput != null) {
			this.tgtDistance = new Distance(dashInput.get());
		}
		if(speedDashInput != null) {
			this.speed = speedDashInput.get();
		}
		this.dt = SprocketRobot.getDriveTrain();
		stopDist = new Distance(dt.getDistance().getY()+tgtDistance.get());
		forwards=tgtDistance.get()>0;
	}
	
	/**
	 * When the state updates, make sure the motor are within the speed and acceleration limits
	 * and debug info
	 */
	@Override
	public void stateUpdate() {
		/*
		tgtDir = new XY(0,Utils.constrain(
				Math.sqrt(Math.abs(2*maxAccel.get()*(stopDist.get()-dt.getDistance().get())))
				*(stopDist.get()-dt.getDistance().get()>0?1:-1)
				,-speed,speed));*/
		
		/*
		tgtDir = new XY(0,Utils.constrain(
				Math.sqrt(Math.abs(2*maxAccel.get()*(stopDist.get()-dt.getDistance().getY())/encSpeed))
				*(stopDist.get()-dt.getDistance().getY()>0?1:-1)
				,-speed,speed));
		 */
		
		
		double tgtV2inTicks=2*maxEncAccel*(stopDist.get()-dt.get().get());
		double tgtV=Math.sqrt(Math.abs(tgtV2inTicks))/maxEncTicksPerSec*(stopDist.get()-dt.get().get()>0?1:-1);
		Debug.num("dt-get-get", dt.get().get());
		Debug.num("stopDist", stopDist.get());
		tgtV=Utils.constrain(tgtV, -speed, speed);
		tgtDir = new XY(0,tgtV);
		
		//tgtDir = new XY(0, speed);
	}
	
	/**
	 * @return true if the robot has traveled the specified distance
	 */
	@Override
	public boolean isDone() {
		Debug.msg("forwards", forwards?"YES":"NO");
		Debug.msg("DISTANCE", dt.getDistance().getY());
		Debug.msg("StopDistance", stopDist.get());
		if(forwards)
			return dt.getDistance().getY()>stopDist.get()-1;
		else
			return dt.getDistance().getY()<stopDist.get()+1;
	}
	
	/**
	 * reset the target direction and drive train
	 */
	@Override
	public void userStop() {
		tgtDir = new XY(0, 0);
		this.dt = null;
	}

}
