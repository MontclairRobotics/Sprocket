package org.montclairrobotics.sprocket.vision;

import java.util.ArrayList;

import org.opencv.core.MatOfPoint;

public interface FilterContours {
	public ArrayList<MatOfPoint> filterContoursOutput();
}
