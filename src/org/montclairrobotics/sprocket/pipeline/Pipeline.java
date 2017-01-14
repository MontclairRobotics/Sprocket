package org.montclairrobotics.sprocket.pipeline;

import java.util.ArrayList;

import org.montclairrobotics.sprocket.utils.Input;

public class Pipeline <T> implements Input<T>{

	private ArrayList<Step<T>> steps;
	private Input<T> init;
	
	public Pipeline(Input<T> init,ArrayList<Step<T>> steps)
	{
		this.steps=steps;
		this.init=init;
	}

	public T get()
	{
		return get(init.get());
	}
	
	public T get(T res)
	{
		for(Step<T> step:steps)
		{
			res=step.get(res);
		}
		return res;
	}
}
