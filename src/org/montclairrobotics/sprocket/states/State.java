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
	public void updateState();
	/**
	 * This is a required method called once per loop.
	 * If it returns true, this state will stop and the next one will start.
	 * @return true if you want to start the next state, false otherwise.
	 * @see getNextState
	 */
	public boolean isDone();
}
