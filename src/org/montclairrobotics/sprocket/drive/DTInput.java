package org.montclairrobotics.sprocket.drive;

import org.montclairrobotics.sprocket.geometry.Angle;
import org.montclairrobotics.sprocket.geometry.Distance;
import org.montclairrobotics.sprocket.geometry.Vector;

public interface DTInput {
	
	public enum Type{PERCENT, SPEED};
	public Vector getDir();
	public Angle getTurn();
	public Type getInputType();
	
	public boolean isEnabled();
	
	public void setMaxSpeed(Distance d);
	public void setMaxTurn(Angle a);
}
