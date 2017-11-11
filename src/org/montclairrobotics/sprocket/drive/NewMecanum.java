package org.montclairrobotics.sprocket.drive;

import org.montclairrobotics.sprocket.geometry.Degrees;
import org.montclairrobotics.sprocket.geometry.Vector;

/**
 * Created by MHS Robotics on 11/11/2017.
 */

public class NewMecanum implements DTMapper {
    @Override
    public void setup(DriveModule[] driveModules) {

    }

    public void map(DTTarget driveTarget, DriveModule[] driveModules) {
        //Setting up variables
        double x = driveTarget.getDirection().getX();
        double y = driveTarget.getDirection().getY();
        double turn = driveTarget.getTurn();
        double maxPower=1;

        for(DriveModule module:driveModules)
        {
            double xSign=module.getOffset().getX()>0?1:-1;
            double ySign=module.getOffset().getY()>0?1:-1;
            double dirSign=module.getOffset().crossProduct(module.getForce())>0?1:-1;
            module.temp=(y*xSign*-1+x*ySign+turn)*dirSign;
            if(module.temp>maxPower)
            {
                maxPower=module.temp;
            }
        }
        for(DriveModule module:driveModules) {
            module.set(module.temp/maxPower);
        }
    }
}
