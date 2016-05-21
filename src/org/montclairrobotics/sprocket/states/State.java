package org.montclairrobotics.sprocket.states;

public interface State {
	/**
	 * This is called once when the state is started.
	 */
	public void onStart();
	/**
	 * This is called once when the state is stopped.
	 */
	public void onStop();
	/**
	 * This is called every loop until the state is stopped.
	 */
	public void update();
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
