package org.montclairrobotics.sprocket.drive;

import org.montclairrobotics.sprocket.utils.Angle;
import org.montclairrobotics.sprocket.utils.Degree;
import org.montclairrobotics.sprocket.utils.PID;
import org.montclairrobotics.sprocket.utils.Updatable;
import org.montclairrobotics.sprocket.utils.UpdateClass;
import org.montclairrobotics.sprocket.utils.Updater;

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
	
	public SwivelMotor(SpeedController motor,Encoder encoder,PID encPID)
	{
		super(motor);
		this.encoder=encoder;
		this.pid.setMinMaxInOut(-180, 180, -1, 1);
		if(motor instanceof CANTalon)
		{
			CANTalon talon = (CANTalon)motor;
			talon.setControlMode(TalonControlMode.PercentVbus.value);
			talon.reset();
			talon.enable();
			talon.enableControl();
		}
		tgtAngle=new Degree(0);
		Updater.add(this, UpdateClass.MotorController);
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
		pid.in(encoder.getDistance());
		return pid.out();
	}
}
