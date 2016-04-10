package org.montclairrobotics.sprocket.states;

import org.montclairrobotics.sprocket.utils.Updatable;
import org.montclairrobotics.sprocket.utils.Updater;

public abstract class StateMachine implements Updatable {
	private State state;
	
	public StateMachine(State start)
	{
		Updater.add(this);
		state=start;
		state.onStart();
	}
	
	public void update()
	{
		state.update();
		if(state.isDone())
		{
			state.onStop();
			state=state.getNextState();
			state.onStart();
		}
	}
}
