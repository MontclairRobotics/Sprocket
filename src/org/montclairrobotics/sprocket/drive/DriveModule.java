package org.montclairrobotics.sprocket.drive;

import org.montclairrobotics.sprocket.geometry.Angle;
import org.montclairrobotics.sprocket.geometry.Distance;
import org.montclairrobotics.sprocket.geometry.Speed;
import org.montclairrobotics.sprocket.geometry.Vector;
import org.montclairrobotics.sprocket.updater.Priority;
import org.montclairrobotics.sprocket.updater.Updatable;
import org.montclairrobotics.sprocket.updater.Updater;
import org.montclairrobotics.sprocket.utils.PID;

import edu.wpi.first.wpilibj.Encoder;
 

public class DriveModule implements Updatable{
	
	private Vector position,force;//the relative position of this wheel, and the velocity at max power
	private IMotor[] motors;//all motors part of this module
	private Encoder encoder;//this module's encoder
	private PID pid=null;//the pid controller
	private Distance encoderTick;//the distance per encoder tick
	
	private Speed speed;//our target speed
	
	public DriveModule(Vector pos,Vector force,IMotor... motors)
	{
		this.position=pos;
		this.force=force;
		this.motors=motors;
		
		Updater.add(this, Priority.before(Priority.OUTPUT));
	}
	//Currying stuff
	public DriveModule setEncoder(Encoder enc,Distance encTick)
	{
		this.encoder=enc;
		this.encoderTick=encTick;
		return this;
	}
	public DriveModule setPID(PID pid)
	{
		if(encoder!=null&&encoderTick!=null)
		{
			this.pid=pid.copy().setInput(new Input<Double>(){

				public Double getRaw() {
					return encoder.getRate()*encoderTick.getMeters();
				}
				
			});
		}
		return this;
	}
	public DriveModule setPosition(Vector pos)
	{
		if(pos!=null)
			this.position=pos;
		return this;
	}
	public DriveModule setForce(Vector force) {
		if(force!=null)
			this.force=force;
		return this;
	}
	
	
	//set the target velocity and rotation; return our target vector
	public Vector set(Vector velocity,Angle rotation)
	{
		Vector tgt=velocity.add(position.getRotationVector(rotation));
		speed=new Speed(Vector.dotProduct(tgt,force));
		return tgt;
	}
	
	//sets the actual power
	public void update()
	{
		double power;
		if(pid==null)
		{
			power=speed.divide(force.getMag()).get();
		}
		else
		{
			power=pid.setTarget(speed.get()).get();
		}
		for(IMotor motor:motors)
		{
			motor.set(power);
		}
	}
	
	public Vector getPosition()
	{
		return position;
	}
	public Vector getForce()
	{
		return force;
	}
}
