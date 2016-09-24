package org.montclairrobotics.sprocket.examples;

import org.montclairrobotics.sprocket.frc.FRCMotor.M_TYPE;

import edu.wpi.first.wpilibj.Solenoid;

public class Valves {
	public static final double SHOOT_SPEED=0.8,INTAKE_SPEED=-0.555;
	public static final boolean
		LIFT_ON=false,
		HALF_ON=true;
	private Solenoid[] LiftValves;
	private Solenoid[] ShooterValves;
	private Solenoid[] HalfValves;
	private Motor[] shootMotors;
	private M_TYPE SHOOTER_MOTOR_TYPE=M_TYPE.TALON;
	
	public static final int[] LIFT_PORTS={
		0
	};
	
	public static final int[] SHOOTER_PORTS={
			1
	};
	
	public static final int[] LIFT_HALF_PORTS={
			2
	};
	
	public static final int[] SHOOTER_MOTOR_PORTS=
		{
				0,5,
		};
	
	public Valves()
	{
		LiftValves=new Solenoid[LIFT_PORTS.length];
        for(int i = 0; i < LIFT_PORTS.length; i++)
        {
        	LiftValves[i] = new Solenoid(LIFT_PORTS[i]);
        }
        ShooterValves=new Solenoid[SHOOTER_PORTS.length];
        for(int i = 0; i < SHOOTER_PORTS.length; i++)
        {
        	
        	ShooterValves[i] = new Solenoid(SHOOTER_PORTS[i]);
        }
        HalfValves=new Solenoid[LIFT_HALF_PORTS.length];
        for(int i = 0; i < LIFT_HALF_PORTS.length; i++)
        {
        	HalfValves[i] = new Solenoid(LIFT_HALF_PORTS[i]);
        }
        shootMotors=new Motor[SHOOTER_MOTOR_PORTS.length];
        for(int i = 0; i < SHOOTER_MOTOR_PORTS.length; i++)
        {
        	shootMotors[i] = new Motor(Motor.makeMotor(SHOOTER_MOTOR_PORTS[i],SHOOTER_MOTOR_TYPE),"SHOOTER :"+SHOOTER_MOTOR_PORTS[i]);
        }
        shootMotors[1].setInverted(true);
		
		raise();
		halfOff();
	}
	
	public void raise()
	{
		resetShooterPush();
		for(Solenoid s : LiftValves) {
			s.set(LIFT_ON);
		}
	}
	
	public void lower()
	{
		resetShooterPush();
		for(Solenoid s : LiftValves) {
			s.set(!LIFT_ON);
		}
		halfOn();
	}
	
	
	public void halfOn()
	{
		resetShooterPush();
		for(Solenoid s:HalfValves)
		{
			s.set(HALF_ON);
		}
	}
	public void halfOff()
	{
		resetShooterPush();
		for(Solenoid s:HalfValves)
		{
			s.set(!HALF_ON);
		}
	}
	
	public void shootOut()
	{
		for(Solenoid s : ShooterValves) {
			
			s.set(true);
		}
	}
	
	public void shootIn()
	{
		for(Solenoid s : ShooterValves) {
			
			s.set(false);
		}
	}
	
	public void resetShooterPush() {
		for(Solenoid s : ShooterValves)
        {
        	s.set(false);
        }
	}
	
	public void setShoot(double spd)
	{
		for(Motor m:shootMotors)
		{
			m.setSpeed(spd);
		}
	}
	
	public boolean isHalfExtended() {
		return !HalfValves[0].get();
	}
	
}
