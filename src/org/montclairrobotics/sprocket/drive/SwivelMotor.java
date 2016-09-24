package org.montclairrobotics.sprocket.drive;

import org.montclairrobotics.sprocket.core.SprocketMotor;
import org.montclairrobotics.sprocket.geometry.Angle;
import org.montclairrobotics.sprocket.geometry.Degree;
import org.montclairrobotics.sprocket.updater.Priority;
import org.montclairrobotics.sprocket.updater.Updatable;
import org.montclairrobotics.sprocket.updater.Updater;
import org.montclairrobotics.sprocket.utils.Input;
import org.montclairrobotics.sprocket.utils.PID;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.SpeedController;

/**
 * A motor that rotates to a given angle
 * @author Hymowitz
 *
 */

public class SwivelMotor implements Updatable{

	private Angle tgtAngle=Angle.ZERO;
	private SprocketMotor motor;
	private Encoder encoder;
	private PID pid;
	
	public SwivelMotor(SprocketMotor motor,Encoder encoder,PID encPID)
	{
		this.motor=motor;
		this.encoder=encoder;
		this.pid=encPID.copy().setInput(new Input<Double>(){

			public Double getRaw() {
				return encoder.getDistance();
			}
			}).setMinMax(-180, 180, -1, 1);
		Updater.add(this, Priority.OUTPUT);
	}
	public void setAngle(Angle a)
	{
		this.tgtAngle=a;
	}
	public Angle getAngle()
	{
		return new Degree(encoder.getDistance());
	}
	public double calcSpeed()
	{
		pid.setTarget(tgtAngle.toDegrees(),false);
		return pid.get();
	}
	public void update()
	{
		pid.setTarget(tgtAngle.toDegrees());
		motor.set(pid.get());
	}
}
