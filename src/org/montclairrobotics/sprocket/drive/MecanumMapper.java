package org.montclairrobotics.sprocket.drive;


import org.montclairrobotics.sprocket.geometry.Degrees;
import org.montclairrobotics.sprocket.geometry.Vector;

public class MecanumMapper implements DTMapper {

    @Override
    public void map(DTTarget driveTarget, DriveModule[] driveModules) {
        Vector dir = driveTarget.getDirection();
        double dirPower = dir.getMagnitude();
        double turn = driveTarget.getTurn();

        for (DriveModule driveModule : driveModules) {
            Vector offset = driveModule.getOffset();
            Vector turnVec = offset.rotate(new Degrees(90)).normalize().scale(turn / (dirPower + Math.abs(turn)));
            Vector finalVec = dir.normalize().scale(dirPower / (dirPower + Math.abs(turn))).add(turnVec);
            driveModule.set(finalVec.dotProduct(driveModule.getForce()));
        }
    }

}
