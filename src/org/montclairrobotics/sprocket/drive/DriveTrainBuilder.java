package org.montclairrobotics.sprocket.drive;

import java.util.ArrayList;

import org.montclairrobotics.sprocket.control.ArcadeDriveInput;
import org.montclairrobotics.sprocket.geometry.Angle;
import org.montclairrobotics.sprocket.geometry.Inch;
import org.montclairrobotics.sprocket.geometry.Vector;
import org.montclairrobotics.sprocket.utils.PID;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.SpeedController;

public class DriveTrainBuilder {

    private ArrayList<DriveModule> modules;
    private DriveTrainInput input;
    private DriveTrainType driveTrainType;
    private DrivePipeline drivePipeline;

    public DriveTrainBuilder() {
        modules = new ArrayList<>();
    }


    public DriveTrainBuilder addDriveModule(DriveModule module) {
        modules.add(module);
        return this;
    }

    public DriveTrainBuilder addWheel(SpeedController motor, Angle forceAngle, Vector pos, SEncoder enc, PID pid, Inch maxSpeed, boolean invert) {
        DriveModule module = new DriveModule(motor, forceAngle,
                pos,
                enc, pid, maxSpeed);
        if(invert) {
            module.setInverted(true);
        }
        return this;
    }

    public DriveTrainBuilder addWheel(SpeedController motor, Angle forceAngle, Vector position, SEncoder enc, PID pid, Inch maxSpeed) {
        return addWheel(motor, forceAngle, position, enc, pid, maxSpeed, false);
    }

    public DriveTrainBuilder addWheel(SpeedController motor, Angle forceAngle, Vector position) {
        return addWheel(motor, forceAngle, position, null, null, null);
    }

    public DriveTrainBuilder setInput(DriveTrainInput input) {
        this.input = input;
        return this;
    }
    
    public DriveTrainBuilder setDriveTrainType(DriveTrainType type) {
    	driveTrainType = type;
    	return this;
    }
    
    public DriveTrainBuilder setDefaultPipeline() {
    	drivePipeline = new DrivePipeline();
    	return this;
    }
    
    public DriveTrainBuilder setArcadeDriveInput(Joystick stick) {
        return setInput(new ArcadeDriveInput(stick));
    }

    public DriveTrainBuilder setArcadeDriveInputSpeedControl(Joystick stick, Inch maxSpeed) {
        return setInput(new ArcadeDriveInput(stick, maxSpeed));
    }

    public DriveTrain build() throws InvalidDriveTrainException {
        if(modules.size() == 0) {
            throw new InvalidDriveTrainException("DriveTrain requires at least one drive module to be defined.");
        }
        
        if(input == null) {
        	throw new InvalidDriveTrainException("DriveTrain needs an input source");
        }
        
        DriveTrainMapper mapper;
        switch(driveTrainType) {
        case MECANUM:
        	mapper = new MecanumMapper();
        break;
        default:
        	mapper = new TankMapper();
        }
        
        if(drivePipeline == null) setDefaultPipeline();
        
        return new DriveTrain(modules.toArray(new DriveModule[]{}), input, drivePipeline, mapper);
    }

}