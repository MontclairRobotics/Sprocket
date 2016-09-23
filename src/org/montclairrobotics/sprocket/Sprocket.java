package org.montclairrobotics.sprocket;

import org.montclairrobotics.sprocket.drive.DriveTrain;

public class Sprocket {

    private static DriveTrain driveTrain;


    public static void registerDriveTrain(DriveTrain driveTrain) throws Exception {
        if(driveTrain != null) {
            throw new Exception("Overriding previous drive train!");
        }
        Sprocket.driveTrain = driveTrain;
    }

    public static DriveTrain getDriveTrain() {
        return driveTrain;
    }

}
