package org.montclairrobotics.sprocket.drive;

import org.montclairrobotics.sprocket.pid.PID;
import org.montclairrobotics.sprocket.utils.Angle;
import org.montclairrobotics.sprocket.utils.Degree;
import org.montclairrobotics.sprocket.utils.Distance;
import org.montclairrobotics.sprocket.utils.Input;

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
		this.pid.setInput(new EncoderDistance(encoder)).setMinMaxIn(-180, 180,true).setMinMaxOut(-1, 1);
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
	public Distance calcSpeed()
	{
		pid.setTarget(tgtAngle.toDegrees(),false);
		return new Distance(pid.get(),maxSpeed());
	}
	public static class EncoderDistance implements Input
	{
		private Encoder enc;
		public EncoderDistance(Encoder enc)
		{
			this.enc=enc;
		}
		public double get()
		{
			if(enc==null)return 0.0;
			return enc.getDistance();
		}
	}
}
