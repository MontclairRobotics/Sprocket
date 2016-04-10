package org.montclairrobotics.sprocket.drive;

import org.montclairrobotics.sprocket.control.Control;
import org.montclairrobotics.sprocket.drive.DriveMotor.M_TYPE;
import org.montclairrobotics.sprocket.utils.Dashboard;
import org.montclairrobotics.sprocket.utils.PID;

public class Rover extends DriveTrain{
	
	private double leftSpd,rightSpd,tgtSpeed;
	private boolean straight;
	
	public Rover(int[][] ports, M_TYPE motorType, int[][][] encoders, PID encPID) {
		super(ports, motorType, encoders, encPID);
	}

	public void setSpeedTank(double lSpd,double rSpd)
	{
		leftSpd = lSpd;
		rightSpd = rSpd;
		tgtSpeed=(lSpd+rSpd)/2;
	}
	public void setSpeedXY(double x, double y)
	{   
		x*=.75;
		if (!forward) {
			y = y*-1;
		}
        tgtSpeed=y;
		if (Math.abs(x)<Control.DEAD_ZONE)
		{
			if(Math.abs(y)<Control.DEAD_ZONE)
			{
				leftSpd=0;
				rightSpd=0;
			}
			else
			{
				leftSpd=y;
				rightSpd=y;
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
	        leftSpd=(y+x)/max;
	        rightSpd=(y-x)/max;
	        straight=false;
		}
		Dashboard.putNumber("leftSpeed", leftSpd,true);
		Dashboard.putNumber("rightSpeed", rightSpd,true);
	}

	public void correct(double correction)
	{
		if (tgtSpeed > 0){
			if(1+correction > 0)
				rightSpd=tgtSpeed*(1/(1+correction));
			else
				rightSpd=0;
			leftSpd=tgtSpeed*(1+correction);
		}
		else {
			if(1+correction!=0)
				leftSpd=tgtSpeed*(1/(1+correction));
			else
				leftSpd=0;
			rightSpd=tgtSpeed*(1+correction);
		}
	}
	
	public boolean isStraight()
	{
		return straight;
	}
	
	public void update() {
		for(DriveMotor wheel:wheels[0])
		{
			wheel.setSpeed(leftSpd);
		}
		for(DriveMotor wheel:wheels[1])
		{
			wheel.setSpeed(rightSpd);
		}
	}
}
