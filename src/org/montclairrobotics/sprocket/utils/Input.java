package org.montclairrobotics.sprocket.utils;

/**
 * A seminal part of the Sprocket framework. The Input interface is a generic interface which is
 * meant to define any sort of source of a value which changes. For example, a gyroscope could be a
 * Input\<Angle\> while an encoder could be a Input\<Double\> or an Input\<Distance\>.
 * @param <T> The type of the variable which the input returns.
 */
public interface Input<T> {

	/**
	 * @return The value of the input.
	 */
    public T get();

	/**
	 * This method takes in an Input\<Double\> and returns an input which negates the output of the specified input.
	 * @param a The input to be negated.
	 * @return The negated input.
	 */
	public static Input<Double> neg(Input<Double> a)
    {
    	return new Input<Double>(){
			@Override
			public Double get() {
				return -a.get();
			}};
    }
}
