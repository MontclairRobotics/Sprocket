package org.montclairrobotics.sprocket.drive.steps;

import org.montclairrobotics.sprocket.drive.DTTarget;
import org.montclairrobotics.sprocket.geometry.Angle;
import org.montclairrobotics.sprocket.geometry.Radians;
import org.montclairrobotics.sprocket.geometry.Vector;
import org.montclairrobotics.sprocket.geometry.XY;
import org.montclairrobotics.sprocket.pipeline.Step;

/**
 * Deadzone is a drivetrain step which sets all inputs below a certain threshold to zero.
 * This step is intended to negate the effects of miniscule joystick inputs when the intent is
 * to send no input to the drivetrain.
 */
public class Deadzone implements Step<DTTarget>{

	private Vector deadZone;
	private Angle turnDeadZone;
	
	private boolean relative;


	/**
	 * Creates a default deadzone of 10%.
	 */
	public Deadzone()
	{
		this(.1,.1);
	}

	/**
	 * Creates a deadzone with specified x and y deadzone parameters.
	 * @param x The X deadzone value.
	 * @param y The Y deadzone value.
	 */
	public Deadzone(double x,double y)
	{
		this(x,y,true);
	}

	//TODO: WTF IS RELATIVE
	/**
	 * Creates a deadzone with specified x and y deadzone parameters.
	 * @param x The X deadzone value.
	 * @param y The Y deadzone value.
	 */
	public Deadzone(double x,double y,boolean relative)
	{
		this(new XY(0,y),new Radians(x),relative);
	}

	/**
	 * Creates a deadzone with specified x and y deadzones, along with a turn speed deadzone.
	 * @param dz The lateral movement deadzone bounds.
	 * @param turnDZ The turn speed deadzone.
	 */
	public Deadzone(Vector dz,Angle turnDZ)
	{
		this(dz,turnDZ,false);
	}

	//TODO: WTF IS RELATIVE
	/**
	 * Creates a deadzone with specified x and y deadzones, along with a turn speed deadzone.
	 * @param dz The lateral movement deadzone bounds.
	 * @param turnDZ The turn speed deadzone.
	 */
	public Deadzone(Vector dz,Angle turnDZ,boolean relative)
	{
		this.deadZone=dz;
		this.turnDeadZone=turnDZ;
		this.relative=relative;
	}

	/**
	 * @param in The DTTarget from the previous pipeline step.
	 * @return The deadzoned DTTarget.
	 */
	@Override
	public DTTarget get(DTTarget in) {
		if(relative)
		{
			relative=false;
		}
		Vector tgtDir=in.getDirection();
		if(Math.abs(tgtDir.getX())<deadZone.getX())
		{
			tgtDir=new XY(0,tgtDir.getY());
		}
		if(Math.abs(tgtDir.getY())<deadZone.getY())
		{
			tgtDir=new XY(tgtDir.getX(),0);
		}
		Angle tgtTurn=in.getTurn();
		if(Math.abs(tgtTurn.toDegrees())<turnDeadZone.toDegrees())
		{
			tgtTurn=Angle.ZERO;
		}

		DTTarget output=new DTTarget(tgtDir,tgtTurn);
		return output;
	}

}
