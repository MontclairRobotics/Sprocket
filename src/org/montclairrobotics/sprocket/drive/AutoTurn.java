package org.montclairrobotics.sprocket.drive;

import org.montclairrobotics.sprocket.utils.Angle;
import org.montclairrobotics.sprocket.utils.Degree;
import org.montclairrobotics.sprocket.utils.Polar;
import org.montclairrobotics.sprocket.utils.Updatable;
import org.montclairrobotics.sprocket.utils.Update;

/**
 * This class should be instantiated each time 
 * you want to auto turn a certain number of degrees
 * @author Jack Hymowitz
 *
 */

public class AutoTurn implements Updatable {
	
	public static final double MAX_ERROR=3;//degrees
	
	private DriveTrain driveTrain;
	private Lock lock;
	private double degreesTgt;
	private boolean done;
	/**
	 * Create an instance of this object each time you want to autoturn
	 * @param degrees the degrees to turn as a double
	 * @param dt the DriveTrain to drive with
	 * @param l the Lock to steer with
	 */
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
		driveTrain.drive(0,0);
		lock.setLock(true);
		done=Math.abs((((lock.getCurVal()-degreesTgt)+180)%360+360)%360)<MAX_ERROR;
		if(done)
		{
			driveTrain.drive(new Polar(0,new Degree(0)),0);
		}
	}
}
