package org.montclairrobotics.sprocket.auto.states;

import org.montclairrobotics.sprocket.drive.steps.GyroCorrection;
import org.montclairrobotics.sprocket.drive.utils.GyroLock;
import org.montclairrobotics.sprocket.geometry.Angle;
import org.montclairrobotics.sprocket.geometry.Distance;
import org.montclairrobotics.sprocket.states.MultiState;
import org.montclairrobotics.sprocket.states.State;
import org.montclairrobotics.sprocket.states.StateMachine;
import org.montclairrobotics.sprocket.utils.Input;

public class DriveEncoderGyroWithInput extends StateMachine{
	
	
	public DriveEncoderGyroWithInput(Input<Distance> d,Input<Angle> a,Input<Boolean> relative,Input<Double> speed,Distance encSpeed,GyroCorrection driveGyro)
	{
		super(
			new TurnGyroWithInput(a,driveGyro,relative),
			new MultiState(0,
					new DriveEncodersWithInput(d,speed,encSpeed),
					new State(){
						@Override
						public void start() {
							driveGyro.setTargetAngle(a.get(),relative.get());
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
}
