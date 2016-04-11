package org.montclairrobotics.sprocket.drive;

import org.montclairrobotics.sprocket.utils.Angle;
import org.montclairrobotics.sprocket.utils.Degree;
import org.montclairrobotics.sprocket.utils.Updatable;
import org.montclairrobotics.sprocket.utils.Update;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.CANTalon.TalonControlMode;
import edu.wpi.first.wpilibj.SpeedController;

/**
 * A mostly empty placeholder class for a SwivelMotor, 
 * because I don't know how to implement one.
 * @author Hymowitz
 *
 */

public class SwivelMotor implements Updatable{

	private Angle tgtAngle;
	private SpeedController motor;
	private static boolean shutdown=false;
	
	public SwivelMotor(SpeedController motor)
	{
		this.motor=motor;
		if(motor instanceof CANTalon)
		{
			CANTalon talon = (CANTalon)motor;
			talon.setControlMode(TalonControlMode.PercentVbus.value);
			talon.reset();
			talon.enable();
			talon.enableControl();
		}
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
			//SHUTDOWN
			return;
		}
		//TODO Rotate to tgtAngle
	}
	
	public static void shutdown() {
		shutdown = true;
	}
}
