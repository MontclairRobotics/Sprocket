package org.montclairrobotics.sprocket.auto.states;

import org.montclairrobotics.sprocket.SprocketRobot;
import org.montclairrobotics.sprocket.auto.AutoState;
import org.montclairrobotics.sprocket.geometry.Degrees;
import org.montclairrobotics.sprocket.utils.Input;
import org.montclairrobotics.sprocket.utils.PID;

public class TurnGyro extends AutoState {
	
	private PID pid; 
	
	public TurnGyro(PID pid) {
		this.pid = pid;
	}
	
	public TurnGyro(PID pid, Input<Double> gyro) {
		this(pid);
		pid.setInput(gyro);
	}
	
	@Override
	public void stateUpdate() {
		output.tgtTurn = new Degrees(SprocketRobot.getDriveTrain().getMaxTurn().toDegrees() * pid.get());
	}

	@Override
	public boolean isDone() {
		return Math.abs(pid.get()) < 0.1;
	}

}
