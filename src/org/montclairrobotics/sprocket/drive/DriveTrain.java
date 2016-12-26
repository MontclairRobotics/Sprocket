package org.montclairrobotics.sprocket.drive;

import java.util.ArrayList;

public class DriveTrain {

    private ArrayList<DriveModule> driveModules;
    private DrivePipeline pipeline;
    private DriveTrainMapper powerMapper;
    private DriveTrainInput input;


    public DriveTrain(DriveModule[] modules, DriveTrainInput input, DrivePipeline pipeline, DriveTrainMapper mapper) {

    }

}
