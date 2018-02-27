package org.montclairrobotics.sprocket.actions;

import org.montclairrobotics.sprocket.loop.Priority;
import org.montclairrobotics.sprocket.loop.Updatable;
import org.montclairrobotics.sprocket.loop.Updater;
import org.montclairrobotics.sprocket.utils.Debug;


public class StateMachine implements State, Updatable {

	private State[] states;
	private int index;
	private boolean top;
	
	public StateMachine(State...s) {
		this.states = s;
		index = -1;
		Updater.add(this, Priority.NORMAL);
	}
	
	public void start(boolean top) {
		this.top = top;
		index= 0;
		states[index].start();
        Debug.print("Auto Mode Running: stateMachine", "");
	}
	
	@Override
	public void start() {
		start(false);
	}

	@Override
	public void update() {
		if (!top || isDone())
			return;
		
		states[index].update();
		
		while(states[index].isDone()) {
			states[index].stop();
			index++;
			
			if(isDone()) {
				stop();
				return;
			}
			
			states[index].start();
			states[index].update();
		}
	}
	
	@Override
	public boolean isComplete() {
		return (index < 0) || (index >= states.length);
	}
	
	@Override
	public void stop() {
		if (isDone())
			return;
		
		states[index].stop();
		index = -1;
		top = false;
	}

	@Override
	public boolean isDone() {
		return (index < 0) || (index >= states.length);
	}
	
	public State[] getStates() {
		return states;
	}
}
