package org.montclairrobotics.sprocket.utils;

import org.montclairrobotics.sprocket.control.Button;
import org.montclairrobotics.sprocket.control.ButtonAction;
import org.montclairrobotics.sprocket.control.DashboardButton;
import org.montclairrobotics.sprocket.control.DashboardInput;

public class PIDThingy extends PID
{
	private Input<Double> TempP;
	private Button test;
	private Button apply;
	private Button run;
	private DashboardInput realP,realI,realD;
	private Input<Double> cyclesPer10Sec;

	public PIDThingy(Input<Double> TempP,Input<Double> cyclesPer10Sec,Button test,Button apply,Button run)
	{
		super();
		this.TempP=TempP;
		this.test=test;
		this.apply=apply;
		this.cyclesPer10Sec=cyclesPer10Sec;
		this.run=run;

		realP=new DashboardInput("PID Tuner P");
		realI=new DashboardInput("PID Tuner I");
		realD=new DashboardInput("PID Tuner D");
		
		apply.setPressAction(new ButtonAction(){
			@Override
			public void onAction() {
				recalculatePIDs();
			}});
		
		
	}
	
	public PIDThingy(Button runButton) {
		this(new DashboardInput("Temp P"), new DashboardInput("Cycles/10sec"), new DashboardButton("Test"), new DashboardButton("Apply"), runButton);
	}
	
	public void recalculatePIDs() {
		if(cyclesPer10Sec.get()==0.0)
		{
			return;
		}
		double period=0.1/cyclesPer10Sec.get();

		realP.set(0.6*TempP.get());
		realI.set(2/period);
		realD.set(period/8);
	}
	public PID copy()
	{
		return new PIDThingy(TempP,cyclesPer10Sec,test,apply,run).setInput(getInput()).setMinMax(minIn,maxIn,minOut,maxOut);
	}
	public void update()
	{
		
		if(test.get())
		{
			super.setPID(TempP.get(),0,0);
		}
		else if(run.get())
		{
			super.setPID(realP.get(), realI.get(), realD.get());
		}
		else
		{
			super.setPID(0, 0, 0);
		}
		super.update();
		Debug.msg("PID Tuner Test Mode",test.get()?"Enabled":"Disabled");
		Debug.msg("PID Tuner Active",run.get()?"Enabled":"Disabled");
		Debug.msg("PID Tuner Test P",TempP.get());
	}
}