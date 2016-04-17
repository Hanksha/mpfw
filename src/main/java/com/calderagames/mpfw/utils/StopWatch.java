package com.calderagames.mpfw.utils;

public class StopWatch {
	private long startTime;
	private Unit unit;
	
	public StopWatch() {
		this(Unit.MILLISECOND);
	}
	
	public StopWatch(Unit unit) {
		this.unit = unit;
	}
	
	public void start() {
		startTime = System.nanoTime();
	}
	
	public double getTime() {
		return (System.nanoTime() - startTime) / unit.div;
	}
	
	@Override
	public String toString() {
		return getTime() + unit.name;
	}
	
	public enum Unit {
		SECOND(1000000000d, "s"), 
		MILLISECOND(1000000d, "ms"), 
		NANOSECOND(1d, "ns");
		
		public final double div;
		public final String name;
		
		private Unit(double div, String name) {
			this.div = div;
			this.name= name;
		}
	}
}
