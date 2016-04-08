package org.montclairrobotics.sprocket.drive;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.CANTalon.TalonControlMode;
import edu.wpi.first.wpilibj.Encoder;
//import edu.wpi.first.wpilibj.TalonSRX;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.VictorSP;

public class DriveMotor {
	//constants
	public static enum M_TYPE{TALONSRX,VICTORSP,TALON};
	private static final M_TYPE defaultType = M_TYPE.TALON;
	private static final double DEFAULT_P = 0.1, DEFAULT_I = 0.001, DEFAULT_D = 0.0;
	
	//set once
	private Encoder encoder;
	private SpeedController motor;
	private double PID_P = 0.1, PID_I = 0.001, PID_D = 0.0;
	
	//variables
	private double speed;
	private static boolean shutdown=false;

	public DriveMotor(int m_port, M_TYPE type,int encoderPort1,int encoderPort2) 
	{
		this(m_port,type,encoderPort1,encoderPort2,DEFAULT_P,DEFAULT_I,DEFAULT_D);
	}
	public DriveMotor(int m_port, M_TYPE type,int encoderPort1,int encoderPort2,double P,double I,double D)
	{
		this(m_port,type);
		encoder = new Encoder(encoderPort1, encoderPort2);
	}
	public DriveMotor(int m_port)
	{
		this(m_port,defaultType);
	}
	public DriveMotor(int m_port,M_TYPE type)
	{
		switch(type)
		{
		case TALONSRX:
			motor = new CANTalon(m_port);
			break;
		case VICTORSP:
			motor = new VictorSP(m_port);
			break;
		case TALON:
			motor = new Talon(m_port);
			break;
		default:
			break;
		}
		if(type==M_TYPE.TALONSRX)
		{
			CANTalon talon = (CANTalon)motor;
			talon.setControlMode(TalonControlMode.PercentVbus.value);
			talon.reset();
			talon.enable();
			talon.enableControl();
		}
	}
	
	public void setSpeed(double spd)
	{
		speed = spd;//encoders ? spd * ROT_TO_DEGREES : spd*SCALE_FACTOR; // Control scale constant
	
		if(Robot.debugOutputs) Robot.dashboard.putNumber("Motor "+motorPort, spd);
	}
	
	public void update()
	{
		if(shutdown)
		{
			speed = 0;
			motor.set(0);
			if(motor instanceof CANTalon) ((CANTalon)motor).disableControl();
			return;
		}
		/*if(encoders) {
			controller.setSetpoint(speed);
		} else {*/
			motor.set(speed);
			if(Robot.debugOutputs) Robot.dashboard.putNumber("Speed-" + motorPort, speed);
			if(encoders && Robot.debugOutputs) Robot.dashboard.putNumber("encoder-" + motorPort, encoder.get());
		//}
	}
	
	public boolean isInverted() {
		return motor.getInverted();
	}
	
	public void setInverted(boolean invert) {
		motor.setInverted(invert);
	}
	
	public void toggleInvert() {
		motor.setInverted(!motor.getInverted());
	}
	
	public static void shutdown() {
		shutdown = true;
	}
	
	public double getDistance()
	{
		if(!encoders)return 0;
		return encoder.getDistance();
	}
	
	public double getSpeed() {
		return getRate();
	}
	
	public double getRate() {
		if(!encoders) return 0;
		return encoder.getRate();
	}
	
	public void resetDistance()
	{
		if(!encoders)return;
		encoder.reset();
	}
	
	/*public SpeedController getMotor() {
		return motor;
	}*/
	
}
