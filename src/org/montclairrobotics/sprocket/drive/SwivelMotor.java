package org.montclairrobotics.sprocket.drive;

import org.montclairrobotics.sprocket.updater.Updatable;
import org.montclairrobotics.sprocket.updater.Updater;
import org.montclairrobotics.sprocket.utils.Angle;
import org.montclairrobotics.sprocket.utils.Degree;
import org.montclairrobotics.sprocket.utils.Input;
import org.montclairrobotics.sprocket.utils.PID;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.CANTalon.TalonControlMode;
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
	
	public SwivelMotor(SpeedController motor,String name,Encoder encoder,PID encPID)
	{
		super(motor,name);
		this.encoder=encoder;
		this.pid.setInput(new EncoderDistance(encoder)).setMinMax(-180, 180, -1, 1);
		tgtAngle=new Degree(0);
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
	public static class EncoderDistance implements Input
	{
		private Encoder enc;
		public EncoderDistance(Encoder enc)
		{
			this.enc=enc;
		}
		public double getInput()
		{
			if(enc==null)return 0.0;
			return enc.getDistance();
		}
	}
}
