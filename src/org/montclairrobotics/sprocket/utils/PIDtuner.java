package org.montclairrobotics.sprocket.utils;

import org.montclairrobotics.sprocket.control.Button;
import org.montclairrobotics.sprocket.control.ButtonAction;

public class PIDTuner extends PID
{
	private Input<Double> TempP;
	private Button test;
	private Button apply;
	private double realP,realI,realD;
	private Input<Double> cyclesPerSec;

	public PIDTuner(Input<Double> P,Input<Double> cyclesPerSec,Button test,Button apply)
	{
		super();
		this.TempP=P;
		this.test=test;
		this.apply=apply;
		this.cyclesPerSec=cyclesPerSec;
		
		apply.setPressAction(new ButtonAction(){
			@Override
			public void onAction() {
				recalculatePIDs();
			}});
	}
	
	public void recalculatePIDs() {
		if(cyclesPerSec.get()==0.0)
		{
			return;
		}
		double period=1/cyclesPerSec.get();
		realP=0.6*TempP.get();
		realI=2/period;
		realD=period/8;
	}
	public PID copy()
	{
		return new PIDTuner(TempP,cyclesPerSec,test,apply).setInput(getInput()).setMinMax(minIn,maxIn,minOut,maxOut);
	}
	public void update()
	{
		if(test.get())
		{
			super.setPID(TempP.get(),0,0);
		}
		else if(apply.get())
		{
			super.setPID(realP, realI, realD);
		}
		super.update();
		Debug.msg("PID Tuner Test Mode",test.get()?"Enabled":"Disabled");
		Debug.msg("PID Tuner Active",test.get()?"Enabled":"Disabled");
		Debug.msg("PID Tuner Test P",TempP.get());
		Debug.msg("PID Tuner Saved P",realP);
		Debug.msg("PID Tuner Saved I",realI);
		Debug.msg("PID Tuner Saved D",realD);
	}
}