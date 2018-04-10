package org.montclairrobotics.sprocket.utils;

public abstract class DoubleInput implements Input<Double> {
	public static final DoubleInput ZERO = new DoubleInput() {
		public Double get() { return 0.0; }
	};
	
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
	
	public DoubleInput abs() {
		return new DoubleInput() {
			public Double get() { return Math.abs(this.get()); }
		};
	}
	
	public static DoubleInput square(Input<Double> i) {
		return pow(i, 2);
	}
	
	public static DoubleInput sqrt(Input<Double> i) {
		return pow(i, 1.0 / 2.0);
	}
	
	public static DoubleInput pow(Input<Double> i, double n) {
		return new DoubleInput() {
			public Double get() { return Math.pow(i.get(), n); }
		};
	}
	
	public static DoubleInput opposite(Input<Double> i) {
		return new DoubleInput() {
			public Double get() { return -1 * i.get(); }
		};
	}
	
	public static DoubleInput abs(Input<Double> i) {
		return new DoubleInput() {
			public Double get() { return Math.abs(i.get()); }
		};
	}
}
