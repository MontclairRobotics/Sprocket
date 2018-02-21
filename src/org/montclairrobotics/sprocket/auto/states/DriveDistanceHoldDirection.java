package org.montclairrobotics.sprocket.auto.states;

import org.montclairrobotics.sprocket.actions.MultiState;
import org.montclairrobotics.sprocket.geometry.Vector;

public class DriveDistanceHoldDirection extends MultiState {
	public DriveDistanceHoldDirection(Vector direction,double power,double tgt,boolean relative)
	{
		super(0,new DriveDistance(direction, power),new TurnGyro(tgt,relative));
	}
	public DriveDistanceHoldDirection(Vector direction,double power)
	{
		this(direction,power,0,true);
	}
}
