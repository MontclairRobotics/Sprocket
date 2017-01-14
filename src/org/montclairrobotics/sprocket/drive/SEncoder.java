package org.montclairrobotics.sprocket.drive;

import org.montclairrobotics.sprocket.geometry.Inch;

import edu.wpi.first.wpilibj.Encoder;

public class SEncoder {

    private Encoder enc;
    private double ticksPerInch;


    public SEncoder(int a, int b, double ticksPerInch, boolean reverse) {
        enc = new Encoder(a, b, reverse);
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

    public Inch getInches() {
        return new Inch(getTicks()/ticksPerInch);
    }

    public Inch getSpeed() {
        return new Inch(getTickRate()/ticksPerInch);
    }

    public void reset() {
        enc.reset();
    }

    public Encoder getWPIEncoder() {
        return enc;
    }

}
