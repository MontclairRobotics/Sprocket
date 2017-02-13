package org.montclairrobotics.sprocket.vision;

import java.util.ArrayList;

import org.montclairrobotics.sprocket.geometry.Vector;
import org.opencv.core.MatOfPoint;

public interface ImageProcess {
	public TurnDistanceInput get(ArrayList<MatOfPoint> input);
}
