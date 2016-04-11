package org.montclairrobotics.sprocket.drive;

import org.montclairrobotics.sprocket.utils.Updatable;
import org.montclairrobotics.sprocket.utils.UpdateClass;
import org.montclairrobotics.sprocket.utils.Updater;

public class AutoDrive implements Updatable {
	
	public enum UNITS{ROTATIONS,DEGREES,INCHES,FEET};
	
	private DriveTrain driveTrain;
	private double totDistance;
	private double speed;
	private boolean done;
	
	public AutoDrive(double distance,UNITS u,double speed,DriveTrain dt,double WHEEL_CIRC)
	{
		this.speed=speed;
		this.driveTrain=dt;
		done=false;
		if(u==UNITS.FEET)
		{
			distance*=12;
			u=UNITS.INCHES;
		}
		if(u==UNITS.INCHES)
		{
			distance/=WHEEL_CIRC;
			u=UNITS.ROTATIONS;
		}
		if(u==UNITS.ROTATIONS)
		{
			distance*=360;
			u=UNITS.DEGREES;
		}
		totDistance=driveTrain.getAvgEncoderClicks()+(distance*((speed>0)?1:-1));
		dt.setSpeedXY(0.0, speed, true);
		Updater.add(this, UpdateClass.Autonomous);
	}
	
	public boolean isDone()
	{
		return done;
	}
	
	public void update() {
		if(done) return;
		done=speed>0==driveTrain.getAvgEncoderClicks()>totDistance;
		if(done)
		{
			driveTrain.setSpeedXY(0.0,0.0);
		}
	}
}
