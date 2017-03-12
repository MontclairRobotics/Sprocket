package org.montclairrobotics.sprocket.auto.states;

import org.montclairrobotics.sprocket.drive.steps.DriveGyro;
import org.montclairrobotics.sprocket.drive.utils.GyroLock;
import org.montclairrobotics.sprocket.geometry.Angle;
import org.montclairrobotics.sprocket.geometry.Distance;
import org.montclairrobotics.sprocket.states.MultiState;
import org.montclairrobotics.sprocket.states.State;
import org.montclairrobotics.sprocket.states.StateMachine;

public class DriveEncoderGyro extends StateMachine{
	public DriveEncoderGyro(Distance d,Angle a,boolean relative,double speed,Distance encSpeed,DriveGyro driveGyro)
	{
		super(
			new TurnGyro(a,driveGyro,relative),
			new MultiState(0,
					new DriveEncoders(d,speed,encSpeed),
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
}
