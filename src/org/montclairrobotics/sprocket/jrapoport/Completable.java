package org.montclairrobotics.sprocket.jrapoport;

public interface Completable {
	void start();
	
	boolean isComplete();
	
	void stop();
}
