package org.montclairrobotics.sprocket.auto.states;

import edu.wpi.first.wpilibj.interfaces.Gyro;
import org.montclairrobotics.sprocket.auto.AutoState;
import org.montclairrobotics.sprocket.drive.steps.GyroCorrection;

public class ResetGyro extends AutoState
{
    GyroCorrection gCorrrect;

    public ResetGyro(GyroCorrection gC) {
        gCorrrect=gC;
    }

    @Override
    public void userStart() {
        gCorrrect.reset();
    }

    @Override
    public void stateUpdate() {

    }

    @Override
    public boolean isDone() {
        return true;
    }
}
