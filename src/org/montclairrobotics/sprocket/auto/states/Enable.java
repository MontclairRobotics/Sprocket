package org.montclairrobotics.sprocket.auto.states;

import org.montclairrobotics.sprocket.states.State;

public class Enable implements State{

	private Togglable obj;
	
	public Enable(Togglable obj)
	{
		this.obj=obj;
	}
	
	@Override
	public void start() {
		obj.enable();
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
