package com.calderagames.mpfw.graphics;

/**Holds the texture region and dimension for drawing 2d sprite using SpriteBatch.*/
public class Sprite {
	
	public TextureRegion texRegion;
	public float width;
	public float height;
	public float x, y;
	public float[] vertices;
	public float originX, originY;
	public float rotation;
	public float scaleX, scaleY;
	public boolean flipX, flipY;
	public Color color;
	public boolean dirty;
	
	private Sprite() {
		color = new Color(1f, 1f, 1f, 1f);
		rotation = 0;
		originX = originY = 0;
		scaleX = scaleY = 1;
		vertices = new float[8];
		dirty = true;
	}
	
	public Sprite(float width, float height, TextureRegion texRegion) {
		this();
		this.width = width;
		this.height = height;
		this.texRegion = texRegion;
	}

	public TextureRegion getTexRegion() {
		return texRegion;
	}

	public void setTexRegion(TextureRegion texRegion) {
		this.texRegion = texRegion;
	}

	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
		dirty = true;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
		dirty = true;
	}

	public float getOriginX() {
		return originX;
	}

	public void setOriginX(float originX) {
		this.originX = originX;
		dirty = true;
	}

	public float getOriginY() {
		return originY;
	}

	public void setOriginY(float originY) {
		this.originY = originY;
	}

	public void setOriginCenter() {
		originX = width / 2;
		originY = height / 2;
		dirty = true;
	}
	
	public float getRotation() {
		return rotation;
	}

	public void setRotation(float rotate) {
		this.rotation = rotate;
		dirty = true;
	}

	public float getScaleX() {
		return scaleX;
	}

	public void setScaleX(float scaleX) {
		this.scaleX = scaleX;
		dirty = true;
	}

	public float getScaleY() {
		return scaleY;
	}

	public void setScaleY(float scaleY) {
		this.scaleY = scaleY;
		dirty = true;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color.setColor(color);
	}

	public void setWidth(float width) {
		this.width = width;
		dirty = true;
	}

	public void setHeight(float height) {
		this.height = height;
		dirty = true;
	}

	public float getWidth() {
		return width;
	}

	public float getHeight() {
		return height;
	}
	
	public boolean isFlipX() {
		return flipX;
	}

	public void setFlipX(boolean flipX) {
		this.flipX = flipX;
	}

	public boolean isFlipY() {
		return flipY;
	}

	public void setFlipY(boolean flipY) {
		this.flipY = flipY;
	}

	public float[] getVertices() {
		
		if(dirty) {
			dirty = false;

			float localX = -originX;
			float localY = -originY;
			float localX2 = localX + width;
			float localY2 = localY + height;
			float worldOriginX = this.x - localX;
			float worldOriginY = this.y - localY;
			if (scaleX != 1 || scaleY != 1) {
				localX *= scaleX;
				localY *= scaleY;
				localX2 *= scaleX;
				localY2 *= scaleY;
			}
			if (rotation != 0) {
				final float cos = (float) Math.cos(Math.toRadians(rotation));
				final float sin = (float) Math.sin(Math.toRadians(rotation));
				final float localXCos = localX * cos;
				final float localXSin = localX * sin;
				final float localYCos = localY * cos;
				final float localYSin = localY * sin;
				final float localX2Cos = localX2 * cos;
				final float localX2Sin = localX2 * sin;
				final float localY2Cos = localY2 * cos;
				final float localY2Sin = localY2 * sin;

				final float x1 = localXCos - localYSin + worldOriginX;
				final float y1 = localYCos + localXSin + worldOriginY;
				final float x2 = localXCos - localY2Sin + worldOriginX;
				final float y2 = localY2Cos + localXSin + worldOriginY;
				final float x3 = localX2Cos - localY2Sin + worldOriginX;
				final float y3 = localY2Cos + localX2Sin + worldOriginY;
				
				vertices[0] = x1;
				vertices[1] = y1;

				vertices[2] = x1 + (x3 - x2);
				vertices[3] = y3 - (y2 - y1);

				vertices[4] = x3;
				vertices[5] = y3;

				vertices[6] = x2;
				vertices[7] = y2;

			} else {
				final float x1 = localX + worldOriginX;
				final float y1 = localY + worldOriginY;
				final float x2 = localX2 + worldOriginX;
				final float y2 = localY2 + worldOriginY;

				vertices[0] = x1;
				vertices[1] = y1;

				vertices[2] = x2;
				vertices[3] = y1;

				vertices[4] = x2;
				vertices[5] = y2;

				vertices[6] = x1;
				vertices[7] = y2;
			}
		}
		
		return vertices;
	}
}
