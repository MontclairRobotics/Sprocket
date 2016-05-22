package org.montclairrobotics.sprocket.utils;

public abstract class Motion {
	  
	  public DistanceUnit distanceUnit;
	  public TimeUnit timeUnit;
	  
	  public double initTime;
	  public double finalTime;
	  
	  public abstract double positionAtTime(double t);
	  public abstract double initialPosition();
	  public abstract double finalPosition();
	  
	  public abstract double velocityAtTime(double t);
	  public abstract double initialVelocity();
	  public abstract double finalVelocity();
	  
	  public abstract double accelerationAtTime(double t);
	  public abstract double initialAcceleration();
	  public abstract double finalAcceleration();
	  
}
