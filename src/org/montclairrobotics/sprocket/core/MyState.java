package org.montclairrobotics.sprocket.core;

import org.montclairrobotics.sprocket.geometry.Angle;
import org.montclairrobotics.sprocket.geometry.Vector;
import org.montclairrobotics.sprocket.loop.Priority;
import org.montclairrobotics.sprocket.loop.Updatable;
import org.montclairrobotics.sprocket.loop.Updater;
import org.montclairrobotics.sprocket.utils.Input;
import org.montclairrobotics.sprocket.utils.ZeroInput;

public class MyState {

	public static double maxAccel = 0.0;
	
	public static Input<Vector> absVelocity = ZeroInput.ZERO_VECTOR, absPosition = ZeroInput.ZERO_VECTOR;
	public static Input<Angle> absAngularVelocity = ZeroInput.ZERO_ANGLE, absAngle = ZeroInput.ZERO_ANGLE;
	
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
		public VelocityInput(Input<Vector> local)
		{
			this.local=local;
		}

		@Override
		public Vector get() {
			// TODO Auto-generated method stub
			return local.get().rotate(absAngle.get());
		}
	}
	
	public class PosIntegrator implements Updatable, Input<Vector> {
		private Vector pos;
		public PosIntegrator() {
			Updater.add(this, Priority.INPUT);
			pos=Vector.ZERO;
		}
		
		public void update() {
			pos=pos.add(absVelocity.get().scale(Updater.getLoopTime()));
		}
		
		@Override
		public Vector get() {
			return pos;
		}
		
	}
	
	public class AngleIntegrator implements Updatable,Input<Angle>
	{
		private Angle ang;
		public AngleIntegrator()
		{
			Updater.add(this, Priority.INPUT);
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
