package org.montclairrobotics.sprocket.utils;

import edu.wpi.first.wpilibj.Timer;

//MODIFIED FROM:
/**********************************************************************************************
 * Arduino PID AutoTune Library - Version 0.0.1
 * by Brett Beauregard <br3ttb@gmail.com> brettbeauregard.com
 *
 * This Library is ported from the AutotunerPID Toolkit by William Spinelli
 * (http://www.mathworks.com/matlabcentral/fileexchange/4652) 
 * Copyright (c) 2004
 *
 * This Library is licensed under the BSD License:
 * Redistribution and use in source and binary forms, with or without 
 * modification, are permitted provided that the following conditions are 
 * met:
 * 
 *     * Redistributions of source code must retain the above copyright 
 *       notice, this list of conditions and the following disclaimer.
 *     * Redistributions in binary form must reproduce the above copyright 
 *       notice, this list of conditions and the following disclaimer in 
 *       the documentation and/or other materials provided with the distribution
 *       
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" 
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE 
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE 
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE 
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR 
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF 
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS 
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN 
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) 
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE 
 * POSSIBILITY OF SUCH DAMAGE.
 **********************************************************************************************/

public class PIDtuner extends PID{
	
	private static final int MAX_OSCILLATIONS=10;
	
	private enum PeakTypes {FIRST,HIGH,LOW};		
			
	private double lastLoops=Timer.getFPGATimestamp()*1000,loops=lastLoops;
	private double sampleTime;
	private boolean on=false;
	private int peakCount=0;
	private PeakTypes peakType;
	private boolean justchanged;
	private double absMax;
	private double absMin;
	private double setpoint;
	private double outputStart;
	private double oStep;
	private double noiseBand;
	private double[] lastInputs;
	private int nLookBack=0;
	private double peak2=0.0;
	private double peak1=0.0;
	private double[] peaks=new double[MAX_OSCILLATIONS];
	private double Ku=0.0;
	private double Pu=0.0;
	
	public PIDtuner(double P, double I, double D, double minIn, double maxIn,
			double minOut, double maxOut,
			double noiseBand,double outputChange,int samplingTime) {
		super(P, I, D, minIn, maxIn, minOut, maxOut);
		
		this.noiseBand = noiseBand;
		this.oStep = outputChange;
		setLookbackSec(samplingTime);
	}

	public void update()
	{
		if(peakCount>9 && on)
		{
			finish();
			return;
		}
		loops=(int)(Timer.getFPGATimestamp()*1000);
		if((loops-lastLoops)<sampleTime) return;
		lastLoops = loops;
		double refVal = getIn();
		if(!on)
		{ //initialize working variables the first time around
			peakType = PeakTypes.FIRST;
			peakCount=0;
			justchanged=false;
			absMax=refVal;
			absMin=refVal;
			setpoint = refVal;
			on = true;
			outputStart = 0.0;
			setOut(outputStart+oStep);
		}
		else
		{
			if(refVal>absMax)absMax=refVal;
			if(refVal<absMin)absMin=refVal;
		}
		
		//oscillate the output base on the input's relation to the setpoint
		
		if(refVal>setpoint+noiseBand) setOut(outputStart-oStep);
		else if (refVal<setpoint-noiseBand) setOut(outputStart+oStep);
		
	  boolean isMax=true,isMin=true;
	  //id peaks
	  for(int i=lastInputs.length-1;i>=0;i--)
	  {
	    double val = lastInputs[i];
	    if(isMax) isMax = refVal>val;
	    if(isMin) isMin = refVal<val;
	    if(i+1<lastInputs.length)
	    	lastInputs[i+1] = lastInputs[i];
	  }
	  lastInputs[0] = refVal;  
	  if(lastInputs.length<9)
	  {  //we don't want to trust the maxes or mins until the inputs array has been filled
		return;
		}
	  
	  if(isMax)
	  {
	    if(peakType==PeakTypes.FIRST)peakType=PeakTypes.HIGH;
	    if(peakType==PeakTypes.LOW)
	    {
	      peakType = PeakTypes.HIGH;

	      justchanged=true;
	      peak2 = peak1;
	    }
	    peak1 = loops;
	    //peaks[peakCount] = refVal;
	   
	  }
	  else if(isMin)
	  {
	    if(peakType==PeakTypes.FIRST)peakType=PeakTypes.LOW;
	    if(peakType==PeakTypes.HIGH)
	    {
	      peakType=PeakTypes.LOW;
	      peakCount++;
	      justchanged=true;
	    }
	    
	    if(peakCount<10)peaks[peakCount] = refVal;
	  }
	  
	  if(justchanged && peakCount>2)
	  { //we've transitioned.  check if we can autotune based on the last peaks
	    double avgSeparation = (Math.abs(peaks[peakCount-1]-peaks[peakCount-2])+Math.abs(peaks[peakCount-2]-peaks[peakCount-3]))/2;
	    if( avgSeparation < 0.05*(absMax-absMin))
	    {
			finish();
		  return;
		 
	    }
	  }
	   justchanged=false;
	   return;
	}
	void start()
	{
		on=true;
	}
	void finish()
	{
		on=false;
	      //we can generate tuning parameters!
	      Ku = 4*(2*oStep)/((absMax-absMin)*3.14159);
	      Pu = (double)(peak1-peak2);
	}

	double getK()
	{
		return 0.6 * Ku;
	}

	double getI()
	{
		return  1.2*Ku / Pu;  // Ki = Kc/Ti
	}

	double getD()
	{
		return 0.075 * Ku * Pu;  //Kd = Kc * Td
	}

	void setLookbackSec(int value)
	{
		if (value<1) value = 1;
		
		if(value<25)
		{
			lastInputs=new double[value * 4];
			sampleTime = 0.250;
		}
		else
		{
			lastInputs=new double[100];
			sampleTime = value*0.001;
		}
	}	
}
