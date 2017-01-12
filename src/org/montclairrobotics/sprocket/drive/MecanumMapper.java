package org.montclairrobotics.sprocket.drive;


import org.montclairrobotics.sprocket.geometry.Angle;
import org.montclairrobotics.sprocket.geometry.Position;
import org.montclairrobotics.sprocket.geometry.Vector;
import org.montclairrobotics.sprocket.geometry.XY;

public class MecanumMapper implements DriveTrainMapper {

    @Override
    public void map(DriveTrainTarget driveTarget, DriveModule[] driveModules) {
        double turn = driveTarget.getTurn();

        for(DriveModule m : driveModules) {
            double power = getPower(driveTarget.getDirection(), m.getForceAngle());

            //Get vector for rotating
            XY turnVector;
            if(m.getOffset() == Position.FL) {
                turnVector = new XY(1, 1);
            } else if(m.getOffset() == Position.FR) {
                turnVector = new XY(1, -1);
            } else if(m.getOffset() == Position.BL) {
                turnVector = new XY(-1, 1);
            } else if(m.getOffset() == Position.BR) {
                turnVector = new XY(-1, -1);
            } else {
                turnVector = new XY(0, 0);
            }

            //Scale it to the appropriate turn speed
            turnVector.scale(turn, false);
            double turnPower = getPower(turnVector, m.getForceAngle());

            power += turnPower;

            //TODO: SCALING!!!!!!!
            m.set(power);
        }
    }


    private static double getPower(Vector vec, Angle forceAngle) {
        // Power = tY*csc(angle) + tX*sec(angle)
        return vec.getY() * (1/Math.sin(forceAngle.toRadians())) + vec.getX() * (1/Math.sin(forceAngle.toRadians()));
    }

}
