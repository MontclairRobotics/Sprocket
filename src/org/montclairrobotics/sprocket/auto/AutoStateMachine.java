package org.montclairrobotics.sprocket.auto;

import org.montclairrobotics.sprocket.states.State;
import org.montclairrobotics.sprocket.states.StateMachine;

public class AutoStateMachine extends StateMachine implements IAutoState {

	public AutoStateMachine(State...s)
	{
		super(s);
	}
	public final void start()
	{
		super.start();
		userStart();
	}

	/**
	 * Run when the AutoState beigns
	 */
	public void userStart(){}
	public final void stop()
	{
		userStop();
		super.stop();
	}

	/**
	 * Run when the AutoState ends
	 */
	public void userStop(){}

}
