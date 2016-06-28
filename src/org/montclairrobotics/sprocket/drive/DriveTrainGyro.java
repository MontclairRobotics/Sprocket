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
	private Input heading;
	private PID pid;
	private boolean lock,lastLock,fieldCentric;
	private Angle headingConstant=Angle.zero;
	
	private Angle target=Angle.zero;
	
	public DriveTrainGyro(DriveTrain dt)
	{
		Updater.add(this, Priority.before(Priority.DRIVE_CALC));
		this.dt=dt;
	}
	public DriveTrainGyro(DriveTrain dt,Input heading,PID pid)
	{
		this(dt);
		setInputHeading(heading);
		setPID(pid);
	}
	public DriveTrainGyro setInputHeading(Input heading)
	{
		this.heading=heading;
		pid.setInput(heading);
		return this;
	}
	public DriveTrainGyro setPID(PID pid)
	{
		this.pid=pid.copy()
				.setInput(heading)
				.setMinMaxIn(-180, 180, true)
				.setTotOutMode(false);
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
		this.headingConstant=heading;
		return this;
	}
	public DriveTrainGyro resetHeading()
	{
		return setCurrentHeading(new Angle(heading.get(),Angle.Unit.DEGREES));
	}
	
	public void update()
	{
		if(pid==null) return;
		if(!lock)
		{
			target=dt.getDriveRotation();
			if(!fieldCentric&&heading!=null)
			{
				target=target
						.add(new Angle(heading.get(),Angle.Unit.DEGREES))
						.add(headingConstant);
			}
		}
		dt.setDriveRotation(new Angle(pid.setTarget(target.toDegrees()).get(),Angle.Unit.DEGREES));
	}
	public void setTarget(Angle target, boolean fieldCentric) {
		if(!fieldCentric)
			target=target.add(new Angle(heading.get(),Angle.Unit.DEGREES));
		this.target=target;
		this.lock=true;
	}
	public DriveTrain getDriveTrain() {
		return dt;
	}
}
