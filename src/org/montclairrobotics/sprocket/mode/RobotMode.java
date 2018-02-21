package org.montclairrobotics.sprocket.mode;

import org.montclairrobotics.sprocket.jrapoport.Completable;
import org.montclairrobotics.sprocket.jrapoport.Updatable;

public abstract class RobotMode implements Completable, Updatable {
	protected final String name;
	
	public RobotMode(String name) {
		this.name = name;
	}
	
	@Override
	public abstract void start();

	@Override
	public abstract void update();

	@Override
	public abstract boolean isComplete();

	@Override
	public abstract void stop();
	
}
