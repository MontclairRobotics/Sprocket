package org.montclairrobotics.sprocket.frc;

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
public abstract class FRCRobot extends IterativeRobot implements IRobot,Updatable{

	public Sprocket sprocket;
	
	public FRCRobot() {
		sprocket=new Sprocket(this);
		sprocket.autoActionInput=new DashboardAutoSelector();
        Sprocket.debugger=new DashboardDebug();
	}
	
    @Override
    public void startCompetition() {
        super.startCompetition();
    }
    @Override
    public void robotInit()
    {
    	sprocket.initS();
    }
    @Override
    public final void autonomousInit() {
    	sprocket.startS(MODE.AUTO);
    }

    @Override
    public final void teleopInit() {
    	sprocket.startS(MODE.TELEOP);
    }

    @Override
    public final void testInit() {
        sprocket.startS(MODE.TEST);
    }
    
    @Override
    public final void disabledInit() {
    	sprocket.stopS();
    }
    
    @Override
    public final void autonomousPeriodic() {
        sprocket.updateS();
    }

    @Override
    public final void teleopPeriodic() {
        sprocket.updateS();
    }

    @Override
    public final void testPeriodic() {
        sprocket.updateS();
    } 
    
    @Override
    public final void disabledPeriodic() {
        sprocket.disabledUpdateS();
    }

}
