package org.montclairrobotics.sprocket.actions;

import org.montclairrobotics.sprocket.loop.Priority;
import org.montclairrobotics.sprocket.loop.Updatable;
import org.montclairrobotics.sprocket.loop.Updater;


public class StateMachine implements State, Updatable{

	private State[] states;
	private int index;
	private boolean top;
	
	public StateMachine(State...s)
	{
		this.states=s;
		index=-1;
		Updater.add(this, Priority.AUTO);
	}
	public void start(boolean top)
	{
		this.top=top;
		index=0;
		states[index].start();
	}
	@Override
	public void start() {
		start(false);
	}

	@Override
	public void stop() {
		if(isDone())return;
		states[index].stop();
		index=-1;
		top=false;
	}

	@Override
	public void enabled() {
		if(isDone())return;
		states[index].enabled();
		while(states[index].isDone())
		{
			states[index].stop();
			index++;
			if(isDone())return;
			states[index].start();
			states[index].enabled();
		}
	}

	@Override
	public boolean isDone() {
		return index<0||index>=states.length;
	}
	public State[] getStates()
	{
		return states;
	}
	@Override
	public void update() {
		if(top)
		{
			enabled();
		}
	}
	@Override
	public void disabled() {
		// TODO Auto-generated method stub
		
	}
}
