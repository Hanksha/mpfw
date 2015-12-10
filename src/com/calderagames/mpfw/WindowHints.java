package com.calderagames.mpfw;

import static org.lwjgl.glfw.GLFW.*;

public class WindowHints {

	public enum ClientAPI {
		OPENGL_API(GLFW_OPENGL_API),
		OPENGL_ES_API(GLFW_OPENGL_ES_API);
		
		public final int api;
		
		ClientAPI(int api) {
			this.api = api;
		}
	}
	
	public enum OpenGLProfiles {
		ANY_PROFILE(GLFW_OPENGL_ANY_PROFILE),
		CORE_PROFILE(GLFW_OPENGL_CORE_PROFILE),
		COMPATIBILITY_PROFILE(GLFW_OPENGL_COMPAT_PROFILE);
		
		public final int profile;
		
		OpenGLProfiles(int profile) {
			this.profile = profile;
		}
	}
	
	public boolean resizable;
	public boolean visible;
	public boolean fullscreen;
	public boolean decorated = true;
	public boolean autoIconify = true;
	public boolean forwardCompatible;
	public boolean debugContext;
	
	public int contextMajorVersion = 3;
	public int contextMinorVersion = 2;

	public ClientAPI clientAPI = ClientAPI.OPENGL_API;
	public OpenGLProfiles openGLProfile = OpenGLProfiles.CORE_PROFILE;
	
	/**Constructs a WindowHints with the default values:</br>
	 * <ul>
	 * <li>{@link #resizable} = false</li>
	 * <li>{@link #visible} = false</li>
	 * <li>{@link #fullscreen} = false</li>
	 * <li>{@link #decorated} = true</li>
	 * <li>{@link #autoIconify} = true</li>
	 * <li>{@link #forwardCompatible} = false</li>
	 * <li>{@link #debugContext} = false</li>
	 * <li>{@link #contextMajorVersion} = 3</li>
	 * <li>{@link #contextMinorVersion} = 2</li>
	 * <li>{@link #clientAPI} = {@link ClientAPI#OPENGL_API}</li>
	 * <li>{@link #openGLProfile} = {@link OpenGLProfiles#CORE_PROFILE}</li>
	 * </ul>
	 * */
	public WindowHints() {
	}

	public void setResizable(boolean b) {
		resizable = b;
	}

	public void setVisible(boolean b) {
		visible = b;
	}
	
	public void setFullscreen(boolean b) {
		fullscreen = b;
	}

	public void setAutoIconify(boolean b) {
		autoIconify = b;
	}
	
	public void setContextVersionMajor(int version) {
		contextMajorVersion = version;
	}
	
	public void setContextVersionMinor(int version) {
		contextMinorVersion = version;
	}
	
	public void setOpenGLForwardCompatible(boolean b) {
		forwardCompatible = b;
	}
	
	public void setOpenGLDebugContext(boolean b) {
		debugContext = b;
	}
	
	public void setOpenGLProfile(OpenGLProfiles openGLProfile) {
		this.openGLProfile = openGLProfile;
	}
}
