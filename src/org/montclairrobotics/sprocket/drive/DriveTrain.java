package org.montclairrobotics.sprocket.drive;

import org.montclairrobotics.sprocket.control.Control;
import org.montclairrobotics.sprocket.drive.DriveMotor.M_TYPE;
import org.montclairrobotics.sprocket.utils.Dashboard;
import org.montclairrobotics.sprocket.utils.PID;
import org.montclairrobotics.sprocket.utils.Updatable;
import org.montclairrobotics.sprocket.utils.Update;

/*
 * Please note this is for a traditional chasis with wheels on one side and wheels on another
 */

public class DriveTrain implements Updatable{
	//static constants
	public static final double DEFAULT_P=0.03,DEFAULT_I=0,DEFAULT_D=0.3;
	private static final int TIME_TO_RESET_AUTOLOCK=5;//iterations until lock is deactivated
	private static final M_TYPE defaultType = M_TYPE.TALON;
	
	//constants
	private DriveMotor[] leftWheels, rightWheels;
	private PID pid;
	
	//variables
	double tgtSpeed,leftSpd, rightSpd;
	private boolean forward = true;
	public boolean lock;
	private int loopsSinceLastLock=TIME_TO_RESET_AUTOLOCK;
	public static boolean shutdown = false;
	
	/*
	 * leftPorts = the ports for the left motors
	 * rightPorts = the ports for the right motors
	 * encoders = two dimensional array; collection of 2 ports per encoder. 
	 * 				if there is only one encoder or less than the entire length, 
	 * 				the remainder will be filled with encoder[0]
	 * motorType = the type of the motor
	 * p,i,d	= the p,i, and d values for the PID controller.
	 */
	
	public DriveTrain(int[]leftPorts,int[]rightPorts){
		this(leftPorts,rightPorts,defaultType);
	}
	public DriveTrain(int[]leftPorts,int[]rightPorts,M_TYPE motorType){
		this(leftPorts,rightPorts,motorType,null);
	}
	public DriveTrain(int[]leftPorts,int[]rightPorts,PID drivePID){
		this(leftPorts,rightPorts,defaultType,drivePID);
	}
	public DriveTrain(int[]leftPorts,int[]rightPorts,M_TYPE motorType,PID drivePID){
		this(leftPorts,rightPorts,motorType,drivePID,null,null);
	}
	public DriveTrain(int[]leftPorts,int[]rightPorts,int[][]leftEncoders,int[][]rightEncoders){
		this(leftPorts,rightPorts,defaultType,null,leftEncoders,rightEncoders);
	}
	public DriveTrain(int[]leftPorts,int[]rightPorts,PID drivePID,int[][]leftEncoders,int[][]rightEncoders){
		this(leftPorts,rightPorts,defaultType,drivePID,leftEncoders,rightEncoders);
	}
	public DriveTrain(int[]leftPorts,int[]rightPorts,M_TYPE motorType,PID drivePID,int[][]leftEncoders,int[][]rightEncoders){
		this(leftPorts,rightPorts,motorType,drivePID,leftEncoders,rightEncoders,null);
	}
	public DriveTrain(int[]leftPorts,int[]rightPorts,int[][]leftEncoders,int[][]rightEncoders,PID encPID){
		this(leftPorts,rightPorts,defaultType,null,leftEncoders,rightEncoders,encPID);
	}
	public DriveTrain(int[]leftPorts,int[]rightPorts,M_TYPE motorType,int[][]leftEncoders,int[][]rightEncoders,PID encPID){
		this(leftPorts,rightPorts,motorType,null,leftEncoders,rightEncoders,encPID);
	}
	public DriveTrain(int[]leftPorts,int[]rightPorts,PID drivePID,int[][]leftEncoders,int[][]rightEncoders,PID encPID){
		this(leftPorts,rightPorts,defaultType,drivePID,leftEncoders,rightEncoders,encPID);
	}
	
	
	public DriveTrain(int[]leftPorts,int[]rightPorts,M_TYPE motorType,PID drivePID,int[][]leftEncoders,int[][]rightEncoders,PID encPID)
	{
		leftWheels = new DriveMotor[leftPorts.length];
		rightWheels = new DriveMotor[rightPorts.length];
		
		for(int i=0; i<leftPorts.length; i++)
		{
			if(leftEncoders==null)
				leftWheels [i]= new DriveMotor(leftPorts[i],motorType);
			else
				leftWheels [i]=new DriveMotor(leftPorts[i],motorType,leftEncoders[(i<leftEncoders.length)?i:0],encPID);
		}
		for(int i=0;i<rightPorts.length; i++)
		{
			if(rightEncoders==null)
				rightWheels [i]= new DriveMotor(rightPorts[i],motorType);
			else
				rightWheels [i]=new DriveMotor(rightPorts[i],motorType,rightEncoders[(i<rightEncoders.length)?i:0],encPID);
		}
		for(DriveMotor motor : rightWheels) {
			motor.setInverted(true);
		}
		
		pid=drivePID;
		Update.add(this);
	}
	
	
	public double getAvgEncoderClicks() {
		double sum = 0;
		
		for (int i = 0; i < leftWheels.length;i++)
		{
			sum += leftWheels[i].getDistance();
		}
		for(int i=0;i<rightWheels.length;i++)
		{
			sum+=rightWheels[i].getDistance();
		}
		
		return sum / (leftWheels.length+rightWheels.length);
	}
	
	public void setSpeedTank(double lSpd,double rSpd)
	{
		leftSpd = lSpd;
		rightSpd = rSpd;
		tgtSpeed=(lSpd+rSpd)/2;
	}
	
	public void setSpeedXY(double x, double y)
	{
		setSpeedXY(x,y,false);
	}
	public void setSpeedXY(double x,double y,boolean hardLock)
	{
		setSpeedXY(x,y,false,hardLock);
	}
	public void setSpeedXY(double x, double y,boolean softLock,boolean hardLock)
	{   
		x*=.75;
		if (!forward) {
			y = y*-1;
		}
		if (Math.abs(x)<Control.DEAD_ZONE)
		{
			if(Math.abs(y)<Control.DEAD_ZONE)
			{
				leftSpd=0;
				rightSpd=0;
				this.lock=false;
				tgtSpeed=0;
			}
			else
			{
				leftSpd=y;
				rightSpd=y;
				this.lock=softLock||hardLock;
				tgtSpeed=y;
			}
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
	        this.lock=hardLock;
	        tgtSpeed=y;
		}
		Dashboard.putNumber("leftSpeed", leftSpd,true);
		Dashboard.putNumber("rightSpeed", rightSpd,true);
	}

	
	private double getCurrentVal()//TODO TODO TODO make this the input value
	{
		return 0.0;
	}
	private void calcLock(boolean lock)
	{
		
		if(lock)
		{
			if(loopsSinceLastLock>=TIME_TO_RESET_AUTOLOCK)
			{
				pid.setTarget(getCurrentVal());
			}
			else
			{
				execLock(pid.getRawOut());
			}
			loopsSinceLastLock=0;
			pid.setCur(getCurrentVal());
			Dashboard.putString("Lock", "ON");
		}
		else
		{
			loopsSinceLastLock++;
			Dashboard.putString("Lock", "OFF");
		}
	}
	
	private void execLock(double correction)
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
		Dashboard.putNumber("Correction", correction,true);
		Dashboard.putNumber("Error", pid.getError(),true);
	}
	public void update()
	{
		calcLock(lock);
		for(int i=0; i<leftWheels.length; i++)
		{
			leftWheels[i].setSpeed(leftSpd);
		}	
		for(int i=0; i<rightWheels.length; i++)
		{
			rightWheels[i].setSpeed(rightSpd);
		};
	}
}
