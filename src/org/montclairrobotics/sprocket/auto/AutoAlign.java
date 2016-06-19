package org.montclairrobotics.sprocket.auto;

import org.montclairrobotics.sprocket.drive.DriveTrain;
import org.montclairrobotics.sprocket.utils.Grip;
import org.montclairrobotics.sprocket.utils.XY;


//TODO: Use PID controller with auto-align
public class AutoAlign {
	
	private int loopsAtTarget = 0;
	private XY target;
	private Grip grip;
	private DriveTrain dt;
	private Zones zones;
	
	public AutoAlign(Grip grip, DriveTrain dt)
	{
		this.grip = grip;
		this.dt = dt;
		zones = new Zones();
	}
	
	public AutoAlign setTarget(XY target)
	{
		this.target = target;
		return this;
	}
	
	public AutoAlign setZones(Zones zones)
	{
		this.zones = zones;
		return this;
	}
	
	public AutoAlign addZoneX(int pixels, double speed)
	{
		zones.addX(pixels, speed);
		return this;
	}
	
	public AutoAlign addZoneY(int pixels, double speed)
	{
		zones.addY(pixels, speed);
		return this;
	}
	
	public void align()
	{
		if(grip == null || target == null) return;			//Fail if grip or target do not exist
		int x = grip.getX();								//Find target coordinates
		int y = grip.getY();
		double rot = zones.getRot((int)target.getX()-x);
		double spd = zones.getSpd((int)target.getY()-y);
		if(rot==0&&spd==0)
		{
			loopsAtTarget++;
		}
		else
		{
			loopsAtTarget=0;
		}
		dt.driveSpeedRotation(spd,rot);
	}
	
	public boolean isAtTarget()
	{
		return loopsAtTarget>10;
	}
}
