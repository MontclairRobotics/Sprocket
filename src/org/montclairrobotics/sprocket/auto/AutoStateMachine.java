package org.montclairrobotics.sprocket.auto;

import org.montclairrobotics.sprocket.drive.DTInput;
import org.montclairrobotics.sprocket.loop.Updater;
import org.montclairrobotics.sprocket.states.State;
import org.montclairrobotics.sprocket.states.StateMachine;

public class AutoStateMachine extends StateMachine implements IAutoState{
	IAutoState[] states;
	DTInput output;
	double t;
	
	public AutoStateMachine(IAutoState...s)
	{
		super(s);
		this.states=s;
	}
	public void setDTInput(AutoDTInput output)
	{
		this.output=output;
		for(IAutoState s:states)
		{
			s.setDTInput(output);
		}
	}
	@Override
	public void start()
	{
		super.start();
		t=Updater.getTime();
	}
	@Override
	public double timeInState() {
		// TODO Auto-generated method stub
		return t-Updater.getTime();
	}
	@Override
	public DTInput getDTInput() {
		return output;
	}
}
