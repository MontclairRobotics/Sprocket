package org.montclairrobotics.sprocket.modes;

public abstract class TestMode extends RobotMode {
	private RobotMode mode;
	
	public TestMode(RobotMode mode) {
		super("{" + mode.name + "}");
		
		this.mode = mode;
	}
	
	@Override
	public String toString() {
		return "Test Mode" + ((name.isEmpty()) ? "" :  ": " + name);
	}
	
	@Override
	public void start() {
		mode.start();
	}

	@Override
	public void update() {
		mode.update();
	}

	@Override
	public boolean isComplete() {
		return mode.isComplete();
	}

	@Override
	public void stop() {
		mode.update();
	}
}
