package org.montclairrobotics.sprocket.dataconstructs;

import org.montclairrobotics.sprocket.utils.Utils;

import com.sun.javafx.geom.transform.BaseTransform.Degree;

/**
 * CLOCKWISE IS POSITIVE
 * 
 * 
 * An abstract class to hold an angle
 * Extended by Degree and Radian
 * @author Hymowitz
 * @see Degree
 * @see Radian
 */

public class Angle {
	public enum Unit{DEGREES,RADIANS};
	public static final Unit DEFAULT=Unit.DEGREES;
	public static Angle 
		zero=new Angle(0),
		quarter=new Angle(90,Unit.DEGREES),
		half=new Angle(180,Unit.DEGREES);
	
	private double radians;
	
	public Angle(double angle)
	{
		this(angle,DEFAULT);
	}
	public Angle(double angle,Unit unit)
	{
		if(unit==Unit.DEGREES)
			angle=Math.toRadians(angle);
		this.radians=angle;
	}
	private double mod(boolean mod)
	{
		if(mod)return Utils.mod(radians, -Math.PI, Math.PI);
		return radians;
	}
	public double toRadians()
	{
		return toRadians(true);
	}
	public double toRadians(boolean mod)
	{
		return mod(mod);
	}
	public double toDegrees()
	{
		return toDegrees(true);
	}
	public double toDegrees(boolean mod)
	{
		return Math.toDegrees(mod(mod));
	}
	public Angle add(Angle a)
	{
		return new Angle(toRadians()+a.toRadians(),Unit.RADIANS);
	}
	public Angle subtract(Angle a)
	{
		return new Angle(toRadians()+a.toRadians(),Unit.RADIANS);
	}
	public Angle opposite()
	{
		return new Angle(radians+Math.PI,Unit.RADIANS);
	}
	public Angle negative()
	{
		return new Angle(-radians,Unit.RADIANS);
	}
	public Angle times(double d) {
		return new Angle(radians*d);
	}
	public Angle times(Angle d) {
		return new Angle(radians*d.toRadians());
	}
	public double divide(double d) 
	{
		return radians/d;
	}
	public double divide(Angle d) 
	{
		return radians/d.toRadians();
	}
	public double sin()
	{
		return Math.sin(toRadians());
	}
	public double cos()
	{
		return Math.cos(toRadians());
	}
	public double tan()
	{
		return Math.tan(toRadians());
	}
	public static Angle atan(Distance y,Distance x)
	{
		return new Angle(Math.atan2(y.toMeters(),x.toMeters()));
	}
}
