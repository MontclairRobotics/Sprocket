package org.montclairrobotics.sprocket.states;

/**
 * Extend this class as a sub-class in a StateMachine
 *
 * <p>Each State must provide an isDone() method, and a getNextState() which returns an instance of
 * the next State
 *
 * <p>All methods are called at least once.
 *
 * @see org.montclairrobotics.sprocket.examples.Auto
 * @author Hymowitz
 */
public abstract class StateObj implements State {
  /** This is called once when the state is started. */
  public void onStart() {}
  /** This is called once when the state is stopped. */
  public void onStop() {}
  /** This is called every loop until the state is stopped. */
  public void update() {}
}
