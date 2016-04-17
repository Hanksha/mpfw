package com.calderagames.mpfw.animation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import com.calderagames.mpfw.graphics.TextureRegion;

public class Animation {
	/**Array of TextureRegion that represents each frame of the animation*/
	private TextureRegion[] frames;
	/**Array of duration in seconds for each frame (one duration per frame)*/
	private float[] durations;
	private float totalDuration;
	private int frameIndex;
	/**Current time in seconds spent on the current frame*/
	private float currentTime;
	private boolean paused;
	
	public enum PlayMode {
		NORMAL,
		LOOP
	}
	
	private PlayMode playMode;
	
	@SuppressWarnings("unused")
	private Animation() {
	}
	
	public Animation(TextureRegion[] frames, float[] durations, PlayMode playMode) {
		if(frames.length != durations.length)
			throw new IllegalArgumentException("The number of frames should be equal to the number of duration.");
		
		this.frames = new TextureRegion[frames.length];
		for(int i = 0; i < frames.length; i++) {
			this.frames[i] = frames[i];
		}
		this.durations = Arrays.copyOf(durations, durations.length);
		this.playMode = playMode;
		
		for(float duration: durations) {
			totalDuration += duration;
		}
	}
	
	public Animation(TextureRegion[] frames, float[] durations) {
		this(frames, durations, PlayMode.NORMAL);
	}
	
	public Animation(TextureRegion[] frames, float duration, PlayMode playMode) {
		this(frames, new float[frames.length], playMode);
		for(int i = 0; i < durations.length; i++) {
			this.durations[i] = duration;
		}
	}
	
	public Animation(TextureRegion[] frames, float duration) {
		this(frames, duration, PlayMode.NORMAL);
	}
	
	public void update(float dt) {
		if(!paused) {
			currentTime += dt;
			
			if(currentTime > durations[frameIndex]) {
				currentTime -= durations[frameIndex];
				frameIndex++;
				if(frameIndex >= frames.length) {
					if(playMode == PlayMode.LOOP) {
						frameIndex = 0;
					}
					else {
						frameIndex = frames.length - 1;
						stop();
					}
				}
			}
		}
	}
	
	public void start() {
		paused = false;
	}
	
	public void stop() {
		paused = true;
	}
	
	public void setPlayMode(PlayMode playMode) {
		this.playMode = playMode;
	}
	
	public PlayMode getPlayMode() {
		return playMode;
	}
	
	public TextureRegion getFrame() {
		return frames[frameIndex];
	}
	
	public float getDuration() {
		return durations[frameIndex];
	}
	
	public float getCurrentTime() {
		return currentTime;
	}
	
	public int getCurrentFrameIndex() {
		return frameIndex;
	}
	
	public TextureRegion[] getFrames() {
		return frames;
	}
	
	public float[] getDurations() {
		return durations;
	}
	
	public float getTotalDuration() {
		return totalDuration;
	}
	
	public boolean isPaused() {
		return paused;
	}
	
	public static class Builder {
		private ArrayList<TextureRegion> frames;
		private ArrayList<Float> durations;
		private PlayMode playMode = PlayMode.NORMAL;
		
		public Builder() {
			frames = new ArrayList<>();
			durations = new ArrayList<>();
		}
		
		public Builder addDurations(Float ... durations) {
			Collections.addAll(this.durations, durations);
			return this;
		}
		
		public Builder addFrames(TextureRegion ... frames) {
			Collections.addAll(this.frames, frames);
			return this;
		}
		
		public Builder setPlayMode(PlayMode playMode) {
			this.playMode = playMode;
			return this;
		}
		
		public Animation build() {
			float[] durations = new float[this.durations.size()];
			int i = 0;
			for(Float f: this.durations)
				durations[i++] = f;
			return new Animation(frames.toArray(new TextureRegion[frames.size()]), durations, playMode);
		}
	}
}
