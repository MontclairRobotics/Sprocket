package org.montclairrobotics.sprocket.utils;

public class ParametricMotion extends Motion {

	private LinearMotion x;
	private LinearMotion y;
	private FallingBodyMotion z;

	public ParametricMotion(LinearMotion x, LinearMotion y, FallingBodyMotion z, DistanceUnit s, TimeUnit t) {
		this.x = x;
		this.y = y;
		this.z = z;
		
		this.distanceUnit = s;
		this.timeUnit = t;
		
		this.initTime = 0;
		this.finalTime = Double.MAX_VALUE;
	}
	
	public ParametricMotion(LinearMotion x, LinearMotion y, FallingBodyMotion z, DistanceUnit s, TimeUnit t, double t0, double tf) {
		this.x = x;
		this.y = y;
		this.z = z;
		
		this.distanceUnit = s;
		this.timeUnit = t;
		
		this.initTime = t0;
		this.finalTime = tf;
	}
	
	public String positionFunctionAsString() {
		return "X: " + x.positionFunctionAsString() + ", Y: " + y.positionFunctionAsString() + ", Z: " + z.positionFunctionAsString();
	}
	
	// For ParametricMotion, all position, velocity, and acceleration calculations are scalar quantities relative to the origin.
	public double positionAtTime(double t) {
		return Math.sqrt(Math.pow(x.positionAtTime(t), 2) + Math.pow(y.positionAtTime(t), 2) + Math.pow(z.positionAtTime(t), 2));
	}
	
	public LinearMotion getX() {
		return x;
	}
	
	public LinearMotion getY() {
		return y;
	}
	
	public FallingBodyMotion getZ() {
		return z;
	}
	
	public void setX(LinearMotion newX) {
		x = newX;
	}
	
	public void setY(LinearMotion newY) {
		y = newY;
	}
	
	public void setZ(FallingBodyMotion newZ) {
		z = newZ;
	}
	
	public XY getCartesianFloorVectorAtTime(double t) {
		return new XY(x.positionAtTime(t), z.positionAtTime(t));
	}
	
	public Polar getPolarFloorVectorAtTime(double t) {
		XY cartesian = new XY(x.positionAtTime(t), z.positionAtTime(t));
		return new Polar(cartesian.getMag(), cartesian.getAngle());
	}

}
