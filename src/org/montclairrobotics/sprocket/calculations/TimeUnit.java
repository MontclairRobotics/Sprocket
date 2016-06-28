package org.montclairrobotics.sprocket.calculations;


public enum TimeUnit implements Unit {
	ms, s, min, hr;
	
	public double getConversionFactor() {
		switch (this) {
		case ms:  return 0.001;
		case s:   return 1; // Default unit
		case min: return 60;
		case hr:  return 3600;
		default:  return 0;
		}
	}
	
	public double convertQuantity(double q, Unit newUnit) {
		if (newUnit.getClass() == TimeUnit.class) {
			return q * this.getConversionFactor() / newUnit.getConversionFactor();
		} else {
			return (Double) null;
		}
	}
}
