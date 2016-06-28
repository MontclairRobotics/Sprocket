package org.montclairrobotics.sprocket.drive;

import org.montclairrobotics.sprocket.dataconstructs.Angle;
import org.montclairrobotics.sprocket.dataconstructs.Distance;
import org.montclairrobotics.sprocket.input.Input;
import org.montclairrobotics.sprocket.pid.PID;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.SpeedController;

/**
 * A motor that rotates to a given angle
 * @author Hymowitz
 *
 */

public class SwivelMotor extends Motor{

	private Angle tgtAngle;
	private PID pid;
	private Encoder encoder;
	private Angle initialAngle;
	
	public SwivelMotor(SpeedController motor,String name,Encoder encoder,PID encPID)
	{
		super(motor,name);
		this.encoder=encoder;
		this.pid.setInput(new Input(){

			@Override
			public double getInput() {
				return encoder.getDistance();
			}})
			.setMinMaxIn(-180, 180,true)
			.setMinMaxOut(-1, 1)
			.setTotOutMode(false);
		tgtAngle=Angle.zero;
	}
	public void setInitialAngle(Angle a)
	{
		initialAngle=a;
	}
	public void setAngle(Angle a)
	{
		this.tgtAngle=a;
	}
	public Angle getAngle()
	{
		return initialAngle.add(new Angle(encoder.getDistance(),Angle.Unit.DEGREES));
	}
	public Distance calcSpeed()
	{
		pid.setTarget(initialAngle.toDegrees()+tgtAngle.toDegrees(),false);
		return new Distance(pid.get());
	}
}
