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

public class PID implements Updatable, Input<Double> {
	private Input<Double> input;
	private double P, I, D;
	
	protected Range inRange;
	protected Range outRange;
	
	private double output;
	private double target;
	private double totalError, error;
	private double prevInput;
	
	private long lastUpdateTime;
	
	/**
	 * PID default constructor.
	 */
	public PID() {
		this(0.0, 0.0, 0.0);
	}
	
	/**
	 * @param P the Proportional Constant
	 * @param I the Integral Constant
	 * @param D the Derivative Constant
	 */
	public PID(double P, double I, double D) {
		this.input = null;
		this.P = P;
		this.I = I;
		this.D = D;
		this.inRange = new Range();
		this.outRange = new Range();
		this.lastUpdateTime = System.currentTimeMillis();
		resetTarget();
		
		Updater.add(this, Priority.INPUT_PID);
	}
	
	public PID setInput(Input<Double> i) {
		this.input = i;
		return this;
	}
	
	public PID setPID(double P, double I, double D) {
		this.P = P;
		this.I = I;
		this.D = D;
		return this;
	}

	@Deprecated
	public PID setMinMax(double minIn, double maxIn, double minOut, double maxOut) {
		this.inRange = new Range(minIn, maxIn);
		this.outRange = new Range(minOut, maxOut);
		return this;
	}
	
	public PID setInRange(double min, double max) {
		this.inRange = new Range(min, max);
		return this;
	}
	
	public PID setOutRange(double min, double max) {
		this.outRange = new Range(min, max);
		return this;
	}
	
	public PID setInRange(Range r) {
		this.inRange = r;
		return this;
	}
	
	public PID setOutRange(Range r) {
		this.inRange = r;
		return this;
	}
	
	/**
	 * Copy constructor so you can copy PID controllers
	 * @return a copy of this PID controller
	 */
	public PID copy() {
		return new PID(P, I, D).setInput(input).setInRange(inRange.copy()).setOutRange(outRange.copy());
	}
	
	@Deprecated
	public PID setTarget() {
		return setTarget(0.0, false);
	}
	
	public PID resetTarget() {
		return setTarget(0.0, true);
	}
	
	public PID setTarget(double t) {
		return setTarget(t, false);
	}
	/**
	 * Sets the target value.
	 * @param t the new value of target
	 * @param reset true if the PID should reset, false otherwise
	 */
	public PID setTarget(double t, boolean reset) {
		this.target = t;

		if (reset) {
			error = 0.0;
			prevInput = 0.0;
			totalError = 0.0;
		}

		return this;
	}

	/**
	 * Get the output value
	 * @return the output
	 */
	@Override
	public Double get() {
		return output;
	}
	
	private double calculate() {
		double curInput = input.get();
		
		double loopTime = (System.currentTimeMillis() - lastUpdateTime) / 1000.0;
		lastUpdateTime = System.currentTimeMillis();
		
		error = target - curInput;
		double dVal = curInput - prevInput;
		prevInput = curInput;
		
		if (!inRange.isZero()) {
			error = inRange.wrap(error);
			dVal =  inRange.wrap(dVal);
			
			Debug.print("dVal", dVal);
		}
		
		totalError += error * loopTime;
		
		if (I != 0.0) {
			double potentialIGain = (error + totalError) * I;
			
			if (outRange.contains(potentialIGain)) {
				totalError += error;
			} else {
				totalError = outRange.constrain(potentialIGain) / I;
			}
		}
	
		double out = P*(error * loopTime) + I*(totalError) + D*(-dVal / loopTime); //+ calculateFeedForward();

		if (!outRange.isZero()) {
			out = outRange.constrain(out);
		}

		return out;
	}
	
	@Deprecated
	public void setOut(double out) {
		this.output = out;
	}
	
	@Deprecated
	public double getCurInput() {
		return input.get();
	}
	
	public double getCurrentInput() {
		return input.get();
	}
	
	public Input<Double> getInput() {
		return input;
	}
	
	public double getError() {
		return error;
	}

	@Override
	public void update() {
		if (input == null || input.get() == null) {
			return;
		}
		
		output = calculate();
	}
	
	public double getTarget() {
		return target;
	}
	
	public double getP() {
		return P;
	}
	
	public double getI() {
		return I;
	}
	
	public double getD() {
		return D;
	}
}