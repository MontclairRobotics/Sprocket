package org.montclairrobotics.sprocket.utils;

public enum DistanceUnit implements Unit {
	in, ft, yd, mi,
	mm, cm, m, km;
	
	public double getConversionFactor() {
		switch (this) {
		case in: return 0.0254;
		case ft: return 0.3048;
		case yd: return 0.9144;
		case mi: return 1609.3;
		case mm: return 0.001;
		case cm: return 0.01;
		case m:  return 1;
		case km: return 1000;
		default: return 0;
		}
	}
	
}
