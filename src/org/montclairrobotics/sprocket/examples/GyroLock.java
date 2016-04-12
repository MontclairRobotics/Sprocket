package org.montclairrobotics.sprocket.examples;

import org.montclairrobotics.sprocket.drive.DriveTrain;
import org.montclairrobotics.sprocket.drive.Lock;
import org.montclairrobotics.sprocket.utils.PID;

public class GyroLock extends Lock{

	public GyroLock(DriveTrain dt, PID pid) {
		super(dt, pid);
	}

	@Override
	public double getCurVal() {
		// TODO RETURN CURRENT VAL
		return 0;
	}
}
