package org.montclairrobotics.sprocket.motors;


import org.montclairrobotics.sprocket.geometry.Distance;
import org.montclairrobotics.sprocket.utils.Debug;
import org.montclairrobotics.sprocket.utils.Input;

import edu.wpi.first.wpilibj.Encoder;

public class SEncoder implements Input<Double> {

    private Encoder enc;
    private int eId;
    private double ticksPerInch;
    public double maxSpeed;


    public SEncoder(int a, int b, double ticksPerInch, double maxSpeed, boolean reverse) {
        enc = new Encoder(a, b, reverse);
        eId = a;
        Debug.msg("enc-" + eId, "init");
        this.ticksPerInch = ticksPerInch;
        this.maxSpeed = maxSpeed;
    }

    public SEncoder(int a, int b, double ticksPerInch, double maxSpeed) {
        this(a, b, ticksPerInch, maxSpeed, false);
    }

    public SEncoder(Encoder e, double ticksPerInch, double maxSpeed) {
        enc = e;
        this.ticksPerInch = ticksPerInch;
        this.maxSpeed = maxSpeed;
    }




    public int getTicks() {
        return enc.getRaw();
    }

    public double getTickRate() {
        return enc.getRate();
    }

    public Distance getInches() {
        return new Distance(getTicks()/ticksPerInch);
    }

    public Distance getSpeed() {
        return new Distance(getTickRate()/ticksPerInch);
    }

    public void reset() {
        enc.reset();
    }

    public double getScaledSpeed(){
        return getTickRate() / maxSpeed;
    }

    public Encoder getWPIEncoder() {
        return enc;
    }

	@Override
	public Double get() {
		Debug.num("encoder speed-" + eId, this.getSpeed().get());
		return this.getSpeed().get();
	}

}
