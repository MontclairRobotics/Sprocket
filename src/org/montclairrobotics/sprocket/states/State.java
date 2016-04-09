package org.montclairrobotics.sprocket.states;

import org.montclairrobotics.sprocket.utils.Updatable;


public abstract class State implements Updatable{
	public void onStart(){}
	public void onStop(){}
	public boolean isDone(){return false;}
	public State getNextState(){return null;}
	public void update(){};
}
