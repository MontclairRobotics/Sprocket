package org.montclairrobotics.sprocket.auto.states;

import org.montclairrobotics.sprocket.states.State;
import org.montclairrobotics.sprocket.utils.Togglable;

/**
 * Disables a toggleable. This can be used to stop a toggleable action in autonomous
 */
public class Disable implements State{
	
	/**
	 * The toggleable to be disabled
	 */
	private Togglable obj;
	
	/**
	 * @param obj the object to be disabled
	 */
	public Disable(Togglable obj)
	{
		this.obj=obj;
	}
	
	/**
	 * Disable the objecton start
	 */
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
	
	/**
	 * The state only has to run through once so it will always be done
	 * @return true
	 */
	@Override
	public boolean isDone() {
		return true;
	}

}
