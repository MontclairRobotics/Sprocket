package org.montclairrobotics.sprocket.drive;

import org.montclairrobotics.sprocket.utils.Angle;
import org.montclairrobotics.sprocket.utils.Degree;
import org.montclairrobotics.sprocket.utils.PID;
import org.montclairrobotics.sprocket.utils.Updatable;
import org.montclairrobotics.sprocket.utils.Update;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.CANTalon.TalonControlMode;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.SpeedController;

/**
 * A motor that rotates to a given angle
 * @author Hymowitz
 *
 */

public class SwivelMotor implements Updatable{

	private Angle tgtAngle;
	private SpeedController motor;
	private Encoder encoder;
	private PID pid;
	private static boolean shutdown=false;
	
	public SwivelMotor(SpeedController motor,Encoder encoder,PID pid)
	{
		this.motor=motor;
		this.encoder=encoder;
		this.pid=pid.copy();
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
		Update.add(this);
	}
	public void setAngle(Angle a)
	{
		this.tgtAngle=a;
	}
	
	public void update()
	{
		if(shutdown)
		{
			motor.set(0.0);
		}
		pid.setTarget(tgtAngle.toDegrees(),false);
		pid.in(encoder.getDistance());
		motor.set(pid.out());
	}
	
	public static void shutdown() {
		shutdown = true;
	}
}
