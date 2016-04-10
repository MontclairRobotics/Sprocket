package org.montclairrobotics.sprocket.drive;

import org.montclairrobotics.sprocket.utils.Updatable;
import org.montclairrobotics.sprocket.utils.Update;


public class AutoTurn implements Updatable {
	
	public static final double MAX_ERROR=3;//degrees
	
	private DriveTrain driveTrain;
	private Lock lock;
	private double degreesTgt;
	private boolean done;
	
	public AutoTurn(double degrees,DriveTrain dt,Lock l)
	{
		driveTrain=dt;
		lock=l;
		degreesTgt=lock.rotateTo(degrees);
		done=false;
		Update.add(this);
	}
	
	public boolean isDone()
	{
		return done;
	}

	public void update() {
		if(done)return;
		driveTrain.setSpeedXY(0.0,0.0);
		lock.setLock(true);
		done=Math.abs((((lock.getCurVal()-degreesTgt)+180)%360+360)%360)<MAX_ERROR;
		if(done)
		{
			driveTrain.setSpeedXY(0.0,0.0);
		}
	}
}
