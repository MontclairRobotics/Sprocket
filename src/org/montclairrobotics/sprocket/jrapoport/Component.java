package org.montclairrobotics.sprocket.jrapoport;

import java.util.ArrayList;

/**
 * 
 * @author Joshua Rapoport
 * @version 2/15/18
 *
 */

public abstract class Component implements Updatable {
	private ArrayList<Action> queuedActions = new ArrayList<Action>();
	
	private Action currentAction = null;
	
	private ArrayList<Action> completedActions = new ArrayList<Action>();
	
	/** @return the list of elements of type <tt>Action</tt> that are queued for execution. */
	Action[] getQueuedActions() {
		return queuedActions.toArray(new Action[queuedActions.size()]);
	}

	/** @return the instance of type <tt>Action</tt> that is currently running. */
	Action getCurrentAction() {
		return currentAction;
	}

	/** @return the list of elements of type <tt>Action</tt> that have already been completed. */
	Action[] getCompletedActions() {
		return completedActions.toArray(new Action[completedActions.size()]);
	}

	/**
	 * Adds an action to the end of the list of queued actions.
	 * @param action the action to be added
	 * @return the success of the operation.
	 */
	boolean queueAction(Action action) {
		return queuedActions.add(action);
	}

	/**
	 * Inserts an action at a specific point in the list of queued actions.
	 * @param index the location to insert
	 * @param action the action to be added
	 * @return the success of the operation.
	 */
	void queueAction(int index, Action action) {
		queuedActions.add(index, action);
	}
	
	/** Clears all actions, both queued and completed. */
	void clearAllActions() {
		clearQueuedActions();
	}
	
	/** Clears all actions that are queued to run. */
	void clearQueuedActions() {
		queuedActions.clear();
	}
	
	/** Clears all actions that have been completed (frees up memory). */
	void clearCompletedActions() {
		completedActions.clear();
	}

	// TODO easily breakable
	@Override
	public void update() {
		if (currentAction != null) {
			currentAction.update();
		}
		
		if (currentAction.isComplete()) {
			currentAction.stop();
			completedActions.add(currentAction);
			
			if (queuedActions.isEmpty()) {
				currentAction = null;
			} else {
				currentAction = queuedActions.remove(0);
				currentAction.start();
			}
		}
	}
}
