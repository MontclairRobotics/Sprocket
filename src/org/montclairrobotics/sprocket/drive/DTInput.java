package org.montclairrobotics.sprocket.drive;

import org.montclairrobotics.sprocket.geometry.Angle;
import org.montclairrobotics.sprocket.geometry.Distance;
import org.montclairrobotics.sprocket.geometry.Vector;

public interface DTInput {
	
	public Vector getDir();
	public double getTurn();
	
	public default void setMaxSpeed(Distance speed){}
	public default void setMaxTurn(double maxTurn){}
}
