package org.montclairrobotics.sprocket.pipeline;

import java.util.ArrayList;

import org.montclairrobotics.sprocket.drive.DriveTrainInput;

public class Pipeline <T>{

	private ArrayList<Step<T>> steps;
	private T init;
	
	public Pipeline(T init,ArrayList<Step<T>> steps)
	{
		this.steps=steps;
		this.init=init;
	}
	
	public Pipeline(T init) {
		this.steps=new ArrayList<Step<T>>();
		this.init=init;
	}

	public T get()
	{
		return get(init);
	}
	
	public T get(T res)
	{
		for(Step<T> step:steps)
		{
			res=step.get(init);
		}
		return res;
	}
}
