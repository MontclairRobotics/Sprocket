package org.montclairrobotics.sprocket.auto.states;

import org.montclairrobotics.sprocket.auto.AutoState;
import org.montclairrobotics.sprocket.utils.Debug;

public class Delay extends AutoState{

	private Number time;

	public Delay(Number time)
	{
		this.time=time;
	}
	
	
	
	@Override
	public void userStart() {
		super.userStart();
		super.TIMEOUT=time.doubleValue();
        Debug.msg("It reached the Delay","");
	}

	@Override
	public void enabled() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean userIsDone() {
		Debug.num("Time", super.TIMEOUT);
		Debug.num("Time in State", timeInState());
        ////////////Debug.msg("IsDone",isDone()?"TRUE":"FALSE"); AAAAAAAAAAAAAAAAAAaaaaa
		return false;
	}

	@Override
	public void disabled() {
		// TODO Auto-generated method stub
		
	}
	
}
