package org.montclairrobotics.sprocket;

import edu.wpi.first.wpilibj.IterativeRobot;
import org.montclairrobotics.sprocket.loop.Updater;

public class SprocketRobot extends IterativeRobot {

    @Override
    public void startCompetition() {
        super.startCompetition();
    }

    @Override
    public void robotInit() {
        super.robotInit();
    }

    @Override
    public void disabledInit() {
        super.disabledInit();
    }

    @Override
    public void autonomousInit() {
        super.autonomousInit();
    }

    @Override
    public void teleopInit() {
        super.teleopInit();
    }

    @Override
    public void testInit() {
        super.testInit();
    }

    @Override
    public void disabledPeriodic() {
        super.disabledPeriodic();
    }

    @Override
    public void autonomousPeriodic() {
        super.autonomousPeriodic();
        Updater.loop();
    }

    @Override
    public void teleopPeriodic() {
        super.teleopPeriodic();
        Updater.loop();
    }

    @Override
    public void testPeriodic() {
        super.testPeriodic();
        Updater.loop();
    }

}
