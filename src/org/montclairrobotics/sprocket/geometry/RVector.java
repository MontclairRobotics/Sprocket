package org.montclairrobotics.sprocket.geometry;
//A boring old wrapper class
public interface RVector extends Vector{
	public static RVector ZERO=new RXY(0,0);
	public static RVector ToReal(Vector v)
	{
		return new RXY(v.getX(),v.getY());
	}
}
