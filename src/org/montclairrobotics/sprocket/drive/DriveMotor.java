package org.montclairrobotics.sprocket.drive;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.CANTalon.TalonControlMode;
import edu.wpi.first.wpilibj.Encoder;
//import edu.wpi.first.wpilibj.TalonSRX;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.VictorSP;

public class DriveMotor {
	
	private int motorPort, encoderPort1, encoderPort2;
	private double speed;
	
	private Encoder encoder;
	private SpeedController motor;
	
	private boolean encoders;
	
	private static boolean shutdown=false;
	private static char defaultType = 't'; //s FOR TALONSRX, v FOR VICTORSP, t for Talon XYZ
	//Change values in Map for motor ports when switching modes
	
	public static final int ROT_TO_DEGREES = 360;
	public static double PID_P = 0.1, PID_I = 0.001, PID_D = 0.0;
	public static final double SCALE_FACTOR = 1;
	
	
	
	/*public DriveMotor(int id) {
		this(Map.MOTOR_PORTS[id]);
	}
	public DriveMotor(int[] ports)
	{
		this(ports,true);
	}
	
	public DriveMotor(int id, boolean encoders)
	{
		this(Map.MOTOR_PORTS[id], encoders);
	}
	public DriveMotor(int[]ports, boolean encoders)
	{
		this(ports,encoders,defaultType);
	}
	
	public DriveMotor(int id,boolean encoders, char motorType)
	{
		this(Map.MOTOR_PORTS[id],encoders,motorType);
	}*/
	
	public DriveMotor(int port)
	{
		this(port,0,0);
	}
	public DriveMotor(int port,int encoderPort0,int encoderPort1)
	{
		encoders=(encoderPort0!=0||encoderPort1!=0);
		motorPort=port;
	}
	
	
	public DriveMotor(int[] ports, boolean encoders,char motorType) {
		this.encoders = encoders;
		//int[][] ports = type == 'd' ? Map.MOTOR_PORTS : Map.SHOOTER_PORTS;
		motorPort = ports[0];
		// What!?
		switch(motorType)
		{
		case 's':
			motor = new CANTalon(motorPort);
			break;
		case 'v':
			motor = new VictorSP(motorPort);
			break;
		case 't':
			motor = new Talon(motorPort);
			break;
		default:
			break;
		}
		
		if(encoders) {
			encoderPort1 = ports[1];
			encoderPort2 = ports[2];
			
			encoder = new Encoder(encoderPort1, encoderPort2);
		}
		if(motor instanceof CANTalon) {
			CANTalon talon = (CANTalon)motor;
			talon.setControlMode(TalonControlMode.PercentVbus.value);
			talon.reset();
			talon.enable();
			talon.enableControl();
		}
	}
	
	public void setSpeed(double spd)
	{
		speed = encoders ? spd * ROT_TO_DEGREES : spd*SCALE_FACTOR; // Control scale constant
	
		if(Robot.debugOutputs) Robot.dashboard.putNumber("Motor "+motorPort, spd);
	}
	
	public void update()
	{
		if(shutdown)
		{
			speed = 0;
			motor.set(0);
			if(motor instanceof CANTalon) ((CANTalon)motor).disableControl();
			return;
		}
		/*if(encoders) {
			controller.setSetpoint(speed);
		} else {*/
			motor.set(speed);
			if(Robot.debugOutputs) Robot.dashboard.putNumber("Speed-" + motorPort, speed);
			if(encoders && Robot.debugOutputs) Robot.dashboard.putNumber("encoder-" + motorPort, encoder.get());
		//}
	}
	
	public boolean isInverted() {
		return motor.getInverted();
	}
	
	public void setInverted(boolean invert) {
		motor.setInverted(invert);
	}
	
	public void toggleInvert() {
		motor.setInverted(!motor.getInverted());
	}
	
	public static void shutdown() {
		shutdown = true;
	}
	
	public double getDistance()
	{
		if(!encoders)return 0;
		return encoder.getDistance();
	}
	
	public double getSpeed() {
		return getRate();
	}
	
	public double getRate() {
		if(!encoders) return 0;
		return encoder.getRate();
	}
	
	public void resetDistance()
	{
		if(!encoders)return;
		encoder.reset();
	}
	
	/*public SpeedController getMotor() {
		return motor;
	}*/
	
}
