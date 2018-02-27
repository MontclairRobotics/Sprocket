package org.montclairrobotics.sprocket.drive.steps;

import org.montclairrobotics.sprocket.drive.DTTarget;
import org.montclairrobotics.sprocket.geometry.Vector;
import org.montclairrobotics.sprocket.jrapoport.Togglable;
import org.montclairrobotics.sprocket.pipeline.Step;

public class Squared implements Step<DTTarget>, Togglable {
	private boolean enabled =true;
	
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
		if (!enabled) {
			return in;
		}
		
		Vector dir = in.getDirection();
		
		return new DTTarget(dir.scale(dir.getMagnitude()), in.getTurn());
	}

	@Override
	public boolean isEnabled() {
		return enabled;
	}
}
