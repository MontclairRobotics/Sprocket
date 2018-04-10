package org.montclairrobotics.sprocket.modes;

import org.montclairrobotics.sprocket.jrapoport.State;
import org.montclairrobotics.sprocket.jrapoport.StateMachine;

public abstract class AutoMode extends RobotMode {
	private StateMachine stateMachine;
	
	public AutoMode(String name, StateMachine machine) {
		super(name);
		
		this.stateMachine = machine;
	}
	
	public AutoMode(String name, State... states) {
		this(name, new StateMachine(name, states));
	}
	
	public AutoMode(State... states) {
		this("", new StateMachine("", states));
	}
	
	@Override
	public String toString() {
		return "Auto Mode" + ((name.isEmpty()) ? "" :  ": " + name);
	}
	
	@Override
	public void start() {
		stateMachine.start();
	}

	@Override
	public void update() {
		stateMachine.update();
	}

	@Override
	public boolean isComplete() {
		return stateMachine.isComplete();
	}

	@Override
	public void stop() {
		stateMachine.stop();
	}

}
