package org.montclairrobotics.sprocket.jrapoport;

import java.util.ArrayList;

import org.montclairrobotics.sprocket.loop.Updatable;

public abstract class Component implements Updatable, Togglable {
	protected final String name;
	
	private ArrayList<Action> queuedActions = new ArrayList<Action>();
	private Action currentAction = null;
	
	public Component(String name) {
		this.name = name;
	}
	
	public Component() {
		this.name = super.toString();
	}
	
	@Override
	public String toString() {
		return name;
	}
	
	/** @return the list of elements of type <tt>Action</tt> that are queued for execution. */
	public final Action[] getQueuedActions() {
		return (Action[]) queuedActions.toArray();
	}

	/** @return the instance of type <tt>Action</tt> that is currently running. */
	public final Action getCurrentAction() {
		return currentAction;
	}

	/**
	 * Adds an action to the end of the list of queued actions.
	 * @param action the action to be added
	 * @return the success of the operation.
	 */
	public final boolean queueAction(Action action) {
		return queuedActions.add(action);
	}

	/**
	 * Inserts an action at a specific point in the list of queued actions.
	 * @param index the location to insert
	 * @param action the action to be added
	 * @return the success of the operation.
	 */
	public final void queueAction(int index, Action action) {
		queuedActions.add(index, action);
	}
	
	/** Clears all actions, both queued and current. */
	public final void clearAllActions() {
		clearQueuedActions();
		currentAction = null;
	}
	
	/** Clears all actions that are queued to run. */
	public final void clearQueuedActions() {
		queuedActions.clear();
	}
	
	private void nextAction() {
		currentAction = queuedActions.remove(0);
		currentAction.start();
	}

	@Override
	public final void enable() {
		nextAction();
		
		if (currentAction != null)
			currentAction.start();
	}
	
	@Override
	public final void disable() {
		if (currentAction != null)
			currentAction.stop();
		
		queueAction(0, currentAction);
		currentAction = null;
	}
	
	public final boolean isEnabled() {
		return currentAction != null;
	}
	
	@Override
	public void update() {
		if (!this.isEnabled()) {
			return;
		}
		
		currentAction.update();
		
		if (currentAction.isComplete()) {
			currentAction.stop();
			
			if (queuedActions.isEmpty()) {
				currentAction = null;
			} else {
				nextAction();
			}
		}
	}
}
