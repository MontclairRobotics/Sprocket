package org.montclairrobotics.sprocket.drive;

import org.montclairrobotics.sprocket.utils.Input;

public class EncoderInput implements Input<Double> {

    private SEncoder enc;

    public EncoderInput(SEncoder e) {
        enc = e;
    }

    @Override
    public Double get() {
        return enc.getSpeed().get();
    }
}
