package org.montclairrobotics.sprocket.geometry;

public class XY extends Vector{

	private Unit x,y;
	
	public XY(Unit x, Unit y)
	{
		x.isSameType(y);
		this.x=x;
		this.y=y;
	}

	public XY(double x, double y, Unit u) {
		this.x = new Unit(x, u);
		this.y = new Unit(y, u);
	}

	public Speed getMag() {
		return new Speed(Math.sqrt(x.multiply(x).get() + y.multiply(y).get()), Speed.MS);
	}

	public Angle getAngle() {
		return new Radian(Math.atan2(x.get(),y.get()));
	}

	public Unit getX() {
		return x;
	}

	public Unit getY() {
		return y;
	}

}
