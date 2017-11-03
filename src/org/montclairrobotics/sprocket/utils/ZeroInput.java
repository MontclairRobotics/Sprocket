package org.montclairrobotics.sprocket.utils;

public class ZeroInput implements Input<Double>{
	public static final ZeroInput ZERO_INPUT=new ZeroInput();
	@Override
	public Double get() {
		// TODO Auto-generated method stub
		return 0.0;
	}

}
