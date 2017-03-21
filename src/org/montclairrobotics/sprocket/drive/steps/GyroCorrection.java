package org.montclairrobotics.sprocket.drive.steps;

import org.montclairrobotics.sprocket.SprocketRobot;
import org.montclairrobotics.sprocket.drive.DTTarget;
import org.montclairrobotics.sprocket.geometry.Angle;
import org.montclairrobotics.sprocket.geometry.Degrees;
import org.montclairrobotics.sprocket.geometry.Radians;
import org.montclairrobotics.sprocket.loop.Updater;
import org.montclairrobotics.sprocket.pipeline.Step;
import org.montclairrobotics.sprocket.utils.Debug;
import org.montclairrobotics.sprocket.utils.Input;
import org.montclairrobotics.sprocket.utils.PID;
import org.montclairrobotics.sprocket.utils.PID;
import org.montclairrobotics.sprocket.utils.Togglable;

public class GyroCorrection implements Step<DTTarget>, Togglable {

	private PID pid;
	private boolean enabled;
	private boolean used;
	private Angle reset;
	
	public GyroCorrection(Input<Double> gyro,PID pid)
	{
		this(pid);
		this.pid.setInput(gyro);
	}
	public GyroCorrection(PID pid)
	{
		this.pid=pid.copy();
		this.pid.setMinMax(-180, 179, -1, 1);
	}
	
	public void use()
	{
		used=true;
	}
	
	@Override
	public DTTarget get(DTTarget in) {
		DTTarget out=in;
		if(enabled&&used)
		{
			double tgt=pid.get();
			Angle tgtAngle=new Radians(tgt);
			out=new DTTarget(in.getDirection(),tgtAngle);
		}
		used=false;
		Debug.msg("Gyro Enabled",enabled);
		Debug.msg("Gyro Error", pid.getError());
		return out;
	}
	
	public void reset()
	{
		resetRaw(getCurrentAngleRaw());
	}
	public void resetRaw(Angle r)
	{
		this.reset=r;
	}
	public void resetRelative(Angle r)
	{
		resetRaw(r.add(getCurrentAngleRaw()));
	}
	
	public Angle getTargetAngle() 
	{
		return new Degrees(pid.getTarget());
	}
	public Angle getCurrentAngleRaw()
	{
		return new Degrees(pid.getCurInput());
	}
	public Angle getCurrentAngleReset()
	{
		return getCurrentAngleRaw().subtract(reset);
	}
	
	public void setTargetAngleRaw(Angle a) 
	{
		pid.setTarget(a.toDegrees());
	}
	public void setTargetAngleReset(Angle a)
	{
		pid.setTarget(reset.add(a).toDegrees());
	}
	public void setTargetAngleRelative(Angle a)
	{
		pid.setTarget(getCurrentAngleRaw().add(a).toDegrees());
	}
	
	public void setTargetAngleRaw()
	{
		pid.setTarget(0);
	}
	public void setTargetAngleRelative()
	{
		pid.setTarget(pid.getInput().get());
	}
	public void setTargetAngleReset()
	{
		pid.setTarget(reset.toDegrees());
	}
	
	public PID getPID()
	{
		return pid;
	}

	@Override
	public void enable() {
		enabled=true;
	}

	@Override
	public void disable() {
		enabled=false;
	}
	public void setTargetAngle(Angle a,boolean relative) {
		// TODO Auto-generated method stub
		if(relative)
		{
			setTargetAngleRelative(a);
		}
		else
		{
			setTargetAngleReset(a);
		}
	}

}
