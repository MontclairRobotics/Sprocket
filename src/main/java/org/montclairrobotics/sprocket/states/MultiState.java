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
	public void start() {
		for(State s : states) {
			s.start();
		}
	}

	@Override
	public void stop() {
		for(State s : states) {
			s.stop();
		}
	}

	@Override
	public void stateUpdate() {
		for(State s : states) {
			s.stateUpdate();
		}
	}

	@Override
	public boolean isDone() {
		return done.get();
	}

}
