package org.montclairrobotics.sprocket.jrapoport;

import java.util.ArrayList;

public abstract class Component implements Updatable, Togglable {
	protected final String name;
	
	private ArrayList<Action> queuedActions = new ArrayList<Action>();
	
	private Action currentAction = null;
	
	private ArrayList<Action> completedActions = new ArrayList<Action>();
	
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

	/** @return the list of elements of type <tt>Action</tt> that have already been completed. */
	public final Action[] getCompletedActions() {
		return (Action[]) completedActions.toArray();
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
	
	/** Clears all actions, both queued and completed. */
	public final void clearAllActions() {
		clearQueuedActions();
	}
	
	/** Clears all actions that are queued to run. */
	public final void clearQueuedActions() {
		queuedActions.clear();
	}
	
	/** Clears all actions that have been completed (frees up memory). */
	public final void clearCompletedActions() {
		completedActions.clear();
	}
	
	private void nextAction() {
		currentAction = queuedActions.remove(0);
		currentAction.start();
	}

	@Override
	public void enable() {
		nextAction();
	}
	
	@Override
	public void disable() {
		queueAction(0, currentAction);
		currentAction = null;
	}
	
	@Override
	public void update() {
		if (currentAction == null) {
			return;
		}
		
		currentAction.update();
		
		if (currentAction.isComplete()) {
			currentAction.stop();
			completedActions.add(currentAction);
			
			if (queuedActions.isEmpty()) {
				currentAction = null;
			} else {
				nextAction();
			}
		}
	}
}
