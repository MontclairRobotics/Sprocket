package org.montclairrobotics.sprocket.drive;

import org.montclairrobotics.sprocket.geometry.Vector;

/**
 * A class for holding the input to the drivetrain, in addition to any other information
 * Now it is all in one neat place.
 * 
 * Contents include:
 * 	- dir, a vector indicating speed and direction of translation
 * 	- rot, a double indicating speed of rotation
 * 
 * 
 * 
 * 	No comments please, Rich.
 */
public class DTRequest
{		
	//Lock object for future multithreading
	public Object lock=new Object();
	
	//Drivetrain request parameters
	public Vector dir=Vector.ZERO;//Direction and speed to travel in, from -1 to 1; (0,1) meaning forward
	public double rot=0.0;//Turning speed, from -1 to 1; 1 meaning turning right and 0 meaning not turning
	
	//Copy constructor to get a snapshot of the request
	public DTRequest copy()
	{
		synchronized(lock)
		{
			DTRequest r = new DTRequest();
			r.dir=dir;
			r.rot=rot;
			return r;
		}
	}
}