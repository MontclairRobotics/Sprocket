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

public class TargetablePID extends PID implements Updatable {

	private Input<Double> input;
	private Input<Double> target;
	protected double minIn;
	protected double maxIn; 
	
	/**
	 * PID constructor with default values
	 */
	public TargetablePID()
	{
		this(0.0,0.0,0.0);
	}
	
	/**
	 * @param P the Promotional Constant
	 * @param I the Integrity Constant
	 * @param D the Durability Constant
	 */
	public TargetablePID(double P,double I,double D)
	{
		super(P,I,D);
		this.minIn=0.0;
		this.maxIn=0.0;
		input=new ZeroInput();
		target=new ZeroInput();
		Updater.add(this, Priority.INPUT_PID);
	}
	
	public TargetablePID setMinMaxIn(double minIn,double maxIn)
	{
		this.minIn=minIn;
		this.maxIn=maxIn;
		return this;
	}
	
	public TargetablePID setInput(double i)
	{
		return setInput(new DoubleInput(i));
	}
	public TargetablePID setInput(Input<Double> i)
	{
		this.input=i;
		return this;
	}
	
	public TargetablePID setTarget(double t)
	{
		return setTarget(new DoubleInput(t));
	}
	public TargetablePID setTarget(Input<Double> t)
	{
		this.target=t;
		return this;
	}
	
	public Input<Double> getInput()
	{
		return input;
	}
	public double getInputVal()
	{
		return input.get();
	}
	public Input<Double> getTarget()
	{
		return target;
	}
	public double getTargetVal()
	{
		return target.get();
	}
	/**
	 * Copy constructor so you can copy PID controllers
	 * @return a copy of this PID controller
	 */
	public TargetablePID copy()
	{
		return (TargetablePID)new TargetablePID().setInput(input).setTarget(target).setMinMaxIn(minIn,maxIn).setPID(P,I,D).setMinMaxOut(minOut,maxOut);
	}
	
	public Input<Double> getError()
	{
		return new Input<Double>(){

			@Override
			public Double get() {
				// TODO Auto-generated method stub
				return getCurError();
			}};
	}
	public double getCurError()
	{
		if(minIn!=0.0&&maxIn!=0.0)
		{
			return Utils.wrap(target.get()-input.get(),minIn,maxIn);
		}
		return target.get()-input.get();
	}
}