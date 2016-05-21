package org.montclairrobotics.sprocket.utils;

public enum TimeUnit implements Unit {
	sec, min, hr;
	
	public double getConversionFactor() {
		switch (this) {
		case sec: return 1;
		case min: return 60;
		case hr:  return 3600;
		default:  return 0;
		}
	}
}
