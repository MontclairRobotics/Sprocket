package org.montclairrobotics.sprocket.examples;

import org.montclairrobotics.sprocket.control.Button;
import org.montclairrobotics.sprocket.control.Control;

public class Buttons {
	public Buttons()
	{
		new HalfDown(2);
		new HalfUp(3);
		new ShootDown(4);
		new ShootUp(5);
		new Shoot(1);
	}
	
	public void reset()
	{
		//Sets everything to default
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
}
