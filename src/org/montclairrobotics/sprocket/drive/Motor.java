package org.montclairrobotics.sprocket.drive;

import org.montclairrobotics.sprocket.utils.Angle;
import org.montclairrobotics.sprocket.utils.Degree;
import org.montclairrobotics.sprocket.utils.PID;
import org.montclairrobotics.sprocket.utils.Polar;
import org.montclairrobotics.sprocket.utils.Updatable;
import org.montclairrobotics.sprocket.utils.UpdateClass;
import org.montclairrobotics.sprocket.utils.Updater;
import org.montclairrobotics.sprocket.utils.Vector;
import org.montclairrobotics.sprocket.utils.XY;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.CANTalon.TalonControlMode;

/**
 * A standard Motor wrapper
 * @author Hymowitz
 *
 */

public class Motor implements Updatable{
	
	public static enum M_TYPE{TALONSRX,VICTORSP,TALON};
	
	private Vector goal,actualVector;
	private SpeedController motor;
	private Encoder encoder;
	private PID pid;
	private static boolean shutdown=false;
	
	public Motor(SpeedController motor){this(motor,null,null);}
	public Motor(SpeedController motor,Encoder encoder){this(motor,encoder,null);}
	/**
	 * Creates a motor with an encoder and pid controller
	 * @param motor The SpeedController to use
	 * @param encoder The Encoder to use
	 * @param encPID The PID values to use (will be copied, can use one PID instance with multiple motors)
	 */
	public Motor(SpeedController motor,Encoder encoder,PID encPID)
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
		this.pid.setMinMaxInOut(0, 0, -1, 1);
		Updater.add(this, UpdateClass.MotorController);
	}
	/**
	 * Sets the speed (not directly)
	 * @param spd The speed to spin at
	 */
	public void setSpeed(double spd)
	{
		setVelocity(new XY(spd,0));
	}
	/**
	 * Sets the velocity vector of this wheel with no rotation
	 * @param v The velocity Vector of the robot
	 */
	public void setVelocity(Vector v)
	{
		goal=v;
	}
	/**
	 * Calculates the speed of this wheel
	 * Overload this method for more complicated driveTrains
	 * @param goal The goal velocity vector for this wheel
	 * @return the speed as a double of this wheel
	 */
	public double calcSpeed(Vector goal)
	{
		actualVector=new XY(0,goal.getY());
		return actualVector.getY();
	}
	public Vector getGoal()
	{
		return goal;
	}
	public Vector getActual()
	{
		return actualVector;
	}
	/**
	 * The automatically called function to update the speed of this wheel
	 * Will attempt to use a PID controller if it is not null.
	 */
	public void update()
	{
		double tgtSpeed=calcSpeed(goal);
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
			pid.setTarget(tgtSpeed,false);
			pid.in(encoder.getRate());
			speed=pid.out();//tgtSpeed*(1+pid.out());
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
	public void setActual(Vector actualVector) {
		this.actualVector = actualVector;
	}
	
	/**
	 * Helper method to create a SpeedController of a given type
	 * @param port The motor port
	 * @param type The motor type
	 * @return The SpeedController
	 */
	public static SpeedController makeMotor(int port,M_TYPE type)
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
