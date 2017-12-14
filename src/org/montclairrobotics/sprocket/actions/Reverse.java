package org.montclairrobotics.sprocket.actions;

public class Reverse implements Action {

	private Action a;
	
	public Reverse(Action a) {
		this.a = a;
	}
	
	public void start() {
		a.stop();
	}
	
	public void enabled() {
		a.disabled();
	}
	
	public void stop() {
		a.start();
	}
	
	public void disabled() {
		a.enabled();
	}
}
