package org.montclairrobotics.sprocket.states;

import org.montclairrobotics.sprocket.updater.Priority;
import org.montclairrobotics.sprocket.updater.Updatable;
import org.montclairrobotics.sprocket.updater.Updater;

/**
 * Extend this class to make a simple state machine;
 * there is an example.
 * The state machine must call super with the starting State
 * Each State must then provide the next state
 * @see org.montclairrobotics.sprocket.examples.Auto
 * @author Hymowitz
 *
 */

public class StateMachine implements Updatable,State{
	private State[] states;
	private State state;
	private int i;
	private boolean cascadeMode;
	/** 
	 * Start the state machine with the start state
	 * @param start An instance of the start state
	 */
	public StateMachine(State[] states)
	{
		this.states=states;
		i=-1;
		cascadeMode=false;
	}
	public StateMachine(Cascadable start)
	{
		this.state=start;
		cascadeMode=true;
	}
	public void start()
	{
		onStart();
		Updater.add(this, Priority.CONTROL);
	}
	public void onStart()
	{
		onStop();
		if(cascadeMode)
		{
		}
		else
		{
			i=0;
			state=states[i];
		}
		state.onStart();
	}
	public void update()
	{
		if(isDone())return;
		state.update();
		if(state.isDone())
		{
			state.onStop();
			if(cascadeMode)
			{
				i++;
				state=states[i];
			}
			else
			{
				state=((Cascadable)state).getNextState();
			}
			if(isDone())return;
			state.onStart();
			update();
		}
	}
	public void onStop()
	{
		if(isDone())return;
		states[i].onStop();
		i=-1;
	}
	public boolean isDone()
	{
		return (cascadeMode)?
				(state==null):
				(i<0||i>=states.length||states[i]==null);
	}
}
