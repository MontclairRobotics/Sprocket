package org.montclairrobotics.sprocket.auto.states;

import org.montclairrobotics.sprocket.states.State;
import org.montclairrobotics.sprocket.utils.Togglable;

/**
 * Enables a toggleable. This can be used to start toggleable operations in an auto mode
 */
public class Enable implements State{
	/**
	 * The toggleable to be enabled
	 */
	private Togglable obj;
	
	/**
	 * @param obj object to be enabled
	 */
	public Enable(Togglable obj)
	{
		this.obj=obj;
	}
	
	/**
	 * Enable the object on start
	 */
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
	
	/**
	 * The state only has to run through once so it will always be done
	 * @return true
	 */
	@Override
	public boolean isDone() {
		return true;
	}

}
