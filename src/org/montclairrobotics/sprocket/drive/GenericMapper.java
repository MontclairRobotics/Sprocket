package org.montclairrobotics.sprocket.drive;


import org.montclairrobotics.sprocket.geometry.Angle;
import org.montclairrobotics.sprocket.geometry.Vector;

public class GenericMapper implements DTMapper {

	@Override
	public void map(DTTarget driveTarget, DriveModule[] driveModules) {
		//Stores parts of target in local variables for convenience
		Vector dir = driveTarget.getDirection();
		double turn = driveTarget.getTurn();

		//The motor power will be scaled to the highest of either the directional input or the turn input
		double power = dir.getMagnitude() > Math.abs(turn) ? dir.getMagnitude() : Math.abs(turn);

		//Calculates how much each component should be weighted in the final vector
		double totalInput = dir.getMagnitude() + Math.abs(turn);
		double dirWeight = dir.getMagnitude()/totalInput;
		double turnWeight = Math.abs(turn)/totalInput;

		dir = dir.normalize().scale(dirWeight); //Scales direction to appropriate weighting

		//Creates variables for scaling the motor powers
		double[] powers = new double[driveModules.length];
		double maxPower = 0.0;

		//Storing the lowest motor force so the motor can scale appropriately
		double lowestForce = Double.MAX_VALUE;

		//Calculating torque factor for each drive module, projecting that onto the motor
		for(int i = 0; i < driveModules.length; i++) {
			DriveModule module = driveModules[i]; //Gets drive module from array
			Vector torqueVector = module.getOffset().rotate(Angle.QUARTER); //Finds the vector where force applied creates a CW turn
			torqueVector = torqueVector.normalize().scale(turnWeight); //Scales it to the appropriate weighting

			Vector moduleVec = dir.add(torqueVector); //Adds the torque vector to the directional vector
			moduleVec = moduleVec.normalize(); //Normalises the vector, magnitude of resultant vector cannot affect the dot product
			double dot = moduleVec.dotProduct(module.getForce()); //Projects target vector onto the motor

			//Searches for largest dot product
			if(Math.abs(dot) > maxPower) {
				maxPower = Math.abs(dot);
				powers[i] = dot;
			}

			//Searches for least powerful motor for scaling
			if(driveModules[i].getForce().getMagnitude() < lowestForce) {
				lowestForce = driveModules[i].getForce().getMagnitude();
			}
		}

		//Scaling
		for(int i = 0; i < driveModules.length; i++) {
			powers[i] = (powers[i]/maxPower) * power; //Scales each power relative to the maximum power, and then to the desired maximum motor power
			powers[i] = powers[i] * (lowestForce/driveModules[i].getForce().getMagnitude()); //Scales to account for imbalanced motor powers
			driveModules[i].set(powers[i]);
		}

	}

}