package org.montclairrobotics.sprocket.states;

/*
 * Extend this class as a sub-class in a StateMachine
 * 
 * Each State must provide an isDone() method,
 * 	and a getNextState which returns an instance of another State
 * 
 * In addition, each state has access to the
 * onStart() method, called once the State is created
 * onStop() method, called once after isDone() returns true
 * update() method, called once per loop until isDone() returns true
 * 
 * All methods are called at least once.
 */

public abstract class State{
	public void onStart(){}
	public void onStop(){}
	public void update(){}
	public abstract boolean isDone();
	public abstract State getNextState();
}
