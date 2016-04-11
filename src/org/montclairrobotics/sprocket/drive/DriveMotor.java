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
	
	//constants
	private SpeedController motor;
	private Encoder encoder;
	private PID pid;
	private Vector offset;
	private Angle orientation;
	
	//variables
	private static boolean shutdown=false;
	private Vector goal;
	private double tgtSpeed;

	
	public DriveMotor(SpeedController motor)
	{
		this(motor,null,null,null,null);
	}
	public DriveMotor(SpeedController motor,Encoder encoder)
	{
		this(motor,encoder,null,null,null);
	}
	public DriveMotor(SpeedController motor,Encoder encoder,PID encPID)
	{
		this(motor,encoder,encPID,null,null);
	}
	public DriveMotor(SpeedController motor,Encoder encoder,PID encPID,Vector offset)
	{
		this(motor,encoder,encPID,offset,null);
	}
	
	public DriveMotor(SpeedController motor,Encoder encoder,PID encPID,Vector offset,Angle orientation)
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
	public void setVelocity(Vector direction,double rotation)
	{
		goal=direction.add(offset.getRotationVector(rotation)).rotate(orientation);
	}
	
	public void calcSpeed()
	{
		tgtSpeed=goal.getY();
	}
	
	public void update()
	{
		calcSpeed();
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
		motor.set(speed);
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
