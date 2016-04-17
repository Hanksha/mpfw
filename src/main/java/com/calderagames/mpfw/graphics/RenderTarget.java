package com.calderagames.mpfw.graphics;

import static org.lwjgl.opengl.GL30.*;
import static org.lwjgl.opengl.GL11.*;

public class RenderTarget {

	private int frameBufferID;
	private Texture texture;
	private int depthRenderBufferID;

	// dimensions
	private int FRAME_WIDTH;
	private int FRAME_HEIGHT;

	// clear color
	private Color color;
	
	private boolean inUse;

	public RenderTarget(int width, int height) {
		FRAME_WIDTH = width;
		FRAME_HEIGHT = height;
		color = new Color(0f, 0f, 0f, 1f);
		
		frameBufferID = glGenFramebuffers();
		int colorTextureID = glGenTextures();
		depthRenderBufferID = glGenRenderbuffers();

		// frame buffer object
		glBindFramebuffer(GL_FRAMEBUFFER, frameBufferID);

		// color texture
		glBindTexture(GL_TEXTURE_2D, colorTextureID);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
		glTexImage2D(GL_TEXTURE_2D, 0, GL_RGB8, FRAME_WIDTH, FRAME_HEIGHT, 0, GL_RGBA, GL_INT, (java.nio.ByteBuffer) null);
		glFramebufferTexture2D(GL_FRAMEBUFFER, GL_COLOR_ATTACHMENT0, GL_TEXTURE_2D, colorTextureID, 0);

		// depth buffer
		glBindRenderbuffer(GL_RENDERBUFFER, depthRenderBufferID);
		glRenderbufferStorage(GL_RENDERBUFFER, GL_DEPTH_COMPONENT, FRAME_WIDTH, FRAME_HEIGHT);
		glFramebufferRenderbuffer(GL_FRAMEBUFFER, GL_DEPTH_ATTACHMENT, GL_RENDERBUFFER, depthRenderBufferID);

		// check completeness
		if(glCheckFramebufferStatus(GL_FRAMEBUFFER) == GL_FRAMEBUFFER_COMPLETE) {
			// System.out.println("Frame buffer created sucessfully.");
		}
		else
			System.out.println("An error occured creating the frame buffer.");

		glBindFramebuffer(GL_FRAMEBUFFER, 0);
		
		texture = new Texture(colorTextureID, 0);
	}

	public void begin() {
		if(inUse)
			return;
		glViewport(0, 0, FRAME_WIDTH, FRAME_HEIGHT);
		glBindFramebuffer(GL_FRAMEBUFFER, frameBufferID);
		
		inUse = true;
	}
	
	public void beginAndClear() {
		begin();
		clear();
	}
	
	public void clear() {
		if(!inUse)
			return;
		glClearColor(color.r, color.g, color.b, color.a);
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
	}

	public void end() {
		if(!inUse)
			return;
		glBindFramebuffer(GL_FRAMEBUFFER, 0);
		inUse = false;
	}

	public void dispose() {
		glDeleteFramebuffers(frameBufferID);
		glDeleteRenderbuffers(depthRenderBufferID);
		texture.dispose();
	}

	public void setClearColor(float r, float g, float b) {
		color.r = r;
		color.g = g;
		color.b = b;
	}

	public int getWidth() {
		return FRAME_WIDTH;
	}

	public int getHeight() {
		return FRAME_HEIGHT;
	}

	public Texture getTexture() {
		return texture;
	}
}
