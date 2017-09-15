package org.montclairrobotics.sprocket.drive.steps;

import org.montclairrobotics.sprocket.actions.Action;
import org.montclairrobotics.sprocket.drive.DTTarget;
import org.montclairrobotics.sprocket.geometry.Angle;
import org.montclairrobotics.sprocket.geometry.Radians;
import org.montclairrobotics.sprocket.pipeline.Step;

public class TurnLimiter implements Step<DTTarget>, Action{

	private double maxSpeed;
	private boolean enabled=true;

	public TurnLimiter(double maxSpeed)
	{
		this.maxSpeed=maxSpeed;
	}

	@Override
	public void start() {
		enabled=true;
	}

	@Override
	public void stop() {
		enabled=false;
	}

	@Override
	public DTTarget get(DTTarget in) {
		if(!enabled)return in;
		double out=in.getTurn();
		if(Math.abs(out)>maxSpeed)
		{
			out=maxSpeed*(out>0?1:-1);
		}
		return new DTTarget(in.getDirection(),out);
	}

	@Override
	public void enabled() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void disabled() {
		// TODO Auto-generated method stub
		
	}
}
