package org.montclairrobotics.sprocket.drive.steps;

import org.montclairrobotics.sprocket.drive.DTTarget;
import org.montclairrobotics.sprocket.geometry.Vector;
import org.montclairrobotics.sprocket.pipeline.Step;

public class SpeedLimiter implements Step<DTTarget>, Togglable{

	private double maxSpeed;
	private boolean enabled;

	public SpeedLimiter(double maxSpeed)
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
		Vector out=in.getDirection();
		if(out.getMagnitude()>maxSpeed)
		{
			out=out.setMag(maxSpeed);
		}
		return new DTTarget(out,in.getTurn());
	}
}
