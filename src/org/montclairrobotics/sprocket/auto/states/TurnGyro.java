package org.montclairrobotics.sprocket.auto.states;

import org.montclairrobotics.sprocket.SprocketRobot;
import org.montclairrobotics.sprocket.auto.AutoState;
import org.montclairrobotics.sprocket.geometry.Angle;
import org.montclairrobotics.sprocket.geometry.Degrees;
import org.montclairrobotics.sprocket.geometry.Radians;
import org.montclairrobotics.sprocket.utils.Input;
import org.montclairrobotics.sprocket.utils.PID;

public class TurnGyro extends AutoState {
	
	private PID pid; 
	
	public TurnGyro(Angle tgt,PID pid) {
		this.pid = pid.copy();
		this.pid.setMinMax(-180, 179, 0, 0);
		this.pid.setTarget(tgt.toDegrees()+this.pid.getInput().get());
	}
	
	public TurnGyro(Angle tgt,PID pid, Input<Double> gyro) {
		this(tgt,pid);
		this.pid.setInput(gyro);
	}
	
	@Override
	public void stateUpdate() {
		tgtTurn = new Radians(pid.get());
	}

	@Override
	public boolean isDone() {
		return Math.abs(pid.get()) < SprocketRobot.getDriveTrain().getMaxTurn().toRadians()*0.1;
	}

}
