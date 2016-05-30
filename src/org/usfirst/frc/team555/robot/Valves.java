package org.usfirst.frc.team555.robot;

import org.montclairrobotics.sprocket.control.Button;
import org.montclairrobotics.sprocket.control.Control;
import org.montclairrobotics.sprocket.drive.Motor;
import org.montclairrobotics.sprocket.drive.Motor.M_TYPE;
import org.montclairrobotics.sprocket.utils.XY;

import edu.wpi.first.wpilibj.Joystick;
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
        new HalfDown(10);
		new HalfUp(11);
		new ShootDown(7);
		new ShootUp(6);
		new Shoot(1);
		new ShootMotorOn(3);
		new ShootMotorIntake(2);
		Robot.alignButton=new AlignOn(11,new XY(168,144));
		
		raise();
		halfOff();
	}
	public class AlignOn extends Button
	{
		private int loopsAtTarget=0;
		private static final double 
			BUFFER_X_SMALL=13,
			BUFFER_X_BIG=50,
			BUFFER_Y_SMALL=14,
			BUFFER_Y_BIG=60,
			TURN_SMALL=0.21,
			TURN_BIG=0.27,
			MOVE_SMALL=0.25,
			MOVE_BIG=0.3,
			BLEND=1;
		private double x,y;
		private XY target;
		public AlignOn(int id,XY target) {
			super(Control.sticks[Control.DRIVE_STICK], id);
			this.target=target;
		}
		public void down()
		{
			x=x*(1-BLEND)+(Robot.grip.getX()-target.getX())*BLEND;
			y=y*(1-BLEND)+(Robot.grip.getY()-target.getY())*BLEND;
			double spd,rot;
			boolean atTarget=false;
			if(x==0)
				rot=0;
			else if(x>BUFFER_X_BIG)
				rot=TURN_BIG;
			else if(x>BUFFER_X_SMALL)
				rot=TURN_SMALL;
			else if(x<-BUFFER_X_BIG)
				rot=-TURN_BIG;
			else if(x<-BUFFER_X_SMALL)
				rot=-TURN_SMALL;
			else
			{
				rot=0;
				atTarget=true;
			}
			if(y==0)
				spd=0;
			else if(y>BUFFER_Y_BIG)
				spd=-MOVE_BIG;
			else if(y>BUFFER_Y_SMALL)
				spd=-MOVE_SMALL;
			else if(y<-BUFFER_Y_BIG)
				spd=MOVE_BIG;
			else if(y<-BUFFER_Y_SMALL)
				spd=MOVE_SMALL;
			else
			{
				spd=0;
				if(atTarget)
				{
					loopsAtTarget++;
				}
			}
			Robot.driveTrain.driveSpeedRotation(rot,spd);
			Robot.align=true;
		}
		public void up()
		{
			Robot.align=false;
		}
		public int getLoopsAtTarget()
		{
			return loopsAtTarget;
		}
	}
	public class ShootMotorOn extends Button
	{

		public ShootMotorOn(int id) {
			super(Control.sticks[1],id);
		}
		public void onDown()
		{
			setShoot(SHOOT_SPEED);
		}
		public void onUp()
		{
			setShoot(0);
		}
		
	}
	public class ShootMotorIntake extends Button
	{
		public ShootMotorIntake(int id) {
			super(Control.sticks[1],id);
		}
		public void onDown()
		{
			setShoot(INTAKE_SPEED);
		}
		public void onUp()
		{
			setShoot(0);
		}
	}
	public class HalfDown extends Button
	{
		public HalfDown(int id)
		{
			super(Control.sticks[1],id);
		}
		public void onDown()
		{
			halfOff();
		}
	}
	public class HalfUp extends Button
	{
		public HalfUp(int id)
		{
			super(Control.sticks[1],id);
		}
		public void onDown()
		{
			halfOn();
		}
	}
	public class ShootDown extends Button
	{
		public ShootDown(int id)
		{
			super(Control.sticks[1],id);
		}
		public void onDown()
		{
			lower();
		}
	}
	public class ShootUp extends Button
	{
		public ShootUp(int id)
		{
			super(Control.sticks[1],id);
		}
		public void onDown()
		{
			raise();
		}
	}
	public class Shoot extends Button
	{
		public Shoot(int id)
		{
			super(Control.sticks[1],id);
		}
		public void onDown()
		{
			shootOut();
		}
		public void onUp()
		{
			shootIn();
		}
	}
	/**===============**/
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
