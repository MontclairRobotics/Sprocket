package org.montclairrobotics.sprocket.jrapoport;

import java.util.ArrayList;
import java.util.List;

import org.montclairrobotics.sprocket.loop.Updatable;

public class StateMachine implements Togglable, Completable, Updatable {
	protected String name;
	
	private List<State> states = new ArrayList<State>();
	private int index;
	private boolean enabled;
	
	public StateMachine(String name) {
		this(name, new State[] {});
	}
	
	public StateMachine(String name, State... states) {
		this.name = name;
		
		for (State s : states) {
			this.states.add(s);
		}
		
		this.index = 0;
		this.enabled = false;
	}
	
	@Override
	public String toString() {
		return "State Machine" + (name.isEmpty() ? "" : ": " + name);
	}
	
	/** @return the list of elements of type <tt>State</tt> that are queued for execution. */
	public final List<State> getQueuedStates() {
		return states.subList(index + 1, states.size());
	}

	/** @return the instance of type <tt>State</tt> that is currently running. */
	public final State getCurrentState() {
		return states.get(index);
	}
	
	/** @return the list of elements of type <tt>State</tt> that have finished runnning. */
	public final List<State> getCompletedStates() {
		return states.subList(0, index);
	}

	/**
	 * Adds an state to the end of the queue.
	 * @param	state the state to be added
	 * @return	the success of the operation.
	 */
	public final boolean queueState(State state) {
		return states.add(state);
	}

	/**
	 * Inserts an action at a specific point in the list of queued actions.
	 * @param index	the location to insert
	 * @param state	the state to be added
	 * @return		the success of the operation.
	 */
	public final void queueState(int index, State state) {
		states.add(index, state);
	}
	
	public final boolean removeState(int index) {
		return states.remove(index) != null;
	}
	
	/** Clears all states, both queued and current. */
	public final void clearAllStates() {
		states.clear();
	}
	
	/** Clears all queued states. */
	public final void clearQueuedStates() {
		getQueuedStates().clear();
	}
	
	/** Clears all completed states. */
	public final void clearCompletedStates() {
		getCompletedStates().clear();
	}
	
	
	private void nextState() {
		index++;
		getCurrentState().start();
	}
	
	@Override
	public void start() {
		this.enable();
	}

	@Override
	public void enable() {
		enabled = true;
		
		if (!this.isComplete())
			getCurrentState().start();
	}

	@Override
	public void disable() {
		enabled = false;
		
		if (!this.isComplete())
			getCurrentState().stop();
	}

	@Override
	public boolean isEnabled() {
		return enabled;
	}

	@Override
	public void update() {
		if (!this.isEnabled() || this.isComplete()) {
			return;
		}
		
		getCurrentState().update();
		
		if (getCurrentState().isComplete()) {
			getCurrentState().stop();
			
			if (!this.isComplete()) {
				nextState();
			}
		}
	}
	
	@Override
	public boolean isComplete() {
		return index >= states.size();
	}

	@Override
	public void stop() {
		this.disable();
	}
}
