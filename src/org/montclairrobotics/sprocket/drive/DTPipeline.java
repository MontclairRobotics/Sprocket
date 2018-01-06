package org.montclairrobotics.sprocket.drive;

import java.util.ArrayList;
import java.util.Arrays;

import org.montclairrobotics.sprocket.pipeline.Pipeline;
import org.montclairrobotics.sprocket.pipeline.Step;

/**
 * The DTPipeline takes in different steps
 * and then runs through them taking the output
 * of one step and passing it into the input of the next step.
 * This is done for drive train inputs to add limiters and corrections
 * from the input to the drive train.
 * DTPipeline is a wrapper around pipeline to make adding DTSteps easier
 */
public class DTPipeline extends Pipeline<DTTarget>{
	
	/**
	 * @param steps steps to be added to the pipeline
	 */
	public DTPipeline(Step<DTTarget>... steps) {
		super(new ArrayList<Step<DTTarget>>(Arrays.asList(steps)));
	}
	
	/**
	 * @param steps to be added to the pipeline
	 */
	public DTPipeline(ArrayList<Step<DTTarget>> steps) {
		super(steps);
	}

}
