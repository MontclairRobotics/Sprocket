package org.montclairrobotics.sprocket.drive;

import org.montclairrobotics.sprocket.control.Control;
import org.montclairrobotics.sprocket.drive.DriveMotor.M_TYPE;
import org.montclairrobotics.sprocket.utils.Dashboard;
import org.montclairrobotics.sprocket.utils.PID;

public class Rover extends DriveTrain{
	
	private static final double MIN_SPEED=0.1;
	private static final double MAX_STRAIGHT_ROTATION=0.1;
	private static final double ROT_FACTOR=0.75;
	
	private double leftSpeed,rightSpeed,tgtSpeed;
	private boolean straight;
	
	public Rover(int[][] ports, M_TYPE motorType, int[][][] encoders, PID encPID) {
		super(ports, motorType, encoders, encPID);
	}

	public void driveTank(double lSpd,double rSpd)
	{
		leftSpeed = lSpd;
		rightSpeed = rSpd;
		tgtSpeed=(lSpd+rSpd)/2;
	}
	
	public void drivePolar(double speed,double angle,double rotation)
	{
		drivePolar(((angle>0)?speed:-speed),rotation);
	}
	public void drivePolar(double speed,double rotation)
	{
		driveXY(Math.sin(Math.toRadians(rotation)),(((rotation+90)%360<180)?speed:-speed));
	}
	
	public void driveXY(double x, double y)
	{   
        tgtSpeed=y;
		x*=ROT_FACTOR;
		if (Math.abs(x)<MAX_STRAIGHT_ROTATION)
		{
			if(Math.abs(y)<MIN_SPEED)
			{
				tgtSpeed=0;
				leftSpeed=0;
				rightSpeed=0;
			}
			else
			{
				leftSpeed=y;
				rightSpeed=y;
			}
			straight=true;
		}
		else
		{
			double max;
			if(Math.abs(x)>=Math.abs(y))
			{
				max=1+Math.abs(y/x);
			}
			else
			{
				max=1+Math.abs(x/y);
			}
	        leftSpeed=(y+x)/max;
	        rightSpeed=(y-x)/max;
	        straight=false;
		}
		Dashboard.putNumber("leftSpeed", leftSpeed,true);
		Dashboard.putNumber("rightSpeed", rightSpeed,true);
	}

	public void correct(double correction)
	{
		if (tgtSpeed > 0){
			if(1+correction > 0)
				rightSpeed=tgtSpeed*(1/(1+correction));
			else
				rightSpeed=0;
			leftSpeed=tgtSpeed*(1+correction);
		}
		else {
			if(1+correction!=0)
				leftSpeed=tgtSpeed*(1/(1+correction));
			else
				leftSpeed=0;
			rightSpeed=tgtSpeed*(1+correction);
		}
	}
	
	public boolean isStraight()
	{
		return straight;
	}
	
	public void update() {
		for(DriveMotor wheel:wheels[0])
		{
			wheel.setSpeed(leftSpeed);
		}
		for(DriveMotor wheel:wheels[1])
		{
			wheel.setSpeed(rightSpeed);
		}
	}
}
