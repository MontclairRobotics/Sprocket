package org.montclairrobotics.sprocket.jrapoport;

public final class Supuroketto {
	/** The last update time. */
	private static long lastUpdate = 0;
	
	/** @return the current time (in milliseconds). */
	public static long currentTimeMillis() {
		return System.currentTimeMillis();
	}
	
	/** @return the loop duration (in milliseconds). */
	public static long loopTimeMillis() {
		return currentTimeMillis() - lastUpdate;
	}
	
	/** @return the refresh rate of the robot (in loops per second). */
	public static double getFPS() {
		return 1 / (Supuroketto.loopTimeMillis() / 1000.0);
	}
}
