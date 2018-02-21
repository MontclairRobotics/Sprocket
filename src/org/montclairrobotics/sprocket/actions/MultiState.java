package org.montclairrobotics.sprocket.actions;

import org.montclairrobotics.sprocket.utils.Input;

/**
 * Multiple states at once
 *
 */

public class MultiState extends MultiAction {
	
	private Input<Boolean> done;
	
	public MultiState(Input<Boolean> done, Action... states) {
		super(states);
		this.done = done;
	}
	
	public MultiState(final int stateToStopAt, final Action... states) {
		super(states);
		
		if (stateToStopAt < 0 || stateToStopAt > states.length) {
			this.done = new Input<Boolean>() {
				@Override
				public Boolean get() {
					for(Action s : states) {
						if(!s.isDone()) {
							return false;
						}
					}
					return true;
				}
			};
		} else {
			this.done = new Input<Boolean>() {
				@Override public Boolean get() { return states[stateToStopAt].isDone(); }
			};
		}
	}
	
	public MultiState(Action... states) {
		this(-1, states);
	}

	@Override
	public boolean isDone() {
		return done.get();
	}

}
