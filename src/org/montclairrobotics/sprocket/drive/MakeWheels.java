package org.montclairrobotics.sprocket.drive;

import org.montclairrobotics.sprocket.utils.PID;
import org.montclairrobotics.sprocket.utils.Vector;
import org.montclairrobotics.sprocket.utils.XY;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.VictorSP;

public class MakeWheels {
	
	
	public static enum M_TYPE{TALONSRX,VICTORSP,TALON};
	
	public static final Vector leftOffset=new XY(-1,0),rightOffset=new XY(1,0);
	
	public static DriveMotor[] makeStandard(M_TYPE type,int[] leftPorts,int[] rightPorts,
			int[][]leftEncoders,int[][]rightEncoders,PID encPID)
	{
		DriveMotor[] r=new DriveMotor[leftPorts.length+rightPorts.length];
		int i=0;
		for(int j=0;j<leftPorts.length;j++)
		{
			r[i]=new DriveMotor(makeMotor(leftPorts[j],type),makeEncoder(leftEncoders[j]),encPID,leftOffset);
			i++;
		}
		for(int j=0;j<leftPorts.length;j++)
		{
			r[i]=new DriveMotor(makeMotor(rightPorts[j],type),makeEncoder(rightEncoders[j]),encPID,rightOffset);
			i++;
		}
	}
	public static SpeedController makeMotor(int port,M_TYPE type)
	{
		switch(type)
		{
		case TALONSRX:
			return new CANTalon(port);
		case VICTORSP:
			return new VictorSP(port);
		case TALON:
			return new Talon(port);
		default:
			return null;
		}
	}
	public static Encoder makeEncoder(int[] ports)
	{
		if(ports==null)return null;
		return new Encoder(ports[0],ports[1]);
	}
}
