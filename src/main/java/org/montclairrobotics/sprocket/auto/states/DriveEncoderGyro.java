package org.montclairrobotics.sprocket.auto.states;

import org.montclairrobotics.sprocket.drive.steps.GyroCorrection;
import org.montclairrobotics.sprocket.geometry.Angle;
import org.montclairrobotics.sprocket.geometry.Degrees;
import org.montclairrobotics.sprocket.geometry.Distance;
import org.montclairrobotics.sprocket.states.MultiState;
import org.montclairrobotics.sprocket.states.State;
import org.montclairrobotics.sprocket.states.StateMachine;
import org.montclairrobotics.sprocket.utils.Input;

public class DriveEncoderGyro extends StateMachine{
	
	
	
	public DriveEncoderGyro(double distance,double speed,Angle angle,boolean relative, GyroCorrection driveGyro)
	{
		super(
			new TurnGyro(angle,driveGyro,relative),
			new MultiState(0,
					new DriveEncoders(distance,speed),
					new State(){
						@Override
						public void start() {
							driveGyro.setTargetAngle(angle,relative);
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
	/*
	public DriveEncoderGyro(double d, double speed, GyroCorrection driveGyro) {
		super(
				new MultiState(0,
						new DriveEncoders(d, speed),
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
	*/
	/*public DriveEncoderGyro(Input<Double> dInput, Input<Double> a, boolean relative, Input<Double> speed, double maxEncAccel, double maxEncTicksPerSec, GyroCorrection driveGyro) {
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
	}*/
	
	/*public DriveEncoderGyro(Input<Double> d, Input<Double> speed, GyroCorrection driveGyro) {
		super(
				new MultiState(0,
						new DriveEncoders(d, speed),
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
	}*/
	
}
