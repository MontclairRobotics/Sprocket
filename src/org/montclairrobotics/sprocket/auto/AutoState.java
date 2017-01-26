package org.montclairrobotics.sprocket.auto;

import org.montclairrobotics.sprocket.drive.DTInput;
import org.montclairrobotics.sprocket.geometry.Angle;
import org.montclairrobotics.sprocket.geometry.Vector;
import org.montclairrobotics.sprocket.loop.Updater;
import org.montclairrobotics.sprocket.states.State;

public abstract class AutoState implements State{
	private double t;
	public final void start()
	{
		t=Updater.getTime();
		userStart();
	}
	public void userStart(){}
	public final void stop()
	{
		userStop();
		AutoMode.tgtDir=Vector.ZERO;
		AutoMode.tgtTurn=Angle.ZERO;
		AutoMode.inputType=DTInput.Type.SPEED;
	}
	public void userStop(){}
	public double timeInState()
	{
		return Updater.getTime()-t;
	}
}
