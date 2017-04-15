package org.montclairrobotics.sprocket.drive.steps;

import org.montclairrobotics.sprocket.drive.DTTarget;
import org.montclairrobotics.sprocket.geometry.Angle;
import org.montclairrobotics.sprocket.geometry.Degrees;
import org.montclairrobotics.sprocket.geometry.Distance;
import org.montclairrobotics.sprocket.geometry.Radians;
import org.montclairrobotics.sprocket.geometry.Vector;
import org.montclairrobotics.sprocket.loop.Updater;
import org.montclairrobotics.sprocket.pipeline.Step;
import org.montclairrobotics.sprocket.utils.Debug;
import org.montclairrobotics.sprocket.utils.Utils;

public class AccelLimit implements Step<DTTarget>,Togglable{

	private Distance maxAccel;
	private Angle maxTurn;
	
	private Vector lastDir;
	private Angle lastTurn;
	
	private boolean enabled=true;
	
	public AccelLimit()
	{
		this(new Distance(4),new Degrees(180));
	}
	public AccelLimit(double maxAccel,double maxTurn)
	{
		this(new Distance(maxAccel),new Radians(maxTurn));
	}
	public AccelLimit(Distance maxAccel,Angle maxTurn) 
	{
		this.maxAccel=maxAccel;
		this.maxTurn=maxTurn;
		lastDir=Vector.ZERO;
		lastTurn=Angle.ZERO;
	}
	
	@Override
	public DTTarget get(DTTarget in) {
		Debug.msg("AccelLimit", enabled?"ENABLED":"DISABLED");
		if(enabled)
		{
			Vector dDir=in.getDirection().subtract(lastDir);
			
			//Debug.num("maxAccel", maxAccel.get()*Updater.getLoopTime());
			//Debug.string("dDirBefore", dDir.toString());
			if(dDir.getMagnitude()>maxAccel.get()*Updater.getLoopTime())
			{
				dDir=dDir.setMag(maxAccel.get()*Updater.getLoopTime());
			}
	
			//Debug.string("dDirAfter", dDir.toString());
			//Debug.string("lastDir", lastDir.toString());
			//Debug.string("newDir", (lastDir.add(dDir)).toString());
			
			Angle dAng=in.getTurn().subtract(lastTurn);
			dAng=new Degrees(Utils.constrain(dAng.toDegrees(),-maxTurn.toDegrees()*Updater.getLoopTime(),maxTurn.toDegrees()*Updater.getLoopTime()));
			
			DTTarget tgt= new DTTarget(lastDir.add(dDir),lastTurn.add(dAng));
			
			lastDir=tgt.getDirection();
			lastTurn=tgt.getTurn();
			return tgt;
		}
		else
		{
			return in;
		}
	}
	@Override
	public void enable() {
		enabled=true;
	}
	@Override
	public void disable() {
		enabled=false;
	}

}
