package com.calderagames.mpfw.math;

/**
 * Simple dimension class containing
 * width and height
 */
public class Dimension {

	public int width, height;
	
	public Dimension() {
		width = 0;
		height = 0;
	}

	
	public Dimension(int width, int height) {
		this.width = width;
		this.height = height;
	}
	
	public void set(Dimension dim) {
		width = dim.width;
		height = dim.height;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof Dimension) {
			Dimension dim = (Dimension) obj;
			
			if(dim.width == width && dim.height == height)
				return true;
			else
				return false;
		}
			
		return false;
	}
	
	@Override
	public String toString() {
		return "width: " + width + " height: " + height;
	}
}
