package org.montclairrobotics.sprocket.jrapoport;

public interface Togglable {
	/** Called once, after it is activated. */
	void enable();
	/** Called once, after it is deactivated. */
	void disable();
	
	boolean isEnabled();
}
