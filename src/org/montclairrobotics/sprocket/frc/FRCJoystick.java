package org.montclairrobotics.sprocket.frc;

import org.montclairrobotics.sprocket.control.Joystick;
import org.montclairrobotics.sprocket.geometry.Angle;
import org.montclairrobotics.sprocket.geometry.Degree;
import org.montclairrobotics.sprocket.geometry.Distance;
import org.montclairrobotics.sprocket.geometry.Vector;
import org.montclairrobotics.sprocket.geometry.XY;
import org.montclairrobotics.sprocket.utils.Input;

public class FRCJoystick extends edu.wpi.first.wpilibj.Joystick implements Joystick{

	public FRCJoystick(int port) {
		super(port);
	}

	public double getMag() {
		return super.getMagnitude();//It's worth it.
	}

	public Angle getAngle() {
		return new Degree(super.getDirectionDegrees());
	}

	public Vector getVector()
	{
		return new XY(new Distance(getX(),Distance.M),new Distance(getY(),Distance.M));
	}

	public Input<Vector> getInput() {
		return new Input<Vector>(){
			public Vector getRaw() {
				return getVector();
			}
		};
	}
}
