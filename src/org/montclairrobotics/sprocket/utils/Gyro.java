package org.montclairrobotics.sprocket.utils;

public interface Gyro {
	public Angle getHeading();
	public double getRate();
	public void reset();
}