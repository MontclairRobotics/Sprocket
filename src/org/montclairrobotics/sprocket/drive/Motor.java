package org.montclairrobotics.sprocket.drive;

import org.montclairrobotics.sprocket.pid.PID;
import org.montclairrobotics.sprocket.updater.Priority;
import org.montclairrobotics.sprocket.updater.Updatable;
import org.montclairrobotics.sprocket.updater.Updater;
import org.montclairrobotics.sprocket.utils.Dashboard;
import org.montclairrobotics.sprocket.utils.Distance;
import org.montclairrobotics.sprocket.utils.Input;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.CANTalon.TalonControlMode;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.VictorSP;

/**
 * A standard Motor wrapper
 * @author Hymowitz
 *
 */

public class Motor implements Updatable{
	
	public static enum M_TYPE{TALONSRX,VICTORSP,TALON};

	private String name;
	private Distance maxSpeed=new Distance(49*2,Distance.INCHES),encScale=Distance.INCHES;
	
	private Distance goal=new Distance(0,Distance.METERS);
	
	private SpeedController motor;
	private Encoder encoder;
	private EncoderRate encRate;
	private PID pid;
	private static boolean shutdown=false;
	/**
	 * Creates a motor with an encoder and pid controller
	 * @param motor The SpeedController to use
	 */
	public Motor(int port,M_TYPE type)
	{
		this(makeMotor(port,type),"Motor: "+port);
	}
	public Motor(SpeedController motor,String name)
	{
		this.motor=motor;
		this.name=name;
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
	/**
	 * Sets the speed this motor should be imputed so that it turns at max speed
	 * If the robot has a maximum speed of 3 ft/s, this should be 3 
	 * and the input should be in ft/s
	 * @param maxSpeed the maximum speed
	 * @return this
	 */
	public Motor setMaxSpeed(Distance maxSpeed)
	{
		this.maxSpeed=maxSpeed;
		return this;
	}
	
	/**
	 * Sets the encoder to use with the PID settings
	 * @param e the Encoder
	 * @param rateAtMaxPower the encoder rate at max power
	 * @return this
	 */	
	public Motor setEncoder(Encoder e,Distance scale)
	{
		if(e==null)return this;
		this.encoder=e;
		this.encRate=new EncoderRate(e);
		this.encScale=scale;
		return updatePID();
	}
	
	/**
	 * Updates the PID controller
	 * @return this
	 */
	public Motor updatePID()
	{
		if(pid!=null && encRate!=null)
			pid.setInput(encRate).setMinMaxOut(-1,1);
		return this;
	}
	/**
	 * Sets the PID values for this motor
	 * @param a the PID values
	 * @return this
	 */
	public Motor setPID(PID a)
	{
		this.pid=a.setTotOutMode(true);
		return updatePID();
	}
	
	public static class EncoderRate implements Input
	{
		private Encoder enc;
		public EncoderRate(Encoder enc)
		{
			this.enc=enc;
		}
		public double get()
		{
			if(enc==null)return 0.0;
			return enc.getRate();
		}
	}
	
	/**
	 * Sets the speed
	 * @param spd The speed to spin at
	 */
	public void setSpeed(double spd)
	{
		setSpeed(new Distance(spd,maxSpeed));
	}
	public void setSpeed(Distance spd)
	{
		goal=spd;
	}
	/**
	 * Returns the speed to drive at
	 * Overwrite this method to use your own calculations
	 * Will be converted to a percentage of maxSpeed; 1 if not set
	 * @return Speed to drive at
	 */
	public Distance calcSpeed()
	{
		return goal;
	}
	/**
	 * The automatically called function to update the speed of this wheel
	 * Will attempt to use a PID controller if it is not null.
	 */
	public void update()
	{
		Distance tgtSpeed=calcSpeed();

		Dashboard.putString("V MAG OUT",tgtSpeed.to(Distance.INCHES)+"");
		double power=0;
		if(shutdown)
		{
			power = 0;
			motor.set(0);
			if(motor instanceof CANTalon) ((CANTalon)motor).disableControl();
			return;
		}
		if(encoder==null||pid==null)
		{
			power=tgtSpeed.to(maxSpeed);
		}
		else
		{
			Dashboard.putString(name+" target pid=",tgtSpeed.to(encScale)+"");
			Dashboard.putString(name+" pid input", pid.getInput().get()+"");
			pid.setTarget(tgtSpeed.to(encScale));
			power = pid.get();
			Dashboard.putString(name+" pid output", power+"");
		}
		motor.set(power);
		Dashboard.putNumber("Motor "+name, power);
	}
	
	public Distance maxSpeed()
	{
		return maxSpeed;
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
	public static boolean isShutdown()
	{
		return shutdown;
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
	/**
	 * Helper method to create a SpeedController of a given type
	 * @param port The motor port
	 * @param name 
	 * @param type The motor type
	 * @return The SpeedController
	 */
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
	
	/**
	 * Helper method to create an Encoder from a two dimensional array
	 * @param ports the two dimensional array
	 * @param i the encoder id
	 * @return the Encoder
	 */
	public static Encoder makeEncoder(int[][] ports,int i)
	{
		if(ports==null||ports.length>=i)return null;
		return makeEncoder(ports[i]);
	}
	public static Encoder makeEncoder(int[] ports)
	{
		return new Encoder(ports[0],ports[1]);
	}
}
