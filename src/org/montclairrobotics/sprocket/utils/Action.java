package org.montclairrobotics.sprocket.utils;

public interface Action {
	public default void start(){}
	public default void enabled(){}
	public default void stop(){}
	public default void disabled(){}
}
