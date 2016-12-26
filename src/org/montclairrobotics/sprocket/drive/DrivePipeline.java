package org.montclairrobotics.sprocket.drive;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DrivePipeline {

    private List<DrivePipelineStep> steps;

    public DrivePipeline(DrivePipelineStep... stepList) {
        steps = Arrays.asList(stepList);
    }

    public DrivePipeline() {
        steps = new ArrayList<>();
    }


    public void setPipeline(List<DrivePipelineStep> steps) {
        this.steps = steps;
    }

    public void run(DriveTrainTarget target) {
        for(DrivePipelineStep step : steps) {
            step.run(target);
        }
    }
}
