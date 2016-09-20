package org.montclairrobotics.sprocket.geometry;

public class XY extends Vector{

	private double x,y;
	private double mag;
	private Angle angle;
	private boolean calcMag,calcAngle;
	
	public XY(double x,double y)
	{
		this.x=x;
		this.y=y;
	}
	
	public double getMag() {
		if(!calcMag)
		{
			calcMag=true;
			mag=Math.sqrt(x*x+y*y);
		}
		return mag;
	}

	public Angle getAngle() {
		if(!calcAngle)
		{
			calcAngle=true;
			angle= new Radian(Math.atan2(x,y));
		}
		return angle;
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

}
