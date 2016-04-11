package org.montclairrobotics.sprocket.drive;

import org.montclairrobotics.sprocket.utils.Updatable;
import org.montclairrobotics.sprocket.utils.UpdateClass;
import org.montclairrobotics.sprocket.utils.Updater;


public class AutoTurn implements Updatable {
	
	public static final double MAX_ERROR=3;//degrees
	
	private DriveTrain driveTrain;
	private double degreesTgt;
	private boolean done;
	
	public AutoTurn(double degrees,DriveTrain dt)
	{
		driveTrain=dt;
		degreesTgt=driveTrain.rotateTo(degrees);
		done=false;
		Updater.add(this, UpdateClass.Autonomous);
	}
	
	public boolean isDone()
	{
		return done;
	}

	public void update() {
		if(done)return;
		driveTrain.setSpeedXY(0.0,0.0,true);
		done=Math.abs((((driveTrain.getGyroVal()-degreesTgt)+180)%360+360)%360)<MAX_ERROR;
		if(done)
		{
			driveTrain.setSpeedXY(0.0,0.0,false);
		}
	}
}
