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
 * Uses the math from the WPI PIDController class.
 * @see PIDController
 */

public class PID implements Updatable, Input<Double>{

	private Input<Double> input;
	private double P, I, D;
	protected double minIn;
	protected double maxIn;
	protected double minOut;
	protected double maxOut;
	
	//private boolean calculated=false;
	private double out;
	private double target;
	private double totalError, prevVal, error;
	
	private long lastUpdateTime;
	
	/**
	 * PID default constructor.
	 */
	public PID() {
		this.input = null;
		this.P = 0.0;
		this.I = 0.0;
		this.D = 0.0;
		this.minOut = 0.0;
		this.maxOut = 0.0;
		this.minIn = 0.0;
		this.maxIn = 0.0;
		this.lastUpdateTime = System.currentTimeMillis();
		setTarget();
		Updater.add(this, Priority.INPUT_PID);
	}
	
	/**
	 * @param P the Propotional Constant
	 * @param I the Integral Constant
	 * @param D the Derivitive Constant
	 */
	public PID(double P,double I,double D) {
		this.input = null;
		this.P = P;
		this.I = I;
		this.D = D;
		this.minOut = 0.0;
		this.maxOut = 0.0;
		this.minIn = 0.0;
		this.maxIn = 0.0;
		this.lastUpdateTime = System.currentTimeMillis();
		setTarget();
		Updater.add(this, Priority.INPUT_PID);
	}
	
	public PID setInput(Input<Double> i) {
		this.input = i;
		return this;
	}
	
	public PID setPID(double P, double I, double D){
		this.P = P;
		this.I = I;
		this.D = D;
		return this;
	}

	public PID setMinMax(double minIn, double maxIn, double minOut, double maxOut) {
		this.minOut = minOut;
		this.maxOut = maxOut;
		this.minIn = minIn;
		this.maxIn = maxIn;
		return this;
	}
	/**
	 * Copy constructor so you can copy PID controllers
	 * @return a copy of this PID controller
	 */
	public PID copy() {
		return new PID(P, I, D).setInput(input).setMinMax(minIn, maxIn, minOut, maxOut);
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

		if(reset) {
			error = 0.0;
			prevVal = 0.0;
			totalError = 0.0;
		}

		return this;
	}

	/**
	 * Get the output value
	 * @return the output
	 */
	public Double get() {
		//out = calculate(input.get());
		return out;
	}
	
	private double calculate(double val) {
		double loopTime = (System.currentTimeMillis() - lastUpdateTime) / 1000.0;
		lastUpdateTime = System.currentTimeMillis();
		error = target-val;
		
		double dVal = val - prevVal;
		
		if(minIn != 0 && maxIn != 0)
		{
			double diff=maxIn-minIn;
			error=((error-minIn)%diff+diff)%diff+minIn;
			dVal=((dVal-minIn)%diff+diff)%diff+minIn;
			Debug.msg("dVal",dVal);
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
	
		double out = (P * error * loopTime) + (I * totalError) + (D * -dVal/loopTime); //+ calculateFeedForward();

		prevVal = val;
		
		if(minOut!=0 || maxOut!=0) {
			if (out > maxOut)
				out = maxOut;
			else if (out < minOut)
				out = minOut;
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

	public void update() {
		if (input == null || input.get() == null) { return; }
		out=calculate(input.get());
	}
	
	public double getTarget()
	{
		return target;
	}
	
	public double getP()
	{
		return P;
	}
	
	public double getI()
	{
		return I;
	}
	
	public double getD()
	{
		return D;
	}
}