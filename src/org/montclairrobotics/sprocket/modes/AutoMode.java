package org.montclairrobotics.sprocket.modes;

public abstract class AutoMode extends RobotMode {
	public AutoMode(String name/*, States... states*/) {
		super("Auto: " + name);
	}
	
	public AutoMode(/* States... states*/) {
		super("Robot Mode: Autonomous");
	}
	
	@Override
	public void start() {}

	@Override
	public void update() {}

	@Override
	public boolean isComplete() { return false; }

	@Override
	public void stop() {}

}
