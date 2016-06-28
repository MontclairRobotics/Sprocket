package org.montclairrobotics.sprocket.calculations;

public abstract class Motion {
	
	public DistanceUnit distanceUnit;
	public TimeUnit timeUnit;
	
	public double initTime;
	public double finalTime;
	
	// DESCRIPTION
	
	public abstract String positionFunctionAsString();
	
	// POSITION
	
	public abstract double positionAtTime(double t);
	
	public double initialPosition() {
		return positionAtTime(initTime);
	}
	
	public double finalPosition() {
		return positionAtTime(finalTime);
	}
	  
	// VELOCITY
	
	public double velocityAtTime(double t) {
		double dsdt = (positionAtTime(t+0.000000001) - positionAtTime(t)) / 0.000000001;
		return Math.round(dsdt * 10000) / 10000;
	}
	  
	public double initialVelocity() {
		return velocityAtTime(initTime);
	}
	
	public double finalVelocity() {
		return velocityAtTime(finalTime);
	}
	
	// ACCELERATION
	
	public double accelerationAtTime(double t) {
		double dvdt = (velocityAtTime(t+0.000000001) - velocityAtTime(t)) / 0.000000001;
		return Math.round(dvdt * 10000) / 10000;
	}
	
	public double initialAcceleration() {
		return accelerationAtTime(initTime);
	}
	
	public double finalAcceleration() {
		return accelerationAtTime(finalTime);
	}
	
}
