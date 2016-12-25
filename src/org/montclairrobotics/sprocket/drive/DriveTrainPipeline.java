package org.montclairrobotics.sprocket.drive;

import java.util.ArrayList;
import java.util.Arrays;

public class DriveTrainPipeline {

    private ArrayList<DriveTrainStep> steps;

    public DriveTrainPipeline(DriveTrainStep... stepList) {
        this.steps = new ArrayList<>(stepList.length);
        steps.addAll(Arrays.asList(stepList));
    }

    public DriveTrainPipeline() {
        this.steps = new ArrayList<>();
    }


    public void setPipeline(DriveTrainStep... stepList) {
        this.steps = new ArrayList<>(stepList.length);
        steps.addAll(Arrays.asList(stepList));
    }

    public void run(DriveTrainInput input) {
        for(DriveTrainStep step : steps) {
            step.run(input);
        }
    }
}
