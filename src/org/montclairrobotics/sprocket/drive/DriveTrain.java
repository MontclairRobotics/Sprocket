package org.montclairrobotics.sprocket.drive;

import org.montclairrobotics.sprocket.geometry.Angle;
import org.montclairrobotics.sprocket.geometry.Distance;
import org.montclairrobotics.sprocket.geometry.Radians;
import org.montclairrobotics.sprocket.loop.Priority;
import org.montclairrobotics.sprocket.loop.Updatable;
import org.montclairrobotics.sprocket.loop.Updater;
import org.montclairrobotics.sprocket.utils.Input;

public class DriveTrain implements Updatable {

	public static Distance maxSpeed=Distance.ZERO;
	public static Angle maxTurn=Angle.ZERO;
	
	private Input<DriveTrainTarget> input;
	private DriveModule[] modules;
    private DriveTrainMapper mapper;


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
    	Updater.add(this, Priority.DRIVE_CALC);
    }

	@Override
	public void update() {
		DriveTrainTarget target = input.get();
		mapper.map(target, modules);
	}
    
	
	//======================GETTERS AND SETTERS======================
	public DriveTrain setInput(Input<DriveTrainTarget> input)
    {
    	this.input=input;
    	return this;
    }
    public Input<DriveTrainTarget> getInput()
    {
    	return input;
    }
    public DriveTrain setMapper(DriveTrainMapper mapper)
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
    
}
