package org.montclairrobotics.sprocket.jrapoport;

import org.montclairrobotics.sprocket.loop.Updatable;
import org.montclairrobotics.sprocket.utils.Input;

/**
 * @author Joshua Rapoport
 * @version 2/27/18
 */

public interface State extends Completable, Updatable {
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
	public static State waitMilis(double t) {
		return new State() {
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
	 * Runs a list of states in parallel (at the same time).
	 * @param states the list of actions to be performed.
	 * @return a state that runs a list of states in parallel.
	 */
	public static State inParallel(State... states) {
		return new State() {
			public void start() { for (State a : states) { a.start(); } }

			public void update() { for (State a : states) { if (!a.isComplete()) { a.update(); } } }

			public boolean isComplete() {
				for (State a : states) {
					if (!a.isComplete())
						return false;
				}
				
				return true;
			}

			public void stop() { for (State a : states) { a.stop(); } }
		};
	}
	
	/**
	 * Runs a list of states in series (sequentially).
	 * @param states the list of states to be performed.
	 * @return a state that runs a list of states in series.
	 */
	public static State inSeries(State... states) {
		return new State() {
			int i = 0;

			public void start() { if (states.length > 0) { states[0].start(); } }

			public void update() { states[i].update(); }

			public boolean isComplete() {
				if (states[i].isComplete()) {
					states[i].stop();
					i++;
				}
				
				return i >= states.length;
			}

			public void stop() {}
		};
	}
	
	/**
	 * Waits for a specific state to finish, or until time runs out.
	 * @param s the state being watched.
	 * @param t the maximum amount of time to wait (in milliseconds).
	 * @return a state that waits until another state is complete, or until time runs out.
	 */
	static State waitForCompletion(State s, long t) {
		return new State() {
			State wait = State.waitMilis(t);
			
			public void start() { wait.start(); }

			public void update() { wait.update(); }

			public boolean isComplete() { return s.isComplete() || wait.isComplete(); }

			public void stop() { wait.stop(); }
		};
	}
	
	/**
	 * Waits for a specific boolean input to return <tt>true</tt>, or until time runs out.
	 * @param c the boolean input being watched.
	 * @param t the maximum amount of time to wait (in milliseconds).
	 * @return a state that waits for the <tt>c.get() == true</tt>, or until time runs out.
	 */
	static State waitForCondition(Input<Boolean> c, long t) {
		return new State() {
			State wait = State.waitMilis(t);
			
			public void start() { wait.start(); }

			public void update() { wait.update(); }

			public boolean isComplete() { return c.get() || wait.isComplete(); }

			public void stop() { wait.stop(); }
		};
	}
	
	static State toggle(Togglable t) {
		return new State() {
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