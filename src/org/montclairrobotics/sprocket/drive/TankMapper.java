package org.montclairrobotics.sprocket.drive;

public class TankMapper implements DTMapper {

    @Override
    public void map(DTTarget driveTarget, DriveModule[] driveModules) {
        double power = driveTarget.getDirection().getY();
        double turn = driveTarget.getTurn().toDegrees();

        double leftPower = (power + turn);
        double rightPower = (power - turn);
        
        double scaleFactor = driveTarget.getDirection().getMagnitude()/((leftPower+rightPower)/2);
        
        leftPower *= scaleFactor;
        rightPower *= scaleFactor;

        for(DriveModule m : driveModules) {
            if(m.getOffset().getX() < 0) {
                m.set(leftPower);
            } else if(m.getOffset().getX() > 0){
                m.set(rightPower);
            }
        }
    }
}
