package org.montclairrobotics.sprocket.utils;

public interface Action {
	public default void onEnable(){}
	public default void enabled(){}
	public default void onDisable(){}
	public default void disabled(){}
}
