package org.montclairrobotics.sprocket.modes;

public abstract class DisabledMode extends RobotMode {
	public DisabledMode(String name) {
		super(name);
	}
	
	public DisabledMode() {
		super("");
	}
	
	@Override
	public String toString() {
		return "Disabled Mode" + ((name.isEmpty()) ? "" :  ": " + name);
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
