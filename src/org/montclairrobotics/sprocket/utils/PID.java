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

	private Input<Double> error;
	protected double P,I,D;
	protected double minOut;
	protected double maxOut;
	
	//private boolean calculated=false;
	private double out;
	private double totalError, prevError, curError;
	
	private long lastUpdateTime;
	
	/**
	 * PID constructor with default values
	 */
	public PID()
	{
		this(0.0,0.0,0.0);
	}
	
	/**
	 * @param P the Promotional Constant
	 * @param I the Integrity Constant
	 * @param D the Durability Constant
	 */
	public PID(double P,double I,double D)
	{
		this.error=null;
		this.P=P;
		this.I=I;
		this.D=D;
		this.minOut=-1.0;
		this.maxOut=1.0;
		this.lastUpdateTime = System.currentTimeMillis();
		curError=0.0;
		prevError=0.0;
		totalError=0.0;
		Updater.add(this, Priority.INPUT_PID);
	}
	
	public PID setError(Input<Double> i)
	{
		this.error=i;
		return this;
	}
	
	public PID setPID(double P, double I, double D){
		this.P=P;
		this.I=I;
		this.D=D;
		return this;
	}
	public PID setMinMaxOut(double minOut, double maxOut)
	{
		this.minOut=minOut;
		this.maxOut=maxOut;
		return this;
	}
	/**
	 * Copy constructor so you can copy PID controllers
	 * @return a copy of this PID controller
	 */
	public PID copy()
	{
		return new PID().setError(error).setPID(P,I,D);
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
		curError=getErrorVal();
		totalError += curError * loopTime;
		if (I != 0.0) 
		{
			double potentialIGain = (curError+totalError) * I;
			if (potentialIGain < maxOut) {
				if (potentialIGain > minOut) {
					totalError += curError;
				} else {
					totalError = minOut / I;
				}
			} else {
				totalError = maxOut / I;
			}
		}
	
		double out = P * curError * loopTime + I * totalError +
	             D * (curError - prevError) / loopTime; //+ calculateFeedForward();

		prevError = curError;
		
		if(minOut!=0 || maxOut!=0)
		{
			if (out > maxOut) out=maxOut;
			else if (out < minOut) out=minOut;
		}
		return out;
	}
	
	public Input<Double> getError(){
		return error;
	}
	public double getErrorVal()
	{
		return error.get();
	}

	public void update()
	{
		if(error == null || error.get() == null) return;
		out=calculate(error.get());
	}
}