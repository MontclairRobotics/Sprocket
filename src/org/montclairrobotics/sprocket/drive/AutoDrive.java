package org.montclairrobotics.sprocket.drive;

import org.montclairrobotics.sprocket.utils.Degree;
import org.montclairrobotics.sprocket.utils.Distance;
import org.montclairrobotics.sprocket.utils.Polar;
import org.montclairrobotics.sprocket.utils.Updatable;
import org.montclairrobotics.sprocket.utils.Update;
import org.montclairrobotics.sprocket.utils.Vector;

public class AutoDrive implements Updatable {
	
	//public enum UNITS{ROTATIONS,DEGREES,INCHES,FEET};
	
	private Vector velocity;
	private DriveTrain driveTrain;
	private Lock lock;
	private double totDistance;
	private double speed;
	private boolean done;
	
	public AutoDrive(Distance distance,Vector velocity,DriveTrain dt,Lock l)
	{
		this.velocity=velocity;
		this.driveTrain=dt;
		this.lock=l;
		done=false;
		
		totDistance=driveTrain.getAvgEncoderClicks()+(distance.getDistance()*((speed>0)?1:-1));
		driveTrain.drive(velocity);
		lock.setLock(true);
		Update.add(this);
	}
	
	public boolean isDone()
	{
		return done;
	}
	
	public void update() {
		if(done) return;
		driveTrain.drive(velocity);
		done=speed>0==driveTrain.getAvgEncoderClicks()>totDistance;
		if(done)
		{
			driveTrain.drive(0.0,0.0);
		}
	}
}
