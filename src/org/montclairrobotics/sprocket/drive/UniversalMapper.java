package org.montclairrobotics.sprocket.drive;

import org.montclairrobotics.sprocket.geometry.Vector;

public class UniversalMapper implements DTMapper{

	private double maxTurn = 0.1;
	
	@Override
	public void setup(DTModule[] driveModules)
	{
		if (driveModules.length == 0) return;
		
		for (DTModule module : driveModules) {
			double t = Math.abs(module.getOffset().cross(module.getForce()));
			if (t > maxTurn)
				maxTurn = t;
		}
	}
	
	@Override
	public void map(DTTarget driveTarget, DTModule[] driveModules) {
		if (driveModules.length == 0) return;
		Vector dir = driveTarget.getDirection();
		double turn = driveTarget.getTurn();
		double maxForce = 0.1;
		double maxPow = 0.1;
		
		for (DTModule module : driveModules) {
			double f=Math.abs(module.getForce().normalize().dot(dir.normalize()));
			if(f > maxForce)
				maxForce = f;
		}
		
		for (DTModule module : driveModules) {
			double d = module.getForce().normalize().dot(dir) / maxForce;
			double t = module.getOffset().cross(module.getForce()) * turn / maxTurn;
			module.temp = d + t;
			double pow = Math.abs(module.temp);
			if(pow > maxPow) {
				maxPow = pow;
			}
		}
		
		for (DTModule module : driveModules) {
			if (maxPow > 1) {
				module.set(module.temp / maxPow);
			} else {
				module.set(module.temp);
			}
		}
	}

}
