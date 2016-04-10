package org.montclairrobotics.sprocket.states;

import org.montclairrobotics.sprocket.utils.Updatable;


public abstract class State implements Updatable{
	public void onStart(){}
	public void onStop(){}
	public void update(){};
	public abstract boolean isDone();
	public abstract State getNextState();
}
