package org.montclairrobotics.sprocket.jrapoport;

public interface Togglable {
	void enable();
	void disable();
	
	boolean isEnabled();
}
