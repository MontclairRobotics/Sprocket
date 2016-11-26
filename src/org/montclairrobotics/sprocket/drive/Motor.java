package org.montclairrobotics.sprocket.drive;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.SpeedController;
import org.montclairrobotics.sprocket.geometry.Distance;
import org.montclairrobotics.sprocket.geometry.Unit;
import org.montclairrobotics.sprocket.updater.Priority;
import org.montclairrobotics.sprocket.updater.Updatable;
import org.montclairrobotics.sprocket.updater.Updater;
import org.montclairrobotics.sprocket.utils.PID;

public class Motor implements Updatable {

    private MotorType type;
    private SpeedController motor;
    private Encoder enc;
    private PID pid;
    private Unit rateUnit = Distance.IN;
    private Unit wheelCircumference = Distance.IN;
    private double pulsesPerRotation;

    private double power;


    /**
     * Constructs a Sprocket default motor that wraps over the WPILib motor and can be interacted with by Sprocket classes
     * @param motor
     * @param type
     */
    public Motor(SpeedController motor, MotorType type) {
        this.motor = motor;
        this.type = type;
        if(type == MotorType.CANTALON) {
            CANTalon srx = (CANTalon) motor;
            srx.changeControlMode(CANTalon.TalonControlMode.PercentVbus);
            srx.enableControl();
        }
        Updater.add(this, Priority.DRIVE_CALC);
    }

    public void setRateUnit(Unit u) {
        rateUnit = u;
        enc.setDistancePerPulse(wheelCircumference.get(rateUnit)/pulsesPerRotation);
    }

    public void setEncoder(Encoder e, double pulsesPerRotation) {
        setEncoder(e);
        enc.setDistancePerPulse(wheelCircumference.get(Distance.IN)/pulsesPerRotation);
        this.pulsesPerRotation = pulsesPerRotation;
    }

    public void setEncoder(Encoder e) {
        enc = e;
    }

    public void buildPIDController(Encoder e, double pulsesPerRotation, double P, double I, double D, Unit wheelCircumference) {
        setPIDController(new PID(P, I, D));
        setEncoder(e, pulsesPerRotation);
        setWheelCircumference(wheelCircumference);
        pid.setInput(new EncoderRateInput(e));
    }

    public void setPIDController(PID pid) {
        this.pid = pid;
    }

    public PID getPIDController() {
        return pid;
    }

    public void setWheelCircumference(Unit u) {
        wheelCircumference = u;
    }

    public void set(double power) {
        this.power = power;
        if(pid != null) pid.setTarget(power);
    }

    public double getPower() {
        return power;
    }

    public double getRate() {
        if(enc != null) {
            return enc.getRate();
        } else {
            return 0.0;
        }
    }

    @Override
    public void update() {
        if(pid == null) {
            motor.set(power);
        } else {
            motor.set(pid.get());
        }

    }
}
