package org.montclairrobotics.sprocket.motors;


import org.montclairrobotics.sprocket.core.IEncoder;
import org.montclairrobotics.sprocket.geometry.Distance;
import org.montclairrobotics.sprocket.utils.Debug;
import org.montclairrobotics.sprocket.utils.Input;


public class SEncoder implements Input<Double> {

    private IEncoder enc;
    private int eId;
    private double ticksPerInch;


    /*public SEncoder(int a, int b, double ticksPerInch, boolean reverse) {
        enc = new Encoder(a, b, reverse);
        eId = a;
        Debug.msg("enc-" + eId, "init");
        this.ticksPerInch = ticksPerInch;
    }

    public SEncoder(int a, int b, double ticksPerInch) {
        this(a, b, ticksPerInch, false);
    }*/

    public SEncoder(IEncoder e, double ticksPerInch) {
        enc = e;
        this.ticksPerInch = ticksPerInch;
    }




    public double getRawDistance() {
        return enc.getDistance();
    }

    public double getRawSpeed() {
        return enc.getSpeed();
    }

    public Distance getDistance() {
        return new Distance(getRawDistance()/ticksPerInch);
    }

    public Distance getSpeed() {
        return new Distance(getRawSpeed()/ticksPerInch);
    }

    public void reset() {
        enc.reset();
    }

    public IEncoder getRawEncoder() {
        return enc;
    }

	@Override
	public Double get() {
		Debug.num("encoder speed-" + eId, this.getSpeed().get());
		return this.getSpeed().get();
	}

}
