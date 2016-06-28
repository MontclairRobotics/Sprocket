package org.montclairrobotics.sprocket.auto;

import org.montclairrobotics.sprocket.states.State;
import org.montclairrobotics.sprocket.states.StateMachine;

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class AutoChooser {
	private SendableChooser chooser;
	public AutoChooser(String[]names,State[][]options)
	{
		if(names.length<1||names.length!=options.length)return;
		chooser=new SendableChooser();
		chooser.addDefault(names[0], options[0]);
		for(int i=1;i<names.length;i++)
		{
			chooser.addObject(names[i], options[i]);
		}
		SmartDashboard.putData("AUTO CHOOSER",chooser);
	}
	public StateMachine startStateMachine()
	{
		StateMachine stateMachine= new StateMachine(getRaw());
		stateMachine.start();
		return stateMachine;
	}
	public State[] getRaw()
	{
		return (State[]) chooser.getSelected();
	}
	
	
}