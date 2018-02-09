package org.montclairrobotics.sprocket.jrapoport;

public interface Completable extends Togglable {
	@Override
	void start();
	
	boolean isComplete();
	
	@Override
	void stop();
}
