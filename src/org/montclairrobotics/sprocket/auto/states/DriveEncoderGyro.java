package org.montclairrobotics.sprocket.auto.states;

import org.montclairrobotics.sprocket.drive.steps.GyroCorrection;
import org.montclairrobotics.sprocket.geometry.Angle;
import org.montclairrobotics.sprocket.geometry.Degrees;
import org.montclairrobotics.sprocket.geometry.Distance;
import org.montclairrobotics.sprocket.states.MultiState;
import org.montclairrobotics.sprocket.states.State;
import org.montclairrobotics.sprocket.states.StateMachine;
import org.montclairrobotics.sprocket.utils.Input;

/**
 * DriveEncoderGyro is a state that autonomously drives the robot as well as corrects
 * the heading using a gyroscope. This state can be created using a distance and a speed or
 * just the distance and the angle will be set to the current heading the robot is facing
 */
public class DriveEncoderGyro extends StateMachine{
	
	
	/**
	 * Creates an auto state that drives a given distance, at a given angle.
	 * @param d the distance to drive
	 * @param a the heading that the robot should drive
	 * @param relative if true, the angle will be relative to the robots current position, if false, the angle will be absolute
	 * @param speed the speed at which the robot will drive
	 * @param maxEncAccel acceleration cap set by the encoders
	 * @param maxEncTicksPerSec speed cap set by the encoders
	 * @param driveGyro the gyro correction that will be used to correct the robot heading
	 */
	public DriveEncoderGyro(Distance d,Angle a,boolean relative,double speed, double maxEncAccel, double maxEncTicksPerSec, GyroCorrection driveGyro)
	{
		super(
			new TurnGyro(a,driveGyro,relative),
			new MultiState(0,
					new DriveEncoders(d,speed, maxEncAccel, maxEncTicksPerSec),
					new State(){
						@Override
						public void start() {
							driveGyro.setTargetAngle(a,relative);
						}
						@Override
						public void stop() {}
						@Override
						public void stateUpdate() {
							driveGyro.use();
						}

						@Override
						public boolean isDone() {
							// TODO Auto-generated method stub
							return false;
						}}
					)
			);
	}
	
	/**
	 * Creates an auto state that drives straight a certain distance using gyro correction
	 * @param d the distance to drive
	 * @param speed the speed at which the robot will drive
	 * @param maxEncAccel acceleration cap set by the encoders
	 * @param maxEncTicksPerSec speed cap set by the encoders
	 * @param driveGyro the gyro correction that will be used to correct the robot heading
	 */
	public DriveEncoderGyro(Distance d, double speed, double maxEncAccel, double maxEncTicksPerSec, GyroCorrection driveGyro) {
		super(
				new MultiState(0,
						new DriveEncoders(d, speed, maxEncAccel, maxEncTicksPerSec),
						new State(){
					@Override
					public void start() {
						driveGyro.setTargetAngleRelative();
					}
					@Override
					public void stop() {}
					@Override
					public void stateUpdate() {
						driveGyro.use();
					}

					@Override
					public boolean isDone() {
						// TODO Auto-generated method stub
						return false;
					}})
				);
	}
	
	
	/**
	 * Creates an auto state that drives a given distance, at a given angle, both as sprocket inputs.
	 * @see Input
	 * @param dInput the distance to drive given as a sprocket input
	 * @param a the heading that the robot should drive given as a sprocket input
	 * @param relative if true, the angle will be relative to the robots current position, if false, the angle will be absolute
	 * @param speed the speed at which the robot will drive
	 * @param maxEncAccel acceleration cap set by the encoders
	 * @param maxEncTicksPerSec speed cap set by the encoders
	 * @param driveGyro the gyro correction that will be used to correct the robot heading
	 */
	public DriveEncoderGyro(Input<Double> dInput, Input<Double> a, boolean relative, Input<Double> speed, double maxEncAccel, double maxEncTicksPerSec, GyroCorrection driveGyro) {
		super(
				new TurnGyro(a,driveGyro,relative),
				new MultiState(0,
						new DriveEncoders(dInput,speed, maxEncAccel, maxEncTicksPerSec),
						new State(){
							@Override
							public void start() {
								driveGyro.setTargetAngle(new Degrees(a.get()),relative);
							}
							@Override
							public void stop() {}
							@Override
							public void stateUpdate() {
								driveGyro.use();
							}
							@Override
							public boolean isDone() {
								// TODO Auto-generated method stub
								return false;
							}}
						)
				);
	}
	
	/**
	 * Creates an auto state that drives straight a certain distance, given as a sprocket input, using gyro correction
	 * @see Input
	 * @param d the distance to drive, given as a sprocket input
	 * @param speed the speed at which the robot will drive
	 * @param maxEncAccel acceleration cap set by the encoders
	 * @param maxEncTicksPerSec speed cap set by the encoders
	 * @param driveGyro the gyro correction that will be used to correct the robot heading
	 */
	public DriveEncoderGyro(Input<Double> d, Input<Double> speed, double maxEncAccel, double maxEncTicksPerSec, GyroCorrection driveGyro) {
		super(
				new MultiState(0,
						new DriveEncoders(d, speed, maxEncAccel, maxEncTicksPerSec),
						new State(){
					@Override
					public void start() {
						driveGyro.setTargetAngleRelative();
					}
					@Override
					public void stop() {}
					@Override
					public void stateUpdate() {
						driveGyro.use();
					}

					@Override
					public boolean isDone() {
						// TODO Auto-generated method stub
						return false;
					}})
				);
	}
	
}
