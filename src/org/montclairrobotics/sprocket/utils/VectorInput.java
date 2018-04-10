package org.montclairrobotics.sprocket.utils;

import org.montclairrobotics.sprocket.geometry.Vector;

public abstract class VectorInput implements Input<Vector> {
	public static final VectorInput ZERO = new VectorInput() {
		public Vector get() { return Vector.ZERO; }
	};
	
	public double getX() {
		return this.get().getX();
	}
	
	public double getY() {
		return this.get().getY();
	}
	
	public VectorInput square() {
		return this.pow(2);
	}
	
	public VectorInput sqrt() {
		return this.pow(1.0 / 2.0);
	}
	
	public VectorInput pow(double n) {
		return new VectorInput() {
			public Vector get() { return this.get().setMag(Math.pow(get().getMagnitude(), n)); }
		};
	}
	
	public VectorInput opposite() {
		return new VectorInput() {
			public Vector get() { return this.get().scale(-1); }
		};
	}
	
	public VectorInput abs() {
		return new VectorInput() {
			public Vector get() { return Vector.xy(Math.abs(this.get().getX()), Math.abs(this.get().getY())); }
		};
	}
	
	public static VectorInput square(Input<Vector> i) {
		return pow(i, 2);
	}
	
	public static VectorInput sqrt(Input<Vector> i) {
		return pow(i, 1.0 / 2.0);
	}
	
	public static VectorInput pow(Input<Vector> i, double n) {
		return new VectorInput() {
			public Vector get() { return Vector.xy(Math.pow(i.get().getX(), n), Math.pow(i.get().getY(), n)); }
		};
	}
	
	public static VectorInput opposite(Input<Vector> i) {
		return new VectorInput() {
			public Vector get() { return Vector.xy(i.get().getX(), i.get().getY()).scale(-1); }
		};
	}
	
	public static VectorInput abs(Input<Vector> i) {
		return new VectorInput() {
			public Vector get() { return Vector.xy(Math.abs(i.get().getX()), Math.abs(i.get().getY())); }
		};
	}
}
