package org.montclairrobotics.sprocket.states;

import org.montclairrobotics.sprocket.utils.Updatable;
import org.montclairrobotics.sprocket.utils.Update;

/*
 * Extend this class to make a simple state machine;
 * there is an example.
 * The state machine must call super with the starting State
 * Each State must then provide the next state
 */

public abstract class StateMachine implements Updatable {
	private State state;
	
	public StateMachine(State start)
	{
		Update.add(this);
		state=start;
		state.onStart();
	}
	
	public void update()
	{
		if(state==null)return;
		state.update();
		if(state.isDone())
		{
			state.onStop();
			state=state.getNextState();
			if(state==null)return;
			state.onStart();
			update();
		}
	}
}
