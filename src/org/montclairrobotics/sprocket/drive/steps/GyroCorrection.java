package org.montclairrobotics.sprocket.drive.steps;

import org.montclairrobotics.sprocket.drive.DTTarget;
import org.montclairrobotics.sprocket.geometry.Angle;
import org.montclairrobotics.sprocket.geometry.Degrees;
import org.montclairrobotics.sprocket.jrapoport.Togglable;
import org.montclairrobotics.sprocket.pipeline.Step;
import org.montclairrobotics.sprocket.utils.Debug;
import org.montclairrobotics.sprocket.utils.Input;
import org.montclairrobotics.sprocket.utils.PID;
import org.montclairrobotics.sprocket.utils.Range;

public class GyroCorrection implements Step<DTTarget>, Togglable {
	private PID pid;
	private boolean enabled = true;
	private boolean used;
	private Angle reset = Angle.ZERO;
	
	private Range outRange = Range.power();
	private double maxError;
	private double farP;
	
	public GyroCorrection(Input<Double> gyro, PID pid, double maxError, double farP) {
		this(pid);
		this.pid.setInput(gyro);
		this.maxError=maxError;
		this.farP=farP;
	}
	
	public GyroCorrection(PID pid, double maxError, double powIfMaxError) {
		this.pid = pid.copy();
		this.pid.setInRange(Range.angleInDegrees());
		this.pid.setOutRange(outRange);

		this.maxError = maxError;
		this.farP = powIfMaxError;
	}
	
	public GyroCorrection(PID pid) {
		this(pid, 180, 1);
	}
	
	public void use() {
		used = true;
	}
	
	public void setOutRange(double min, double max) {
		this.outRange = new Range(min, max);
	}
	
	public void setOutRange(Range r) {
		this.outRange = r;
	}
	
	public void resetOutRange() {
		this.outRange = Range.power();
	}
	
	@Override
	public DTTarget get(DTTarget in) {
		DTTarget out = in;
		if (enabled && used) {
			double target;
			if(Math.abs(pid.getError()) > maxError) {
				target = farP * pid.getError() * (pid.getP() > 0 ? 1 : -1);
			} else {
				target = pid.get();
				target = outRange.constrain(target);
			}
			out = new DTTarget(in.getDirection(), target);
		}
		used = false;
		Debug.print("Gyro Enabled",enabled);
		Debug.print("Gyro Error", pid.getError());
		return out;
	}
	
	public Angle getError() {
		return new Degrees(pid.getError());
	}
	
	public void reset() {
		resetRaw(getCurrentAngleRaw());
	}
	
	public void resetRaw(Angle r) {
		this.reset = r;
	}
	
	public void resetRelative(Angle r) {
		resetRaw(r.add(getCurrentAngleRaw()));
	}
	
	public Angle getTargetAngle() {
		return new Degrees(pid.getTarget());
	}
	
	public Angle getCurrentAngleRaw() {
		return new Degrees(pid.getCurrentInput());
	}
	
	public Angle getCurrentAngleReset() {
		return getCurrentAngleRaw().subtract(reset);
	}
	
	public void setTargetAngleRaw(Angle a) {
		pid.setTarget(a.toDegrees());
	}
	
	public void setTargetAngleReset(Angle a) {
		pid.setTarget(reset.add(a).toDegrees());
	}
	
	public void setTargetAngleRelative(Angle a) {
		pid.setTarget(getCurrentAngleRaw().add(a).toDegrees());
	}
	
	public void setTargetAngleRaw() {
		pid.setTarget(0);
	}
	
	public void setTargetAngleRelative() {
		pid.setTarget(pid.getInput().get());
	}
	
	public void setTargetAngleReset() {
		pid.setTarget(reset.toDegrees());
	}
	
	public PID getPID() {
		return pid;
	}

	public void setTargetAngle(Angle a, boolean relative) {
		if (relative) {
			setTargetAngleRelative(a);
		} else {
			setTargetAngleReset(a);
		}
	}
	
	@Override
	public void enable() {
		enabled = true;
	}
	
	@Override
	public void disable() {
		enabled = false;
	}

	@Override
	public boolean isEnabled() {
		return enabled;
	}

}
