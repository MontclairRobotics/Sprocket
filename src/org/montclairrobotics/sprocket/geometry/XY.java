package org.montclairrobotics.sprocket.geometry;

public class XY<T extends Unit> extends Vector{

	private T x,y;
	
	public XY(T x,T y)
	{
		this.x=x;
		this.y=y;
	}
	
	public Speed getMag() {
		return new Speed(Math.sqrt(x.multiply(x).get() + y.multiply(y).get()), Speed.MS);
	}

	public Angle getAngle() {
		return new Radian(Math.atan2(x.get(),y.get()));
	}

	public T getX() {
		return x;
	}

	public T getY() {
		return y;
	}

}
