package org.montclairrobotics.sprocket.drive.steps;

import org.montclairrobotics.sprocket.actions.Action;
import org.montclairrobotics.sprocket.drive.DTTarget;
import org.montclairrobotics.sprocket.geometry.Vector;
import org.montclairrobotics.sprocket.pipeline.Step;

public class PowerCurve implements Step<DTTarget>, Action {
	private double p;
	private boolean enabled;
	
	public PowerCurve(double p) {
		this.p = p;
	}
	
	@Override
	public void start() {
		enabled = true;
	}

	@Override
	public void enabled() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void stop() {
		enabled = false;
	}

	@Override
	public void disabled() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public DTTarget get(DTTarget in) {
		if (!enabled)
			return in;
		
		Vector dir = in.getDirection();
		return new DTTarget(dir.scale(p * Math.pow(dir.getMagnitude(), 2) + 1 - p), in.getTurn());
	}

}
