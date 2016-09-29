package org.montclairrobotics.sprocket.geometry;

public class XY extends Vector{

	private Distance x,y;
	
	public XY(Distance x,Distance y)
	{
		this.x=x;
		this.y=y;
	}
	
	public Distance getMag() {
		return new Distance(Math.sqrt(x.times(x).getMeters()+y.times(y).getMeters()),Distance.M);
	}

	public Angle getAngle() {
		return new Radian(Math.atan2(x.getMeters(),y.getMeters()));
	}

	public Distance getX() {
		return x;
	}

	public Distance getY() {
		return y;
	}

}
