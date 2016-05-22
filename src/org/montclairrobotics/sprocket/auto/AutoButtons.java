package org.montclairrobotics.sprocket.auto;

import org.montclairrobotics.sprocket.control.Button;
import org.montclairrobotics.sprocket.drive.DriveTrain;
import org.montclairrobotics.sprocket.utils.Grip;
import org.montclairrobotics.sprocket.utils.XY;
import edu.wpi.first.wpilibj.Joystick;

public class AutoButtons {
	public static class AlignButton extends Button
	{
		private AutoAlign align;
		public AlignButton(Joystick stick,int button,Grip grip,DriveTrain dt,XY target,Zones zones)
		{
			super(stick, button);
			align=new AutoAlign(grip,dt)
				.setTarget(target)
				.setZones(zones);
		}
		public void down()
		{
			align.align();
		}
	}
}
