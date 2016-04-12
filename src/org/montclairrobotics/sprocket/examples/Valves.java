package org.montclairrobotics.sprocket.examples;

import org.montclairrobotics.sprocket.control.Button;
import org.montclairrobotics.sprocket.control.Control;

import edu.wpi.first.wpilibj.Solenoid;

public class Valves {
	
	private Solenoid[] LiftValves;
	private Solenoid[] ShooterValves;
	private Solenoid[] HalfValves;
	
	public static final int[] LIFT_PORTS={
		0
	};
	
	public static final int[] SHOOTER_PORTS={
			1
	};
	
	public static final int[] LIFT_HALF_PORTS={
			2
	};
	
	public Valves()
	{
        for(int i = 0; i < LIFT_PORTS.length; i++)
        {
        	LiftValves[i] = new Solenoid(LIFT_PORTS[i]);
        }
        
        for(int i = 0; i < SHOOTER_PORTS.length; i++)
        {
        	
        	ShooterValves[i] = new Solenoid(SHOOTER_PORTS[i]);
        }
        
        for(int i = 0; i < LIFT_HALF_PORTS.length; i++)
        {
        	HalfValves[i] = new Solenoid(LIFT_HALF_PORTS[i]);
        }
        new HalfDown(2);
		new HalfUp(3);
		new ShootDown(4);
		new ShootUp(5);
		new Shoot(1);
	}
	
	public class HalfDown extends Button
	{
		public HalfDown(int id)
		{
			super(Control.SHOOT_STICK,id);
		}
		public void onDown()
		{
			//Lower half
		}
	}
	public class HalfUp extends Button
	{
		public HalfUp(int id)
		{
			super(Control.SHOOT_STICK,id);
		}
		public void onDown()
		{
			//raise half
		}
	}
	public class ShootDown extends Button
	{
		public ShootDown(int id)
		{
			super(Control.SHOOT_STICK,id);
		}
		public void onDown()
		{
			//Lower Shooter
		}
	}
	public class ShootUp extends Button
	{
		public ShootUp(int id)
		{
			super(Control.SHOOT_STICK,id);
		}
		public void onDown()
		{
			//Raise Shooter
		}
	}
	public class Shoot extends Button
	{
		public Shoot(int id)
		{
			super(Control.SHOOT_STICK,id);
		}
		public void onDown()
		{
			//Shoot piston out
		}
		public void onUp()
		{
			//Shoot piston in
		}
	}
	
	public void raise()
	{
		resetShooterPush();
		for(Solenoid s : LiftValves) {
			s.set(true);
		}
		halfOn();
	}
	
	public void raiseArm() {
		resetShooterPush();
		for(Solenoid s : LiftValves) {
			s.set(true);
		}
	}
	
	public void lower()
	{
		resetShooterPush();
		for(Solenoid s : LiftValves) {
			s.set(false);
		}
		halfOn();
	}
	
	public void raiseOne(){
		resetShooterPush();
		for(Solenoid s : LiftValves) {
			s.set(true);
		}
	}
	
	public void lowerOne(){
		resetShooterPush();
		for(Solenoid s : LiftValves) {
			s.set(false);
		}
	}
	
	public void halfOn()
	{
		resetShooterPush();
		for(Solenoid s:HalfValves)
		{
			s.set(false);
		}
	}
	public void halfOff()
	{
		resetShooterPush();
		for(Solenoid s:HalfValves)
		{
			s.set(true);
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
	
	public void lowerArm() {
		for(Solenoid s : LiftValves) {
			s.set(true);
		}
	}
	
	public boolean isHalfExtended() {
		return !HalfValves[0].get();
	}
	
}
