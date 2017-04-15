package org.montclairrobotics.sprocket.drive.steps;

import org.montclairrobotics.sprocket.drive.DTTarget;
import org.montclairrobotics.sprocket.geometry.Vector;
import org.montclairrobotics.sprocket.pipeline.Step;
import org.montclairrobotics.sprocket.utils.Action;

public class SpeedLimiter implements Step<DTTarget>, Action{

	private double maxSpeed;
	private boolean enabled=true;

	public SpeedLimiter(double maxSpeed)
	{
		this.maxSpeed=maxSpeed;
	}

	@Override
	public void onEnable() {
		enabled=true;
	}

	@Override
	public void onDisable() {
		enabled=false;
	}

	@Override
	public DTTarget get(DTTarget in) {
		if(!enabled)return in;
		Vector out=in.getDirection();
		if(out.getMagnitude()>maxSpeed)
		{
			out=out.setMag(maxSpeed);
		}
		return new DTTarget(out,in.getTurn());
	}
}
