package org.montclairrobotics.sprocket.drive.steps;

import org.montclairrobotics.sprocket.SprocketRobot;
import org.montclairrobotics.sprocket.drive.DTTarget;
import org.montclairrobotics.sprocket.geometry.Angle;
import org.montclairrobotics.sprocket.geometry.Radians;
import org.montclairrobotics.sprocket.geometry.XY;
import org.montclairrobotics.sprocket.pipeline.Step;
import org.montclairrobotics.sprocket.utils.Input;
import org.montclairrobotics.sprocket.utils.PID;

public class TankDriveConversion implements Step<DTTarget>{

	private PID pid;
	private Input<Boolean> enabled;
	private Angle maxTurn;
	public TankDriveConversion(PID pid,Input<Boolean>enabled)
	{
		this.pid=pid.copy();
		this.pid.setMinMax(-180, 179, 0, 0);
		this.enabled=enabled;
	}
	@Override
	public DTTarget get(DTTarget in) {
		if(enabled.get())
		{
			Angle diff=in.getDirection().getAngle();
			if(Math.abs(diff.toDegrees())<90)
			{
				pid.setTarget(diff.toDegrees());
				return new DTTarget(new XY(0,in.getDirection().getMagnitude()),
						new Radians(pid.get()*SprocketRobot.getDriveTrain().getMaxTurn().toRadians()));
			}
			else
			{
				pid.setTarget(diff.add(Angle.HALF).toDegrees());
				return new DTTarget(new XY(0,-in.getDirection().getMagnitude()),new Radians(pid.get()*SprocketRobot.getDriveTrain().getMaxTurn().toRadians()));
			}	
		}
		else
		{
			return in;
		}
	}

}
