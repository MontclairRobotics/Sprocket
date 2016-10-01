package org.montclairrobotics.sprocket.utils;

public class Quantity<U : Unit> {
	public double magnitude;
	public U unit;
	
	new Quantity(double mag, U unit) {
		this.magnitude = mag;
		this.unit = unit;
	}
	
	public void convertTo(U newUnit) {
		magnitude = q * unit.getConversionFactor() / newUnit.getConversionFactor();
		unit = newUnit;
	}
}
