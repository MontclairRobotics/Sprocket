package org.montclairrobotics.sprocket.states;

import org.montclairrobotics.sprocket.utils.Input;

public class MultiState implements State {
	
	private State[] states;
	private Input<Boolean> done;
	
	public MultiState(Input<Boolean> done, State... states) {
		this.states = states;
		this.done = done;
	}
	
	public MultiState(int stateToStopAt, State... states) {
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
		
		this.states = states;
	}
	
	public MultiState(State... states) {
		this(-1, states);
	}
	
	@Override
	public void onEnable() {
		for(State s : states) {
			s.onEnable();
		}
	}

	@Override
	public void onDisable() {
		for(State s : states) {
			s.onDisable();
		}
	}

	@Override
	public void enabled() {
		for(State s : states) {
			s.enabled();
		}
	}

	@Override
	public boolean isDone() {
		return done.get();
	}

}
