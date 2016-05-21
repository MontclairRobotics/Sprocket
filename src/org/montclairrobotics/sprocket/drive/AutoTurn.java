package org.montclairrobotics.sprocket.drive;

import org.montclairrobotics.sprocket.updater.Priority;
import org.montclairrobotics.sprocket.updater.Updatable;
import org.montclairrobotics.sprocket.updater.Updater;
import org.montclairrobotics.sprocket.utils.Angle;
import org.montclairrobotics.sprocket.utils.Degree;
import org.montclairrobotics.sprocket.utils.Polar;

/**
 * This class should be instantiated each time 
 * you want to auto turn a certain number of degrees
 * @author Jack Hymowitz
 *
 */

public class AutoTurn implements Updatable {
	
	public static final double MAX_ERROR=3;//degrees

	private static final int REQUIRED_LOOPS_CORRECT = 30;
	
	private DriveTrain driveTrain;
	private Angle target;
	private int loopsCorrect=0;
	private boolean done;
	/**
	 * Create an instance of this object each time you want to autoturn
	 * @param degrees the degrees to turn as a double
	 * @param dt the DriveTrain to drive with
	 * @param l the Lock to steer with
	 */
	public AutoTurn(Angle rotation,DriveTrain dt,boolean relativeToRobot)
	{
		driveTrain=dt;
		target=driveTrain.rotateTo(rotation,relativeToRobot);
		if(target==null)
		{
			done=true;
		}
		else
		{
			done=false;
			Updater.add(this, Priority.CALC);
		}
	}
	
	public boolean isDone()
	{
		return done;
	}

	public void update() {
		if(done)return;
		driveTrain.driveSpeedRotation(0,0);
		if(Math.abs((driveTrain.getHeading().subtract(target)).toDegrees())<MAX_ERROR)
		{
			loopsCorrect++;
			done=loopsCorrect>REQUIRED_LOOPS_CORRECT;
		}
		else
		{
			loopsCorrect=0;
		}
		if(done)
		{
			driveTrain.drive(new Polar(0,new Degree(0)),0);
		}
	}
}
