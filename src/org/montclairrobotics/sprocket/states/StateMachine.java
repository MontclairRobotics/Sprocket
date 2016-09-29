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
	private int i=-1;
	private boolean master=false;
	/** 
	 * Start the state machine with the start state
	 * @param start An instance of the start state
	 */
	public StateMachine(State... states)
	{
		this.states=states;
		Updater.add(this, Priority.CALC);
	}
	public void start()
	{
		onStart();
		master=true;
	}
	public void stop()
	{
		onStop();
	}
	public void onStart()
	{
		onStop();
		i=0;
		this.states[i].onStart();
	}
	public void updateState()
	{
		if(isDone())return;
		states[i].updateState();
		if(states[i].isDone())
		{
			states[i].onStop();
			i++;
			if(isDone())
			{
				onStop();
				return;
			}
			states[i].onStart();
			updateState();//Recursion!
		}
	}
	public void onStop()
	{
		if(isDone())return;
		states[i].onStop();
		i=-1;
		master=false;
	}
	public boolean isDone()
	{
		return i<0||i>=states.length||states[i]==null;
	}
	public void update()
	{
		if(master)
			updateState();
	}
}
