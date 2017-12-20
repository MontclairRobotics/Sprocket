package org.montclairrobotics.sprocket.core;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public abstract class Component implements Runnable{
	
	private static final ScheduledExecutorService scheduler =
		     Executors.newScheduledThreadPool(5);//Not quite sure about this line
	
	enum State{Disabled,Auto,Teleop,Test};
	private State state;//My state: determines what functions to run
	
	private long millisPerLoop;
	
	/**
	 * Make a Component to run every so often; like Drivetrain or Auto or an arm on the robot
	 * @param millisPerLoop
	 */
	public Component(long millisPerLoop)
	{
		this.millisPerLoop=millisPerLoop;
	}
	/**
	 * Make sure you call this from sprocket robot when this component is started
	 */
	public final void startLoop()
	{
		scheduler.scheduleAtFixedRate(this,millisPerLoop,millisPerLoop,TimeUnit.MILLISECONDS);
		setState(State.Disabled);
	}
	
	/**
	 * Start our current state
	 * Make sure you call this from sprocket robot at every state change
	 */
	public void setState(State s)
	{
		state=s;
		switch(state)
		{
		case Disabled:
			disabledInit();
			return;
		case Auto:
			autoInit();
			break;
		case Teleop:
			teleopInit();
			break;
		case Test:
			testInit();
			break;
		}
		init();
	}
	
	/**
	 * Update our current state
	 */
	public void run()
	{
		switch(state)
		{
		case Disabled:
			disabledUpdate();
			return;
		case Auto:
			autoUpdate();
			break;
		case Teleop:
			teleopUpdate();
			break;
		case Test:
			testUpdate();
			break;
		}
		update();
	}
	
	/**
	 * Make sure you call this from SprocketRobot after everything is constructed but before starting
	 */
	public void setup(){}//Called once to set up the component (don't use the constructor!)
	
	public void init(){}//Called when an enabled state is inited;
	public void update(){}//Called every enabled loop
	
	public void disabledInit(){}//Called once when the robot is disabled, including right after power up
	public void disabledUpdate(){}//Called every disabled loop

	public void autoInit(){}//Called once when auto is inited
	public void teleopInit(){}//Called once when teleop is inited
	public void testInit(){}//Called once when test is inited
	public void autoUpdate(){}//Called every auto loop
	public void teleopUpdate(){}//Called every teleopUpdate loop
	public void testUpdate(){}//Called every testUpdate loop
}
