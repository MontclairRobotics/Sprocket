package org.montclairrobotics.sprocket.modes;

public abstract class TeleopMode extends RobotMode {
	public TeleopMode(String name) {
		super("Teleop: " + name);
	}
	
	public TeleopMode() {
		super("Robot Mode: Teleop");
	}
	
	@Override
	public void start() {}

	@Override
	public void update() {}

	@Override
	public final boolean isComplete() {
		return false;
	}

	@Override
	public void stop() {}
}
