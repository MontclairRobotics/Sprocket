package org.montclairrobotics.sprocket.mode;

public abstract class DisabledMode extends RobotMode {
	public DisabledMode(String name) {
		super(name);
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
