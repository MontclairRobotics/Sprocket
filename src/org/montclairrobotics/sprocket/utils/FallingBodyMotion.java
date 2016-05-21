package org.montclairrobotics.sprocket.utils;

public class FallingBodyMotion extends Motion {
	
	public double initVelocity; // Initial vertical velocity
	public double initHeight; // Initial height
	
	public FallingBodyMotion(double initV, double initS) {
		this.initVelocity = initV;
		this.initHeight = initS;
		
		this.distanceUnit = DistanceUnit.m;
		this.timeUnit = TimeUnit.s;
		
		this.typeOfMotion = Type.QUADRATIC;
		
		this.initTime = 0;
		this.finalTime = Double.MAX_VALUE;
	}
	
	public FallingBodyMotion(double initV, double initS, double t0, double tf) {
		this.initVelocity = initV;
		this.initHeight = initS;
		
		this.distanceUnit = DistanceUnit.m;
		this.timeUnit = TimeUnit.s;
		
		this.typeOfMotion = Type.QUADRATIC;
		
		this.initTime = t0;
		this.finalTime = tf;
	}
	
	public FallingBodyMotion(double initV, double initS, DistanceUnit s, TimeUnit t) {
		this.initVelocity = initV;
		this.initHeight = initS;
		
		this.distanceUnit = s;
		this.timeUnit = t;
		
		this.typeOfMotion = Type.QUADRATIC;
		
		this.initTime = 0;
		this.finalTime = Double.MAX_VALUE;
	}
	
	public FallingBodyMotion(double initV, double initS, DistanceUnit s, TimeUnit t, double t0, double tf) {
		this.initVelocity = initV;
		this.initHeight = initS;
		
		this.distanceUnit = s;
		this.timeUnit = t;
		
		this.typeOfMotion = Type.QUADRATIC;
		
		this.initTime = t0;
		this.finalTime = tf;
	}
	
	// Acceleration due to gravity
	public static double smallG(DistanceUnit s, TimeUnit t) {
		final double smallGInMpS2 = 9.806;
		
		return Unit.convertQuantity(smallGInMpS2, DistanceUnit.m, s) / Math.pow(Unit.convertQuantity(1, TimeUnit.s, t), 2);
	}
	
	@Override
	public double positionAtTime(double t) {
		return -0.5*FallingBodyMotion.smallG(distanceUnit, timeUnit)*Math.pow(t, 2) + initVelocity*t + initHeight;
	}

	@Override
	public double initialPosition() {
		return initHeight;
	}

	@Override
	public double finalPosition() {
		return positionAtTime(finalTime);
	}

	@Override
	public double velocityAtTime(double t) {
		return -FallingBodyMotion.smallG(distanceUnit, timeUnit)*t + initVelocity;
	}

	@Override
	public double initialVelocity() {
		return initVelocity;
	}

	@Override
	public double finalVelocity() {
		return velocityAtTime(finalTime);
	}

	@Override
	public double accelerationAtTime(double t) {
		return -FallingBodyMotion.smallG(distanceUnit, timeUnit);
	}

	@Override
	public double initialAcceleration() {
		return accelerationAtTime(initTime);
	}

	@Override
	public double finalAcceleration() {
		return accelerationAtTime(finalTime);
	}
	
	public double timeAtMaximumHeight() {
		return initVelocity / FallingBodyMotion.smallG(distanceUnit, timeUnit);
	}
	
	public double maximumHeight() {
		return positionAtTime(timeAtMaximumHeight());
	}

}
