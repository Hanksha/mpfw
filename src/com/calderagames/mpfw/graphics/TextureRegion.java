package com.calderagames.mpfw.graphics;

/**
 * Defines a rectangular region of a texture. The coordinate system used has its origin in the upper left corner with the x-axis
 * pointing to the right and the y axis pointing downwards.
 */
public class TextureRegion {

	/**Reference of the texture*/
	private Texture texture;

	/**Coordinates of the region*/
	private float[] texCoords;

	/**Constructs an uninitialized texture region with no reference texture*/
	public TextureRegion() {
		texCoords = new float[8];
	}
	
	/**
	 * Constructs a region of the specified texture
	 * @param texture texture of the region
	 * @param x top-left corner of the region
	 * @param y top-left corner of the region
	 * @param width the width of the region (can be negative to flip the sprite)
	 * @param height the height of the region (can be negative to flip the sprites)
	 */
	public TextureRegion(Texture texture, float x, float y, float width, float height) {
		this.texture = texture;

		x /= texture.getWidth();
		y /= texture.getHeight();
		width /= texture.getWidth();
		height /= texture.getHeight();

		texCoords = new float[] { x, y, //top left
		                          x + width, y, //top right
		                          x + width, y + height, //bottom right
		                          x, y + height //bottom left 
		                      	};
	}

	/**Make a swallow copy of the coordinates array*/
	public TextureRegion(float[] coords) {
		texCoords = coords;
	}

	/**Returns a float array of the region coordinates*/
	public float[] getRegion() {
		return texCoords;
	}

	/**Returns the texture associated with the region*/
	public Texture getTexture() {
		return texture;
	}
}
