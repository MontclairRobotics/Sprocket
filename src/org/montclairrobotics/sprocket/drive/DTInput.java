package org.montclairrobotics.sprocket.drive;

import org.montclairrobotics.sprocket.geometry.Angle;
import org.montclairrobotics.sprocket.geometry.Distance;
import org.montclairrobotics.sprocket.geometry.Vector;

public interface DTInput {
	
	public Vector getDir();
	public Angle getTurn();
	
	public default void setMaxSpeed(Distance speed){}
	public default void setMaxTurn(Angle turn){}
}
