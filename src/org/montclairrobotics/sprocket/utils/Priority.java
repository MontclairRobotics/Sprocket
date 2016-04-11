package org.montclairrobotics.sprocket.utils;

public class Priority {
	public static final Priority HIGHEST = new Priority(Integer.MAX_VALUE);
	public static final Priority HIGH = new Priority(100);
	public static final Priority NORMAL = new Priority(10);
	public static final Priority LOW = new Priority(0);
	public static final Priority LOWEST = new Priority(Integer.MIN_VALUE);
	
	int p;
	
	public static Priority lowerThan(Priority p) {
		if(p.getPriority() == Integer.MIN_VALUE) return new Priority(p);
		else return new Priority(p.getPriority()-1);
	}
	
	public static Priority higherThan(Priority p) {
		if(p.getPriority() == Integer.MAX_VALUE) return new Priority(p);
		else return new Priority(p.getPriority()+1);
	}
	
	public static Priority between(Priority p1, Priority p2) {
		return new Priority((p1.getPriority() + p2.getPriority())/2);
	}
	
	public Priority(int p) {
		this.p = p;
	}
	
	public Priority(Priority p) {
		this.p = p.getPriority();
	}
	
	public int getPriority() {
		return p;
	}
}
