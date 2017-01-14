package org.montclairrobotics.sprocket.drive;

import org.montclairrobotics.sprocket.utils.Input;

public class DriveTrainInput implements Input<DriveTrainTarget>{

	private DriveTrainTarget driveTarget;
	public void set(DriveTrainTarget driveTarget)
	{
		this.driveTarget=driveTarget;
	}
	public DriveTrainTarget get() {
		return driveTarget;
	}

}
