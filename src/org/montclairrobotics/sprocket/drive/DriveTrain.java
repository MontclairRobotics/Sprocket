package org.montclairrobotics.sprocket.drive;

import org.montclairrobotics.sprocket.core.Sprocket;
import org.montclairrobotics.sprocket.geometry.Distance;
import org.montclairrobotics.sprocket.geometry.Vector;
import org.montclairrobotics.sprocket.loop.Priority;
import org.montclairrobotics.sprocket.loop.Updatable;
import org.montclairrobotics.sprocket.loop.Updater;
import org.montclairrobotics.sprocket.pipeline.Pipeline;
import org.montclairrobotics.sprocket.utils.Debug;
import org.montclairrobotics.sprocket.utils.Input;

public class DriveTrain implements Updatable, Input<Distance> {
		
	private DTInput input;
	private DTInput defaultInput;
	private Pipeline<DTTarget> pipeline;
	private DTModule[] modules;
    private DTMapper mapper;


    public DriveTrain(DTModule... modules) {
    	this.modules = modules;
    	input=new ZeroDTInput();
    	pipeline=new ZeroPipeline();
    	Sprocket.setMainDriveTrain(this);
    	Updater.add(this, Priority.DRIVE_CALC);
    }

	@Override
	public void update() {
		//DTInput input = auto ? this.autoInput : this.input;
		
		Vector tgtDir=input.getDir();
		double tgtTurn=input.getTurn();
		DTTarget target = new DTTarget(tgtDir,tgtTurn);
		Debug.string("DriveTrain INPUT:",target.toString());
		target=pipeline.get(target);
		Debug.string("DriveTrain OUTPUT:",target.toString());
		mapper.map(target, modules);
	}
	
	//======================GETTERS AND SETTERS======================
	public DriveTrain setDefaultInput(DTInput input)
	{
		this.input=input;
		this.defaultInput = input;
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
    
    public Vector getPosition() {
    	if(modules.length==0)
    		return Vector.ZERO;
    	Vector totDist = Vector.ZERO;
    	int modulesWithEnc = 0;
    	for(DTModule m : modules) {
    		if(!m.hasEncoder()) continue;
    		totDist=totDist.add(m.getForce().setMag(m.getDistance().get()));
    		modulesWithEnc++;
    	}
    	Vector avgDist=totDist.scale(1.0/modulesWithEnc);
    	return avgDist;
    }
    
    public Vector getVelocity() {
    	if(modules.length==0)
    		return Vector.ZERO;
    	Vector totDist = Vector.ZERO;
    	int modulesWithEnc = 0;
    	for(DTModule m : modules) {
    		if(!m.hasEncoder()) continue;
    		totDist=totDist.add(m.getForce().setMag(m.getEnc().getSpeed().get()));
    		modulesWithEnc++;
    	}
    	Vector avgDist=totDist.scale(1.0/modulesWithEnc);
    	return avgDist;
    }
    
    public DTModule[] getModules()
    {
    	return modules;
    }
    
    public DriveTrain setTempInput(DTInput tempInput) {
    	input = tempInput;
    	return this;
    }
    
    public void useDefaultInput() {
    	input = defaultInput;
    }

	@Override
	public Distance get() {
		return new Distance(-this.getPosition().getY());
	}
    
}
