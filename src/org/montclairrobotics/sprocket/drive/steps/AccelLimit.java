package org.montclairrobotics.sprocket.drive.steps;

import org.montclairrobotics.sprocket.drive.DTTarget;
import org.montclairrobotics.sprocket.geometry.Vector;
import org.montclairrobotics.sprocket.jrapoport.Supuroketto;
import org.montclairrobotics.sprocket.jrapoport.Togglable;
import org.montclairrobotics.sprocket.loop.Updater;
import org.montclairrobotics.sprocket.pipeline.Step;
import org.montclairrobotics.sprocket.utils.Debug;
import org.montclairrobotics.sprocket.utils.Range;

public class AccelLimit implements Step<DTTarget>, Togglable {

	private double maxAccel;
	private double maxTurn;
	
	private Vector lastDir;
	private double lastTurn;
	
	private boolean enabled = true;
	
	public AccelLimit() {
		this(4,4);
	}
	
	public AccelLimit(double maxAccel, double maxTurn) {
		this.maxAccel = maxAccel;
		this.maxTurn = maxTurn;
		lastDir = Vector.ZERO;
		lastTurn = 0;
	}
	
	@Override
	public DTTarget get(DTTarget in) {
		Debug.print("AccelLimit", enabled?"ENABLED":"DISABLED");
		if(enabled) {
			Vector dDir=in.getDirection().subtract(lastDir);
			
			//Debug.num("maxAccel", maxAccel.get()*Updater.getLoopTime());
			//Debug.string("dDirBefore", dDir.toString());
			if(dDir.getMagnitude()>maxAccel*Updater.getLoopTime())
			{
				dDir=dDir.setMag(maxAccel*Updater.getLoopTime());
			}
	
			//Debug.string("dDirAfter", dDir.toString());
			//Debug.string("lastDir", lastDir.toString());
			//Debug.string("newDir", (lastDir.add(dDir)).toString());
			
			double dAng = in.getTurn() - lastTurn;
			dAng = new Range(maxTurn*Supuroketto.loopTimeSec()).constrain(dAng);
			
			DTTarget tgt = new DTTarget(lastDir.add(dDir), lastTurn + dAng);
			
			lastDir = tgt.getDirection();
			lastTurn = tgt.getTurn();
			return tgt;
		} else {
			return in;
		}
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
