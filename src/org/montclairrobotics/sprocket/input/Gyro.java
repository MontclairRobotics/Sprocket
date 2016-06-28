package org.montclairrobotics.sprocket.input;

import org.montclairrobotics.sprocket.dataconstructs.Angle;

public interface Gyro {
	public Angle getHeading();
	public double getRateRotation();
	public double getX();
	public double getY();
	public double getZ();
	public double getXRate();
	public double getYRate();
	public double getZRate();
	public void reset();
}
