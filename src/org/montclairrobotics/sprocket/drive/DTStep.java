package org.montclairrobotics.sprocket.drive;

import org.montclairrobotics.sprocket.pipeline.Step;

/**
 * DTSteps can be added to the pipeline to manipulate
 * the instructions to the drive train. An example of
 * a DTStep would be gyro lock, where the instructions
 * to the drive train are not the same as the inputs
 * because they have been manipulated by the pipeline
 */
public interface DTStep extends Step<DTTarget>{
}
