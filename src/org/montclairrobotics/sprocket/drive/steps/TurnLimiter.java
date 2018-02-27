package org.montclairrobotics.sprocket.drive.steps;

import org.montclairrobotics.sprocket.drive.DTTarget;
import org.montclairrobotics.sprocket.jrapoport.Togglable;
import org.montclairrobotics.sprocket.pipeline.Step;

public class TurnLimiter implements Step<DTTarget>, Togglable {
	private double maxSpeed;
	private boolean enabled = true;

	public TurnLimiter(double maxSpeed) {
		this.maxSpeed = maxSpeed;
	}

	@Override
	public DTTarget get(DTTarget in) {
		if (!enabled) {
			return in;
		}
		
		double out = in.getTurn();
		
		if (Math.abs(out) > maxSpeed) {
			out = maxSpeed*(out > 0 ? 1 : -1);
		}
		
		return new DTTarget(in.getDirection(), out);
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
	public boolean isEnabled() {
		return enabled;
	}
}
