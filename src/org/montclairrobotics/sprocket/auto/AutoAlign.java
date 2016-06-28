package org.montclairrobotics.sprocket.auto;

import org.montclairrobotics.sprocket.dataconstructs.Point;
import org.montclairrobotics.sprocket.drive.DriveTrain;
import org.montclairrobotics.sprocket.input.Grip;
import org.montclairrobotics.sprocket.input.Input;
import org.montclairrobotics.sprocket.pid.PID;


//TODO: Use PID controller with auto-align
public class AutoAlign {
	
	public static int REQUIRED_LOOPS_AT_TARGET=20;
	
	private int loopsAtTarget = 0;
	private DriveTrain dt;
	private Grip grip;
	private PID xPID,yPID;
	
	public AutoAlign(DriveTrain dt,Grip grip,Point target,PID xPID,PID yPID)
	{
		this.dt=dt;
		this.grip=grip;
		this.xPID=xPID.copy()
				.setInput(new Input<Double>(){
					public Double getInput() {
						return grip.getX()+0.0;
					}
				})
				.setTotOutMode(false);
		this.yPID=yPID.copy()
				.setInput(new Input<Double>(){
					public Double getInput(){
						return grip.getY()+0.0;
					}
				})
				.setTotOutMode(false);
	}
	
	public AutoAlign setTarget(Point target)
	{
		xPID.setTarget(target.x);
		yPID.setTarget(target.y);
		return this;
	}
	
	public void align()
	{
		if(grip == null || xPID == null || yPID == null) return;			//Fail if grip or target do not exist

		double x = xPID.get();
		double y = yPID.get();
		if(Math.abs(x)<dt.getDeadZone()||Math.abs(y)<dt.getDeadZone())
		{
			loopsAtTarget++;
		}
		else
		{
			loopsAtTarget=0;
		}
		dt.driveArcade(x,y);
	}
	public boolean atTarget()
	{
		return loopsAtTarget>REQUIRED_LOOPS_AT_TARGET;
	}
}
