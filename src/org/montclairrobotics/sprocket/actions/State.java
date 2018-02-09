package org.montclairrobotics.sprocket.actions;

/**
 * The basic unit of a State Machine, very similar to Action, 
 * 		except States also know when they are finished.
 * Consequently, state machines can be made which move from state to state when each one is finished.
 *
 */
public interface State extends Action {
	boolean isDone();
}
