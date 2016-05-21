package org.montclairrobotics.sprocket.states;

/**
 * Extend this class as a sub-class in a StateMachine
 * <p>
 * Each State must provide an isDone() method,
 * and a getNextState() which returns an instance of the next State
 * <p>
 * All methods are called at least once.
 * @see org.montclairrobotics.sprocket.examples.Auto
 * @author Hymowitz
 *
 */

public abstract class StateObj implements State{
	/**
	 * This is called once when the state is started.
	 */
	public void onStart(){}
	/**
	 * This is called once when the state is stopped.
	 */
	public void onStop(){}
	/**
	 * This is called every loop until the state is stopped.
	 */
	public void update(){}
	/**
	 * This is a required method called once per loop.
	 * If it returns true, this state will stop and the next one will start.
	 * @return true if you want to start the next state, false otherwise.
	 * @see getNextState
	 */
	public abstract boolean isDone();
	/**
	 * Once this state is done, this method will be called to get 
	 * an instance of another state.
	 * @return An instance of the next state, or null if this is the last state.
	 */
	public abstract State getNextState();
}
