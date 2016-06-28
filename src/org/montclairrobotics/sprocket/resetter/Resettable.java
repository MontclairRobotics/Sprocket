package org.montclairrobotics.sprocket.resetter;

public interface Resettable {
	public void onStop();
	public void startAuto();
	public void startTeleop();
}
