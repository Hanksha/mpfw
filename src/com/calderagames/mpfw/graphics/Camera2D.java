package com.calderagames.mpfw.graphics;

import com.calderagames.mpfw.math.Vector2f;

public class Camera2D {

	/**Position of the camera on x/y coordinates*/
	private Vector2f pos;
	
	/**Bounds of the camera*/
	private float xmin, xmax, ymin, ymax;
	
	/**Constructs an uninitialized Camera2D*/
	public Camera2D() {
		pos = new Vector2f();
	}
	
	/**
	 * Constructs a Camera2D with the give position and bounds.
	 * @param position the initial position x/y of the camera
	 * @param xmin the minimum value of the x-coordinate
	 * @param xmax the maximum value of the x-coordinate
	 * @param ymin the minimum value of the y-coordinate
	 * @param ymax the maximum value of the y-coordinate
	 */
	public Camera2D(Vector2f position, float xmin, float xmax, float ymin, float ymax) {
		pos = new Vector2f();
		pos.set(position);
		this.xmin = xmin;
		this.xmax = xmax;
		this.ymin = ymin;
		this.ymax = ymax;
		
		checkBounds();
	}

	/**Sets the position on the x axis*/
	public void setX(float x) {
		pos.x = x;
		checkBounds();
	}
	
	/**Sets the position on the y axis*/
	public void setY(float y) {
		pos.y = y;
		checkBounds();
	}

	/**Gets the position on the x axis*/
	public float getX() {
		return pos.x;
	}

	/**Gets the position on the y axis*/
	public float getY() {
		return pos.y;
	}
	
	/**Check if the position is not out of bounds*/
	private void checkBounds() {
		if(pos.x < xmin)
			pos.x = xmin;
		else if(pos.x > xmax)
			pos.x = xmax;
		if(pos.y < ymin)
			pos.y = ymin;
		else if(pos.y > ymax)
			pos.y = ymax;
	}
}
