package org.montclairrobotics.sprocket.vision;

import java.util.ArrayList;

import org.opencv.core.MatOfPoint;
import org.opencv.core.Rect;
import org.opencv.imgproc.Imgproc;

import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.wpilibj.vision.VisionPipeline;

public class FilterContoursTarget extends SprocketVisionTarget
{

	public FilterContoursTarget(FilterContoursPipeline visionPipeline, int tgtX,int tgtY,int IMG_WIDTH,int IMG_HEIGHT)
	{
		this(visionPipeline, tgtX,tgtY,getCamera(IMG_WIDTH,IMG_HEIGHT));
	}
	public FilterContoursTarget(FilterContoursPipeline visionPipeline,int tgtX,int tgtY,UsbCamera camera)
	{
		super(visionPipeline,new ImageInput(){

			@Override
			public TurnDistanceInput get() {
				Rect r = Imgproc.boundingRect(visionPipeline.filterContours().get(0));
				return new TurnDistanceInput(tgtX-r.x+r.width/2,tgtY-r.y+r.height/2);
			}}, camera);
	}

}
