package org.montclairrobotics.sprocket.auto.states;

import org.montclairrobotics.sprocket.auto.AutoState;
import org.montclairrobotics.sprocket.utils.Debug;
import org.montclairrobotics.sprocket.utils.Input;


/**
 * A delay state stops the execution of the autonomous mode for a specified amount of time.
 */
public class Delay extends AutoState{
	
	/**
	 * The amount of time for the delay in seconds, given as a sprocket Input.
	 * @see Input
	 */
	private Input<Double> timeInput;
	
	/**
	 * The amount of time for the delay in seconds, given as a double.
	 */
	private double time;
	
	/**
	 * @param time time to delay in seconds
	 */
	public Delay(double time)
	{
		this.time=time;
	}
	
	/**
	 * @param timeInput input that will return the time in seconds
	 *                  @see Input
	 */
	public Delay(Input<Double> timeInput) {
		this.timeInput = timeInput;
	}
	
	
	/**
	 * If an Input was passed in, use it to specify the time to wait
	 * @see Input
	 */
	@Override
	public void userStart() {
		super.userStart();
		if(timeInput != null) time = timeInput.get();
	}
	
	/**
	 * Wait
	 */
	@Override
	public void stateUpdate() {
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * @return true when the specified time period has elapsed
	 */
	@Override
	public boolean isDone() {
		Debug.num("Time", time);
		Debug.num("Time in State", timeInState());
		return timeInState()>time;
	}
	
}
