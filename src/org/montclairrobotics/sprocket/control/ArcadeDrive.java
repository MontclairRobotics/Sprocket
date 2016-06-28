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
	private JoystickInput x,y;
	public ArcadeDrive(DriveTrain dt,Joystick stick)
	{
		Updater.add(this, Priority.CONTROL);
		this.dt=dt;
		this.x=new JoystickInput(stick,AxisType.kX);
		this.y=new JoystickInput(stick,AxisType.kY);
	}
	public void update()
	{
		if(Resetter.getMode()==Resetter.Mode.TELEOP)
		{
			dt.driveArcade(x.get(),y.get());
		}
	}
}
