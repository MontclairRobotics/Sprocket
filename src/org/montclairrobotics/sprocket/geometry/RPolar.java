package org.montclairrobotics.sprocket.geometry;

public class RPolar extends Polar implements RVector
{

	public RPolar(Distance mag,Angle ang)
	{
		this(mag.get(),ang);
	}
	public RPolar(double mag,Angle ang) {
        super(mag,ang);
    }
}
