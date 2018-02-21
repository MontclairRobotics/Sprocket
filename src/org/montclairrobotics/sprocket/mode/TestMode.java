package org.montclairrobotics.sprocket.mode;

public abstract class TestMode extends RobotMode {
	public TestMode(String name/*, RobotMode mode*/) {
		super(name);
	}
	
	public TestMode(/* RobotMode mode*/) {
		super("Robot Mode: Test");
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
