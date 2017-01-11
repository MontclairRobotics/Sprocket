package org.montclairrobotics.sprocket.pipeline;

public interface Step<T> {
	public T get(T in);
}
