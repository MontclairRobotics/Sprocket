package org.montclairrobotics.sprocket.modes;

public abstract class DisabledMode extends RobotMode {
	public DisabledMode(String name) {
		super("Disabled: " + name);
	}
	
	public DisabledMode() {
		super("Robot Mode: Disabled");
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
