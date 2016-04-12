package org.montclairrobotics.sprocket.utils;


/**
 * A PID controller
 * Create with P,I,D values
 * Call setTarget() to set the target
 * Call get() to get the correction
 * 
 * It just uses the math from the WPI PIDController class.
 * 
 */

public class PID implements Updatable{

	private double P,I,D,minIn,maxIn,minOut,maxOut;
	
	private double in,out;
	private double target;
	private double totalError, prevError, error;
	/**
	 * P the Proportional constant
	 * I the Integral constant
	 * D the Derivative constant
	 * 
	 * minIn the lowest input value
	 * maxIn the highest input value
	 * These will wrap the values between the min and max; max+1=min
	 * 
	 * minOut 
	 * maxOut
	 * constrain output to these 
	 */
	public PID(double P,double I,double D)
	{
		this(P,I,D,0,0);
	}
	public PID(double P,double I,double D,double minIn,double maxIn)
	{
		this(P,I,D,minIn,maxIn,0,0);
	}
	/**
	 * 
	 * @param P the Propotional Constant
	 * @param I the Integral Constant
	 * @param D the Derivitive Constant
	 * @param minIn OPTIONAL the minimum input, or 0 to ignore. Use with maxIn to "wrap" the values, 
	 * eg. so the error between 5 degrees and 355 degrees is 10 degrees
	 * @param maxIn OPTIONAL the maximum input, or 0 to ignore
	 * @param minOut OPTIONAL the minimum output to constrain to, or 0 to ignore
	 * @param maxOut OPTIONAL the maximum output to constrain to, or 0 to ignore
	 */
	public PID(double P,double I,double D, double minIn, double maxIn, double minOut, double maxOut)
	{
		this.P=P;
		this.I=I;
		this.D=D;
		this.minOut=minOut;
		this.maxOut=maxOut;
		this.minIn=minIn;
		this.maxIn=maxIn;
		setTarget();
		Updater.add(this, UpdateClass.ControlTranslator);
	}

	public void setPID(double P, double I, double D){
		this.P=P;
		this.I=I;
		this.D=D;
	}
	public void setMinMaxInOut(double minIn, double maxIn, double minOut, double maxOut)
	{
		this.minOut=minOut;
		this.maxOut=maxOut;
		this.minIn=minIn;
		this.maxIn=maxIn;
	}
	/**
	 * Copy constructor so you can copy PID controllers
	 * @return a copy of this PID controller
	 */
	public PID copy()
	{
		return new PID(P,I,D,minIn,maxIn,minOut,maxOut);
	}
	
	public void setTarget()
	{
		setTarget(0.0,true);
	}
	public void setTarget(double t)
	{
		setTarget(t,true);
	}
	/**
	 * Sets the setpoint
	 * @param t the target/setpoint
	 * @param reset true if the PID should reset, false otherwise
	 */
	public void setTarget(double t,boolean reset)
	{
		target=t;
		if(reset)
		{
			error=0.0;
			prevError=0.0;
			totalError=0.0;
		}
	}
	
	/**
	 * Set the input value (do this once per loop)
	 * @param val the input value
	 */
	public void in(double val)
	{
		in=val;
	}

	/**
	 * Get the output value
	 * @return the output
	 */
	public double out()
	{
		return out;
	}
	
	private double calculate(double val)
	{
		error=target-val;
		if(minIn!=0&&maxIn!=0)
		{
			double diff=maxIn-minIn;
			error=((error-minIn)%diff+diff)%diff+minIn;
		}
		totalError+=error;
		if (I != 0) 
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
	
		double out = P * error + I * totalError +
	             D * (error - prevError); //+ calculateFeedForward();

		prevError = error;
		
		if(minOut!=0 || maxOut!=0)
		{
			if (out > maxOut) out=maxOut;
			else if (out < minOut) out=minOut;
		}
		return out;
	}
	public double getError(){
		return error;
	}

	public void update()
	{
		out=calculate(in);
	}
	
	public double getIn()
	{
		return in;
	}
	public void setOut(double val)
	{
		out=val;
	}
}