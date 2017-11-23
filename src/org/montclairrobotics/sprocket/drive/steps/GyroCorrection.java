package org.montclairrobotics.sprocket.drive.steps;

import org.montclairrobotics.sprocket.actions.Action;
import org.montclairrobotics.sprocket.drive.DTTarget;
import org.montclairrobotics.sprocket.geometry.Angle;
import org.montclairrobotics.sprocket.geometry.Degrees;
import org.montclairrobotics.sprocket.pipeline.Step;
import org.montclairrobotics.sprocket.utils.Debug;
import org.montclairrobotics.sprocket.utils.Input;
import org.montclairrobotics.sprocket.utils.PID;
import org.montclairrobotics.sprocket.utils.Utils;

public class GyroCorrection implements Step<DTTarget>, Action {

	private PID pid;
	private boolean enabled=true;
	private boolean used;
	private Angle reset=Angle.ZERO;
	
	private double minOut=-1;
	private double maxOut=1;
	private double maxError;
	private double farP;
	
	public GyroCorrection(Input<Double> gyro,PID pid,double maxError,double farP)
	{
		this(pid);
		this.pid.setInput(gyro);
		this.maxError=maxError;
		this.farP=farP;
	}
	public GyroCorrection(PID pid,double maxError,double powIfMaxError)
	{
		this.pid=pid.copy();
		this.pid.setMinMax(-180, 179, -1, 1);

		this.maxError=maxError;
		this.farP=powIfMaxError;
	}
	public GyroCorrection(PID pid)
	{
		this(pid,180,1);
	}
	
	public void use()
	{
		used=true;
	}
	
	
	public void setMinMaxOut(double min,double max)
	{
		this.minOut=min;
		this.maxOut=max;
	}
	public void resetMinMaxOut()
	{
		setMinMaxOut(-1,1);
	}
	
	@Override
	public DTTarget get(DTTarget in) {
		DTTarget out=in;
		if(enabled&&used)
		{
			double tgt;
			if(Math.abs(pid.getError())>maxError)
			{
				tgt=farP*pid.getError()*(pid.getP()>0?1:-1);
			}
			else
			{
				tgt=pid.get();
				tgt=Utils.constrain(tgt, minOut, maxOut);
			}
			out=new DTTarget(in.getDirection(),tgt);
		}
		used=false;
		Debug.msg("Gyro Enabled",enabled);
		Debug.msg("Gyro Error", pid.getError());
		return out;
	}
	
	public Angle getError()
	{
		return new Degrees(pid.getError());
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
	public void start() {
		enabled=true;
	}

	@Override
	public void stop() {
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
	@Override
	public void enabled() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void disabled() {
		// TODO Auto-generated method stub
		
	}

}
