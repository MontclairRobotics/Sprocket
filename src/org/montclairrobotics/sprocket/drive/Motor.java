package org.montclairrobotics.sprocket.drive;

import org.montclairrobotics.sprocket.dataconstructs.Distance;
import org.montclairrobotics.sprocket.input.Input;
import org.montclairrobotics.sprocket.pid.PID;
import org.montclairrobotics.sprocket.updater.Priority;
import org.montclairrobotics.sprocket.updater.Updatable;
import org.montclairrobotics.sprocket.updater.Updater;
import org.montclairrobotics.sprocket.utils.Dashboard;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.CANTalon.TalonControlMode;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.SpeedController;

/**
 * A standard Motor wrapper
 * @author Hymowitz
 *
 */

public class Motor implements Updatable{

	private String name;
	private Distance maxSpeed=new Distance(49*2,Distance.INCHES),encScale=Distance.INCHES;
	
	private Distance goal=new Distance(0,Distance.METERS);
	
	private SpeedController motor;
	private Encoder encoder;
	private PID pid;
	private static boolean shutdown=false;
	/**
	 * Creates a motor with an encoder and pid controller
	 * @param motor The SpeedController to use
	 */
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
	public Motor setEncoder(Encoder e,Distance scale,PID encPID)
	{
		if(e==null)return this;
		this.encoder=e;
		this.encScale=scale;
		if(encPID==null)return this;
		this.pid=encPID.copy()
				.setTotOutMode(true)
				.setMinMaxOut(-1, 1)
				.setInput(new Input(){

					@Override
					public double getInput() {
						return e.getRate();
					}});
		return this;
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
		double power;
		if(shutdown)
		{
			power = 0;
			if(motor instanceof CANTalon) ((CANTalon)motor).disableControl();
			return;
		}
		else if(encoder==null||pid==null)
		{
			power=tgtSpeed.to(maxSpeed);
		}
		else
		{
			pid.setTarget(tgtSpeed.to(encScale));
			power = pid.get();
		}
		motor.set(power);
		Dashboard.putDebugNumber("Motor "+name+" power", power);
	}
	
	public Distance getMaxSpeed()
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
}
