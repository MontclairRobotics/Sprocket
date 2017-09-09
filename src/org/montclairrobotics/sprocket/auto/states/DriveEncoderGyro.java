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
	
	public DriveEncoderGyro(Number distance,Number speed,Number angle,Comparable<Boolean> relative,GyroCorrection gyroCorrection)
	{
		super(makeStates(distance,speed,angle,relative,gyroCorrection));
	}
	
	public static State[] makeStates(Number distance,Number speed,Number angle,Comparable<Boolean> relative,GyroCorrection gyroCorrection)
	{
		State[] r={
			new TurnGyro(angle,gyroCorrection,relative),
			new State()
			{

				@Override
				public void start() {
					// TODO Auto-generated method stub
					gyroCorrection.setTargetAngle(new Degrees(angle.doubleValue()),relative.compareTo(true)==0);
				}

				@Override
				public void enabled() {
					// TODO Auto-generated method stub
					gyroCorrection.use();	
				}

				@Override
				public void stop() {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void disabled() {
					// TODO Auto-generated method stub
					
				}

				@Override
				public boolean isDone() {
					// TODO Auto-generated method stub
					return false;
				}
			}
		};
		return r;
	}
}
