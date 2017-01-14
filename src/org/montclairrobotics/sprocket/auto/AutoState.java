package org.montclairrobotics.sprocket.auto;

import org.montclairrobotics.sprocket.drive.DriveTrainTarget;
import org.montclairrobotics.sprocket.drive.MotorInputType;
import org.montclairrobotics.sprocket.geometry.Angle;
import org.montclairrobotics.sprocket.geometry.RVector;
import org.montclairrobotics.sprocket.loop.Updater;
import org.montclairrobotics.sprocket.states.State;

public abstract class AutoState implements State{
	private double t;
	public void start()
	{
		t=Updater.getTime();
	}
	public void stop()
	{
		AutoMode.driveTrainInput.set(new DriveTrainTarget(RVector.ZERO, Angle.ZERO,MotorInputType.SPEED));
	}
	public double timeInState()
	{
		return Updater.getTime()-t;
	}
}
