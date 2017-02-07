package org.montclairrobotics.sprocket.vision;

import java.util.ArrayList;

import org.montclairrobotics.sprocket.GripPipelineC;
import org.opencv.core.MatOfPoint;

import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.vision.VisionPipeline;
import edu.wpi.first.wpilibj.vision.VisionThread;

public class SprocketTarget implements VisionTarget{
	
	private VisionThread visionThread;

	public SprocketTarget(VisionComponent[] pieces,VisionPipeline visionPipeline,MatOfPointsInput input, int IMG_WIDTH,int IMG_HEIGHT)
	{
		
		this(pieces,visionPipeline,input,getCamera(IMG_WIDTH,IMG_HEIGHT));
	}
	public SprocketTarget(VisionComponent[] pieces, VisionPipeline visionPipeline,MatOfPointsInput input,UsbCamera camera)
	{
	    visionThread = new VisionThread(camera, visionPipeline, pipeline -> {
	        if (!input.isEmpty()) {
	            Rect r = Imgproc.boundingRect(pipeline.filterContoursOutput().get(0));
	            synchronized (imgLock) {
	                centerX = r.x + (r.width / 2);
	                centerY = r.y + (r.height/2);
	            }
	        }
	    });
	    visionThread.start();
	    
	    
	    
	}
	
	@Override
	public void update()
	{
		double centerX,centerY;
		synchronized (imgLock) {
			centerX = this.centerX;
			centerY = this.centerY;
		}
	}
	
	public static UsbCamera getCamera(int IMG_WIDTH,int IMG_HEIGHT)
	{
		UsbCamera camera = CameraServer.getInstance().startAutomaticCapture();
		camera.setResolution(IMG_WIDTH, IMG_HEIGHT);
		return camera;
	}
}
