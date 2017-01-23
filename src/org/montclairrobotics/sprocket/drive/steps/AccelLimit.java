package org.montclairrobotics.sprocket.drive.steps;

import org.montclairrobotics.sprocket.drive.DTTarget;
import org.montclairrobotics.sprocket.geometry.Angle;
import org.montclairrobotics.sprocket.geometry.Degrees;
import org.montclairrobotics.sprocket.geometry.Distance;
import org.montclairrobotics.sprocket.geometry.RVector;
import org.montclairrobotics.sprocket.geometry.RXY;
import org.montclairrobotics.sprocket.geometry.Vector;
import org.montclairrobotics.sprocket.loop.Updater;
import org.montclairrobotics.sprocket.pipeline.Step;
import org.montclairrobotics.sprocket.utils.Utils;

public class AccelLimit implements Step<DTTarget>{

	private Distance maxAccel;
	private Angle maxTurn;
	
	private RVector lastDir;
	private Angle lastTurn;
	
	public AccelLimit()
	{
		this(new Distance(4),new Degrees(180));
	}
	public AccelLimit(Distance maxAccel,Angle maxTurn) 
	{
		this.maxAccel=maxAccel;
		this.maxTurn=maxTurn;
		lastDir=RVector.ZERO;
		lastTurn=Angle.ZERO;
	}
	
	@Override
	public DTTarget get(DTTarget in) {
		Vector dDir=in.getDirection().subtract(lastDir);
		if(dDir.getMagnitude()>maxAccel.get()*Updater.getTime())
		{
			dDir.scale(maxAccel.get(),true);
		}
		
		Angle dAng=in.getTurn().subtract(lastTurn);
		dAng=new Degrees(Utils.constrain(dAng.toDegrees(),-maxTurn.toDegrees()*Updater.getLoopTime(),maxTurn.toDegrees()*Updater.getLoopTime()));
		
		DTTarget tgt= new DTTarget(lastDir.add(dDir).toReal(),lastTurn.add(dAng));
		
		lastDir=in.getDirection();
		lastTurn=in.getTurn();
		return tgt;
	}

}
