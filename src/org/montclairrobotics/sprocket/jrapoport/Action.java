package org.montclairrobotics.sprocket.jrapoport;

import org.montclairrobotics.sprocket.loop.Updatable;

/**
 * This is designed to be the main base for every "thing" or action the robot can do
 * This is extended by States, and consequently Auto Modes, 
 * 		so things that can be activated by entering "auto" mode, or "test" mode
 * Also, buttons should take these as their actions, 
 * 		so that you can make something happen if you press a button.
 * This hopefully should make it simple to implement routines, both in autonomous and during teleop
 *
 */

public interface Action extends Completable, Updatable {
	@Override
	public void start();

	@Override
	public void update();

	@Override
	public boolean isComplete();

	@Override
	public void stop();
	
	/**
	 * Tells the item running <tt>this</tt> to wait for a specified amount of time.
	 * @param t the amount of time to wait (in milliseconds).
	 * @return an action that tells the item running <tt>this</tt> to wait for a specified amount of time.
	 */
	public static Action waitMilis(double t) {
		return new Action() {
			long tInit, tElapsed;
			
			public void start() {
				tInit = System.currentTimeMillis();
				tElapsed = 0;
			}

			public void update() { tElapsed = System.currentTimeMillis() - tInit; }

			public boolean isComplete() { return tElapsed > t; }

			public void stop() {}
		};
	}

	/**
	 * Runs a list of actions in parallel (at the same time).
	 * @param actions the list of actions to be performed.
	 * @return an action that runs a list of actions in parallel.
	 */
	public static Action inParallel(Action... actions) {
		return new Action() {
			public void start() { for (Action a : actions) { a.start(); } }

			public void update() { for (Action a : actions) { if (!a.isComplete()) { a.update(); } } }

			public boolean isComplete() {
				boolean complete = false;
				
				for (Action a : actions) {
					complete = complete && a.isComplete();
				}
				
				return complete;
			}

			public void stop() { for (Action a : actions) { a.stop(); } }
		};
	}
	
	/**
	 * Runs a list of actions in series (sequentially).
	 * @param actions the list of actions to be performed.
	 * @return an action that runs a list of actions in series.
	 */
	public static Action inSeries(Action... actions) {
		return new Action() {
			int i = 0;

			public void start() { if (actions.length > 0) { actions[0].start(); } }

			public void update() { actions[i].update(); }

			public boolean isComplete() {
				if (actions[i].isComplete()) {
					actions[i].stop();
					i++;
				}
				
				return i >= actions.length;
			}

			public void stop() {}
		};
	}
	
	/**
	 * Tells the item running <tt>this</tt> to wait for a specific action to finish.
	 * @param a the action being monitored.
	 * @return an action that tells the item running <tt>this</tt> to wait for completion.
	 */
	static Action waitForCompletion(Action a) {
		return new Action() {
			public void start() {}

			public void update() {}

			public boolean isComplete() { return a.isComplete(); }

			public void stop() {}
		};
	}
	
	/**
	 * Tells the item running <tt>this</tt> to wait for a specific action to finish, or until time runs out.
	 * @param a the action being monitored.
	 * @param t the maximum amount of time to wait (in milliseconds).
	 * @return an action that tells the item running <tt>this</tt> to wait for completion, or until time runs out.
	 */
	static Action waitForCompletion(Action a, long t) {
		return new Action() {
			Action wait = Action.waitMilis(t);
			
			public void start() { wait.start(); }

			public void update() { wait.update(); }

			public boolean isComplete() { return a.isComplete() || wait.isComplete(); }

			public void stop() { wait.stop(); }
		};
	}
	
	static Action toggle(Togglable t) {
		return new Action() {
			public void start() {}

			public void update() {
				if (t.isEnabled())
					t.disable();
				else
					t.enable();
			}

			public boolean isComplete() { return true; }

			public void stop() {}
		};
	}
}