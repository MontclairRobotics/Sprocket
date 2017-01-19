package org.montclairrobotics.sprocket.drive;

public class TankMapper implements DTMapper {

    @Override
    public void map(DTTarget driveTarget, DriveModule[] driveModules) {
        double power = driveTarget.getDirection().getY();
        double turn = driveTarget.getTurn().toDegrees() / 45;

        double max = 0;

        if(power == 0.0 || turn == 0.0) {
            max = 1;
        } else if(Math.abs(power) > Math.abs(turn)) {
            max = 1 + Math.abs(turn/power);
        } else {
            max = 1 + Math.abs(power/turn);
        }

        double leftPower = (power + turn)/max;
        double rightPower = (power - turn)/max;

        for(DriveModule m : driveModules) {
            if(m.getOffset().getX() < 0) {
                m.set(leftPower);
            } else if(m.getOffset().getX() > 0){
                m.set(rightPower);
            }
        }
    }
}
