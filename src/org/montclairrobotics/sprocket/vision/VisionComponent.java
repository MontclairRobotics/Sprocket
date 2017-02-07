package org.montclairrobotics.sprocket.vision;

import org.opencv.core.MatOfPoint;

public interface VisionComponent {
	/**
	 * returns the size of the target as a percentage of desired target, 
	 * or a negative number if it is not the target
	 * @param mat The Mat of points to check
	 * @return size of target, or negative number if not target
	 */
	public double getScale(MatOfPoint mat);

}
