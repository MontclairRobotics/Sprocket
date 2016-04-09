package org.montclairrobotics.sprocket.states;

import org.montclairrobotics.sprocket.utils.Updatable;
import org.montclairrobotics.sprocket.utils.Update;

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
		state.update();
		if(state.isDone())
		{
			state.onStop();
			state=state.getNextState();
			state.onStart();
		}
	}
}
