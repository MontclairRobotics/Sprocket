package org.montclairrobotics.sprocket.auto.states;

import org.montclairrobotics.sprocket.auto.AutoState;
import org.montclairrobotics.sprocket.core.Sprocket;
import org.montclairrobotics.sprocket.geometry.Angle;
import org.montclairrobotics.sprocket.geometry.Degrees;
import org.montclairrobotics.sprocket.loop.Updater;
import org.montclairrobotics.sprocket.utils.Debug;

public class TurnGyro extends AutoState {
	
	private Angle tgt;
	private Number tgtInDeg;
	
	private double incorrectTime;
	private Comparable<Boolean> relative;
	
	public static Angle tolerance = new Degrees(4);
	public static double timeAtTarget = 0.5;
	
	public TurnGyro(Number tgtDeg, Comparable<Boolean> relative) {
		this.tgtInDeg=tgtDeg;
		this.relative=relative;
	}
	
	@Override
	public void userStart() {
		tgt = new Degrees(tgtInDeg.doubleValue());
		
		if (relative.compareTo(true) == 0) {
			Sprocket.gyro.setTargetAngleRelative(tgt);
		} else {
			Sprocket.gyro.setTargetAngleReset(tgt);
		}
		
		Sprocket.gyro.setOutRange(-0.5, 0.5);
		incorrectTime = Updater.getTimeSec() - timeAtTarget + 0.1;
	}
	
	@Override
	public void update() {
		Sprocket.gyro.use();
		
		if (Math.abs(Sprocket.gyro.getError().toDegrees()) > tolerance.toDegrees()) {
			incorrectTime = Updater.getTimeSec();
		}
		
		Debug.print("gyroError", Sprocket.gyro.getError().toDegrees());
		Debug.print("incorrectTime", incorrectTime);
		Debug.print("cur-time", Updater.getTimeSec());
		Debug.print("timeCorrect", Updater.getTimeSec() - incorrectTime);
		Debug.print("FINISHED", isComplete());
	}
	
	@Override
	public void userStop() {
		Sprocket.gyro.resetOutRange();
	}

	@Override
	public boolean userIsDone() {
		return (Updater.getTimeSec()-incorrectTime>timeAtTarget);
	}
	
	public Angle getTgt() {
		return tgt;
	}

	public void setTgt(Angle tgt) {
		this.tgt = tgt;
	}
}
