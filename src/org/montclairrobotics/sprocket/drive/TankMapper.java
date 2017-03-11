package org.montclairrobotics.sprocket.drive;

import org.montclairrobotics.sprocket.SprocketRobot;
import org.montclairrobotics.sprocket.geometry.Angle;
import org.montclairrobotics.sprocket.geometry.Vector;
import org.montclairrobotics.sprocket.utils.Debug;

public class TankMapper implements DTMapper {

    @Override
    public void map(DTTarget driveTarget, DriveModule[] driveModules) {
        double power = driveTarget.getDirection().getY()/SprocketRobot.getDriveTrain().getMaxSpeed().get();
        double turn = driveTarget.getTurn().toDegrees()/SprocketRobot.getDriveTrain().getMaxTurn().toDegrees();

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
            	setWheel(m,leftPower);
            } else if(m.getOffset().getX() > 0){
                setWheel(m,rightPower);
            }
        }
        
        Debug.num("power", power);
        Debug.num("turn", turn);
        
    }
    
    public static void setWheel(DriveModule m,double power)
    {
    	if(m.getForce().getY()>0)
    	{
    		m.set(power);
    	}
    	else
    	{
    		m.set(-power);
    	}
    }
    
    /*
	 * If a dot b = |c| and c is || to b,  given a and c returns b
	 */
	public static double inverseDot(Vector force,Vector target)
	{
		Angle diff=force.angleBetween(target);
		double degTo90=Math.abs(Math.abs(diff.toDegrees())-90);
		if(degTo90<30)
		{
			return target.getMagnitude()*degTo90/15;
		}
		return target.getMagnitude()/diff.cos();
	}
    
}
