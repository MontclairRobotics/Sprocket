package org.montclairrobotics.sprocket.utils;

public class Calc {
	
	public static double[] undefined() {
		return null;
	}
	
	public static double[] noSolutions() {
		return new double[] {};
	}
	
	// 0 = ax^2+bx+c
	public static double[] quadraticFormula(double a, double b, double c) {
		if (a != 0) {
			return undefined();
		} else if (Math.pow(b, 2) - 4*a*c < 0) {
			return noSolutions();
		} else {
			return new double[] {
				(-b + Math.sqrt(Math.pow(b, 2) - 4*a*c)) / (2*a),
				(-b - Math.sqrt(Math.pow(b, 2) - 4*a*c)) / (2*a),
			};
		}
	}
	
	// m = (y2 - y1)/(x2 - x1)
	public static double averageRateOfChange(double y2, double y1, double x2, double x1) {
		if (x2 != x1) {
			return (y2 - y1)/(x2 - x1);
		} else {
			return undefined()[0];
		}
	}
	
}
