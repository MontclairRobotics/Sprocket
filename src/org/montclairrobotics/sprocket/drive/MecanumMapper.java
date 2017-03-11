package org.montclairrobotics.sprocket.drive;


import org.montclairrobotics.sprocket.geometry.Angle;
import org.montclairrobotics.sprocket.geometry.Position;
import org.montclairrobotics.sprocket.geometry.Vector;
import org.montclairrobotics.sprocket.geometry.XY;

public class MecanumMapper implements DTMapper {

    @Override
    public void map(DTTarget driveTarget, DriveModule[] driveModules) {
        double turn = driveTarget.getTurn().toDegrees();

        for(DriveModule m : driveModules) {
            double power = getPower(driveTarget.getDirection(), m.getForceAngle());

            //Get vector for rotating
            Vector turnVector;
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
            turnVector.scale(turn);
            double turnPower = getPower(turnVector, m.getForceAngle());

            power += turnPower;

            //TODO: SCALING!!!!!!!
            m.set(power);
        }
    }


    private static double getPower(Vector vector, Angle forceAngle) {
        // Power = tY*csc(angle) + tX*sec(angle)
        return vector.getY() * (1/Math.sin(forceAngle.toRadians())) + vector.getX() * (1/Math.sin(forceAngle.toRadians()));
    }

}
