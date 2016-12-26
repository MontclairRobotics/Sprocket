package org.montclairrobotics.sprocket.drive;

import org.montclairrobotics.sprocket.utils.Input;

public class EncoderInput extends Input {

    private SEncoder enc;

    public EncoderInput(SEncoder e) {
        enc = e;
    }

    @Override
    public double get() {
        return enc.getSpeed().get();
    }

    @Override
    public void set(double value) {}
}
