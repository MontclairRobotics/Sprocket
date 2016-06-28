package org.montclairrobotics.sprocket.drive;

import org.montclairrobotics.sprocket.dataconstructs.Angle;
import org.montclairrobotics.sprocket.input.Input;
import org.montclairrobotics.sprocket.pid.PID;
import org.montclairrobotics.sprocket.updater.Priority;
import org.montclairrobotics.sprocket.updater.Updatable;
import org.montclairrobotics.sprocket.updater.Updater;


public class DriveTrainGyro implements Updatable{
/**UPDATE METHODS**/
	
	private DriveTrain dt;
	private Input<Angle> heading,rate;
	private PID ratePID,headingPID;
	private boolean lock,lastLock,fieldCentric;
	private Angle headingConstant=Angle.zero;
	
	private Angle targetHeading=Angle.zero;
	
	public DriveTrainGyro(DriveTrain dt)
	{
		Updater.add(this, Priority.before(Priority.DRIVE_CALC));
		this.dt=dt;
	}
	public DriveTrainGyro setInputHeading(Input<Angle> heading)
	{
		this.heading=heading;
		if(headingPID!=null)
			headingPID.setInput(new Input<Double>(){
				public Double getInput() {
					return heading.get().toDegrees();
				}
			});
		return this;
	}
	public DriveTrainGyro setInputRate(Input<Angle> rate)
	{
		this.rate=rate;
		if(ratePID!=null)
			ratePID.setInput(new Input<Double>(){
				public Double getInput()
				{
					return rate.get().toDegrees();
				}
			});
		return this;
	}
	public DriveTrainGyro setHeadingPID(PID pid)
	{
		this.headingPID=pid.copy()
				.setMinMaxIn(-180, 180, true)
				.setTotOutMode(true);
		setInputHeading(heading);
		return this;
	}
	public DriveTrainGyro setRatePID(PID pid)
	{
		this.ratePID=pid.copy()
				.setTotOutMode(false);
		setInputRate(rate);
		return this;
	}
	public DriveTrainGyro setLock(boolean lock)
	{
		this.lock=lock;
		return this;
	}
	public DriveTrainGyro setFieldCentric(boolean fieldCentric)
	{
		this.fieldCentric=fieldCentric;
		return this;
	}
	public DriveTrainGyro setCurrentHeading(Angle heading)
	{
		this.headingConstant=this.heading.get().subtract(heading);
		return this;
	}
	public DriveTrainGyro resetHeading()
	{
		return setCurrentHeading(Angle.zero);
	}
	
	public void update()
	{
		if(ratePID==null) return;
		if(lock&&headingPID!=null)
		{
			if(!lastLock)
			{
				headingPID.setTarget(heading.get().toDegrees());
			}
			dt.setDriveRotation(new Angle(headingPID.get(),Angle.Unit.DEGREES));
		}
		else
		{
			Angle raw=dt.getDriveRotation().add(headingConstant);
			if(!fieldCentric&&heading!=null)
			{
				raw=raw.add(heading.get());
			}
			dt.setDriveRotation(new Angle(ratePID.setTarget(raw.toDegrees()).get(),Angle.Unit.DEGREES));
		}
		lastLock=lock;
	}
	public void setTarget(Angle target, boolean fieldCentric) {
		if(!fieldCentric)
			target=target.add(heading.get());
		headingPID.setTarget(target.toDegrees());
		this.lock=true;
		this.lastLock=true;
	}
	public DriveTrain getDriveTrain() {
		return dt;
	}
}
