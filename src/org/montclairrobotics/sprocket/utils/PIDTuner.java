package org.montclairrobotics.sprocket.utils;

import org.montclairrobotics.sprocket.actions.Action;
import org.montclairrobotics.sprocket.control.ButtonListener;
import org.montclairrobotics.sprocket.frc.DashboardButton;
import org.montclairrobotics.sprocket.frc.DashboardInput;

public class PIDTuner extends PID
{
	private Input<Double> TempP;
	private Input<Boolean> test;
	private Input<Boolean> apply;
	private Input<Boolean> run;
	private DashboardInput realP,realI,realD;
	private Input<Double> cyclesPer10Sec;

	public PIDTuner(Input<Double> TempP,Input<Double> cyclesPer10Sec,Input<Boolean> test,Input<Boolean> apply,Input<Boolean> run)
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
		
		new ButtonListener(apply).setAction(new Action(){
			@Override
			public void start() {
				recalculatePIDs();
			}});
		
		
	}
	
	public PIDTuner(Input<Boolean> runButton) {
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
	public PIDTuner copy()
	{
		return (PIDTuner)new PIDTuner(TempP,cyclesPer10Sec,test,apply,run).setInput(getInput()).setMinMax(minIn,maxIn,minOut,maxOut);
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