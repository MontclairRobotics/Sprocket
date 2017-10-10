
package org.montclairrobotics.sprocket.drive;
import org.montclairrobotics.sprocket.geometry.*;

public class MecanumMapper implements DTMapper {

    @Override
    public void map(DTTarget driveTarget, DriveModule[] driveModules) {
        double turn = driveTarget.getTurn().toDegrees();
        
        for (DriveModule module : driveModules) {
            // 1. Get vector for rotating
            Vector turnVector;
            switch (module.getOffset()) {
                case Position.FL:
                    turnVector = new XY(1, 1);   // Quadrant I
                case Position.FR:
                    turnVector = new XY(1, -1);  // Quadrant IV
                case Position.BL:
                    turnVector = new XY(-1, 1);  // Quadrant II
                case Position.BR:
                    turnVector = new XY(-1, -1); // Quadrant III
                default:
                    turnVector = new XY(0, 0);   // Origin (0, 0)
            }

            // 2. Determine the power based on (a) direction and (b) rotation
            double power =
                getPower(driveTarget.getDirection(), module.getForceAngle()) +
                getPower(turnVector.scale(turn),     module.getForceAngle());
            
            // 3. Set the power for this module
            module.set(power);
        }
    }
    
    private double getPower(Vector vector, Angle forceAngle) {
        // Power = t_y / sin(angle) + t_x / cos(angle)
        return (vector.getY() / Math.sin(forceAngle.toRadians())) + (vector.getX() / Math.cos(forceAngle.toRadians()));
    }

}
