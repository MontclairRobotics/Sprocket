package org.montclairrobotics.sprocket.geometry;

public class Unit {
	
	public static final Unit DEFAULT = new Unit();
	
	protected double raw;
	
	protected Unit() {
		raw = 1;
	}
	
	public Unit(double unit, Unit scale) {
		raw = unit * scale.get();
	}
	
	public double get() {
		return raw;
	}
	
	public double get(Unit u) {
		return raw * u.get();
	}
	
	public Unit add(Unit u) {
		return new Unit(raw + u.get(), DEFAULT);
	}
	
	public Unit subtract(Unit u) {
		return new Unit(raw - u.get(), DEFAULT);
	}
	
	public Unit multiply(Unit u) {
		return new Unit(raw * u.get(), DEFAULT);
	}

	public Unit multiply(double d) {
		return new Unit(raw * d, DEFAULT);
	}
	
	public Unit divide(Unit u) {
		return new Unit(raw / u.get(), DEFAULT);
	}
	
}
