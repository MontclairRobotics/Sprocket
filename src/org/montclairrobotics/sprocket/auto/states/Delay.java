package org.montclairrobotics.sprocket.auto.states;

import org.montclairrobotics.sprocket.auto.AutoState;
import org.montclairrobotics.sprocket.utils.Debug;

public class Delay extends AutoState{

	private double time;

	public Delay(double time)
	{
		this.time=time;
	}
	@Override
	public void stateUpdate() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isDone() {
		Debug.num("Time", time);
		Debug.num("Time in State", timeInState());
		return timeInState()>time;
	}
	
}
