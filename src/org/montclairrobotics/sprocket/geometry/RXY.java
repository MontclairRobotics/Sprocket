package org.montclairrobotics.sprocket.geometry;

public class RXY extends XY implements RVector{
	public RXY(double x,double y,Distance scale)
	{
		this(x*scale.get(),y*scale.get());
	}
	public RXY(double x, double y) {
        super(x,y);
    }
}
