package org.montclairrobotics.sprocket.auto.states;

import org.montclairrobotics.sprocket.states.State;

public class Disable implements State{

	private Togglable obj;
	
	public Disable(Togglable obj)
	{
		this.obj=obj;
	}
	
	@Override
	public void start() {
		obj.disable();
	}

	@Override
	public void stop() {
		
	}

	@Override
	public void stateUpdate() {
		
	}

	@Override
	public boolean isDone() {
		return true;
	}

}
