package org.montclairrobotics.sprocket.drive;

import java.util.ArrayList;
import java.util.Arrays;

import org.montclairrobotics.sprocket.pipeline.Pipeline;
import org.montclairrobotics.sprocket.pipeline.Step;

public class DTPipeline extends Pipeline<DTTarget>{

	public DTPipeline(DTStep... steps) {
		super(new ArrayList<Step<DTTarget>>(Arrays.asList(steps)));
		// TODO Auto-generated constructor stub
	}

}
