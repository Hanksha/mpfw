package com.calderagames.mpfw.graphics;

import static org.lwjgl.opengl.GL11.GL_FALSE;
import static org.lwjgl.opengl.GL20.GL_COMPILE_STATUS;
import static org.lwjgl.opengl.GL20.GL_FRAGMENT_SHADER;
import static org.lwjgl.opengl.GL20.GL_VERTEX_SHADER;
import static org.lwjgl.opengl.GL20.glAttachShader;
import static org.lwjgl.opengl.GL20.glBindAttribLocation;
import static org.lwjgl.opengl.GL20.glCompileShader;
import static org.lwjgl.opengl.GL20.glCreateProgram;
import static org.lwjgl.opengl.GL20.glCreateShader;
import static org.lwjgl.opengl.GL20.glGetShaderInfoLog;
import static org.lwjgl.opengl.GL20.glGetShaderi;
import static org.lwjgl.opengl.GL20.glGetUniformLocation;
import static org.lwjgl.opengl.GL20.glLinkProgram;
import static org.lwjgl.opengl.GL20.glShaderSource;
import static org.lwjgl.opengl.GL20.glValidateProgram;
import static org.lwjgl.opengl.GL20.glUseProgram;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.FloatBuffer;
import java.util.HashMap;

import javax.swing.JOptionPane;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL20;

import com.calderagames.mpfw.math.Matrix4f;
import com.calderagames.mpfw.math.Vector2f;
import com.calderagames.mpfw.math.Vector3f;
import com.calderagames.mpfw.math.Vector4f;

/**
 * Holds an openGL shader program and utility methods to create, manage and update it.
 */
public class ShaderProgram {

	/**Default vertex shader*/
	private String defaultVertexShader = 
			"#version 150 core\n" +
			"in vec4 in_position;\n" +
			"in vec4 in_texCoord;\n" +
			"in vec4 in_color;\n" +
			"out vec2 v_texCoord;\n" +
			"out vec4 v_color;\n" +
			"uniform mat4 projection;\n" +
			"uniform mat4 modelView;\n" +
			"void main() {\n" +
			"v_texCoord = in_texCoord.xy;\n" +
			"v_color = in_color;\n" +
			"gl_Position = projection * modelView * in_position;\n" +
			"}";
	
	/**Default fragment shader*/
	private String defaultFragmentShader = 
			"#version 150 core\n" +
			"in vec2 v_texCoord;\n" +
			"in vec4 v_color;\n" +
			"out vec4 color;\n" +
			"uniform sampler2D u_texDiffuse;\n" +
			"void main() {\n" +
			"color = texture(u_texDiffuse, v_texCoord) * v_color;\n" +
			"}";
	
	
	/**Shader program*/
	private int shaderProgram;
	
	/**Attributes*/
	private ShaderAttribute attributes;
	
	/**Name of the shader program (for error tracing)*/
	private String name;
	
	/**Hash map of the uniforms locations*/
	private HashMap<String, Integer> locations;
	
	/**Boolean flags that states if the shader program is in use*/
	private boolean inUse;
	
	/**
	 * Constructs a shader program with the default vertex shader ({@link #defaultVertexShader})
	 * and default fragment shader ({@link #defaultFragmentShader}).
	 */
	public ShaderProgram() {
		name = "default";
		createProgram(defaultVertexShader, defaultFragmentShader, new ShaderAttribute());
	}
	
	/**
	 * Constructs a shader program from file.
	 * @param vertexShaderFile text file containing the vertex shader source code
	 * @param fragmentShaderFile text file containing the fragment shader source code
	 * @param shaderAttrib the attributes of the shader program
	 * @param name the name of the shader program (for error tracing)
	 */
	public ShaderProgram(File vertexShaderFile, File fragmentShaderFile, ShaderAttribute shaderAttrib, String name) {
		this.name = name;
		
		String vertexShaderSource = "";
		String fragmentShaderSource = "";
		
		BufferedReader br = null;
		String line = null;
		
		//Read the vertex shader file
		try {
			br = new BufferedReader(new InputStreamReader(new FileInputStream(vertexShaderFile)));
			
			while((line = br.readLine()) != null)
				vertexShaderSource += line + "\n";
			
			br.close();
		} catch(IOException e) {
			e.printStackTrace();
		}

		//Read the fragment shader file
		try {
			br = new BufferedReader(new InputStreamReader(new FileInputStream(fragmentShaderFile)));
			
			while((line = br.readLine()) != null) 
				fragmentShaderSource += line + "\n";
			
			br.close();
			
		} catch(IOException e) {
			e.printStackTrace();
		}
		
		createProgram(vertexShaderSource, fragmentShaderSource, shaderAttrib);
	}
	
	public ShaderProgram(String vertexShader, String fragmentShader, ShaderAttribute shaderAttrib, String name) {
		createProgram(vertexShader, fragmentShader, shaderAttrib);
	}
	
	private void createProgram(String vertexShaderSource, String fragmentShaderSource, ShaderAttribute shaderAttrib) {
		attributes = shaderAttrib;
		
		//Create the program
		shaderProgram = glCreateProgram();
		
		//Create vertex and fragment shader
		int vertexShader = glCreateShader(GL_VERTEX_SHADER);
		int fragmentShader = glCreateShader(GL_FRAGMENT_SHADER);
		
		//Compile vertex shader source code
		glShaderSource(vertexShader, vertexShaderSource);
		glCompileShader(vertexShader);
		
		//Check for compilation error of vertex shader
		if(glGetShaderi(vertexShader, GL_COMPILE_STATUS) == GL_FALSE) {
			JOptionPane.showMessageDialog(null, "Vertex Shader - " + name + ":\n" + glGetShaderInfoLog(vertexShader, Integer.MAX_VALUE),
										  "Vertex shader wasn't able to be compiled correctly.\n", JOptionPane.ERROR_MESSAGE);
		}

		//Compile fragment shader source code
		glShaderSource(fragmentShader, fragmentShaderSource);
		glCompileShader(fragmentShader);
		
		//Check for compilation error of fragment shader
		if(glGetShaderi(fragmentShader, GL_COMPILE_STATUS) == GL_FALSE) {
			JOptionPane.showMessageDialog(null, "Fragment Shader - " + name + ":\n" + glGetShaderInfoLog(fragmentShader, 1000),
										  "Fragment shader wasn't able to be compiled correctly.\n", JOptionPane.ERROR_MESSAGE);
		}

		//Attach the vertex and fragment shader to the shader program
		glAttachShader(shaderProgram, vertexShader);
		glAttachShader(shaderProgram, fragmentShader);

		//Bind attrib location with the given ShaderAttribute
		glBindAttribLocation(shaderProgram, 0, shaderAttrib.POSITION_ATTR);
		glBindAttribLocation(shaderProgram, 1, shaderAttrib.TEXCOORD_ATTR);
		glBindAttribLocation(shaderProgram, 2, shaderAttrib.COLOR_ATTR);

		//Link the shader program
		glLinkProgram(shaderProgram);
		//Validate the program
		glValidateProgram(shaderProgram);
		
		//Init hash map of locations
		locations = new HashMap<String, Integer>();
		
		//Add the essential uniform locations
		addUniformLocation(attributes.PROJECTION_ATTR);
		addUniformLocation(attributes.MODELVIEW_ATTR);
		addUniformLocation(attributes.TEXDIFFUSE_ATTR);
		
		begin();
		//Set the modelView to identity
		Matrix4f modelView = new Matrix4f();
		setUniformMat4f("modelView", modelView);
		//Set the texture diffuse on texture unit 0
		setUniform1i(attributes.TEXDIFFUSE_ATTR, 0);
		end();
	}
	
	/**
	 * Use the shader program, to be call before to draw sprite</br>
	 * <b>Note:</b> if the method is called between begin() and end()
	 * it has no effect. 
	 */
	public void begin() {
		if(inUse)
			return;
		
		glUseProgram(shaderProgram);
		
		inUse = true;
	}
	
	/**Returns the {@link ShaderAttribute}*/
	public ShaderAttribute getShaderAttrib() {
		return attributes;
	}
	
	/**
	 * Stop using the shader program, to be call once the shader
	 * is not needed anymore</br>
	 * <b>Note:</b> if the method is called after end() it has no effect. 
	 */
	public void end() {
		if(!inUse)
			return;
		
		glUseProgram(0);
		
		inUse = false;
	}
	
	/**
	 * Add the location of the uniform in the shader program
	 * @param name the name of the uniform in the shader program
	 */
	public void addUniformLocation(String name) {
		locations.put(name, glGetUniformLocation(shaderProgram, name));
	}
	
	/**See {@link #setUniform4i(String, int, int, int, int)}.*/
	public void setUniform1i(String name, int v0) {
		if(!inUse) throw new IllegalStateException("The shader program must be bound before to set a uniform");
		GL20.glUniform1i(locations.get(name), v0);
	}
	
	/**See {@link #setUniform4i(String, int, int, int, int)}.*/
	public void setUniform2i(String name, int v0, int v1) {
		if(!inUse) throw new IllegalStateException("The shader program must be bound before to set a uniform");
		GL20.glUniform2i(locations.get(name), v0, v1);
	}
	
	/**See {@link #setUniform4i(String, int, int, int, int)}.*/
	public void setUniform3i(String name, int v0, int v1, int v2) {
		if(!inUse) throw new IllegalStateException("The shader program must be bound before to set a uniform");
		GL20.glUniform3i(locations.get(name), v0, v1, v2);
	}
	
	/** 
	 * Sets the uniform integer with the given name. The {@link ShaderProgram} must be bound for this to work.
	 * @param name the name of the uniform
	 * @param v0 the first value
	 * @param v1 the second value
	 * @param v2 the third value
	 * @param v3 the fourth value 
	 */
	public void setUniform4i(String name, int v0, int v1, int v2, int v3) {
		if(!inUse) throw new IllegalStateException("The shader program must be bound before to set a uniform");
		GL20.glUniform4i(locations.get(name), v0, v1, v2, v3);
	}
	
	/**See {@link #setUniform4f(String, float, float, float, float)}.*/
	public void setUniform1f(String name, float v0) {
		if(!inUse) throw new IllegalStateException("The shader program must be bound before to set a uniform");
		GL20.glUniform1f(locations.get(name), v0);
	}

	/**See {@link #setUniform4f(String, float, float, float, float)}.*/
	public void setUniform2f(String name, float v0, float v1) {
		if(!inUse) throw new IllegalStateException("The shader program must be bound before to set a uniform");
		GL20.glUniform2f(locations.get(name), v0, v1);
	}
	
	/**See {@link #setUniform4f(String, float, float, float, float)}.*/
	public void setUniform2f(String name, Vector2f v) {
		if(!inUse) throw new IllegalStateException("The shader program must be bound before to set a uniform");
		GL20.glUniform2f(locations.get(name), v.x, v.y);
	}

	/**See {@link #setUniform4f(String, float, float, float, float)}.*/
	public void setUniform3f(String name, float v0, float v1, float v2) {
		if(!inUse) throw new IllegalStateException("The shader program must be bound before to set a uniform");
		GL20.glUniform3f(locations.get(name), v0, v1, v2);
	}
	
	/**See {@link #setUniform4f(String, float, float, float, float)}.*/
	public void setUniform3f(String name, Vector3f v) {
		if(!inUse) throw new IllegalStateException("The shader program must be bound before to set a uniform");
		GL20.glUniform3f(locations.get(name), v.x, v.y, v.z);
	}
	
	/** 
	 * Sets the uniform float with the given name. The {@link ShaderProgram} must be bound for this to work.
	 * @param name the name of the uniform
	 * @param v0 the first value
	 * @param v1 the second value
	 * @param v2 the third value
	 * @param v3 the fourth value 
	 */
	public void setUniform4f(String name, float v0, float v1, float v2, float v3) {
		if(!inUse) throw new IllegalStateException("The shader program must be bound before to set a uniform");
		GL20.glUniform4f(locations.get(name), v0, v1, v2, v3);
	}
	
	/**See {@link #setUniform4f(String, float, float, float, float)}.*/
	public void setUniform4f(String name, Vector4f v) {
		if(!inUse) throw new IllegalStateException("The shader program must be bound before to set a uniform");
		GL20.glUniform4f(locations.get(name), v.x, v.y, v.z, v.w);
	}
	
	/**
	 * Sets the matrix uniform with the given name. The {@link ShaderProgram} must be bound for this to work.
	 * @param name the name of the uniform
	 * @param mat the matrix
	 */
	public void setUniformMat4f(String name, Matrix4f mat) {
		if(!inUse) throw new IllegalStateException("The shader program must be bound before to set a uniform");
		FloatBuffer buffer = BufferUtils.createFloatBuffer(16);
		mat.store(buffer);
		buffer.flip();
		GL20.glUniformMatrix4fv(locations.get(name), false, buffer);
	}
}
