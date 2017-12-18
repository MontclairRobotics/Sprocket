package org.montclairrobotics.sprocket.drive.steps;

import org.montclairrobotics.sprocket.drive.DTTarget;
import org.montclairrobotics.sprocket.geometry.Angle;
import org.montclairrobotics.sprocket.geometry.Radians;
import org.montclairrobotics.sprocket.pipeline.Step;
import org.montclairrobotics.sprocket.utils.Togglable;

public class TurnLimiter implements Step<DTTarget>, Togglable{

	private double maxSpeed;
	private boolean enabled;

	public TurnLimiter(double maxSpeed)
	{
		this.maxSpeed=maxSpeed;
	}

	@Override
	public void enable() {
		enabled=true;
	}

	@Override
	public void disable() {
		enabled=false;
	}

	@Override
	public DTTarget get(DTTarget in) {
		Angle out=in.getTurn();
		if(Math.abs(out.toRadians())>maxSpeed)
		{
			out=new Radians(maxSpeed*(out.toRadians()>0?1:-1));
		}
		return new DTTarget(in.getDirection(),out);
	}
}
