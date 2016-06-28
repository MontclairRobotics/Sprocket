package org.montclairrobotics.sprocket.control;

import org.montclairrobotics.sprocket.drive.DriveTrain;
import org.montclairrobotics.sprocket.input.JoystickInput;
import org.montclairrobotics.sprocket.resetter.Resetter;
import org.montclairrobotics.sprocket.updater.Priority;
import org.montclairrobotics.sprocket.updater.Updatable;
import org.montclairrobotics.sprocket.updater.Updater;

import edu.wpi.first.wpilibj.Joystick.AxisType;

public class ArcadeDrive implements Updatable{
	private DriveTrain dt;
	private JoystickInput stick;
	public ArcadeDrive(DriveTrain dt,Joystick stick)
	{
		Updater.add(this, Priority.CONTROL);
		this.dt=dt;
		this.stick=new JoystickInput(stick);
	}
	public void update()
	{
		if(Resetter.getMode()==Resetter.Mode.TELEOP)
		{
			dt.driveArcade(stick.get().getRawX(),stick.get().getRawY());
		}
	}
}
