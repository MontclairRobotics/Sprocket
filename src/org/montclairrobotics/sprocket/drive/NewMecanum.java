package org.montclairrobotics.sprocket.drive;


/**
 * Created by MHS Robotics on 11/11/2017.
 */

public class NewMecanum implements DTMapper {
    @Override
    public void setup(DTModule[] driveModules) {

    }

    public void map(DTTarget driveTarget, DTModule[] driveModules) {
        //Setting up variables
        double x = driveTarget.getDirection().getX();
        double y = driveTarget.getDirection().getY();
        double turn = driveTarget.getTurn();
        double maxPower=1;

        for(DTModule module:driveModules)
        {
            double xSign=module.getOffset().getX()>0?1:-1;
            double ySign=module.getOffset().getY()>0?1:-1;
            double dirSign=module.getOffset().cross(module.getForce()) > 0 ? 1 : -1;
            module.temp=(y*xSign*-1+x*ySign+turn)*dirSign;
            if(module.temp>maxPower)
            {
                maxPower=module.temp;
            }
        }
        for(DTModule module:driveModules) {
            module.set(module.temp/maxPower);
        }
    }
}
