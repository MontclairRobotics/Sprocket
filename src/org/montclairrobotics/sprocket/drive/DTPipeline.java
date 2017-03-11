package org.montclairrobotics.sprocket.drive;

import java.util.ArrayList;
import java.util.Arrays;

import org.montclairrobotics.sprocket.pipeline.Pipeline;
import org.montclairrobotics.sprocket.pipeline.Step;

public class DTPipeline extends Pipeline<DTTarget>{

	public DTPipeline(Step<DTTarget>... steps) {
		super(new ArrayList<Step<DTTarget>>(Arrays.asList(steps)));
	}
	
	public DTPipeline(ArrayList<Step<DTTarget>> steps) {
		super(steps);
	}

}
