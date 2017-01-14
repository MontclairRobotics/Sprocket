package org.montclairrobotics.sprocket.drive;

public class TankMapper implements DriveTrainMapper {

    @Override
    public void map(DriveTrainTarget driveTarget, DriveModule[] driveModules) {
        double power = driveTarget.getDirection().getY().get();
        double turn = driveTarget.getTurn().toDegrees();

        double leftPower = (power + turn)/driveTarget.getDirection().getMagnitude().get();
        double rightPower = (power - turn)/driveTarget.getDirection().getMagnitude().get();

        for(DriveModule m : driveModules) {
            if(m.getOffset().getX().get() < 0) {
                m.set(leftPower);
            } else if(m.getOffset().getX().get() > 0){
                m.set(rightPower);
            }
        }
    }
}
