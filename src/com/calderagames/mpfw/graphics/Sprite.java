package com.calderagames.mpfw.graphics;

import com.calderagames.mpfw.math.Dimension;

public class Sprite {

	/**Texture region of the sprite*/
	private TextureRegion texReg;
	/**Dimension of the sprite*/
	private Dimension dim;
	
	/**Constructs an uninitialized sprite without texture region or dimension*/
	public Sprite() {
		dim = new Dimension();
		texReg = new TextureRegion();
	}
	
	/**
	 * Constructs a sprite with the provided dimension and texture region
	 * @param width the width of the sprite
	 * @param height the height of the sprite
	 * @param texReg the texture region
	 */
	public Sprite(int width, int height, TextureRegion texReg) {
		dim = new Dimension(width, height);
		this.texReg = texReg;
	}
	
	/**Set the width of the sprite*/
	public void setWidth(int width) {
		dim.width = width;
	}
	
	/**Set the height of the sprite*/
	public void setHeight(int height) {
		dim.height = height;
	}
	
	/**Set the texture region*/
	public void setTexRegion(TextureRegion texReg) {
		this.texReg = texReg;
	}
	
	/**Returns the dimension of the sprite*/
	public Dimension getDimension() {
		return dim;
	}
	
	/**Returns the width of the sprite*/
	public int getWidth() {
		return dim.width;
	}
	
	/**Returns the height of the sprite*/
	public int getHeight() {
		return dim.height;
	}
	
	/**Returns the texture region associated with the sprite*/
	public TextureRegion getTexRegion() {
		return texReg;
	}
}
