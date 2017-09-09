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
	
	public MultiState(int stateToStopAt, State... states) {
		super(states);
		final State[] statesFinal=states;
		final int stateToStopAtFinal=stateToStopAt;
		if(stateToStopAt < 0 || stateToStopAt > states.length) {
			this.done = new Input<Boolean>() {
				@Override
				public Boolean get() {
					for(State s : statesFinal) {
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
					return statesFinal[stateToStopAtFinal].isDone();
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
