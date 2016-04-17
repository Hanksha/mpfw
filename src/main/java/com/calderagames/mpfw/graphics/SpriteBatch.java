package com.calderagames.mpfw.graphics;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import com.calderagames.mpfw.Disposable;
import com.calderagames.mpfw.math.Matrix4f;

public class SpriteBatch implements Disposable {

	/**Vertex Array Object id*/
	private int vaoId;
	/**Interleaved VBO id*/
	private int vboId;
	/**Elements VBO id*/
	private int eVboId;
	
	/**Color to be pass in the buffer*/
	private Color color;
	/**Float array that holds all the vertices*/
	private float[] vertices;
	/**Index counter of vertices*/
	private int vertIndex;
	/**Buffer of vertices*/
	private FloatBuffer bufferVert;
	/**Float array that holds all the elements*/
	private int[] elements;
	/**Index counter of elements*/
	private int elemIndex;
	/**Buffer of elements*/
	private IntBuffer bufferElem;

	/**x/y coordinates, reusable*/
	private float x1, y1, x2, y2, x3, y3, x4, y4;
	private float u1, v1, u2, v2, u3, v3, u4, v4;
	
	/**The size of the batch, maximum number of sprite drawn per batch*/
	private int size;
	/**Current number of sprite in the batch*/
	private int counter;
	/**Current texture id*/
	private int currTexId;

	/**The projection matrix*/
	private Matrix4f proj;
	
	/**Default shader program*/
	private ShaderProgram defaultShader;
	/**The current shader program in use for the batch*/
	private ShaderProgram currShader;
	
	private RenderTarget currRenderTarget;
	
	private boolean drawing;
	
	public SpriteBatch(int size) {
		this(size, null);
	}
	
	/**
	 * Constructs a sprite batch with the given size and shader program
	 * @param size the size maximum size of one batch
	 * @param defaultShader the shader program to be use by default
	 */
	public SpriteBatch(int size, ShaderProgram defaultShader) {
		this.size = size;
		
		if(defaultShader == null)
			defaultShader = new ShaderProgram();
		
		this.defaultShader = defaultShader;
		
		currShader = this.defaultShader;
		
		//Default white color
		color = new Color(1f, 1f, 1f, 1f);
		
		//Init vertex array
		vertices = new float[(8 + 8 + 16) * size];
		//Create buffer
		bufferVert = BufferUtils.createFloatBuffer((8 + 8 + 16) * size);
		bufferElem = BufferUtils.createIntBuffer(6 * size);
		elements = new int[6 * size];
		
		//Create VAO and VBO
		//Generate VAO id
		vaoId = GL30.glGenVertexArrays();
		//Bind VAO
		GL30.glBindVertexArray(vaoId);
		
		//Generate interleaved VBO
		vboId = GL15.glGenBuffers();
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vboId);
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, bufferVert, GL15.GL_DYNAMIC_DRAW);
		
		//Stride floatSize = 4 bytes, 2 float per position, 2 for texture coords and 4 for colors
		//Position
		GL20.glEnableVertexAttribArray(0);
		GL20.glVertexAttribPointer(0, 2, GL11.GL_FLOAT, false, 32, 0);//Offset 0
		//Texture coordinates
		GL20.glEnableVertexAttribArray(1);
		GL20.glVertexAttribPointer(1, 2, GL11.GL_FLOAT, false, 32, 8);//Offset 8 (2 * floatSize)
		//Color
		GL20.glEnableVertexAttribArray(2);
		GL20.glVertexAttribPointer(2, 4, GL11.GL_FLOAT, false, 32, 16);//Offset 16 ((2 + 2) * floatSize)
		
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
		
		//Unbind VAO
		GL30.glBindVertexArray(0);
		
		eVboId = GL15.glGenBuffers();
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, eVboId);
		GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, bufferElem, GL15.GL_DYNAMIC_DRAW);
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0);
	}
	
	public void begin() {
		if(drawing)
			throw new IllegalStateException("SpriteBatch.end must be called before begin.");
		
		// Use fbo
		if(currRenderTarget != null)
			currRenderTarget.begin();
		
		//Use the shader
		currShader.begin();
		//Set the projection matrix
		currShader.setUniformMat4f("projection", proj);
		
		drawing = true;
	}
	
	public void end() {
		if(!drawing)
			throw new IllegalStateException("SpriteBatch.begin must be called before end.");
	
		render();
		if(currRenderTarget != null)
			currRenderTarget.end();
		currShader.end();
		drawing = false;
	}

	/**Render the current batch*/
	private void render() {
		if(counter == 0)
			return;
		
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, currTexId);
		
		bufferVert.put(vertices, 0, vertIndex);
		bufferVert.flip();
		
		bufferElem.put(elements, 0, elemIndex);
		bufferElem.flip();
		
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vboId);
		GL15.glBufferSubData(GL15.GL_ARRAY_BUFFER, 0, bufferVert);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
		
		//Bind the VAO and draw
		GL30.glBindVertexArray(vaoId);
		
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, eVboId);
		GL15.glBufferSubData(GL15.GL_ELEMENT_ARRAY_BUFFER, 0, bufferElem);
		
		GL11.glDrawElements(GL11.GL_TRIANGLES, 6 * counter, GL11.GL_UNSIGNED_INT, 0);
		//GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, 6 * counter);
		
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0);
		GL30.glBindVertexArray(0);
		
		//Clear the buffers and set the counters to 0
		bufferVert.clear();
		bufferElem.clear();
		counter = 0;
		vertIndex = 0;
		elemIndex = 0;
	}
	
	public void draw(Sprite sprite) {
		if(!drawing)
			throw new IllegalStateException("SpriteBatch.begin must be called before draw.");
		
		if(currTexId == 0)
			currTexId = sprite.getTexRegion().getTexture().getId();
		
		if(counter == size || currTexId != sprite.getTexRegion().getTexture().getId())
			render();
		
		currTexId = sprite.getTexRegion().getTexture().getId();
		
		float[] vert = sprite.getVertices();
		float[] region = sprite.getTexRegion().getRegion();
		setColor(sprite.getColor());
		
		x1 = vert[0];
		y1 = vert[1];
		x2 = vert[2];
		y2 = vert[3];
		x3 = vert[4];
		y3 = vert[5];
		x4 = vert[6];
		y4 = vert[7];
		
		u1 = region[0];
		v1 = region[1];
		u2 = region[2];
		v2 = region[3];
		u3 = region[4];
		v3 = region[5];
		u4 = region[6];
		v4 = region[7];
		
		if(sprite.isFlipX()) {
			u1 = u1 + u2;
			u2 = u1 - u2;
			u1 = u1 - u2;
			
			u3 = u3 + u4;
			u4 = u3 - u4;
			u3 = u3 - u4;
		}
		
		if(sprite.isFlipY()) {
			v1 = v1 + v4;
			v4 = v1 - v4;
			v1 = v1 - v4;
			
			v2 = v2 + v3;
			v3 = v2 - v3;
			v2 = v2 - v3;
		}
		
		vertices[vertIndex++] = x1;
		vertices[vertIndex++] = y1;
		
		vertices[vertIndex++] = u1;
		vertices[vertIndex++] = v1;
		
		vertices[vertIndex++] = color.r;
		vertices[vertIndex++] = color.g;
		vertices[vertIndex++] = color.b;
		vertices[vertIndex++] = color.a;
		
		vertices[vertIndex++] = x2;
		vertices[vertIndex++] = y2;
		
		vertices[vertIndex++] = u2;
		vertices[vertIndex++] = v2;
		
		vertices[vertIndex++] = color.r;
		vertices[vertIndex++] = color.g;
		vertices[vertIndex++] = color.b;
		vertices[vertIndex++] = color.a;
		
		vertices[vertIndex++] = x3;
		vertices[vertIndex++] = y3;
		
		vertices[vertIndex++] = u3;
		vertices[vertIndex++] = v3;
		
		vertices[vertIndex++] = color.r;
		vertices[vertIndex++] = color.g;
		vertices[vertIndex++] = color.b;
		vertices[vertIndex++] = color.a;
		
		vertices[vertIndex++] = x4;
		vertices[vertIndex++] = y4;
		
		vertices[vertIndex++] = u4;
		vertices[vertIndex++] = v4;
		
		vertices[vertIndex++] = color.r;
		vertices[vertIndex++] = color.g;
		vertices[vertIndex++] = color.b;
		vertices[vertIndex++] = color.a;
		
		final int index = counter * 4;
		elements[elemIndex++] = index + 0; //Top left
		elements[elemIndex++] = index + 1; //Top right
		elements[elemIndex++] = index + 3; //Bottom left
		elements[elemIndex++] = index + 1; //Top right
		elements[elemIndex++] = index + 2; //Bottom right
		elements[elemIndex++] = index + 3; //Bottom left;
		
		counter++;
	}
	
	public void draw(TextureRegion texRegion, float x1, float y1, float x2, float y2, float x3, float y3, float x4, float y4) {
		if(!drawing)
			throw new IllegalStateException("SpriteBatch.begin must be called before draw.");
		
		if(currTexId == 0)
			currTexId = texRegion.getTexture().getId();
		
		if(counter == size || currTexId != texRegion.getTexture().getId())
			render();
		
		currTexId = texRegion.getTexture().getId();

		//check if the vertices are out of the screen
	
		vertices[vertIndex++] = x1;
		vertices[vertIndex++] = y1;
		
		vertices[vertIndex++] = texRegion.getRegion()[0];
		vertices[vertIndex++] = texRegion.getRegion()[1];
		
		vertices[vertIndex++] = color.r;
		vertices[vertIndex++] = color.g;
		vertices[vertIndex++] = color.b;
		vertices[vertIndex++] = color.a;
		
		vertices[vertIndex++] = x2;
		vertices[vertIndex++] = y2;
		
		vertices[vertIndex++] = texRegion.getRegion()[2];
		vertices[vertIndex++] = texRegion.getRegion()[3];
		
		vertices[vertIndex++] = color.r;
		vertices[vertIndex++] = color.g;
		vertices[vertIndex++] = color.b;
		vertices[vertIndex++] = color.a;
		
		vertices[vertIndex++] = x3;
		vertices[vertIndex++] = y3;
		
		vertices[vertIndex++] = texRegion.getRegion()[4];
		vertices[vertIndex++] = texRegion.getRegion()[5];
		
		vertices[vertIndex++] = color.r;
		vertices[vertIndex++] = color.g;
		vertices[vertIndex++] = color.b;
		vertices[vertIndex++] = color.a;
		
		vertices[vertIndex++] = x4;
		vertices[vertIndex++] = y4;
		
		vertices[vertIndex++] = texRegion.getRegion()[6];
		vertices[vertIndex++] = texRegion.getRegion()[7];
		
		vertices[vertIndex++] = color.r;
		vertices[vertIndex++] = color.g;
		vertices[vertIndex++] = color.b;
		vertices[vertIndex++] = color.a;
		
		final int index = counter * 4;
		elements[elemIndex++] = index + 0; //Top left
		elements[elemIndex++] = index + 1; //Top right
		elements[elemIndex++] = index + 3; //Bottom left
		elements[elemIndex++] = index + 1; //Top right
		elements[elemIndex++] = index + 2; //Bottom right
		elements[elemIndex++] = index + 3; //Bottom left;

		counter++;
	}
	
	public void draw(TextureRegion texRegion, float x, float y, float width, float height, float scaleX, float scaleY, float angle, boolean flipX,
	     			boolean flipY) {
 		width = (flipX ? -1 : 1) * width * scaleX;
 		height = (flipY ? -1 : 1) * height * scaleY;

 		if(angle != 0) {
 			final float cos = (float) Math.cos(Math.toRadians(angle));
			final float sin = (float) Math.sin(Math.toRadians(angle));

			x1 = (cos * (-width / 2) - sin * (-height / 2)) + width / 2 + x;
			y1 = (sin * (-width / 2) + cos * (-height / 2)) + height / 2 + y;

			x2 = (cos * (width / 2) - sin * (-height / 2)) + width / 2 + x;
			y2 = (sin * (width / 2) + cos * (-height / 2)) + height / 2 + y;

			x3 = (cos * (width / 2) - sin * (height / 2)) + width / 2 + x;
			y3 = (sin * (width / 2) + cos * (height / 2)) + height / 2 + y;

			x4 = (cos * (-width / 2) - sin * (height / 2)) + width / 2 + x;
			y4 = (sin * (-width / 2) + cos * (height / 2)) + height / 2 + y;
 		}
 		else {
 			x1 = x;
 			y1 = y;

 			x2 = x + width;
 			y2 = y;

 			x3 = x + width;
 			y3 = y + height;

 			x4 = x;
 			y4 = y + height;
 		}

 		draw(texRegion, 
 		     x1 + (flipX ? -width : 0), y1 + (flipY ? -height : 0), 
 		     x2 + (flipX ? -width : 0), y2 + (flipY ? -height : 0), 
 		     x3 + (flipX ? -width : 0), y3 + (flipY ? -height : 0), 
 		     x4 + (flipX ? -width : 0), y4 + (flipY ? -height : 0));
 	}
	
	/**
	 * Sets the projection matrix of the batch.
	 * @param projection the projection matrix
	 */
	public void setProjection(Matrix4f projection) {
		if(projection == null)
			throw new IllegalArgumentException("The projection cannot be null.");
		
		proj = projection;
	}
	
	public void setShader(ShaderProgram newShader) {
		if(newShader == null)
			newShader = defaultShader;
		
		if(currShader.equals(newShader))
			return;
		
		if(drawing) {
			render();
			newShader.begin();
			//Set the projection matrix
			currShader.setUniformMat4f("projection", proj);
		}
		currShader = newShader;
	}
	
	public void setRenderTarget(RenderTarget newRenderTarget) {
		if( (currRenderTarget != null && currRenderTarget.equals(newRenderTarget)) ||
			(currRenderTarget == null && newRenderTarget == null))
			return;
		
		if(drawing) {
			render();
			
			if(newRenderTarget != null)
				newRenderTarget.begin();
			else if(currRenderTarget != null)
				currRenderTarget.end();
		}
		
		currRenderTarget = newRenderTarget;
	}
	
	/**
	 * Sets the color with the given values.
	 * @param r the red value between 0 and 1
	 * @param g the green value between 0 and 1
	 * @param b the blue value between 0 and 1
	 * @param a the alpha value between 0 and 1
	 */
	public void setColor(float r, float g, float b, float a) {
		color.setColor(r, g, b, a);
	}
	
	/**
	 * Sets the color from another Color.
	 * @param color the color to copy from
	 */
	public void setColor(Color color) {
		this.color.setColor(color);
	}

	public void dispose() {
		GL30.glDeleteVertexArrays(vaoId);
		GL15.glDeleteBuffers(vboId);
		GL15.glDeleteBuffers(eVboId);
	}
}
