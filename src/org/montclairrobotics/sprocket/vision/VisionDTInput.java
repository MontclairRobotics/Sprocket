package org.montclairrobotics.sprocket.vision;

import org.montclairrobotics.sprocket.drive.DTInput;
import org.montclairrobotics.sprocket.geometry.Angle;
import org.montclairrobotics.sprocket.geometry.Distance;
import org.montclairrobotics.sprocket.geometry.Radians;
import org.montclairrobotics.sprocket.geometry.Vector;
import org.montclairrobotics.sprocket.geometry.XY;
import org.montclairrobotics.sprocket.loop.Priority;
import org.montclairrobotics.sprocket.loop.Updatable;
import org.montclairrobotics.sprocket.loop.Updater;
import org.montclairrobotics.sprocket.utils.Input;
import org.montclairrobotics.sprocket.utils.PID;

public class VisionDTInput implements DTInput,Updatable{
	
	private VisionTarget target;
	private double turnP,distP,maxTurn,turn,dist;
	private Input<Boolean> enabled;
	public VisionDTInput(VisionTarget target,double turnP,double distP,double maxTurn,Input<Boolean> enabled)
	{
		this.target=target;
		this.turnP=turnP;
		this.distP=distP;
		this.maxTurn=maxTurn;
		this.enabled=enabled;
		Updater.add(this, Priority.CALC);
	}
	@Override
	public void update()
	{
		turn=target.getTurn()*turnP;
		if(Math.abs(turn)>maxTurn)
		{
			dist=0;
		}
		else
		{
			dist=target.getDistance()*distP;
		}
	}
	
	@Override
	public Vector getDir() {
		return new XY(0,dist);
		
	}
	@Override
	public Angle getTurn() {
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
	@Override
	public boolean isEnabled() {
		return enabled.get();
	}
	
}
