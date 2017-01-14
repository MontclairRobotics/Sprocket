package org.montclairrobotics.sprocket.states;

public class StateMachine implements State{

	private State[] states;
	private int index;
	
	public StateMachine(State...s)
	{
		this.states=s;
		index=-1;
	}
	@Override
	public void start() {
		index=0;
		states[index].start();
	}

	@Override
	public void stop() {
		if(isDone())return;
		states[index].stop();
		index=-1;
	}

	@Override
	public void update() {
		if(isDone())return;
		states[index].update();
		while(states[index].isDone())
		{
			states[index].stop();
			index++;
			if(isDone())return;
			states[index].start();
			states[index].update();
		}
	}

	@Override
	public boolean isDone() {
		return index>=0&&index<states.length;
	}

	
}
