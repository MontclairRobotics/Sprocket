package org.montclairrobotics.sprocket.utils;


import org.montclairrobotics.sprocket.loop.Priority;
import org.montclairrobotics.sprocket.loop.Updatable;
import org.montclairrobotics.sprocket.loop.Updater;

/**
 * A PID controller
 * Create with P,I,D values
 * Call setTarget() to set the target
 * Call get() to get the correction
 * 
 * It just uses the math from the WPI PIDController class.
 * 
 */

public class PID implements Updatable {

	private Input<Double> input;
	private double P,I,D;
	protected double minIn;
	protected double maxIn;
	protected double minOut;
	protected double maxOut;
	
	//private boolean calculated=false;
	private double out;
	private double target;
	private double totalError, prevError, error;
	
	private long lastUpdateTime;
	
	/**
	 * PID constructor with default values
	 */
	public PID()
	{
		this.input=null;
		this.P=0.0;
		this.I=0.0;
		this.D=0.0;
		this.minOut=0.0;
		this.maxOut=0.0;
		this.minIn=0.0;
		this.maxIn=0.0;
		this.lastUpdateTime = System.currentTimeMillis();
		setTarget();
		Updater.add(this, Priority.INPUT_PID);
	}
	
	/**
	 * @param P the Propotional Constant
	 * @param I the Integral Constant
	 * @param D the Derivitive Constant
	 */
	public PID(double P,double I,double D)
	{
		setPID(P,I,D);
	}
	
	public PID setInput(Input<Double> i)
	{
		this.input=i;
		return this;
	}
	
	public PID setPID(double P, double I, double D){
		this.P=P;
		this.I=I;
		this.D=D;
		return this;
	}
	public PID setMinMax(double minIn, double maxIn, double minOut, double maxOut)
	{
		this.minOut=minOut;
		this.maxOut=maxOut;
		this.minIn=minIn;
		this.maxIn=maxIn;
		return this;
	}
	/**
	 * Copy constructor so you can copy PID controllers
	 * @return a copy of this PID controller
	 */
	public PID copy()
	{
		return new PID().setInput(input).setPID(P,I,D).setMinMax(minIn,maxIn,minOut,maxOut);
	}
	
	public PID setTarget()
	{
		return setTarget(0.0,false);
	}
	public PID setTarget(double t)
	{
		return setTarget(t,false);
	}
	/**
	 * Sets the setpoint
	 * @param t the target/setpoint
	 * @param reset true if the PID should reset, false otherwise
	 */
	public PID setTarget(double t,boolean reset)
	{
		target=t;
		if(reset)
		{
			error=0.0;
			prevError=0.0;
			totalError=0.0;
		}
		return this;
	}

	/**
	 * Get the output value
	 * @return the output
	 */
	public double get()
	{
		//out = calculate(input.get());
		return out;
	}
	
	private double calculate(double val)
	{
		double loopTime = (System.currentTimeMillis() - lastUpdateTime) / 1000.0;
		lastUpdateTime = System.currentTimeMillis();
		error=target-val;
		if(minIn!=0&&maxIn!=0)
		{
			double diff=maxIn-minIn;
			error=((error-minIn)%diff+diff)%diff+minIn;
		}
		totalError += error * loopTime;
		if (I != 0.0) 
		{
			double potentialIGain = (error+totalError) * I;
			if (potentialIGain < maxOut) {
				if (potentialIGain > minOut) {
					totalError += error;
				} else {
					totalError = minOut / I;
				}
			} else {
				totalError = maxOut / I;
			}
		}
	
		double out = P * error * loopTime + I * totalError +
	             D * (error - prevError) / loopTime; //+ calculateFeedForward();

		prevError = error;
		
		if(minOut!=0 || maxOut!=0)
		{
			if (out > maxOut) out=maxOut;
			else if (out < minOut) out=minOut;
		}
		return out;
	}
	
	public void setOut(double out)
	{
		this.out=out;
	}
	
	public double getCurInput()
	{
		return input.get();
	}
	public Input<Double> getInput()
	{
		return input;
	}
	public double getError(){
		return error;
	}

	public void update()
	{
		out=calculate(input.get());
	}
	
	public double getTarget()
	{
		return target;
	}
}