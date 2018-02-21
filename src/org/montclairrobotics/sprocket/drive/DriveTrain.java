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
		
	private DTModule[] modules;
	private DTStep[] steps;
    private DTMapper mapper;

    
    public DTRequest request;

    public DriveTrain(DTModule... modules) {
    	this.modules = modules;
    	Sprocket.setMainDriveTrain(this);
    	Updater.add(this, Priority.DRIVE_CALC);
    }

    public void setSteps(DTStep... steps) {
    	this.steps=steps;
    }
    
    public void setRequest(DTRequest request)
    {
    	this.request=request;
    }
	@Override
	public void update() {
		//DTInput input = auto ? this.autoInput : this.input;
		for(int i=0;i<steps.length;i++)
		{
			steps[i].doStep(request);
		}
		mapper.map(request, modules);
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
    
	@Override
	public Distance get() {
		return new Distance(this.getPosition().getY());
	}
    
}
