package com.calderagames.mpfw.math;

public class Vector4f {

	/**Coordinates of the vector*/
	public float x, y, z, w;
	
	public Vector4f() {
		x = 0;
		y = 0;
		z = 0;
		w = 0;
	}
	
	public Vector4f(float x, float y, float z, float w) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.w = w;
	}
	
	public void set(Vector4f v) {
		x = v.x;
		y = v.y;
		z = v.z;
		w = v.w;
	}
	
	@Override
	public String toString() {
		return "(" + x + ", " + y + ", " + z + ", " + w + ")";
	}
}
