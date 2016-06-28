package org.montclairrobotics.sprocket.updater;

/**
 * In practice, some Updatable objects will need to run before others in order to make sure that control
 * system inputs aren't delayed and to make sure that all assisting systems run in the correct order. To facilitate
 * that, Sprocket has a priority system to make sure that objects update in the correct order. Objects will
 * be updated in descending priority order (i.e. highest numerical priority first)
 * @see UpdateClass
 */
public class Priority {
	public static final Priority HIGHEST = new Priority(Integer.MAX_VALUE);
	public static final Priority HIGH = new Priority(100);
	public static final Priority NORMAL = new Priority(0);
	public static final Priority LOW = new Priority(-100);
	public static final Priority LOWEST = new Priority(Integer.MIN_VALUE);
	
	public static final Priority INPUT = new Priority(500);
	public static final Priority PID = before(INPUT);
	public static final Priority CONTROL = new Priority(400);
	public static final Priority CALC = new Priority(300);
	public static final Priority DRIVE_CALC=new Priority(200);
	public static final Priority OUTPUT = new Priority(100);
	
	int p;
	
	/**
	 * Gets a priority lower than the specified priority (i.e. an object with the returned priority
	 * will run after an object with the priority argument)
	 * @param p An object with a higher priority than what you want
	 * @return An object with a lower priority than the original
	 */
	public static Priority lowerThan(Priority p) {
		if(p.getPriority() == Integer.MIN_VALUE) return new Priority(p);
		else return new Priority(p.getPriority()-1);
	}
	
	/**
	 * Gets a priority lower than the specified priority (i.e. an object with the returned priority
	 * will run after an object with the priority argument)
	 * @see #lowerThan(Priority)
	 * @param p An object with a higher priority than what you want
	 * @return An object with a lower priority than the original
	 */
	public static Priority after(Priority p) {
		return lowerThan(p);
	}
	
	/**
	 * Gets a priority higher than the specified priority (i.e. an object with the returned priority
	 * will run before an object with the priority argument)
	 * @param p An object with a lower priority than what you want
	 * @return An object with a higher priority than the original
	 */
	public static Priority higherThan(Priority p) {
		if(p.getPriority() == Integer.MAX_VALUE) return new Priority(p);
		else return new Priority(p.getPriority()+1);
	}
	
	/**
	 * Gets a priority higher than the specified priority (i.e. an object with the returned priority
	 * will run before an object with the priority argument)
	 * @see #higherThan(Priority)
	 * @param p An object with a lower priority than what you want
	 * @return An object with a higher priority than the original
	 */
	public static Priority before(Priority p) {
		return higherThan(p);
	}
	
	/**
	 * Gets a priority between two priorities
	 * @param p1
	 * @param p2
	 * @return A priority between p1 and p2
	 */
	public static Priority between(Priority p1, Priority p2) {
		return new Priority((p1.getPriority() + p2.getPriority())/2);
	}
	
	
	/**
	 * Specifies a priority with an integer
	 * @param p The Priority's level
	 */
	public Priority(int p) {
		this.p = p;
	}
	
	/**
	 * Copies a Priority object
	 * @param p A priority object to be copied
	 */
	public Priority(Priority p) {
		this.p = p.getPriority();
	}
	
	/**
	 * Returns this object's priority level
	 * @return The priority level of this object
	 */
	public int getPriority() {
		return p;
	}
}
