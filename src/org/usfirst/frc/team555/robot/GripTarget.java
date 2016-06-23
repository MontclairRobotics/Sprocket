package org.usfirst.frc.team555.robot;

import org.montclairrobotics.sprocket.utils.Dashboard;
import org.montclairrobotics.sprocket.utils.Grip;
import org.montclairrobotics.sprocket.utils.Input;

public class GripTarget implements Input {
	private Grip grip;
	private boolean yAxis;
	
	public GripTarget(Grip g,boolean y)
	{
		this.grip=g;
		this.yAxis=y;
	}
	public double get() {
		if(!yAxis)
		{
			Dashboard.putNumber("input x", grip.getX());
			return grip.getX();
		}
		else
		{
			return grip.getY();
		}
	}

}
