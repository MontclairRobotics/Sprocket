package org.montclairrobotics.sprocket.drive;

import org.montclairrobotics.sprocket.utils.Angle;
import org.montclairrobotics.sprocket.utils.Dashboard;
import org.montclairrobotics.sprocket.utils.Degree;
import org.montclairrobotics.sprocket.utils.PID;
import org.montclairrobotics.sprocket.utils.Polar;
import org.montclairrobotics.sprocket.utils.Updatable;
import org.montclairrobotics.sprocket.utils.Update;
import org.montclairrobotics.sprocket.utils.Vector;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.CANTalon.TalonControlMode;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.VictorSP;

public class DriveMotor implements Updatable{
	//static constants
	public static enum M_TYPE{TALONSRX,VICTORSP,TALON};
	
	//constants
	private int port;
	private SpeedController motor;
	private Encoder encoder;
	private PID pid;
	private Vector offset;
	private Angle orientation;
	
	//variables
	private static boolean shutdown=false;
	private Vector goal;
	private double tgtSpeed;

	public DriveMotor(int m_port,M_TYPE type,Encoder encoder,PID encPID,Vector offset,Angle orientation)
	{
		this.port=m_port;
		
		this.orientation=orientation;
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

		this.encoder=encoder;
		this.pid=encPID.copy();
		this.offset=offset;
		this.orientation=orientation;
		if(orientation==null)
		{
			this.orientation=new Degree(0);
		}
		Update.add(this);
	}
	
	public void setSpeed(double spd)
	{
		setVelocity(new Polar(spd,new Degree(0)));
	}
	public void setVelocity(Vector direction)
	{
		setVelocity(direction,0.0);
	}
	public void setVelocity(Vector direction,double rotation)//OVERWRITE THIS IN EXTENDED FUNCTIONS
	{
		goal=direction.add(offset.getRotationVector(rotation));
	}
	
	public void setSpeed()
	{
		tgtSpeed=goal.getY();
	}
	
	public void update()
	{
		double speed=0;
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
