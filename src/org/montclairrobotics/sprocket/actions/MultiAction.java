package org.montclairrobotics.sprocket.actions;

/**
 * One can perform multiple actions with the same trigger with this utility object
 *
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
