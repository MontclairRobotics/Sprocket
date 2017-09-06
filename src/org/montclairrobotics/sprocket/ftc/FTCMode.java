package org.montclairrobotics.sprocket.ftc;

import org.montclairrobotics.sprocket.actions.Action;
import org.montclairrobotics.sprocket.core.IRobot;
import org.montclairrobotics.sprocket.core.Sprocket;
import org.montclairrobotics.sprocket.core.Sprocket.MODE;
import org.montclairrobotics.sprocket.loop.Updatable;

import edu.wpi.first.wpilibj.IterativeRobot;


/**
 * @author MHS Robotics
 * This class is basically just a wrapper around iterative robot which all Sprocket
 * robots must extend.
 */
public abstract class FTCMode /*extends OpMode*/{

	public Sprocket sprocket;
	public MODE mode;
	public Action action;
	
	public FTCMode()
	{
	}
	public FTCMode(Sprocket sprocket,Action action)
	{
		this.sprocket=sprocket;
		this.mode=MODE.AUTO;
		this.action=action;
	}
	
	//@Override
	public void init() {
        sprocket.initS();
    }

    //@Override
    public void init_loop() {
    	sprocket.disabledUpdateS();
    }

    //@Override
    public void start() {
    	sprocket.currentAction=action;
    	sprocket.startS(mode);
    }

    //@Override
    public void loop() {
    	sprocket.updateS();
       
    }

    //@Override
    public void stop() {
    	sprocket.stopS();
    }
}
