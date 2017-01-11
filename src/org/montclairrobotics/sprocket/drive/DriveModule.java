package org.montclairrobotics.sprocket.drive;

import org.montclairrobotics.sprocket.geometry.Angle;
import org.montclairrobotics.sprocket.geometry.Vector;
import org.montclairrobotics.sprocket.loop.Priority;
import org.montclairrobotics.sprocket.loop.Updatable;
import org.montclairrobotics.sprocket.loop.Updater;
import org.montclairrobotics.sprocket.pipeline.Pipeline;

public class DriveModule implements Updatable{
	private Motor[] motors;
	private Pipeline<Double> outputPipeline=new Pipeline<Double>(0.0);
	private Vector force;
	private Vector offset;
	private double tgtForce;
	
	public DriveModule(Vector force,Vector offset,Motor... motors){
		this.motors=motors;
		Updater.add(this, Priority.OUTPUT);
	}
	public Angle getTorque(Vector tgtDirection)
	{
		return null;
		
	}
	public void setDirectionTurn(Vector tgtDirection,Angle tgtTurn)
	{
		
	}
	
	public void setOutputPipeline(Pipeline<Double> p)
	{
		this.outputPipeline=p;
	}
	@Override
	public void update() {
		tgtForce=outputPipeline.get(tgtForce);
		for(Motor motor:motors)
		{
			motor.set(tgtForce);
		}
	}
	
}
