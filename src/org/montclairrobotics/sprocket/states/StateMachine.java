package org.montclairrobotics.sprocket.states;

import org.montclairrobotics.sprocket.updater.Priority;
import org.montclairrobotics.sprocket.updater.Updatable;
import org.montclairrobotics.sprocket.updater.Updater;

/**
 * Extend this class to make a simple state machine; there is an example. The state machine must
 * call super with the starting State Each State must then provide the next state
 *
 * @see org.montclairrobotics.sprocket.examples.Auto
 * @author Hymowitz
 */
public class StateMachine implements Updatable, State {
  private State[] states;
  private int i;
  /**
   * Start the state machine with the start state
   *
   * @param start An instance of the start state
   */
  public StateMachine(State[] states) {
    Updater.add(this, Priority.CALC);
    this.states = states;
    for (State state : states) {
      if (state instanceof StateMachine) {
        ((StateMachine) state).noAutoUpdate();
      }
    }
    i = -1;
  }

  public void onStart() {
    onStop();
    i = 0;
    this.states[i].onStart();
  }

  public void update() {
    if (isDone()) return;
    states[i].update();
    if (states[i].isDone()) {
      states[i].onStop();
      i++;
      if (isDone()) return;
      states[i].onStart();
      update();
    }
  }

  public void onStop() {
    if (isDone()) return;
    states[i].onStop();
    i = -1;
  }

  public boolean isDone() {
    return i < 0 || i >= states.length || states[i] == null;
  }

  public void noAutoUpdate() {
    Updater.remove(this);
  }
}
