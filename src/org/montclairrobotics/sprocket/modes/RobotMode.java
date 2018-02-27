package org.montclairrobotics.sprocket.modes;

import org.montclairrobotics.sprocket.jrapoport.Completable;
import org.montclairrobotics.sprocket.jrapoport.Supuroketto;
import org.montclairrobotics.sprocket.loop.Updatable;

public abstract class RobotMode implements Completable, Updatable {
	protected final String name;
	
	private final long INIT_TIME;
	
	public RobotMode(String name) {
		this.name = name;
		this.INIT_TIME = Supuroketto.currentTimeMillis();
	}
	
	public long getInitTime() {
		return INIT_TIME;
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
