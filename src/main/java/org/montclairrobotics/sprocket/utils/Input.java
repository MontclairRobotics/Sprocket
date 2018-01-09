package org.montclairrobotics.sprocket.utils;

public interface Input<T> {

    public T get();
    
    public static Input<Double> neg(Input<Double> a)
    {
    	return new Input<Double>(){

			@Override
			public Double get() {
				return -a.get();
			}};
    }
}
