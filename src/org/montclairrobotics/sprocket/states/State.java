package org.montclairrobotics.sprocket.states;

public interface State {
	void start();
	void stop();
	void update();
	boolean isDone();
}
