package org.montclairrobotics.sprocket.drive.steps;

import org.montclairrobotics.sprocket.drive.DTTarget;
import org.montclairrobotics.sprocket.geometry.Vector;
import org.montclairrobotics.sprocket.jrapoport.Togglable;
import org.montclairrobotics.sprocket.pipeline.Step;

public class PowerCurve implements Step<DTTarget>, Togglable {
	private double p;
	private boolean enabled;
	
	public PowerCurve(double p) {
		this.p = p;
	}
	
	@Override
	public void enable() {
		enabled = true;
	}

	@Override
	public void disable() {
		enabled = false;
	}
	
	@Override
	public DTTarget get(DTTarget in) {
		if (!enabled)
			return in;
		
		Vector dir = in.getDirection();
		return new DTTarget(dir.scale(p * Math.pow(dir.getMagnitude(), 2) + 1 - p), in.getTurn());
	}

	@Override
	public boolean isEnabled() {
		return enabled;
	}

}
