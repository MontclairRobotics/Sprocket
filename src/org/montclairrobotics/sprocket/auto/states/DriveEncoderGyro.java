package org.montclairrobotics.sprocket.auto.states;

import org.montclairrobotics.sprocket.actions.MultiState;
import org.montclairrobotics.sprocket.actions.State;
import org.montclairrobotics.sprocket.actions.StateMachine;
import org.montclairrobotics.sprocket.drive.steps.GyroCorrection;
import org.montclairrobotics.sprocket.geometry.Angle;
import org.montclairrobotics.sprocket.geometry.Degrees;
import org.montclairrobotics.sprocket.geometry.Distance;
import org.montclairrobotics.sprocket.utils.Input;

public class DriveEncoderGyro extends StateMachine{
	
	
	
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
						public void enabled() {
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
					public void enabled() {
						driveGyro.use();
					}

					@Override
					public boolean isDone() {
						// TODO Auto-generated method stub
						return false;
					}})
				);
	}

	//Lets make it to States!
	public static State[] makeStatesA(Distance d,Angle a,boolean relative,double speed, double maxEncAccel, double maxEncTicksPerSec, GyroCorrection driveGyro)
	{
		return {
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
							public void disabled() {
							}

							@Override
							public void enabled() {
								driveGyro.use();
							}

							@Override
							public boolean isDone() {
								// TODO Auto-generated method stub
								return false;
							}}
				)};
	}
	public static State[] makeStatesB(Distance d, double speed, double maxEncAccel, double maxEncTicksPerSec, GyroCorrection driveGyro)
	{
		return {new MultiState(0,
				new DriveEncoders(d, speed, maxEncAccel, maxEncTicksPerSec),
				new State(){
					@Override
					public void start() {
						driveGyro.setTargetAngleRelative();
					}
					@Override
					public void stop() {}

					@Override
					public void disabled() {

					}

					@Override
					public void enabled() {
						driveGyro.use();
					}

					@Override
					public boolean isDone() {
						// TODO Auto-generated method stub
						return false;
					}})};
		);
	}
}
