package org.montclairrobotics.sprocket.utils;

public enum MassUnit implements Unit {
	mg, g, kg;
	
	public double getConversionFactor() {
		switch (this) {
		case mg: return 0.001;
		case g:  return 1;
		case kg: return 1000;
		default: return 0;
		}
	}
}
