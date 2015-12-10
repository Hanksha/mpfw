package com.calderagames.mpfw.math;

public class Vector3f {

	/**Coordinates of the vector*/
	public float x, y, z;
	
	public Vector3f() {
		x = 0;
		y = 0;
		z = 0;
	}
	
	public Vector3f(float x, float y, float z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public void set(Vector3f v) {
		x = v.x;
		y = v.y;
		z = v.z;
	}
}
