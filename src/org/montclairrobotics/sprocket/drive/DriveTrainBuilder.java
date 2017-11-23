package org.montclairrobotics.sprocket.drive;

import java.util.ArrayList;

import org.montclairrobotics.sprocket.control.ArcadeDriveInput;
import org.montclairrobotics.sprocket.core.IJoystick;
import org.montclairrobotics.sprocket.core.IMotor;
import org.montclairrobotics.sprocket.geometry.Angle;
import org.montclairrobotics.sprocket.geometry.Polar;
import org.montclairrobotics.sprocket.geometry.Vector;
import org.montclairrobotics.sprocket.motors.Module;
import org.montclairrobotics.sprocket.motors.SEncoder;
import org.montclairrobotics.sprocket.pipeline.Pipeline;
import org.montclairrobotics.sprocket.pipeline.Step;
import org.montclairrobotics.sprocket.utils.PID;

/**
 * DriveTrainBuilder is a helper class which makes constructing DriveTrains more
 * straightforward for beginners. This class abstracts a lot of the more advanced
 * features of DriveTrain to make constructing the vast majority of robots a lot
 * easier than it would be otherwise. All methods in this class can be chained
 * and even though you're using a Builder you still have a lot of functionality
 * and power exposed.
 *
 */
public class DriveTrainBuilder {

    private ArrayList<DriveModule> modules;
    private DTInput input;
    private DriveTrainType driveTrainType;
    private ArrayList<Step<DTTarget>> pipelineSteps;

    public DriveTrainBuilder() {
        modules = new ArrayList<>();
        pipelineSteps = new ArrayList<>();
        //pipelineSteps.add(new Deadzone());
    }


    public DriveTrainBuilder addDriveModule(DriveModule module) {
        modules.add(module);
        return this;
    }
    
    public DriveTrainBuilder addWheel(IMotor motor, Vector offset, Angle force, SEncoder enc, PID pid) {
        modules.add(new DriveModule(offset, new Polar(1, force),enc,pid,Module.MotorInputType.SPEED, motor));
        return this;
    }

    public DriveTrainBuilder addWheel(IMotor motor, Vector offset, Angle force) {
        return addWheel(motor, offset, force, null, null);
    }
    
    
    public DriveTrainBuilder addWheels(Vector offset, Angle force, IMotor... motors) {
    	modules.add(new DriveModule(offset, new Polar(1, force), motors));
    	return this;
    }
    
    
    
    public DriveTrainBuilder setInput(DTInput input) {
        this.input = input;
        return this;
    }
    
    public DriveTrainBuilder setDriveTrainType(DriveTrainType type) {
    	driveTrainType = type;
    	return this;
    }
    
    public DriveTrainBuilder setDefaultPipeline() {
    	pipelineSteps.clear();
    	//pipelineSteps.add(new Deadzone());
    	return this;
    }
    
    public DriveTrainBuilder setPipeline(Pipeline<DTTarget> pipeline) {
    	pipelineSteps = pipeline.getSteps();
    	return this;
    }
    
    public DriveTrainBuilder clearPipeline() {
    	pipelineSteps.clear();
    	return this;
    }
    
    public DriveTrainBuilder addStep(Step<DTTarget> step) {
    	pipelineSteps.add(step);
    	return this;
    }
    
    public DriveTrainBuilder addSteps(Step<DTTarget>... steps) {
    	for(Step<DTTarget> step : steps) {
    		pipelineSteps.add(step);
    	}
    	return this;
    }
    
    public DriveTrainBuilder setArcadeDriveInput(IJoystick stick) {
        return setInput(new ArcadeDriveInput(stick));
    }

    public DriveTrain build() throws InvalidDriveTrainException {
        if(modules.size() == 0) {
            throw new InvalidDriveTrainException("DriveTrain requires at least one drive module to be defined.");
        }
        
        if(input == null) {
        	throw new InvalidDriveTrainException("DriveTrain needs an input source");
        }
        
        DTMapper mapper;
        if(driveTrainType == null) {
        	driveTrainType = DriveTrainType.TANK;
        }
        switch(driveTrainType) {
        case MECANUM:
        	mapper = new MecanumMapper();
        break;
        default:
        	mapper = new TankMapper();
        }

        DriveTrain dt = new DriveTrain(modules.toArray(new DriveModule[]{}));

        dt.setDefaultInput(input);
        dt.setMapper(mapper);
        
        dt.setPipeline(new DTPipeline(pipelineSteps));

        return dt;
    }

}