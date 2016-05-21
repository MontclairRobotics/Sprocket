package org.montclairrobotics.sprocket.utils;

public interface Unit {
	public double getConversionFactor(); // Factor of unit relative to default unit.
	public static <U extends Unit> double convertQuantity(double q, U uInit, U uFinal) {
		return q * uInit.getConversionFactor() / uFinal.getConversionFactor();
	}
}