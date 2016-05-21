package org.montclairrobotics.sprocket.utils;

public abstract class Motion {
  
  public static enum Type {
    LINEAR, QUADRATIC, CUBIC;
  }
  
  public DistanceUnit distanceUnit;
  public TimeUnit timeUnit;
  
  public Type typeOfMotion;
  
  public double time0;
  public double timeF;
  
  public abstract double positionAtTime(double t);
  public abstract double positionAtInitialTime();
  public abstract double positionAtFinalTime();
  
  public abstract double velocityAtTime(double t);
  public abstract double velocityAtInitialTime();
  public abstract double velocityAtFinalTime();
  
  public abstract double accelerationAtTime(double t);
  public abstract double accelerationAtInitialTime();
  public abstract double positionAtFinalTime1();
  
}
