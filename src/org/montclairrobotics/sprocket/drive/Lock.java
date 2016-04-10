package org.montclairrobotics.sprocket.drive;

import org.montclairrobotics.sprocket.utils.Dashboard;
import org.montclairrobotics.sprocket.utils.PID;
import org.montclairrobotics.sprocket.utils.Updatable;
import org.montclairrobotics.sprocket.utils.Update;

public abstract class Lock implements Updatable{

	public static final int LOOPS_TO_UNLOCK=15;
	
	private DriveTrain driveTrain;
	private PID pid;
	private int loopsSinceLastLock=LOOPS_TO_UNLOCK;
	private boolean hardLock,softLock;
	
	public Lock(DriveTrain dt,PID pid)
	{
		driveTrain=dt;
		this.pid=pid;
		Update.add(this);
	}
	
	public void setLock(boolean hardLock)
	{
		setLock(false,hardLock);
	}
	public void setLock(boolean softLock,boolean hardLock)
	{
		this.softLock=softLock;
		this.hardLock=hardLock;
	}
	public abstract double getCurVal();
	
	public double rotateTo()
	{
		return rotateTo(0.0);
	}
	public double rotateTo(double target)
	{
		return rotateTo(target,true);
	}
	public double rotateTo(double target,boolean relative)
	{
		if(relative)
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
