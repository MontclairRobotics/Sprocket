package org.montclairrobotics.sprocket.vision;

import org.opencv.core.MatOfPoint;
import org.opencv.core.Rect;
import org.opencv.imgproc.Imgproc;
import com.ctre.CANTalon;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.SPI.Port;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.vision.VisionRunner;
import edu.wpi.first.wpilibj.vision.VisionThread;

public class BoxComponent implements VisionComponent{
	double width;
	double height;
	double tolerance;
	boolean checkHeight;
	
	public BoxComponent(double width,double height,double ratioTolerance)
	{
		this.width=width;
		this.height=height;
		this.tolerance=ratioTolerance;
		checkHeight=height>width;
	}

	@Override
	public double getScale(MatOfPoint mat) {
		Rect r = Imgproc.boundingRect(mat);
		if(Math.abs((height/width)-(r.height/r.width))>tolerance)
			return -1;
		if(checkHeight)
		{
			return r.height/height;
		}
		else
			return r.width/width;
	}

}
