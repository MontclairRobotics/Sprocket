package org.montclairrobotics.sprocket.drive;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.SpeedController;
import org.montclairrobotics.sprocket.control.ArcadeDriveInput;
import org.montclairrobotics.sprocket.geometry.Angle;
import org.montclairrobotics.sprocket.geometry.Distance;
import org.montclairrobotics.sprocket.geometry.RPolar;
import org.montclairrobotics.sprocket.geometry.RVector;
import org.montclairrobotics.sprocket.pipeline.Pipeline;
import org.montclairrobotics.sprocket.utils.PID;

import java.util.ArrayList;

public class DriveTrainBuilder {

    private ArrayList<DriveModule> modules;
    private DTInput input;
    private DriveTrainType driveTrainType;
    private Pipeline<DTTarget> drivePipeline;

    public DriveTrainBuilder() {
        modules = new ArrayList<>();
    }


    public DriveTrainBuilder addDriveModule(DriveModule module) {
        modules.add(module);
        return this;
    }

    public DriveTrainBuilder addWheel(SpeedController motor, RVector offset, Angle force, SEncoder enc, PID pid, Distance maxSpeed, boolean invert) {
        modules.add(new DriveModule(offset, new RPolar(1, force), new Motor(motor, enc, pid, invert)));
        return this;
    }

    public DriveTrainBuilder addWheel(SpeedController motor, RVector offset, Angle force, SEncoder enc, PID pid, Distance maxSpeed) {
        return addWheel(motor, offset, force, enc, pid, maxSpeed, false);
    }

    public DriveTrainBuilder addWheel(SpeedController motor, RVector offset, Angle force) {
        return addWheel(motor, offset, force, null, null, null);
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
    	drivePipeline = new ZeroPipeline();
    	return this;
    }
    
    public DriveTrainBuilder setArcadeDriveInput(Joystick stick) {
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
        switch(driveTrainType) {
        case MECANUM:
        	mapper = new MecanumMapper();
        break;
        default:
        	mapper = new TankMapper();
        }
        
        if(drivePipeline == null) setDefaultPipeline();

        DriveTrain dt = new DriveTrain(modules.toArray(new DriveModule[]{}));

        dt.setInput(input);
        dt.setMapper(mapper);
        dt.setPipeline(drivePipeline);

        return dt;
    }

}