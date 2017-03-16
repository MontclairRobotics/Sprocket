package org.montclairrobotics.sprocket.motors;


import org.montclairrobotics.sprocket.geometry.Distance;
import org.montclairrobotics.sprocket.utils.Debug;
import org.montclairrobotics.sprocket.utils.Input;

import edu.wpi.first.wpilibj.Encoder;

public class SEncoder implements Input<Double> {

    private Encoder enc;
    private int eId;
    private double ticksPerInch;


    public SEncoder(int a, int b, double ticksPerInch, boolean reverse) {
        enc = new Encoder(a, b, reverse);
        eId = a;
        Debug.msg("enc-" + eId, "init");
        this.ticksPerInch = ticksPerInch;
    }

    public SEncoder(int a, int b, double ticksPerInch) {
        this(a, b, ticksPerInch, false);
    }

    public SEncoder(Encoder e, double ticksPerInch) {
        enc = e;
        this.ticksPerInch = ticksPerInch;
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

    public Encoder getWPIEncoder() {
        return enc;
    }

	@Override
	public Double get() {
		Debug.num("encoder speed-" + eId, this.getSpeed().get());
		return this.getSpeed().get();
	}

}
