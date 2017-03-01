package org.montclairrobotics.sprocket.auto.states;

import org.montclairrobotics.sprocket.states.State;
import org.montclairrobotics.sprocket.utils.Togglable;

public class Enable implements State{

	private Togglable obj;
	
	public Enable(Togglable obj)
	{
		this.obj=obj;
	}
	
	@Override
	public void start() {
		obj.enable();// TODO Auto-generated method stub
		
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
