package org.montclairrobotics.sprocket.drive;

import org.montclairrobotics.sprocket.geometry.Angle;
import org.montclairrobotics.sprocket.geometry.Distance;
import org.montclairrobotics.sprocket.geometry.Radians;
import org.montclairrobotics.sprocket.geometry.Vector;
import org.montclairrobotics.sprocket.loop.Priority;
import org.montclairrobotics.sprocket.loop.Updatable;
import org.montclairrobotics.sprocket.loop.Updater;
import org.montclairrobotics.sprocket.pipeline.Pipeline;

public class DriveTrain implements Updatable {

	private Distance maxSpeed=Distance.ZERO;
	private Angle maxTurn=Angle.ZERO;
	
	private DTInput input;
	private Pipeline<DTTarget> pipeline;
	private DriveModule[] modules;
    private DTMapper mapper;


    public DriveTrain(DriveModule... modules) {
    	this.modules = modules;
    	for(DriveModule module:this.modules)
    	{
    		if(module.getMaxSpeed().get()>maxSpeed.get())
    		{
    			maxSpeed=module.getMaxSpeed();
    		}
    		if(module.getOffset().getMagnitude()>0)
    		{
    			double turn=module.getMaxSpeed().get()/module.getOffset().getMagnitude();
    			if(turn>maxTurn.toRadians())
	    		{
	    			maxTurn=new Radians(turn);
	    		}
    		}
    	}
    	input=new ZeroInput();
    	pipeline=new ZeroPipeline();
    	Updater.add(this, Priority.DRIVE_CALC);
    }

	@Override
	public void update() {
		Vector tgtDir=input.getDir();
		Angle tgtTurn=input.getTurn();
		if(input.getInputType().equals(DTInput.Type.PERCENT))
		{
			tgtDir=tgtDir.scale(maxSpeed.get(),false);
			tgtTurn=tgtTurn.times(maxSpeed.get());
		}
		DTTarget target = new DTTarget(tgtDir,tgtTurn);
		target=pipeline.get(target);
		mapper.map(target, modules);
	}
    
	
	//======================GETTERS AND SETTERS======================
	public DriveTrain setInput(DTInput input)
	{
		this.input=input;
		input.setMaxSpeed(maxSpeed);
		input.setMaxTurn(maxTurn);
		return this;
	}
	public DTInput getInput()
	{
		return input;
	}
	public DriveTrain setPipeline(Pipeline<DTTarget> pipeline)
    {
    	this.pipeline=pipeline;
    	return this;
    }
    public Pipeline<DTTarget> getPipeline()
    {
    	return pipeline;
    }
    public DriveTrain setMapper(DTMapper mapper)
    {
    	this.mapper=mapper;
    	return this;
    }
    public DriveTrain setMaxSpeed(Distance s)
    {
    	maxSpeed=s;
    	return this;
    }
    public Distance getMaxSpeed()
    {
    	return maxSpeed;
    }
    public DriveTrain setMaxTurn(Angle t)
    {
    	maxTurn=t;
    	return this;
    }
    public Angle getMaxTurn()
    {
    	return maxTurn;
    }
    public DriveModule[] getModules()
    {
    	return modules;
    }
}
