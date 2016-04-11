package org.montclairrobotics.sprocket.drive;

import org.montclairrobotics.sprocket.utils.Dashboard;
import org.montclairrobotics.sprocket.utils.PID;
import org.montclairrobotics.sprocket.utils.Updatable;
import org.montclairrobotics.sprocket.utils.Update;

/** 
 * This class should be extended to add the autolock functionality.
 * Extend this, define the getCurVal() to get the gyro heading value, 
 * and instantiate to start heading lock
 * @author Jack Hymowitz
 *
 */

public abstract class Lock implements Updatable{

	public static final int LOOPS_TO_UNLOCK=15;
	
	private DriveTrain driveTrain;
	private PID pid;
	private int loopsSinceLastLock=LOOPS_TO_UNLOCK;
	private boolean hardLock,softLock;
	/**
	 * Create the autolock
	 * @param dt The DriveTrain to correct
	 * @param pid The PID controller to correct with
	 */
	public Lock(DriveTrain dt,PID pid)
	{
		driveTrain=dt;
		this.pid=pid;
		Update.add(this);
	}
	
	/**
	 * Overwrite this method with a way of determining current heading
	 * @return
	 */
	public abstract double getCurVal();
	
	/**
	 * Sets the lock on or off directly
	 * @param hardLock Sets the lock on or off directly
	 */
	public void setLock(boolean hardLock)
	{
		setLock(false,hardLock);
	}
	/**
	 * Sets the lock on or off directly, or only if the robot is driving straight
	 * This can be used as a switch on the Joystick, 
	 * to enable autolock if the robot is driving straight
	 * @param softLock Sets the lock on only if robot is driving straight
	 * @param hardLock Directly turns the lock on or off
	 */
	public void setLock(boolean softLock,boolean hardLock)
	{
		this.softLock=softLock;
		this.hardLock=hardLock;
	}
	/**
	 * Rotate to the current heading
	 * @return current heading
	 */
	public double rotateTo()
	{
		return rotateTo(0.0);
	}
	/**
	 * Rotate to a heading relative to the current heading
	 * @param target relative heading
	 * @return absolute heading
	 */
	public double rotateTo(double target)
	{
		return rotateTo(target,true);
	}
	/**
	 * Rotate to a heading relative to the current heading or the field
	 * @param target the heading
	 * @param robotCentric true if relative to current heading, false if relative to field
	 * @return heading relative to the field
	 */
	public double rotateTo(double target,boolean robotCentric)
	{
		if(robotCentric)
		{
			target+=getCurVal();
		}
		pid.setTarget(target);
		loopsSinceLastLock=0;
		return target;
	}
	public void update()
	{
		if(hardLock||softLock&&driveTrain.isStraight())
		{
			if(loopsSinceLastLock>=LOOPS_TO_UNLOCK)
			{
				rotateTo();
			}
			else
			{
				driveTrain.correct(pid.getRawOut());
			}
			loopsSinceLastLock=0;
			pid.setCur(getCurVal());
		}
		else
		{
			loopsSinceLastLock++;
		}
	}
}
