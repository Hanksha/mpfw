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
	public float x1, y1, x2, y2, x3, y3, x4, y4;
	
	/**The size of the batch, maximum number of sprite drawn per batch*/
	private int size;
	/**Current number of sprite in the batch*/
	private int counter;
	/**Current texture id*/
	private int currTexId;
	/**The previous texture id*/
	private int prevTexId;
	
	/**The current camera*/
	private Camera2D cam;
	/**The default camera*/
	private Camera2D defaultCam;
	/**The projection matrix*/
	private Matrix4f proj;
	
	/**Default shader program*/
	private ShaderProgram defaultShader;
	/**The current shader program in use for the batch*/
	private ShaderProgram currShader;
	
	/**Constructs a sprite batch with a size of 1000 and a default shader program*/
	public SpriteBatch() {
		this(1000);
	}

	/**
	 * Constructs a sprite batch with the given size and a default shader program
	 * @param size the size maximum size of one batch
	 */
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
		
		//If no shader is passed, instantiate a default shader program
		if(defaultShader == null)
			this.defaultShader = new ShaderProgram();
		else
			this.defaultShader = defaultShader;
		
		//Default Camera2D
		defaultCam = new Camera2D();
		
		//Default white color
		color = new Color(1f, 1f, 1f);
		
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
	
	public void begin(Texture texture) {
		begin(texture, defaultShader, defaultCam);
	}
	
	public void begin(Texture texture, ShaderProgram shader, Camera2D camera) {
		//Set the camera
		cam = camera;
		
		//Get the texture id
		currTexId = texture.getId();

		currShader = shader;
		//Use the shader
		currShader.begin();
		//Set the projection matrix
		currShader.setUniformMat4f(currShader.getShaderAttrib().PROJECTION_ATTR, proj);
	}
	
	public void end() {
		render();
		currShader.end();
	}

	/**Render the current batch*/
	private void render() {
		if(counter == 0)
			return;
		
		//Bind the texture if necessary;
		if(prevTexId != currTexId) {
    		GL13.glActiveTexture(GL13.GL_TEXTURE0);
    		GL11.glBindTexture(GL11.GL_TEXTURE_2D, currTexId);
		}
		prevTexId = currTexId;
		
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
	
	public void draw(TextureRegion texRegion, float x1, float y1, float x2, float y2, float x3, float y3, float x4, float y4) {
		if(counter == size)
			render();

		//check if the vertices are out of the screen
		/*if((cam.getX() + x1 < 0 || cam.getX() + x1 > VAR.WIDTH) && (cam.getY() + y1 < 0 || cam.getY() + y1 > VAR.HEIGHT) && (cam.getX() + x2 < 0 || cam.getX() + x2 > VAR.WIDTH) && (cam.getY() + y2 < 0 || cam.getY() + y2 > VAR.HEIGHT) && (cam.getX() + x3 < 0 || cam.getX() + x3 > VAR.WIDTH) && (cam.getY() + y3 < 0 || cam.getY() + y3 > VAR.HEIGHT) && (cam.getX() + x4 < 0 || cam.getX() + x4 > VAR.WIDTH) && (cam.getY() + y4 < 0 || cam.getY() + y4 > VAR.HEIGHT))
			return;*/
		
		vertices[vertIndex++] = cam.getX() + x1;
		vertices[vertIndex++] = cam.getY() + y1;
		
		vertices[vertIndex++] = texRegion.getRegion()[0];
		vertices[vertIndex++] = texRegion.getRegion()[1];
		
		vertices[vertIndex++] = color.r;
		vertices[vertIndex++] = color.g;
		vertices[vertIndex++] = color.b;
		vertices[vertIndex++] = color.a;
		
		vertices[vertIndex++] = cam.getX() + x2;
		vertices[vertIndex++] = cam.getY() + y2;
		
		vertices[vertIndex++] = texRegion.getRegion()[2];
		vertices[vertIndex++] = texRegion.getRegion()[3];
		
		vertices[vertIndex++] = color.r;
		vertices[vertIndex++] = color.g;
		vertices[vertIndex++] = color.b;
		vertices[vertIndex++] = color.a;
		
		vertices[vertIndex++] = cam.getX() + x3;
		vertices[vertIndex++] = cam.getY() + y3;
		
		vertices[vertIndex++] = texRegion.getRegion()[4];
		vertices[vertIndex++] = texRegion.getRegion()[5];
		
		vertices[vertIndex++] = color.r;
		vertices[vertIndex++] = color.g;
		vertices[vertIndex++] = color.b;
		vertices[vertIndex++] = color.a;
		
		vertices[vertIndex++] = cam.getX() + x4;
		vertices[vertIndex++] = cam.getY() + y4;
		
		vertices[vertIndex++] = texRegion.getRegion()[6];
		vertices[vertIndex++] = texRegion.getRegion()[7];
		
		vertices[vertIndex++] = color.r;
		vertices[vertIndex++] = color.g;
		vertices[vertIndex++] = color.b;
		vertices[vertIndex++] = color.a;
		
		int index = counter * 4;
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
		proj = projection;
	}
	
	/**
	 * Sets the camera of the batch.
	 * @param camera the camera
	 */
	public void setCamera(Camera2D camera) {
		cam = camera;
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
