package org.montclairrobotics.sprocket.drive.utils;

import org.montclairrobotics.sprocket.geometry.Angle;
import org.montclairrobotics.sprocket.geometry.Vector;
import org.montclairrobotics.sprocket.utils.Input;

public class MyState {
	public static Input<Vector>
		location,
		velocity;
	public static Input<Angle>
		rotation,
		rotationSpeed;
	public static Vector getAbsLocation() {
		if(location==null)return Vector.ZERO;
		return location.get();
	}
	public static Vector getVelocity() {
		if(velocity==null)return Vector.ZERO;
		return velocity.get();
	}
	public static Angle getAbsRotation() {
		if(rotation==null)return Angle.ZERO;
		return rotation.get();
	}
	public static Angle getRotationSpeed() {
		if(rotationSpeed==null)return Angle.ZERO;
		return rotationSpeed.get();
	}
	
	private Vector zeroLoc=Vector.ZERO;
	private Angle zeroAngle=Angle.ZERO;
	
	public MyState()
	{
		zeroLoc=getAbsLocation();
		zeroAngle=getAbsRotation();
	}
	public MyState(Vector loc,Angle rot)
	{
		zeroLoc=loc.subtract(getAbsLocation());
		zeroAngle=rot.subtract(getAbsRotation());
	}
	public Vector getRelLocation()
	{
		return getAbsLocation().subtract(zeroLoc);
	}
	public Angle getRelRotation()
	{
		return getAbsRotation().subtract(zeroAngle);
	}
}
