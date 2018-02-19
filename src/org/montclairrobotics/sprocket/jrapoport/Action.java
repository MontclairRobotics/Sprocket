package org.montclairrobotics.sprocket.jrapoport;

public abstract class Action implements Completable, Updatable {

	@Override
	public abstract void start();

	@Override
	public abstract void update();

	@Override
	public abstract boolean isComplete();

	@Override
	public abstract void stop();
	
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

			public void update() {
				tElapsed = System.currentTimeMillis() - tInit;
			}

			public boolean isComplete() {
				return tElapsed > t;
			}

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
			@Override
			public void start() { for (Action a : actions) { a.start(); } }

			@Override
			public void update() { for (Action a : actions) { if (!a.isComplete()) { a.update(); } } }

			@Override
			public boolean isComplete() {
				boolean complete = false;
				
				for (Action a : actions) {
					complete = complete && a.isComplete();
				}
				
				return complete;
			}

			@Override
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
			
			@Override
			public void start() { if (actions.length > 0) { actions[0].start(); } }

			@Override
			public void update() { actions[i].update(); }

			@Override
			public boolean isComplete() {
				if (actions[i].isComplete()) {
					actions[i].stop();
					i++;
				}
				
				return i >= actions.length;
			}

			@Override
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
			@Override
			public void start() {}

			@Override
			public void update() {}

			@Override
			public boolean isComplete() { return a.isComplete(); }
			
			@Override
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
			
			@Override
			public void start() { wait.start(); }

			@Override
			public void update() { wait.update(); }

			@Override
			public boolean isComplete() { return a.isComplete() || wait.isComplete(); }
			
			@Override
			public void stop() { wait.stop(); }
		};
	}
}
