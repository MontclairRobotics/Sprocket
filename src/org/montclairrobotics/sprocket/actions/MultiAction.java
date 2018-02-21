package org.montclairrobotics.sprocket.actions;

/**
 * Multiple actions at once
 */

public class MultiAction implements Action {
	private Action[] actions;
	
	public MultiAction(Action... actions) {
		this.actions = actions;
	}
	
	@Override
	public void start() {
		for (Action a : actions) {
			a.start();
		}
	}
	
	@Override
	public void enabled() {
		for(Action a : actions) {
			a.enabled();
		}
	}
	
	@Override
	public void stop() {
		for(Action a : actions) {
			a.stop();
		}
	}
	
	@Override
	public void disabled() {
		for(Action a : actions) {
			a.disabled();
		}
	}
}
