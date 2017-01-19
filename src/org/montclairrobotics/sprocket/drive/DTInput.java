package org.montclairrobotics.sprocket.drive;

import org.montclairrobotics.sprocket.geometry.Angle;
import org.montclairrobotics.sprocket.geometry.Distance;
import org.montclairrobotics.sprocket.geometry.RVector;

public interface DTInput {
	
	public enum Type{PERCENT, SPEED};
	public RVector getDir();
	public Angle getTurn();
	public Type getInputType();
	public void setMaxSpeed(Distance m);
	public void setMaxTurn(Angle a);
}
