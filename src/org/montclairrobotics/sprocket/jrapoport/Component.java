package org.montclairrobotics.sprocket.jrapoport;

public interface Component extends Updatable {
	String getName();
	
	@Override
	void update();
}
