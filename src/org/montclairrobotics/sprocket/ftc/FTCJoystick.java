package org.montclairrobotics.sprocket.ftc;

import org.montclairrobotics.sprocket.control.Joystick;
import org.montclairrobotics.sprocket.frc.SprocketGlobalVars;
import org.montclairrobotics.sprocket.geometry.Angle;
import org.montclairrobotics.sprocket.geometry.Distance;
import org.montclairrobotics.sprocket.geometry.Vector;
import org.montclairrobotics.sprocket.geometry.XY;
import org.montclairrobotics.sprocket.utils.Input;

public class FTCJoystick implements Joystick{

	public static enum Player{P1,P2};
	public static enum Stick{LEFT,RIGHT};
	
	private Input<Double> x,y;
	
	public FTCJoystick(Player player,Stick stick)
	{
		Gamepad g;
		
		if(player==Player.P1)
			g=FTCGlobalVars.gamepad1;
		else
			g=FTCGlobalVars.gamepad2;
		
		if(stick==Stick.LEFT)
		{
			x=new Input<Double>(){
				public Double getRaw() {
					return g.left_stick_x;
				}
			};
			y=new Input<Double>(){
				public Double getRaw() {
					return g.left_stick_y;
				}
			};
		}
		else
		{
			x=new Input<Double>(){
				public Double getRaw() {
					return g.right_stick_x;
				}
			};
			y=new Input<Double>(){
				public Double getRaw() {
					return g.right_stick_y;
				}
			};
		}
	}
	public double getX() {
		return x.get();
	}
	public double getY() {
		return y.get();
	}
	public double getMag() {
		return getVector().getMag().getMeters();
	}
	public Angle getAngle() {
		return getVector().getAngle();
	}
	public Vector getVector()
	{
		return new XY(new Distance(x.get(),Distance.M),new Distance(y.get(),Distance.M));
	}
	public Input<Vector> getInput()
	{
		return new Input<Vector>()
		{
			public Vector getRaw() {
				return getVector();
			}
		};
	}
}
