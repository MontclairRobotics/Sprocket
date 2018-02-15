package org.montclairrobotics.sprocket.drive.steps;

import org.montclairrobotics.sprocket.actions.Action;
import org.montclairrobotics.sprocket.drive.DTTarget;
import org.montclairrobotics.sprocket.pipeline.Step;

public class Sensitivity implements Step<DTTarget>, Action {

	public double dirScale,turnScale;
	private boolean enabled = true;
	
	public Sensitivity(double dir, double turn) {
		this.dirScale = dir;
		this.turnScale = turn;
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
		
		return new DTTarget(in.getDirection().scale(dirScale), in.getTurn() * turnScale);
	}

}
