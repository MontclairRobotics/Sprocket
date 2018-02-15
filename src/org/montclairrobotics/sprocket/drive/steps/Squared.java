package org.montclairrobotics.sprocket.drive.steps;

import org.montclairrobotics.sprocket.actions.Action;
import org.montclairrobotics.sprocket.drive.DTTarget;
import org.montclairrobotics.sprocket.geometry.Vector;
import org.montclairrobotics.sprocket.pipeline.Step;

public class Squared implements Step<DTTarget>, Action {
	private boolean enabled =true;
	
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
		// TODO Auto-generated method stub
		enabled = false;
	}

	@Override
	public void disabled() {
		// TODO Auto-generated method stub
	}

	@Override
	public DTTarget get(DTTarget in) {
		if (!enabled) {
			return in;
		}
		
		Vector dir = in.getDirection();
		
		return new DTTarget(dir.scale(dir.getMagnitude()), in.getTurn());
	}
}
