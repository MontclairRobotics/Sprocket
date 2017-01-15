package org.montclairrobotics.sprocket.drive;

public class TankMapper implements DriveTrainMapper {

    @Override
    public void map(DTTarget driveTarget, DriveModule[] driveModules) {
        double power = driveTarget.getDirection().getY();
        double turn = driveTarget.getTurn().toDegrees();

        double leftPower = (power + turn)/driveTarget.getDirection().getMagnitude();
        double rightPower = (power - turn)/driveTarget.getDirection().getMagnitude();

        for(DriveModule m : driveModules) {
            if(m.getOffset().getX() < 0) {
                m.set(leftPower);
            } else if(m.getOffset().getX() > 0){
                m.set(rightPower);
            }
        }
    }
}
