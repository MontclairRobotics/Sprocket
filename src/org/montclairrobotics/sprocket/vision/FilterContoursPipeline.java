package org.montclairrobotics.sprocket.vision;

import java.util.ArrayList;

import org.opencv.core.MatOfPoint;

import edu.wpi.first.wpilibj.vision.VisionPipeline;

public interface FilterContoursPipeline extends VisionPipeline{

	public ArrayList<MatOfPoint> filterContours();
}
