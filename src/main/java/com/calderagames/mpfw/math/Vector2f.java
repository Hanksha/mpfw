package com.calderagames.mpfw.math;

public class Vector2f {

	/**Coordinates of the vector*/
	public float x, y;
	
	public Vector2f() {
		x = 0;
		y = 0;
	}
	
	public Vector2f(float x, float y) {
		this.x = x;
		this.y = y;
	}
	
	public void set(Vector2f v) {
		x = v.x;
		y = v.y;
	}
}
