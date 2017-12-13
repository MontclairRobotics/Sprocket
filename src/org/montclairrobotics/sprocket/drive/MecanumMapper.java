package org.montclairrobotics.sprocket.drive;


import org.montclairrobotics.sprocket.geometry.Degrees;
import org.montclairrobotics.sprocket.geometry.Vector;

/*
Second attempt to implement a mecanum mappper. This is essentially a simple generic
mapper which works only on symmetrical drivetrains as coding something specifically
for mecanum would probably end up being more complicated (since exact implementations
of a mecanum drivetrain can vary).
 */
public class MecanumMapper implements DTMapper {

    @Override
    public void map(DTTarget driveTarget, DriveModule[] driveModules) {
        //Setting up variables
        Vector dir = driveTarget.getDirection();
        double dirPower = dir.getMagnitude();
        double turn = driveTarget.getTurn();
        double motorPowers[] = new double[driveModules.length];

        //Finding initial motor powers
        for (int i = 0; i < driveModules.length; i++) {
            DriveModule driveModule = driveModules[i];
            Vector offset = driveModule.getOffset(); //Storing the motor offset
            //Rotating the offset 90 degrees to find the vector along which torque should be applied,
            //then scaling the turn vector appropriately
            Vector turnVec = offset.rotate(new Degrees(90)).normalize().scale(turn / (dirPower + Math.abs(turn)));
            //Scaling the directional vector, then adding it to the turn vector calculated
            Vector finalVec = dir.normalize().scale(dirPower / (dirPower + Math.abs(turn))).add(turnVec);
            //Dotting it with the force vector of the motor to get the final power
            motorPowers[i] = finalVec.dot(driveModule.getForce());
        }

        //Finding the highest power calculated
        double highestPower = Double.MIN_VALUE;
        for(double p : motorPowers) {
            if(Math.abs(p) > highestPower) {
                highestPower = Math.abs(p);
            }
        }

        //Finding the higher of either the directional vector or the turn vector
        double maxPower = dirPower > Math.abs(turn) ? dirPower : Math.abs(turn);

        for(int i = 0; i < driveModules.length; i++) {
            //Scaling all motors first relative to that with the highest power, and then down to the max desired power
            motorPowers[i] = (motorPowers[i] / highestPower)*maxPower;
            driveModules[i].set(motorPowers[i]);
        }
    }

	@Override
	public void setup(DriveModule[] driveModules) {
		// TODO Auto-generated method stub
		
	}

}
