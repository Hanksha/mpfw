package com.calderagames.mpfw;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.GL_BLEND;
import static org.lwjgl.opengl.GL11.GL_FALSE;
import static org.lwjgl.opengl.GL11.GL_ONE_MINUS_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_TRUE;
import static org.lwjgl.opengl.GL11.glBlendFunc;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glViewport;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWFramebufferSizeCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.glfw.GLFWVidMode.Buffer;
import org.lwjgl.glfw.GLFWWindowCloseCallback;
import org.lwjgl.glfw.GLFWWindowFocusCallback;
import org.lwjgl.glfw.GLFWWindowIconifyCallback;
import org.lwjgl.glfw.GLFWWindowPosCallback;
import org.lwjgl.glfw.GLFWWindowRefreshCallback;
import org.lwjgl.glfw.GLFWWindowSizeCallback;
import org.lwjgl.opengl.GL;

import com.calderagames.mpfw.WindowEvent.WindowEventType;
import com.calderagames.mpfw.inputs.Input;
import com.calderagames.mpfw.math.Dimension;

public class Window {

	/**Hints of the window*/
	private WindowHints hints;

	/**Boolean flag that states if the window hints changed and the window should be recreated*/
	private boolean windowHintsChanged;

	/**Handle of the window*/
	private long windowHandle;

	/**Reference to callback instances*/
	private GLFWWindowCloseCallback closeCallback;
	private GLFWWindowFocusCallback focusCallback;
	private GLFWWindowIconifyCallback iconifyCallback;
	private GLFWWindowPosCallback positionCallback;
	private GLFWWindowRefreshCallback refreshCallback;
	private GLFWWindowSizeCallback sizeCallback;
	private GLFWFramebufferSizeCallback framebufferSizeCallback;
	private GLFWErrorCallback erroCallback;

	/**Reference to window listener*/
	private WindowEventListener windowListener;

	/**Width, in screen coordinate, of the window*/
	private int currWidth;
	/**Height, in screen coordinate, of the window*/
	private int currHeight;

	/**Title of the window*/
	private String title;

	/**Boolean flag for vsync enable or disable*/
	private boolean vsync;
	
	/**
	 * Constructs a window with the provided dimension, title and  default window hints. 
	 * Also initialize mpfw and inputs.
	 * @param width the width of the window
	 * @param height the height of the window
	 * @param title the title of the window
	 */
	public Window(int width, int height, String title) {
		this(width, height, title, new WindowHints());
	}

	/**
	 * Constructs a window with the provided dimension, title and window hints. 
	 * Also initialize mpfw and inputs.
	 * @param width the width of the window
	 * @param height the height of the window
	 * @param title the title of the window
	 * @param hints window hints for the window creation
	 */
	public Window(int width, int height, String title, WindowHints hints) {
		//Initialize GLFW
		glfwInit();

		//Set the error callback
		glfwSetErrorCallback(erroCallback = new GLFWErrorCallback() {
			@Override
			public void invoke(int error, long description) {
			}
		});

		//Store title
		this.title = title;
		//Store hints
		this.hints = hints;

		//Initialize the callback
		closeCallback = new GLFWWindowCloseCallback() {
			@Override
			public void invoke(long window) {
				if(windowListener != null)
					windowListener.fireEvent(new WindowEvent(WindowEventType.CLOSE, true, 0, 0));
			}
		};

		focusCallback = new GLFWWindowFocusCallback() {
			@Override
			public void invoke(long window, int focused) {
				if(windowListener != null)
					windowListener.fireEvent(new WindowEvent(WindowEventType.FOCUS, focused == GL_TRUE, 0, 0));
			}
		};

		iconifyCallback = new GLFWWindowIconifyCallback() {
			@Override
			public void invoke(long window, int iconified) {
				if(windowListener != null)
					windowListener.fireEvent(new WindowEvent(WindowEventType.ICONIFY, iconified == GL_TRUE, 0, 0));
			}
		};

		positionCallback = new GLFWWindowPosCallback() {
			@Override
			public void invoke(long window, int xpos, int ypos) {
				if(windowListener != null)
					windowListener.fireEvent(new WindowEvent(WindowEventType.POSITION, true, xpos, ypos));
			}
		};

		refreshCallback = new GLFWWindowRefreshCallback() {
			@Override
			public void invoke(long window) {
				if(windowListener != null)
					windowListener.fireEvent(new WindowEvent(WindowEventType.REFRESH, true, 0, 0));
			}
		};

		sizeCallback = new GLFWWindowSizeCallback() {
			@Override
			public void invoke(long window, int width, int height) {
				if(windowListener != null)
					windowListener.fireEvent(new WindowEvent(WindowEventType.SIZE, true, width, height));
			}
		};

		framebufferSizeCallback = new GLFWFramebufferSizeCallback() {
			@Override
			public void invoke(long window, int width, int height) {
				if(windowListener != null)
					windowListener.fireEvent(new WindowEvent(WindowEventType.FRAMEBUFFER_SIZE, true, width, height));
			}
		};

		//Create the window
		createWindow(width, height);
	}

	/**Create a window with the current window hints,
	 * if there was a previously the openGL context will be shared
	 * with the new window
	 * @param width of the window
	 * @param height of the window
	 */
	private void createWindow(int width, int height) {
		//Set the window hints
		glfwWindowHint(GLFW_RESIZABLE, hints.resizable ? GL_TRUE : GL_FALSE);
		glfwWindowHint(GLFW_VISIBLE, hints.visible ? GL_TRUE : GL_FALSE);
		glfwWindowHint(GLFW_DECORATED, hints.decorated ? GL_TRUE : GL_FALSE);
		glfwWindowHint(GLFW_AUTO_ICONIFY, hints.autoIconify ? GL_TRUE : GL_FALSE);
		glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, hints.forwardCompatible ? GL_TRUE : GL_FALSE);
		glfwWindowHint(GLFW_OPENGL_DEBUG_CONTEXT, hints.debugContext ? GL_TRUE : GL_FALSE);
		glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, hints.contextMajorVersion);
		glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, hints.contextMinorVersion);
		glfwWindowHint(GLFW_CLIENT_API, hints.clientAPI.api);
		glfwWindowHint(GLFW_OPENGL_PROFILE, hints.openGLProfile.profile);

		//Get the monitor
		long monitor = glfwGetPrimaryMonitor();
		//Get the video mode
		GLFWVidMode vidMode = glfwGetVideoMode(monitor);
		int monitorWidth = vidMode.width();
		int monitorHeight = vidMode.height();
		
		//Set current width
		currWidth = width;
		//Set current height
		currHeight = height;
		
		//If a window previously created, keep the handle
		long oldWindowHandle = windowHandle;
		
		//Create the window and store the window handle
		windowHandle = glfwCreateWindow(hints.fullscreen ? monitorWidth : width, hints.fullscreen ? monitorHeight : height, title,
										hints.fullscreen ? monitor : 0, oldWindowHandle);

		//Update the resolution helper
		ResolutionHelper.update(hints.fullscreen ? monitorWidth : width, hints.fullscreen ? monitorHeight : height);

		//Check if the window was successfully created
		if(windowHandle == 0)
			throw new RuntimeException("Failed to create window");

		//Make the window the current context
		glfwMakeContextCurrent(windowHandle);
		GL.createCapabilities();
		
		//Destroy the previously created window if any
		if(oldWindowHandle != 0)
			glfwDestroyWindow(oldWindowHandle);
		
		//Center the window
		if(!hints.fullscreen)
			glfwSetWindowPos(windowHandle, monitorWidth / 2 - width / 2, monitorHeight / 2 - height / 2);

		//Finally show the window
		glfwShowWindow(windowHandle);

		//Initialize the Input class with for this window or set the window handle
		if(Input.isInit())
			Input.setWindow(windowHandle);
		else
			Input.init(windowHandle);

		//Set the callback
		glfwSetWindowCloseCallback(windowHandle, closeCallback);
		glfwSetWindowFocusCallback(windowHandle, focusCallback);
		glfwSetWindowIconifyCallback(windowHandle, iconifyCallback);
		glfwSetWindowPosCallback(windowHandle, positionCallback);
		glfwSetWindowRefreshCallback(windowHandle, refreshCallback);
		glfwSetWindowSizeCallback(windowHandle, sizeCallback);
		glfwSetFramebufferSizeCallback(windowHandle, framebufferSizeCallback);

		//Set up opengGL
		glViewport(0, 0, hints.fullscreen ? monitorWidth : width, hints.fullscreen ? monitorHeight : height);
		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		glClearColor(0f, 0f, 0f, 1f);
	}

	/**Update the window, poll the events and swap buffers*/
	public void update() {
		//Swap the buffers
		glfwSwapBuffers(windowHandle);
		//Update inputs
		Input.update();
	}

	/**
	 * Returns if close was requested by the user for this window
	 * @return true if the window should close
	 */
	public boolean shouldClose() {
		return glfwWindowShouldClose(windowHandle) == GL_TRUE;
	}

	/**Recreate a window with the new window hints, keeps the same openGL context for the new window</br>
	 * <b>Note:</b> If no changes were detected the method does nothing
	 */
	public void applyWindowHintsChanges() {
		if(!windowHintsChanged)
			return;

		createWindow(currWidth, currHeight);

		windowHintsChanged = false;
	}

	/**Set the window event listener*/
	public void setWindowEventListener(WindowEventListener listener) {
		windowListener = listener;
	}

	/**Set the title of the window*/
	public void setTitle(String title) {
		this.title = title;
		glfwSetWindowTitle(windowHandle, title);
	}

	/**
	 * Enable or disable fullscreen
	 * <p><b>Note:</b> You HAVE to call {@link #applyWindowHintsChanges()} for the changes to apply</p>
	 * @param b true to enable, false to disable.
	 */
	public void setFullscreen(boolean b) {
		if(hints.fullscreen != b)
			windowHintsChanged = true;

		hints.fullscreen = b;
	}

	/**
	 * Enable or disable decorated
	 * <p><b>Note:</b> You HAVE to call {@link #applyWindowHintsChanges()} for the changes to apply</p>
	 * @param b true to enable, false to disable.
	 */
	public void setDecorated(boolean b) {
		if(hints.decorated != b)
			windowHintsChanged = true;

		hints.decorated = b;
	}

	/**
	 * Enable or disable resizable
	 * <p><b>Note:</b> You HAVE to call {@link #applyWindowHintsChanges()} for the changes to apply</p>
	 * @param b true to enable, false to disable.
	 */
	public void setResizable(boolean b) {
		if(hints.resizable != b)
			windowHintsChanged = true;

		hints.resizable = b;
	}

	/**Enable or disable vertical synchronization*/
	public void setVSync(boolean b) {
		vsync = b;
		glfwSwapInterval(b ? 1 : 0);
	}

	public void setIconify(boolean b) {
		if(b)
			glfwIconifyWindow(windowHandle);
		else
			glfwRestoreWindow(windowHandle);
	}

	/**
	 * Returns the handle of the window
	 * @return long handle
	 */
	public long getHandle() {
		return windowHandle;
	}

	/**Returns the width, in screen coordinate, of the window*/
	public int getWidth() {
		return currWidth;
	}

	/**Returns the height, in screen coordinate, of the window*/
	public int getHeight() {
		return currWidth;
	}

	/**Returns the title of the window*/
	public String getTitle() {
		return title;
	}

	/**
	 * Returns all the resolution supported by the primary monitor
	 * @return an ArrayList of Dimension in ascending order according 
	 * to resolution area (width * height)
	 */
	public static ArrayList<Dimension> getAllResolution() {
		ArrayList<Dimension> allRes = new ArrayList<Dimension>();

		long monitor = GLFW.glfwGetPrimaryMonitor();

		Buffer vidModes = GLFW.glfwGetVideoModes(monitor);
		
		while(vidModes.hasRemaining()) {
			
			int width = vidModes.width();
			int height = vidModes.height();
			
			vidModes.get();
			
			Dimension dim = new Dimension(width, height);

			if(!allRes.contains(dim))
				allRes.add(dim);
		}

		Collections.sort(allRes, new Comparator<Dimension>() {
			@Override
			public int compare(Dimension d1, Dimension d2) {
				if(d1.width * d1.height > d2.width * d2.height) {
					return 1;
				}

				if(d1.width * d1.height < d2.width * d2.height)
					return -1;

				return 0;
			}
		});

		return allRes;
	}

	/**
	 * Returns if the vertical synchronization is enabled
	 * @return true if vsync is enabled
	 */
	public boolean isVSyncEnabled() {
		return vsync;
	}

	/**Destroy the window and free all the resources allocated*/
	public void destroy() {
		//Release callback
		erroCallback.release();
		closeCallback.release();
		focusCallback.release();
		iconifyCallback.release();
		positionCallback.release();
		refreshCallback.release();
		sizeCallback.release();
		framebufferSizeCallback.release();

		//Destroy Input
		Input.destroy();

		//Destroy the window
		glfwDestroyWindow(windowHandle);
		glfwTerminate();
	}
}
