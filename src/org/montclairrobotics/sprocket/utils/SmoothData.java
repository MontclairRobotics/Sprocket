package org.montclairrobotics.sprocket.utils;

public class SmoothData {
	/** The number of data values. */
	private int length;
	/** An array of data values. */
	private double[] data;
	/** The p index in the array of data. */
	private int i;
	/** ... */
	private double sum;
	
	public SmoothData(int n) {
		this.length = n;
		data = new double[length];
		i = 0;
		sum = 0;
	}

	/**
	 * ...
	 * @param in
	 * @return
	 */
	public double smooth(double in) {
		sum += in;

		if (i < length) {
			data[i] = in;
			i++;

			return sum / i;
		} else {
			sum -= data[i % length];
			data[i % length] = in;
			i++;

			return sum / length;
		}
	}
}
