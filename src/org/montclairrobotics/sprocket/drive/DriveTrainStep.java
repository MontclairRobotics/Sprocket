package org.montclairrobotics.sprocket.drive;

import org.montclairrobotics.sprocket.updater.Updatable;

public interface DriveTrainStep extends Updatable {

    void run(DriveTrainInput input);

}
