package org.montclairrobotics.sprocket.vision;

import java.util.ArrayList;

import org.montclairrobotics.sprocket.geometry.Vector;
import org.montclairrobotics.sprocket.loop.Priority;
import org.montclairrobotics.sprocket.loop.Updatable;
import org.montclairrobotics.sprocket.loop.Updater;
import org.montclairrobotics.sprocket.utils.Input;
import org.opencv.core.MatOfPoint;

import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.vision.VisionPipeline;
import edu.wpi.first.wpilibj.vision.VisionThread;

public abstract class SprocketVisionTarget implements VisionTarget,Updatable{
	
	private VisionThread visionThread;
	private TurnDistanceInput output=TurnDistanceInput.ZERO;
	private TurnDistanceInput savedOutput=TurnDistanceInput.ZERO;

	public SprocketVisionTarget(VisionPipeline visionPipeline,ImageInput getTarget, int IMG_WIDTH,int IMG_HEIGHT)
	{
		this(visionPipeline,getTarget,getCamera(IMG_WIDTH,IMG_HEIGHT));
	}
	public SprocketVisionTarget(VisionPipeline visionPipeline,ImageInput getTarget,UsbCamera camera)
	{
	    visionThread = new VisionThread(camera, visionPipeline, pipeline -> {
	        //if (!input.get().isEmpty()) {
	            TurnDistanceInput out =getTarget.get();
	            if(out!=null)
	            {
		            synchronized (output) {
		                this.output=out;
		            }
	            }
	        //}
	    });
	    visionThread.start();
	    
	    Updater.add(this, Priority.HIGHEST);
	    
	}
	
	@Override
	public void update()
	{
		synchronized (output) {
			savedOutput=output;
		}
	}
	
	public double getTurn()
	{
		return savedOutput.getTurn();
	}
	public double getDistance()
	{
		return savedOutput.getDistance();
	}
	
	public static UsbCamera getCamera(int IMG_WIDTH,int IMG_HEIGHT)
	{
		UsbCamera camera = CameraServer.getInstance().startAutomaticCapture();
		camera.setResolution(IMG_WIDTH, IMG_HEIGHT);
		return camera;
	}
}
