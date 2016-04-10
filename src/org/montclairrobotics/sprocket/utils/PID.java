package org.montclairrobotics.sprocket.utils;


/*
 * Usage:
 * 
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
	/*
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
		Updater.add(this);
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
	//sets the setpoint. If reset is true, will reset calculations
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
	
	public void setCur(double val)
	{
		in=val;
	}

	public double getRawOut()
	{
		return out;
	}
	
	public double getAdjOut()
	{
		return target*(1+out);
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
}