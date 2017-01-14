package org.montclairrobotics.sprocket.drive;

import java.util.Arrays;
import java.util.List;

import org.montclairrobotics.sprocket.loop.Priority;
import org.montclairrobotics.sprocket.loop.Updatable;
import org.montclairrobotics.sprocket.loop.Updater;
import org.montclairrobotics.sprocket.utils.Input;

public class DriveTrain implements Updatable {


	private Input<DriveTrainTarget> input;
	private DriveModule[] modules;
    private DriveTrainMapper mapper;


    public DriveTrain(DriveModule... modules) {
    	this.modules = modules;
    	
    	Updater.add(this, Priority.DRIVE_CALC);
    }
    
    public DriveTrain setInput(Input<DriveTrainTarget> input)
    {
    	this.input=input;
    	return this;
    }
    public DriveTrain setMapper(DriveTrainMapper mapper)
    {
    	this.mapper=mapper;
    	return this;
    }

	@Override
	public void update() {
		DriveTrainTarget target = input.get();
		mapper.map(target, modules);
	}
    
    
    
}
