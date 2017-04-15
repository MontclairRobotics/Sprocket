package org.montclairrobotics.sprocket.drive.steps;

import org.montclairrobotics.sprocket.drive.DTTarget;
import org.montclairrobotics.sprocket.geometry.Angle;
import org.montclairrobotics.sprocket.geometry.Radians;
import org.montclairrobotics.sprocket.pipeline.Step;
import org.montclairrobotics.sprocket.utils.Action;

public class TurnLimiter implements Step<DTTarget>, Action{

	private double maxSpeed;
	private boolean enabled=true;

	public TurnLimiter(double maxSpeed)
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
		Angle out=in.getTurn();
		if(Math.abs(out.toRadians())>maxSpeed)
		{
			out=new Radians(maxSpeed*(out.toRadians()>0?1:-1));
		}
		return new DTTarget(in.getDirection(),out);
	}
}
