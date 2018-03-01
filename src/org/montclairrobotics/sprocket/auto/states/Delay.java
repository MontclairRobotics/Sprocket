package org.montclairrobotics.sprocket.auto.states;

import org.montclairrobotics.sprocket.auto.AutoState;
import org.montclairrobotics.sprocket.utils.Debug;

public class Delay extends AutoState {

	private Number time;

	public Delay(Number time) {
		this.time =time;
	}
	
	@Override
	public void userStart() {
//		super.userStart();
		TIMEOUT = time.doubleValue();
        Debug.print("It reached the Delay","");
	}

	@Override
	public void update() {}

	@Override
	public boolean userIsDone() {
		Debug.num("Time", TIMEOUT);
		Debug.num("Time in State", timeInState());
        //Debug.msg("IsDone",isDone()?"TRUE":"FALSE");
		return false;
	}

	@Override
	public void userStop() {
		// TODO Auto-generated method stub
	}
	
}
