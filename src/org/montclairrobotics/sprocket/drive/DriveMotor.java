package org.montclairrobotics.sprocket.drive;

import org.montclairrobotics.sprocket.utils.Updatable;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.CANTalon.TalonControlMode;

public class DriveMotor implements Updatable {
	
	SpeedController motor;
	Encoder encoder;
	double power;
	
	/**
	 * Plug in a pre-configured SpeedController into the DriveMotor class.
	 * Makes as little modifications as possible
	 * @param motor Motor controller that you want to use
	 */
	public DriveMotor(SpeedController motor) {
		this(motor, null);
	}
	
	/**
	 * Plug in a pre-configured SpeedController and accompanying encoder into the DriveMotor class.
	 * @param motor Motor controller that you want to use
	 * @param encoder Encoder that is directly related to the motor (the motor's speed directly correlates to its rate)
	 */
	public DriveMotor(SpeedController motor, Encoder encoder) {
		this.motor = motor;
		this.encoder = encoder;
	}
	
	/**
	 * Uses a CANTalon to create a wrapper in which Sprocket can work with them.
	 * @param motor CAN Talon that you want to use
	 */
	public DriveMotor(CANTalon motor) {
		this(motor, null);
	}
	
	/**
	 * Uses a CANTalon and an accompanying encoder to create a wrapper in which
	 * Sprocket can work with them.
	 * @param motor CAN Talon that you want to use
	 * @param encoder Encoder that is directly related to the motor
	 */
	public DriveMotor(CANTalon motor, Encoder encoder) {
		motor.changeControlMode(TalonControlMode.PercentVbus);
		motor.reset();
		motor.enable();
		this.motor = motor;
		this.encoder = encoder;
	}
	
	/**
	 * Sets the speed of the motor controller
	 * @param power A value from -1.0 to 1.0 where 1.0 is full forwards, -1.0 is full backwards and 0.0 is stop
	 */
	public void set(double power) {
		this.power = power;
	}
	
	@Override
	public void update() {
		motor.set(power);
	}
	
	/**
	 * Returns raw encoder clicks
	 * @return The total amount of encoder pulses. Returns 0 if encoder is null
	 */
	public int getEncoderClicks() {
		if(encoder != null) {
			return encoder.getRaw();
		} else {
			return 0;
		}
	}
	
	/**
	 * Returns the total distance recorded by the encoder, scaled by the setEncoderDistance value
	 * @return The total distance recorded by the encoder. Returns 0.0 is encoder is null
	 */
	public double getEncoderDistance() {
		if(encoder != null) {
			return encoder.getDistance();
		} else {
			return 0.0;
		}
	}
	
	/**
	 * Sets the distance the encoder travels for each pulse
	 * @param scale The distance for each encoder pulse
	 */
	public void setEncoderDistancePerPulse(double scale) {
		if(encoder != null) {
			encoder.setDistancePerPulse(scale);
		}
	}
	
}