package org.montclairrobotics.sprocket.drive;

import org.montclairrobotics.sprocket.utils.Angle;
import org.montclairrobotics.sprocket.utils.PID;
import org.montclairrobotics.sprocket.utils.Vector;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.SpeedController;

public class SwerveModule extends DriveMotor{
	
	private SwivelMotor swivelMotor;
	
	public SwerveModule(SpeedController motor,SwivelMotor swivelMotor)
	{
		this(motor,swivelMotor,null,null,null,null);
	}
	public SwerveModule(SpeedController motor,SwivelMotor swivelMotor,Encoder encoder)
	{
		this(motor,swivelMotor,encoder,null,null,null);
	}
	public SwerveModule(SpeedController motor,SwivelMotor swivelMotor,Encoder encoder,PID encPID)
	{
		this(motor,swivelMotor,encoder,encPID,null,null);
	}
	public SwerveModule(SpeedController motor,SwivelMotor swivelMotor,Encoder encoder,PID encPID,Vector offset)
	{
		this(motor,swivelMotor,encoder,encPID,offset,null);
	}
	public SwerveModule(SpeedController motor,SwivelMotor swivelMotor, Encoder encoder, PID encPID,
			Vector offset, Angle forceAngle) {
		super(motor, encoder, encPID, offset, forceAngle);
		// TODO Auto-generated constructor stub
	}
	
	public double calcSpeed(Vector goal)
	{
		swivelMotor.setAngle(goal.getAngle());
		return goal.getMag();
	}
	
}
