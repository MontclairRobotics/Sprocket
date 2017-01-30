package org.montclairrobotics.sprocket.auto;

import org.montclairrobotics.sprocket.drive.DTInput;
import org.montclairrobotics.sprocket.geometry.Angle;
import org.montclairrobotics.sprocket.geometry.Vector;
import org.montclairrobotics.sprocket.loop.Updater;
import org.montclairrobotics.sprocket.states.State;

public abstract class AutoState implements IAutoState{
	private double t;//time state was started
	public AutoDTInput output;//output to drivetrain
	
	public final void start()
	{
		t=Updater.getTime();
		userStart();
	}
	public void userStart(){}
	public final void stop()
	{
		userStop();
		output.tgtDir=Vector.ZERO;
		output.tgtTurn=Angle.ZERO;
		output.inputType=DTInput.Type.SPEED;
	}
	public void userStop(){}
	public double timeInState()
	{
		return Updater.getTime()-t;
	}
	public void setDTInput(AutoDTInput out)
	{
		this.output=out;
	}
	public DTInput getDTInput()
	{
		return output;
	}
}
