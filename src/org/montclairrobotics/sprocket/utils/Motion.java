package org.montclairrobotics.sprocket.utils;

class abstract Motion {
  
  pubic static enum Type {
    LINEAR, QUADRATIC, CUBIC;
  }
  
  public abstract DistanceUnit distanceUnit;
  public abstract TimeUnit timeUnit;
  
  public abstract Type typeOfMotion;
  
  public abstract double time0 = 0;
  public abstract double timeF = double.MAX_VALUE;
  
  public abstract double positionAtTime(double t);
  public abstract double positionAtInitialTime();
  public abstract double positionAtFinalTime();
  
  public abstract double velocityAtTime(double t);
  public abstract double velocityAtInitialTime();
  public abstract double velocityAtFinalTime();
  
  public abstract double accelerationAtTime(double t);
  public abstract double accelerationAtInitialTime();
  public abstract double positionAtFinalTime();
  
}
