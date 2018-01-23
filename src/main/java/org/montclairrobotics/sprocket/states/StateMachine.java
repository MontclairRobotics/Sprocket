package org.montclairrobotics.sprocket.states;

import org.montclairrobotics.sprocket.loop.Priority;
import org.montclairrobotics.sprocket.loop.Updatable;
import org.montclairrobotics.sprocket.loop.Updater;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

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
	public void start() {
		start(true);
	}

	@Override
	public void stop() {
		if(isDone())return;
		states[index].stop();
		index=-1;
		top=false;
	}

	@Override
	public void stateUpdate() {
		if(isDone())return;
		states[index].stateUpdate();
		while(states[index].isDone())
		{
			states[index].stop();
			index++;
			if(isDone())return;
			startState();
			states[index].stateUpdate();
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
			stateUpdate();
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
			states[index].start();
		}
		SmartDashboard.putString("I hope it gets here","It does!");
	}

	public void setStates(State ... states){
		this.states = states;
	}
}
