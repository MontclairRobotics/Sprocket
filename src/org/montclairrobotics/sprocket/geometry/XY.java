package org.montclairrobotics.sprocket.geometry;

public class XY extends Vector{

	private Speed x,y;
	
	public XY(Speed x,Speed y)
	{
		this.x=x;
		this.y=y;
	}
	
	public Speed getMag() {
		return new Speed(Math.sqrt(x.times(x).get() + y.times(y).get()), Speed.MS);
	}

	public Angle getAngle() {
		return new Radian(Math.atan2(x.get(),y.get()));
	}

	public Speed getX() {
		return x;
	}

	public Speed getY() {
		return y;
	}

}
