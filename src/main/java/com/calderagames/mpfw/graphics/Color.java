package com.calderagames.mpfw.graphics;

/**
 * Holds the red, green and blue value of a color. 
 */
public class Color {

	public float r, g, b, a;

	/**
	 * Constructs a default Color rgb(1f, 1f, 1f, 1f)
	 */
	public Color() {
		r = 1f;
		g = 1f;
		b = 1f;
		a = 1f;
	}

	/**
	 * Constructs a Color with the provided values
	 * @param r the red value between 0 and 1
	 * @param g the green value between 0 and 1
	 * @param b the blue value between 0 and 1
	 * @param a the alpha value between 0 and 1
	 */
	public Color(float r, float g, float b, float a) {
		this.r = r;
		this.g = g;
		this.b = b;
		this.a = a;
	}
	
	/**
	 * Constructs a Color from another Color
	 * @param color the color to copy from
	 */
	public Color(Color color) {
		this.r = color.r;
		this.g = color.g;
		this.b = color.b;
		this.a = color.a;
	}

	/**See {@link #Color(float, float, float, float)} with <code> Color.a</code> set to 1f.*/
	public Color(float r, float g, float b) {
		this.r = r;
		this.g = g;
		this.b = b;
		this.a = 1f;
	}

	public Color(int r, int g, int b, int a) {
		this.r = r / 255f;
		this.g = g / 255f;
		this.b = b / 255f;
		this.a = a / 255f;
	}

	public Color(int r, int g, int b) {
		this.r = r / 255f;
		this.g = g / 255f;
		this.b = b / 255f;
		this.a = 1f;
	}

	public void setColor(float r, float g, float b, float a) {
		this.r = r;
		this.g = g;
		this.b = b;
		this.a = a;
	}

	public void setColor(int r, int g, int b, int a) {
		this.r = r / 255f;
		this.g = g / 255f;
		this.b = b / 255f;
		this.a = a / 255f;
	}

	public void setColor(Color color) {
		r = color.r;
		g = color.g;
		b = color.b;
		a = color.a;
	}
}