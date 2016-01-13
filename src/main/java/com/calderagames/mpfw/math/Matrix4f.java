package com.calderagames.mpfw.math;

import java.nio.FloatBuffer;

/**
 * Holds a 4 x 4 float matrix.
 */
public class Matrix4f {

	/**Float 2D array that represents the matrix*/
	public float[][] m;
	
	/**Construct a matrix and set it to identity*/
	public Matrix4f() {
		m = new float[][] {
			{1, 0, 0, 0},
			{0, 1, 0, 0},
			{0, 0, 1, 0},
			{0, 0, 0, 1},
		};
	}
	
	/**
	 * Constructs a matrix from a float array.
	 * <p><b>Note:</b> A deep copy of the array is made you can dispose it
	 * or modify it.<p>
	 * @param mat the 4x4 float array representing the matrix 
	 */
	public Matrix4f(float[][] mat) {
		if(mat == null) 
			throw new IllegalArgumentException("The argmunent matrix cannot be null.");
		if(mat.length < 4 || mat.length > 4|| mat[0].length < 4 || mat[0].length > 4) 
			throw new IllegalArgumentException("The argement matrix must be of size 4x4");
		
		m = new float[4][4];
		
		for(int r = 0; r < 4; r++) {
			for(int c = 0; c < 4; c++) {
				m[r][c] = mat[r][c];
			}
		}
	}
	
	/**
	 * Constructs a matrix from another matrix.
	 * @param mat the 4x4 float array representing the matrix 
	 */
	public Matrix4f(Matrix4f mat) {
		if(mat == null) 
			throw new IllegalArgumentException("The argmunent matrix cannot be null.");
		
		m = new float[4][4];
		
		for(int r = 0; r < 4; r++) {
			for(int c = 0; c < 4; c++) {
				m[r][c] = mat.m[r][c];
			}
		}
	}
	
	/**
	 * Loads from a float buffer. The buffer stores the matrix in column major
	 * (OpenGL) order.
	 *
	 * @param buffer a float buffer to read from
	 */
	public void load(FloatBuffer buffer) {
		m[0][0] = buffer.get();
		m[0][1] = buffer.get();
		m[0][2] = buffer.get();
		m[0][3] = buffer.get();
		
		m[1][0] = buffer.get();
		m[1][1] = buffer.get();
		m[1][2] = buffer.get();
		m[1][3] = buffer.get();
		
		m[2][0] = buffer.get();
		m[2][1] = buffer.get();
		m[2][2] = buffer.get();
		m[2][3] = buffer.get();
		
		m[3][0] = buffer.get();
		m[3][1] = buffer.get();
		m[3][2] = buffer.get();
		m[3][3] = buffer.get();
	}
	
	/**
	 * Stores this matrix in a float buffer. The matrix is stored in column
	 * major (openGL) order.
	 * @param buf the buffer to store this matrix in
	 */
	public void store(FloatBuffer buffer) {
		buffer.put(m[0][0]);
		buffer.put(m[0][1]);
		buffer.put(m[0][2]);
		buffer.put(m[0][3]);
		buffer.put(m[1][0]);
		buffer.put(m[1][1]);
		buffer.put(m[1][2]);
		buffer.put(m[1][3]);
		buffer.put(m[2][0]);
		buffer.put(m[2][1]);
		buffer.put(m[2][2]);
		buffer.put(m[2][3]);
		buffer.put(m[3][0]);
		buffer.put(m[3][1]);
		buffer.put(m[3][2]);
		buffer.put(m[3][3]);
	}
	
	public Matrix4f set(float m00, float m01, float m02, float m03,
	                    float m10, float m11, float m12, float m13,
	                    float m20, float m21, float m22, float m23,
	                    float m30, float m31, float m32, float m33
	                    ){
		
		m[0][0] = m00;
		m[0][1] = m01;
		m[0][2] = m02;
		m[0][3] = m03;
		
		m[1][0] = m10;
		m[1][1] = m11;
		m[1][2] = m12;
		m[1][3] = m13;
		
		m[2][0] = m20;
		m[2][1] = m21;
		m[2][2] = m22;
		m[2][3] = m23;
		
		m[3][0] = m30;
		m[3][1] = m31;
		m[3][2] = m32;
		m[3][3] = m33;
		
		return this;
	}
	
	/**Set the matrix to identity*/
	public void identity() {
		m[0][0] = 1;
		m[0][1] = 0;
		m[0][2] = 0;
		m[0][3] = 0;
		
		m[1][0] = 0;
		m[1][1] = 1;
		m[1][2] = 0;
		m[1][3] = 0;
		
		m[2][0] = 0;
		m[2][1] = 0;
		m[2][2] = 1;
		m[2][3] = 0;
		
		m[3][0] = 0;
		m[3][1] = 0;
		m[3][2] = 0;
		m[3][3] = 1;
	}
	
	public Matrix4f transpose() {
		return set(
		           m[0][0], m[1][0], m[2][0], m[3][0],
		           m[0][1], m[1][1], m[2][1], m[3][1],
		           m[0][2], m[1][2], m[2][2], m[3][2],
		           m[0][3], m[1][3], m[2][3], m[3][3]
		           );
	}
	
	/**
	 * Adds 2 matrices together, the result is stored
	 * in a new {@link Matrix4f} and returned.
	 * @param mat the matrix to add
	 * @return a new {@link Matrix4f}
	 */
	public Matrix4f add(Matrix4f mat) {
		Matrix4f rslt = new Matrix4f();
		
		rslt.m[0][0] = m[0][0] + mat.m[0][0];
		rslt.m[0][1] = m[0][1] + mat.m[0][1];
		rslt.m[0][2] = m[0][2] + mat.m[0][2];
		rslt.m[0][3] = m[0][3] + mat.m[0][3];

		rslt.m[1][0] = m[1][0] + mat.m[1][0];
		rslt.m[1][1] = m[1][1] + mat.m[1][1];
		rslt.m[1][2] = m[1][2] + mat.m[1][2];
		rslt.m[1][3] = m[1][3] + mat.m[1][3];
		
		rslt.m[2][0] = m[2][0] + mat.m[2][0];
		rslt.m[2][1] = m[2][1] + mat.m[2][1];
		rslt.m[2][2] = m[2][2] + mat.m[2][2];
		rslt.m[2][3] = m[2][3] + mat.m[2][3];
		
		rslt.m[3][0] = m[3][0] + mat.m[3][0];
		rslt.m[3][1] = m[3][1] + mat.m[3][1];
		rslt.m[3][2] = m[3][2] + mat.m[3][2];
		rslt.m[3][3] = m[3][3] + mat.m[3][3];
		
		return rslt;
	}
	
	/**
	 * Subtracts a matrix to another, the result is stored
	 * in a new {@link Matrix4f} and returned.
	 * @param mat the matrix to subtract
	 * @return a new {@link Matrix4f}
	 */
	public Matrix4f sub(Matrix4f mat) {
		Matrix4f rslt = new Matrix4f();
		
		rslt.m[0][0] = m[0][0] - mat.m[0][0];
		rslt.m[0][1] = m[0][1] - mat.m[0][1];
		rslt.m[0][2] = m[0][2] - mat.m[0][2];
		rslt.m[0][3] = m[0][3] - mat.m[0][3];

		rslt.m[1][0] = m[1][0] - mat.m[1][0];
		rslt.m[1][1] = m[1][1] - mat.m[1][1];
		rslt.m[1][2] = m[1][2] - mat.m[1][2];
		rslt.m[1][3] = m[1][3] - mat.m[1][3];
		
		rslt.m[2][0] = m[2][0] - mat.m[2][0];
		rslt.m[2][1] = m[2][1] - mat.m[2][1];
		rslt.m[2][2] = m[2][2] - mat.m[2][2];
		rslt.m[2][3] = m[2][3] - mat.m[2][3];
	
		rslt.m[3][0] = m[3][0] - mat.m[3][0];
		rslt.m[3][1] = m[3][1] - mat.m[3][1];
		rslt.m[3][2] = m[3][2] - mat.m[3][2];
		rslt.m[3][3] = m[3][3] - mat.m[3][3];
		
		return rslt;
	}
	
	/**
	 * Multiplies the left matrix <code>this</code> by the right matrix
	 * and return the result in a new {@link Matrix4f}.
	 * @param mat the right matrix to multiply
	 * @return a new {@link Matrix4f}
	 */
	public Matrix4f mul(Matrix4f mat) {
		float[][] result = new float[4][4];
		
		for(int r1 = 0; r1 < 4; r1++) {
			for(int c1 = 0; c1 < 4; c1++) {
				for(int r2 = 0; r2 < 4; r2++) {
					result[r1][c1] += m[r1][r2] * mat.m[r2][c1];
				}
			}
		}
		
		return new Matrix4f(result);
	}
	
	/**
	 * Transforms the vector and returns it.
	 * @param v the {@link Vector4f} to be transformed
	 * @return {@link Vector4f} v once transformed
	 */
	public Vector4f transform(Vector4f v) {
		Vector4f temp = new Vector4f();
		
		temp.x = m[0][0] * v.x + m[0][1] * v.y + m[0][2] * v.z + m[0][3] * v.w;
		temp.y = m[1][0] * v.x + m[1][1] * v.y + m[1][2] * v.z + m[1][3] * v.w;
		temp.z = m[2][0] * v.x + m[2][1] * v.y + m[2][2] * v.z + m[2][3] * v.w;
		temp.w = m[3][0] * v.x + m[3][1] * v.y + m[3][2] * v.z + m[3][3] * v.w;
		
		v.set(temp);
		
		return v;
	}
	
	/**Translates the matrix by the provided {@link Vector3f} <code>v</code>*/
	public Matrix4f translate(Vector3f v) {
		m[0][0] = 1;
		m[0][1] = 0;
		m[0][2] = 0;
		m[0][3] = v.x;
		
		m[1][0] = 0;
		m[1][1] = 1;
		m[1][2] = 0;
		m[1][3] = v.y;
		
		m[2][0] = 0;
		m[2][1] = 0;
		m[2][2] = 1;
		m[2][3] = v.z;
		
		m[3][0] = 0;
		m[3][1] = 0;
		m[3][2] = 0;
		m[3][3] = 1;
		
		return this;
	}
	
	/**Scales the matrix by the provided {@link Vector3f} <code>v</code>*/
	public Matrix4f scale(Vector3f v) {
		m[0][0] = v.x;
		m[0][1] = 0;
		m[0][2] = 0;
		m[0][3] = 0;
		
		m[1][0] = 0;
		m[1][1] = v.y;
		m[1][2] = 0;
		m[1][3] = 0;
		
		m[2][0] = 0;
		m[2][1] = 0;
		m[2][2] = v.z;
		m[2][3] = 0;
		
		m[3][0] = 0;
		m[3][1] = 0;
		m[3][2] = 0;
		m[3][3] = 1;
		
		return this;
	}
	
	/**
	 * Rotates the matrix by the provided {@link Vector4f} <code>v</code>.</br>
	 * Use <code>x</code>, <code>y</code> and <code>z</code> to indicate 
	 * (with 1 or 0) the axis of rotation and <code>w</code> for the angle in radians.</br>
	 * One axis at a time, multiply against another transformation to obtain additional rotations.
	 */
	public Matrix4f rotate(Vector4f v) {
		float sin = (float) Math.sin(v.w);
		float cos = (float) Math.cos(v.w);
		
		identity();
		
		if(v.x == 1) {
			m[1][1] = cos;
			m[1][2] = -sin;
			m[2][1] = sin;
			m[2][2] = cos;
		}
		else if(v.y == 1) {
			m[0][0] = cos;
			m[0][2] = sin;
			m[2][0] = -sin;
			m[2][2] = cos;
		}
		else if(v.z == 1) {
			m[0][0] = cos;
			m[0][1] = sin;
			m[1][0] = -sin;
			m[1][1] = cos;
		}
		
		/*float c = (float) Math.cos(v.w);
		float s = (float) Math.sin(v.w);
		float oneminusc = 1.0f - c;
		float xy = v.x * v.y;
		float yz = v.y * v.z;
		float xz = v.x * v.z;
		float xs = v.x * s;
		float ys = v.y * s;
		float zs = v.z * s;

		float f00 = v.x * v.x * oneminusc + c;
		float f01 = xy * oneminusc + zs;
		float f02 = xz * oneminusc - ys;
		// n[3] not used
		float f10 = xy * oneminusc - zs;
		float f11 = v.y * v.y * oneminusc + c;
		float f12 = yz * oneminusc + xs;
		// n[7] not used
		float f20 = xz * oneminusc + ys;
		float f21 = yz * oneminusc - xs;
		float f22 = v.z * v.z * oneminusc + c;

		float t00 = m[0][0] * f00 + m[1][0] * f01 + m[2][0] * f02;
		float t01 = m[0][1] * f00 + m[1][1] * f01 + m[2][1] * f02;
		float t02 = m[0][2] * f00 + m[1][2] * f01 + m[2][2] * f02;
		float t03 = m[0][3] * f00 + m[1][3] * f01 + m[2][3] * f02;
		float t10 = m[0][0] * f10 + m[1][0] * f11 + m[2][0] * f12;
		float t11 = m[0][1] * f10 + m[1][1] * f11 + m[2][1] * f12;
		float t12 = m[0][2] * f10 + m[1][2] * f11 + m[2][2] * f12;
		float t13 = m[0][3] * f10 + m[1][3] * f11 + m[2][3] * f12;
		m[2][0] = m[0][0] * f20 + m[1][0] * f21 + m[2][0] * f22;
		m[2][1] = m[0][1] * f20 + m[1][1] * f21 + m[2][1] * f22;
		m[2][2] = m[0][2] * f20 + m[1][2] * f21 + m[2][2] * f22;
		m[2][3] = m[0][3] * f20 + m[1][3] * f21 + m[2][3] * f22;
		m[0][0] = t00;
		m[0][1] = t01;
		m[0][2] = t02;
		m[0][3] = t03;
		m[1][0] = t10;
		m[1][1] = t11;
		m[1][2] = t12;
		m[1][3] = t13;*/

		return this;
	}
	
	/**
     * Set this matrix to be an orthographic projection transformation.
     * @param left the distance from the center to the left frustum edge
     * @param right the distance from the center to the right frustum edge
     * @param bottom the distance from the center to the bottom frustum edge
     * @param top the distance from the center to the top frustum edge
     * @param zNear near clipping plane distance
     * @param zFar far clipping plane distance
     */
    public void setOrtho(float left, float right, float bottom, float top, float zNear, float zFar) {
        m[0][0] = 2.0f / (right - left);
        m[0][1] = 0.0f;
        m[0][2] = 0.0f;
        m[0][3] = 0.0f;
        m[1][0] = 0.0f;
        m[1][1] = 2.0f / (top - bottom);
        m[1][2] = 0.0f;
        m[1][3] = 0.0f;
        m[2][0] = 0.0f;
        m[2][1] = 0.0f;
        m[2][2] = -2.0f / (zFar - zNear);
        m[2][3] = 0.0f;
        m[3][0] = -(right + left) / (right - left);
        m[3][1] = -(top + bottom) / (top - bottom);
        m[3][2] = -(zFar + zNear) / (zFar - zNear);
        m[3][3] = 1.0f;
    }
	
	@Override
	public String toString() {
		String str = "";
		
		for(float[] row: m) {
			for(float col: row)
				str += col + " ";
			
			str+="\n";
		}
		
		return str;
	}
}
