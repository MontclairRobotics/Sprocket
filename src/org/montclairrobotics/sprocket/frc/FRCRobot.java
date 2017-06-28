package org.montclairrobotics.sprocket.frc;

import org.montclairrobotics.sprocket.actions.Action;
import org.montclairrobotics.sprocket.core.IRobot;
import org.montclairrobotics.sprocket.core.Sprocket;
import org.montclairrobotics.sprocket.core.Sprocket.MODE;
import org.montclairrobotics.sprocket.drive.DriveTrain;
import org.montclairrobotics.sprocket.loop.DisabledUpdater;
import org.montclairrobotics.sprocket.loop.Priority;
import org.montclairrobotics.sprocket.loop.Updatable;
import org.montclairrobotics.sprocket.loop.Updater;
import org.montclairrobotics.sprocket.utils.Input;

import edu.wpi.first.wpilibj.IterativeRobot;


/**
 * @author MHS Robotics
 * This class is basically just a wrapper around iterative robot which all Sprocket
 * robots must extend.
 */
public abstract class FRCRobot extends IterativeRobot implements IRobot,Updatable{

	public Sprocket sprocket;
	
	public FRCRobot()
	{
		sprocket=new Sprocket(this);
	}
	
    @Override
    public void startCompetition() {
        super.startCompetition();
    }
    @Override
    public void robotInit()
    {
    	init();
    }
    @Override
    public final void autonomousInit() {
    	sprocket.start(MODE.AUTO);
    }

    @Override
    public final void teleopInit() {
    	sprocket.start(MODE.TELEOP);
    }

    @Override
    public final void testInit() {
        sprocket.start(MODE.TEST);
    }
    
    @Override
    public final void disabledInit() {
    	sprocket.stop();
    }
    
    @Override
    public final void autonomousPeriodic() {
        sprocket.update();
    }

    @Override
    public final void teleopPeriodic() {
        sprocket.update();
    }

    @Override
    public final void testPeriodic() {
        sprocket.update();
    } 
    
    @Override
    public final void disabledPeriodic() {
        sprocket.disabledUpdate();
    }
}
