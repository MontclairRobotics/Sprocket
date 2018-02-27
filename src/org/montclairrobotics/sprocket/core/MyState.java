package org.montclairrobotics.sprocket.core;

import org.montclairrobotics.sprocket.geometry.Angle;
import org.montclairrobotics.sprocket.geometry.Degrees;
import org.montclairrobotics.sprocket.geometry.Vector;
import org.montclairrobotics.sprocket.loop.Priority;
import org.montclairrobotics.sprocket.loop.Updatable;
import org.montclairrobotics.sprocket.loop.Updater;
import org.montclairrobotics.sprocket.utils.Input;
import org.montclairrobotics.sprocket.utils.VectorInput;

public class MyState {
	public static double maxAccel = 0.0;
	
	public static Input<Vector> absVelocity = VectorInput.ZERO, absPosition = VectorInput.ZERO;
	public static Input<Angle> absAngularVelocity = new Input<Angle>() { public Angle get() { return new Degrees(0); } }, absAngle = absAngularVelocity;
	
	private Vector zeroPos;
	private Angle zeroAngle;
	
	public MyState() {
		this(absPosition.get(), absAngle.get());
	}
	
	public MyState(Vector zeroPos, Angle zeroAngle) {
		this.zeroPos = zeroPos;
		this.zeroAngle = zeroAngle;
	}
	
	public Vector getRelVelocity() {
		return absVelocity.get().rotate(zeroAngle.negative());
	}
	
	public Vector getRelPos() {
		return absPosition.get().subtract(zeroPos).rotate(zeroAngle.negative());
	}
	
	public Angle getAngularVelocity() {
		return absAngularVelocity.get();
	}
	
	public Angle getAngle() {
		return zeroAngle.subtract(zeroAngle);
	}
	
	public class VelocityInput implements Input<Vector> {
		private Input<Vector> local;
		public VelocityInput(Input<Vector> local) {
			this.local=local;
		}

		@Override
		public Vector get() {
			return local.get().rotate(absAngle.get());
		}
	}
	
	public class PosIntegrator implements Updatable, Input<Vector> {
		private Vector pos;
		public PosIntegrator() {
			Updater.add(this, Priority.NORMAL);
			pos=Vector.ZERO;
		}
		
		public void update() {
			pos = pos.add(absVelocity.get().scale(Updater.getLoopTime()));
		}
		
		@Override
		public Vector get() {
			return pos;
		}
	}
	
	public class AngleIntegrator implements Updatable, Input<Angle> {
		private Angle ang;
		
		public AngleIntegrator() {
			Updater.add(this, Priority.NORMAL);
			ang = Angle.ZERO;
		}
		
		@Override
		public Angle get() {
			return ang;
		}

		@Override
		public void update() {
			ang = ang.add(absAngularVelocity.get().times(Updater.getLoopTime()));
		}
	}
}
