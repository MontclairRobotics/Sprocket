package org.montclairrobotics.sprocket.auto.states;

import org.montclairrobotics.sprocket.auto.AutoState;
import org.montclairrobotics.sprocket.utils.Debug;
import org.montclairrobotics.sprocket.utils.Input;

public class Delay extends AutoState{

	private Input<Double> timeInput;
	private double time;

	public Delay(double time)
	{
		this.time=time;
	}
	
	public Delay(Input<Double> timeInput) {
		this.timeInput = timeInput;
	}
	
	
	
	@Override
	public void userStart() {
		super.userStart();
		if(timeInput != null) time = timeInput.get();
	}

	@Override
	public void enabled() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isDone() {
		Debug.num("Time", time);
		Debug.num("Time in State", timeInState());
		return timeInState()>time;
	}
	
}
