package org.montclairrobotics.sprocket.utils;

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
}
