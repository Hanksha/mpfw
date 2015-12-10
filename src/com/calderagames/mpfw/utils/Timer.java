package com.calderagames.mpfw.utils;

public class Timer {

	private double time;
	private int delay;
	private double remaining;
	private boolean paused;

	public Timer(int delay) {
		this.delay = delay;
		start();
	}

	/**
	 * Set the delay
	 * @param d in milliseconds
	 */
	public void setDelay(int d) {
		delay = d;
	}

	
	public boolean tick() {
		if(paused)
			return false;
		
		if(delay <= 0)
			return true;
		
		if(time + delay < System.nanoTime() / 1000000d)
			return true;
		
			
		return false;
	}

	public double getRemainingTime() {
		return Math.max(paused ? remaining : (time + delay) - System.nanoTime() / 1000000d, 0);
	}

	public int getDelay() {
		return delay;
	}

	public void start() {
		time = System.nanoTime() / 1000000d;
	}
	
	public void resume() {
		if(!paused)
			return;
		
		paused = false;
		
		time = remaining + (System.nanoTime() / 1000000d) - delay;
	}
	
	public void pause() {
		if(paused)
			return;
		
		paused = true;
		
		remaining = (time + delay) - System.nanoTime() / 1000000d;
	}
	
	public double getPercentRemaining() {
		return (getRemainingTime() / (delay * 1d)) * 100;
	}
	
	public boolean isPaused() {
		return paused;
	}
}
