package org.montclairrobotics.sprocket.modes;

public abstract class TeleopMode extends RobotMode {
	public TeleopMode(String name) {
		super(name);
	}
	
	public TeleopMode() {
		super("");
	}
	
	@Override
	public String toString() {
		return "Teleop Mode" + ((name.isEmpty()) ? "" :  ": " + name);
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
