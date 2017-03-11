package org.montclairrobotics.sprocket.drive;

import java.util.ArrayList;

import org.montclairrobotics.sprocket.pipeline.Pipeline;
import org.montclairrobotics.sprocket.pipeline.Step;

public class ZeroPipeline extends Pipeline<DTTarget>{
	
	public static final ArrayList<Step<DTTarget>> ZERO_STEPS = new ArrayList<Step<DTTarget>>();
	
	public ZeroPipeline() {
		super(ZERO_STEPS);
	}

}
