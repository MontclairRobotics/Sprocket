package org.montclairrobotics.sprocket.actions;

import org.montclairrobotics.sprocket.utils.Input;

/**
 * Can perform multiple actions at once
 *
 */

public class MultiState extends MultiAction implements State {
	
	private Input<Boolean> done;
	
	public MultiState(Input<Boolean> done, State... states) {
		super(states);
		this.done = done;
	}
	
	public MultiState(final int stateToStopAt, final State... states) {
		super(states);
		if(stateToStopAt < 0 || stateToStopAt > states.length) {
			this.done = new Input<Boolean>() {
				@Override
				public Boolean get() {
					for(State s : states) {
						if(!s.isDone()) {
							return false;
						}
					}
					return true;
				}
			};
		} else {
			this.done = new Input<Boolean>() {
				@Override
				public Boolean get() {
					return states[stateToStopAt].isDone();
				}
			};
		}
	}
	
	public MultiState(State... states) {
		this(-1, states);
	}

	@Override
	public boolean isDone() {
		return done.get();
	}

}
