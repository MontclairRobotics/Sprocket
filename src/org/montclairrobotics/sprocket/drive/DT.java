package org.montclairrobotics.sprocket.drive;

import org.montclairrobotics.sprocket.core.Component;

public class DT extends Component{
	
	public DT(long millisInLoop)
	{
		super(millisInLoop);
	}
	public DT() 
	{
		super(50);//Probably around there I think
	}

	public DTRequest input=new DTRequest();//The input to the drivetrain. 
	/*
	 Note: Make sure to use the lock if you are changing it rather than creating a new one, 
	 		after threads have been implemented
	*/
	public DTStep[] steps;//The list of processing steps for the drivetrain
	public DTMapper mapper;//The mapper, that converts a Request to a set of motor powers
	public DTModule[] modules;//The list of drive modules in the drivetrain
	
	private DTRequest target=new DTRequest();//Variable to hold a snapshot of the drive request
	
	@Override
	public void update() {
		if(input!=null)
		{
			target=input.copy();//take a snapshot of the input
		}
		for(DTStep s:steps)
		{
			s.doStep(target);//run the request through the pipeline
		}
		mapper.map(target,modules);//send the request to the motors
	}
	
	
	
	
}
