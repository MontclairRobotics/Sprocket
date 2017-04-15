package org.montclairrobotics.sprocket.states;

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
		startState();
	}
	@Override
	public void onEnable() {
		start(true);
	}

	@Override
	public void onDisable() {
		if(isDone())return;
		states[index].onDisable();
		index=-1;
		top=false;
	}

	@Override
	public void enabled() {
		if(isDone())return;
		states[index].enabled();
		while(states[index].isDone())
		{
			states[index].onDisable();
			index++;
			if(isDone())return;
			startState();
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
	public void startState()
	{
		if(states[index] instanceof StateMachine)
		{
			((StateMachine)states[index]).start(false);
		}
		else
		{
			states[index].onEnable();
		}
	}
}
