package org.montclairrobotics.sprocket.utils;

public abstract class DoubleInput implements Input<Double> {
	public static final DoubleInput ZERO = new DoubleInput() { public Double get() { return 0.0; } };
	
	public DoubleInput square() {
		return this.pow(2);
	}
	
	public DoubleInput sqrt() {
		return this.pow(1.0 / 2.0);
	}
	
	public DoubleInput pow(double n) {
		return new DoubleInput() {
			public Double get() { return Math.pow(this.get(), n); }
		};
	}
	
	public DoubleInput opposite() {
		return new DoubleInput() {
			public Double get() { return -1 * this.get(); }
		};
	}
}
