package org.montclairrobotics.sprocket.drive;

import org.montclairrobotics.sprocket.utils.Dashboard;
import org.montclairrobotics.sprocket.utils.PID;
import org.montclairrobotics.sprocket.utils.Updatable;
import org.montclairrobotics.sprocket.utils.Update;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.CANTalon.TalonControlMode;
import edu.wpi.first.wpilibj.Encoder;
//import edu.wpi.first.wpilibj.TalonSRX;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.VictorSP;

public class DriveMotor implements Updatable{
	//static constants
	public static enum M_TYPE{TALONSRX,VICTORSP,TALON};
	
	//constants
	private Encoder encoder;
	private SpeedController motor;
	private PID pid;
	private int port;
	
	//variables
	private double tgtSpeed,speed;
	private static boolean shutdown=false;

	public DriveMotor(int m_port, M_TYPE type,int[]encoderPorts,double P,double I,double D)
	{
		this(m_port,type,encoderPorts);
		if(P!=0||I!=0||D!=0)
			pid=new PID(P,I,D);
	}
	public DriveMotor(int m_port, M_TYPE type,int[]encoderPorts) 
	{
		this(m_port,type);
		encoder = new Encoder(encoderPorts[0],encoderPorts[1]);
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
		this.port=m_port;
		Update.add(this);
	}
	
	public void setSpeed(double spd)
	{
		tgtSpeed = spd;
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
		if(encoder==null||pid==null)
		{
			speed=tgtSpeed;
		}
		else
		{
			pid.setTarget(tgtSpeed);
			pid.setCur(encoder.getRate());
			speed=pid.getAdjOut();
		}
		Dashboard.putNumber("Motor "+this, speed,true);
	}
	
	public String toString()
	{
		return ""+port;
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
		if(encoder==null)return 0.0;
		return encoder.getDistance();
	}
	
	public double getSpeed() {
		return getRate();
	}
	
	public double getRate() {
		if(encoder==null) return 0.0;
		return encoder.getRate();
	}
	
	public void resetDistance()
	{
		if(encoder==null)return;
		encoder.reset();
	}
}
