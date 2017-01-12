package org.montclairrobotics.sprocket.drive;

import java.util.Arrays;
import java.util.List;

import org.montclairrobotics.sprocket.loop.Priority;
import org.montclairrobotics.sprocket.loop.Updatable;
import org.montclairrobotics.sprocket.loop.Updater;

public class DriveTrain implements Updatable {

    private List<DriveModule> driveModules;
    private DrivePipeline pipeline;
    private DriveTrainMapper powerMapper;
    private DriveTrainInput input;


    public DriveTrain(DriveModule[] modules, DriveTrainInput input, DrivePipeline pipeline, DriveTrainMapper mapper) {
    	this.driveModules = Arrays.asList(modules);
    	this.input = input;
    	this.pipeline = pipeline;
    	this.powerMapper = mapper;
    	
    	Updater.add(this, Priority.DRIVE_CALC);
    }


	@Override
	public void update() {
		DriveTrainTarget target = new DriveTrainTarget(input.getDirection(), input.getTurn(), input.getInputType());
		pipeline.run(target);
		powerMapper.map(target, driveModules.toArray(new DriveModule[]{}));
	}
    
    
    
}
