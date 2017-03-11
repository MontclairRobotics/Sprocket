package org.montclairrobotics.sprocket.states;

public interface State {
	void start();
	void stop();
	void stateUpdate();
	boolean isDone();
}
