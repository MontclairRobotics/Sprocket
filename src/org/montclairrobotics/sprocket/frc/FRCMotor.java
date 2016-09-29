package org.montclairrobotics.sprocket.frc;

import org.montclairrobotics.sprocket.drive.IMotor;
import org.montclairrobotics.sprocket.updater.Priority;
import org.montclairrobotics.sprocket.updater.Updater;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.CANTalon.TalonControlMode;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.VictorSP;

public class FRCMotor implements IMotor {
	
	public static enum M_TYPE{TALONSRX,VICTORSP,TALON};
	
	private SpeedController motor;
	private double power;
	
	public FRCMotor(int port,M_TYPE type)
	{
		this(makeMotor(port,type));
	}
	public FRCMotor(SpeedController motor)
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
		Updater.add(this, Priority.OUTPUT);
	}
	
	public static SpeedController makeMotor(int port, M_TYPE type)
	{
		switch(type)
		{
		case TALONSRX:
			return new CANTalon(port);
		case VICTORSP:
			return new VictorSP(port);
		case TALON:
			return new Talon(port);
		default:
			return null;
		}
	}
	
	public void set(double speed) {
		power=speed;
	}
	
	public void update() {
		motor.set(power);
	}
}
