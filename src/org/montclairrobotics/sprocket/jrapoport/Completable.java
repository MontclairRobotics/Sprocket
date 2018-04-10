package org.montclairrobotics.sprocket.jrapoport;

public interface Completable {
	/** Called once when the object is activated. */
	void start();
	
	/** @return whether <tt>this</tt> is finished or successful. */
	boolean isComplete();
	
	/** Called once when the object is deactivated. */
	void stop();
}
