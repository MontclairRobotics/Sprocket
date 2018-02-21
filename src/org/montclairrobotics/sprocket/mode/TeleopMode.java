package org.montclairrobotics.sprocket.mode;

public abstract class TeleopMode extends RobotMode {
	public TeleopMode(String name) {
		super(name);
	}
	
	public TeleopMode() {
		super("Robot Mode: Teleop");
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
