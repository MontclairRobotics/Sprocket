package org.montclairrobotics.sprocket.jrapoport;

import java.util.ArrayList;

import org.montclairrobotics.sprocket.modes.*;

public final class Supuroketto {
	public enum Mode { AUTO, TELEOP, TEST, DISABLED };
	
	public static FRCRobot robot;
	
	/* Entities */
	
	public static final ArrayList<Entity> entities = new ArrayList<Entity>();
	
	/* Last Update */
	
	private static long lastUpdate = currentTimeMillis();
	
	/* Robot Modes */
	
	private static RobotMode currentMode;
	
	private static AutoMode auto;
	private static TeleopMode teleop;
	private static TestMode test;
	private static DisabledMode disabled;
	
	/** @return the last update time. */
	public static long lastUpdateMillis() {
		return lastUpdate;
	}
	
	/** @return the current time (in milliseconds). */
	public static long currentTimeMillis() {
		return System.currentTimeMillis();
	}
	
	/** @return the loop duration (in milliseconds). */
	public static long loopTimeMillis() {
		return currentTimeMillis() - lastUpdate;
	}
	
	/** @return the loop duration (in seconds). */
	public static double loopTimeSec() {
		return loopTimeMillis() / 1000;
	}
	
	/** @return the refresh rate of the robot (in loops per second). */
	public static double getFPS() {
		return 1 / loopTimeSec();
	}

	public static boolean add(Entity entity) {
		return entities.add(entity);
	}
	
	public static Entity get(String name) {
		for (Entity e : entities) {
			if (e.toString().equals(name))
				return e;
		}
		
		return null;
	}
	
	public static void setAutoMode(AutoMode a) {
		if (auto != null && !auto.isComplete())
			auto.stop();
		
		auto = a;
	}
	
	public static void setTeleopMode(TeleopMode t) {
		if (teleop != null && !teleop.isComplete())
			teleop.stop();
		
		teleop = t;
	}
	
	public static void setTestMode(TestMode t) {
		if (test != null && !test.isComplete())
			test.stop();
		
		test = t;
	}
	
	public static void setDisabledMode(DisabledMode d) {
		if (disabled != null && !disabled.isComplete())
			disabled.stop();
		
		disabled = d;
	}
	
	protected static void init() {
		currentMode.start();
	}
	
	protected static void periodic() {
		lastUpdate = System.currentTimeMillis();
		
		for (Entity e : entities) {
			e.update();
		}
		
		currentMode.update();
	}
	
	protected static void switchModes() {
		if (currentMode != null)
			currentMode.stop();
		
		switch (robot.currentMode()) {
		case AUTO: currentMode = auto;
		case TELEOP: currentMode = teleop;
		case TEST: currentMode = test;
		case DISABLED: currentMode = disabled;
		}
	}
	
	public void stopAll() {
		auto.stop();
		teleop.stop();
		test.stop();
		disabled.stop();
	}
}
