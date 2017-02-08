package org.montclairrobotics.sprocket.vision;

import org.montclairrobotics.sprocket.drive.DTInput;
import org.montclairrobotics.sprocket.geometry.Angle;
import org.montclairrobotics.sprocket.geometry.Distance;
import org.montclairrobotics.sprocket.geometry.Radians;
import org.montclairrobotics.sprocket.geometry.Vector;
import org.montclairrobotics.sprocket.geometry.XY;
import org.montclairrobotics.sprocket.utils.Input;
import org.montclairrobotics.sprocket.utils.PID;

public class VisionDTInput implements DTInput{
	
	private VisionTarget target;
	private double turnP,distP,maxTurn,turn,dist;
	public VisionDTInput(VisionTarget target,double turnP,double distP,double maxTurn)
	{
		this.target=target;
		this.turnP=turnP;
		this.distP=distP;
		this.maxTurn=maxTurn;
	}
	@Override
	public Vector getDir() {
		dist=target.getDistance()*distP;
		if(Math.abs(turn)>maxTurn)
		{
			return Vector.ZERO;
		}
		return new XY(0,dist);
		
	}
	@Override
	public Angle getTurn() {
		turn=target.getTurn()*turnP;
		return new Radians(turn);
	}
	@Override
	public Type getInputType() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void setMaxSpeed(Distance m) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void setMaxTurn(Angle a) {
		// TODO Auto-generated method stub
		
	}
	
}
