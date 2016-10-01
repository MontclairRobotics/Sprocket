package org.montclairrobotics.sprocket.utils;

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
