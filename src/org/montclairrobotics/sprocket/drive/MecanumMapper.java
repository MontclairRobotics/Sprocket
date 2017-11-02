package org.montclairrobotics.sprocket.drive;


import org.montclairrobotics.sprocket.geometry.Degrees;
import org.montclairrobotics.sprocket.geometry.Vector;

public class MecanumMapper implements DTMapper {

    @Override
    public void map(DTTarget driveTarget, DriveModule[] driveModules) {
        Vector dir = driveTarget.getDirection();
        double dirPower = dir.getMagnitude();
        double turn = driveTarget.getTurn();
        double motorPowers[] = new double[driveModules.length];

        for (int i = 0; i < driveModules.length; i++) {
            DriveModule driveModule = driveModules[i];
            Vector offset = driveModule.getOffset();
            Vector turnVec = offset.rotate(new Degrees(90)).normalize().scale(turn / (dirPower + Math.abs(turn)));
            Vector finalVec = dir.normalize().scale(dirPower / (dirPower + Math.abs(turn))).add(turnVec);
            motorPowers[i] = finalVec.dotProduct(driveModule.getForce());
        }

        double highestPower = Double.MIN_VALUE;
        for(double p : motorPowers) {
            if(Math.abs(p) > highestPower) {
                highestPower = p;
            }
        }

        for(int i = 0; i < driveModules.length; i++) {
            motorPowers[i] = motorPowers[i] / highestPower;
            driveModules[i].set(motorPowers[i]);
        }
    }

}
