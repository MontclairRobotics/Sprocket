package org.montclairrobotics.sprocket.pipeline;

import java.util.ArrayList;

import org.montclairrobotics.sprocket.utils.Input;

public class Pipeline <T> {

	private ArrayList<Step<T>> steps;
	
	public Pipeline(ArrayList<Step<T>> steps) {
		this.steps = steps;
	}
	
	public ArrayList<Step<T>> getSteps() {
		return steps;
	}
	
	public T get(T res) {
		for(Step<T> step:steps){
			res=step.get(res);

		}
		return res;
	}
}
