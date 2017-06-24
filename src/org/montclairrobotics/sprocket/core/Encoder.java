package org.montclairrobotics.sprocket.core;


public interface Encoder {
	public double getSpeed();
	public double getDistance();
	public void reset();
}
