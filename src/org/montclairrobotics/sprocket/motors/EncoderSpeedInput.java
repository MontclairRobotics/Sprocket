package org.montclairrobotics.sprocket.motors;

import org.montclairrobotics.sprocket.utils.Input;

public class EncoderSpeedInput implements Input<Double> {

    private SEncoder enc;

    public EncoderSpeedInput(SEncoder e) {
        enc = e;
    }

    @Override
    public Double get() {
        return enc.getSpeed().get();
    }
}
