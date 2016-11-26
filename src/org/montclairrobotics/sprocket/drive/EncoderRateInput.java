package org.montclairrobotics.sprocket.drive;

import edu.wpi.first.wpilibj.Encoder;
import org.montclairrobotics.sprocket.utils.Input;

public class EncoderRateInput implements Input {

    private Encoder enc;

    public EncoderRateInput(Encoder enc) {
        this.enc = enc;
    }

    @Override
    public double get() {
        return enc.getRate();
    }
}
