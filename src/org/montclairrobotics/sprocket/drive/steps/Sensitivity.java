package org.montclairrobotics.sprocket.drive.steps;

import org.montclairrobotics.sprocket.drive.DTTarget;
import org.montclairrobotics.sprocket.jrapoport.Togglable;
import org.montclairrobotics.sprocket.pipeline.Step;

public class Sensitivity implements Step<DTTarget>, Togglable {

	public double dirScale,turnScale;
	private boolean enabled = true;
	
	public Sensitivity(double dir, double turn) {
		this.dirScale = dir;
		this.turnScale = turn;
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
		
		return new DTTarget(in.getDirection().scale(dirScale), in.getTurn() * turnScale);
	}

	@Override
	public boolean isEnabled() {
		return enabled;
	}
}
