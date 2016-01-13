package com.calderagames.mpfw.inputs;

import static org.lwjgl.glfw.GLFW.*;

import java.nio.ByteBuffer;
import java.nio.DoubleBuffer;
import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.glfw.GLFWCursorPosCallback;
import org.lwjgl.glfw.GLFWKeyCallback;
import org.lwjgl.glfw.GLFWMouseButtonCallback;
import org.lwjgl.glfw.GLFWScrollCallback;
import org.lwjgl.opengl.GL11;

import com.calderagames.mpfw.ResolutionHelper;
import com.calderagames.mpfw.inputs.InputEvent.InputEventSources;
import com.calderagames.mpfw.inputs.InputEvent.InputEventTypes;

@SuppressWarnings("unused")
public abstract class Input {
	
	/**Reference of the current window*/
	public static long currWindow;
	
	/**Boolean flag that states if the Input is initialized*/
	private static boolean init;
	
	/**The key or button was released*/
	public static final int RELEASE = GLFW_RELEASE; 
	
	/**The key or button was pressed*/
	public static final int PRESS = GLFW_PRESS;
	
	/**The key was held down until it repeated*/
	public static final int REPEAT = GLFW_REPEAT;
	
	/**Reference to callback inputs*/
	private static GLFWKeyCallback keyCallback;
	private static GLFWMouseButtonCallback mouseBtnCallback;
	private static GLFWCursorPosCallback mousePosCallback;
	private static GLFWScrollCallback scrollCallback;
	
	/**Reference to listeners*/
	private static InputListener inputListener;
	private static KeyboardInputListener keyboardListener;
	private static MouseInputListener mouseListener;
	private static JoystickInputListener joystickListener;
	
	/**Keyboard keys*/
	public static final int 
    KEY_UNKNOWN = GLFW_KEY_UNKNOWN,
    KEY_SPACE = GLFW_KEY_SPACE,
    KEY_APOSTROPHE = GLFW_KEY_APOSTROPHE,
    KEY_COMMA = GLFW_KEY_COMMA,
    KEY_MINUS = GLFW_KEY_MINUS,
    KEY_PERIOD = GLFW_KEY_PERIOD,
    KEY_SLASH = GLFW_KEY_SLASH,
    KEY_0 = GLFW_KEY_0,
    KEY_1 = GLFW_KEY_1,
    KEY_2 = GLFW_KEY_2,
    KEY_3 = GLFW_KEY_3,
    KEY_4 = GLFW_KEY_4,
    KEY_5 = GLFW_KEY_5,
    KEY_6 = GLFW_KEY_6,
    KEY_7 = GLFW_KEY_7,
    KEY_8 = GLFW_KEY_8,
    KEY_9 = GLFW_KEY_9,
    KEY_SEMICOLON = GLFW_KEY_SEMICOLON,
    KEY_EQUAL = GLFW_KEY_EQUAL,
    KEY_A = GLFW_KEY_A,
    KEY_B = GLFW_KEY_B,
    KEY_C = GLFW_KEY_C,
    KEY_D = GLFW_KEY_D,
    KEY_E = GLFW_KEY_E,
    KEY_F = GLFW_KEY_F,
    KEY_G = GLFW_KEY_G,
    KEY_H = GLFW_KEY_H,
    KEY_I = GLFW_KEY_I,
    KEY_J = GLFW_KEY_J,
    KEY_K = GLFW_KEY_K,
    KEY_L = GLFW_KEY_L,
    KEY_M = GLFW_KEY_M,
    KEY_N = GLFW_KEY_N,
    KEY_O = GLFW_KEY_O,
    KEY_P = GLFW_KEY_P,
    KEY_Q = GLFW_KEY_Q,
    KEY_R = GLFW_KEY_R,
    KEY_S = GLFW_KEY_S,
    KEY_T = GLFW_KEY_T,
    KEY_U = GLFW_KEY_U,
    KEY_V = GLFW_KEY_V,
    KEY_W = GLFW_KEY_W,
    KEY_X = GLFW_KEY_X,
    KEY_Y = GLFW_KEY_Y,
    KEY_Z = GLFW_KEY_Z,
    KEY_L_BRACKET = GLFW_KEY_LEFT_BRACKET,
    KEY_BACKSLASH = GLFW_KEY_BACKSLASH,
    KEY_R_BRACKET = GLFW_KEY_RIGHT_BRACKET,
    KEY_GRAVE_ACCENT = GLFW_KEY_GRAVE_ACCENT,
    KEY_WORLD_1 = GLFW_KEY_WORLD_1,
    KEY_WORLD_2 = GLFW_KEY_WORLD_2,
    KEY_ESCAPE = GLFW_KEY_ESCAPE,
    KEY_ENTER = GLFW_KEY_ENTER,
    KEY_TAB = GLFW_KEY_TAB,
    KEY_BACKSPACE = GLFW_KEY_BACKSPACE,
    KEY_INSERT = GLFW_KEY_INSERT,
    KEY_KEY_DELETE = GLFW_KEY_DELETE,
    KEY_RIGHT = GLFW_KEY_RIGHT,
    KEY_LEFT = GLFW_KEY_LEFT,
    KEY_DOWN = GLFW_KEY_DOWN,
    KEY_UP = GLFW_KEY_UP,
    KEY_PAGE_UP = GLFW_KEY_PAGE_UP,
    KEY_PAGE_DOWN = GLFW_KEY_PAGE_DOWN,
    KEY_HOME = GLFW_KEY_HOME,
    KEY_END = GLFW_KEY_END,
    KEY_CAPS_LOCK = GLFW_KEY_CAPS_LOCK,
    KEY_SCROLL_LOCK = GLFW_KEY_SCROLL_LOCK,
    KEY_NUM_LOCK = GLFW_KEY_NUM_LOCK,
    KEY_PRINT_SCREEN = GLFW_KEY_PRINT_SCREEN,
    KEY_PAUSE = GLFW_KEY_PAUSE,
    KEY_F1 = GLFW_KEY_F1,
    KEY_F2 = GLFW_KEY_F2,
    KEY_F3 = GLFW_KEY_F3,
    KEY_F4 = GLFW_KEY_F4,
    KEY_F5 = GLFW_KEY_F5,
    KEY_F6 = GLFW_KEY_F6,
    KEY_F7 = GLFW_KEY_F7,
    KEY_F8 = GLFW_KEY_F8,
    KEY_F9 = GLFW_KEY_F9,
    KEY_F10 = GLFW_KEY_F10,
    KEY_F11 = GLFW_KEY_F11,
    KEY_F12 = GLFW_KEY_F12,
    KEY_F13 = GLFW_KEY_F13,
    KEY_F14 = GLFW_KEY_F14,
    KEY_F15 = GLFW_KEY_F15,
    KEY_F16 = GLFW_KEY_F16,
    KEY_F17 = GLFW_KEY_F17,
    KEY_F18 = GLFW_KEY_F18,
    KEY_F19 = GLFW_KEY_F19,
    KEY_F20 = GLFW_KEY_F20,
    KEY_F21 = GLFW_KEY_F21,
    KEY_F22 = GLFW_KEY_F22,
    KEY_F23 = GLFW_KEY_F23,
    KEY_F24 = GLFW_KEY_F24,
    KEY_F25 = GLFW_KEY_F25,
    KEY_NUMPAD_1 = GLFW_KEY_KP_1,
    KEY_NUMPAD_2 = GLFW_KEY_KP_2,
    KEY_NUMPAD_3 = GLFW_KEY_KP_3,
    KEY_NUMPAD_4 = GLFW_KEY_KP_4,
    KEY_NUMPAD_5 = GLFW_KEY_KP_5,
    KEY_NUMPAD_6 = GLFW_KEY_KP_6,
    KEY_NUMPAD_7 = GLFW_KEY_KP_7,
    KEY_NUMPAD_8 = GLFW_KEY_KP_8,
    KEY_NUMPAD_9 = GLFW_KEY_KP_9,
    KEY_NUMPAD_DECIMAL = GLFW_KEY_KP_DECIMAL,
    KEY_NUMPAD_DIVIDE = GLFW_KEY_KP_DIVIDE,
    KEY_NUMPAD_MULTIPLY = GLFW_KEY_KP_MULTIPLY,
    KEY_NUMPAD_SUBSTRACT = GLFW_KEY_KP_SUBTRACT,
    KEY_NUMPAD_ADD = GLFW_KEY_KP_ADD,
    KEY_NUMPAD_ENTER = GLFW_KEY_KP_ENTER,
    KEY_NUMPAD_EQUAL = GLFW_KEY_KP_EQUAL,
    KEY_L_SHIFT = GLFW_KEY_LEFT_SHIFT,
    KEY_L_CONTROL = GLFW_KEY_LEFT_CONTROL,
    KEY_L_ALT = GLFW_KEY_LEFT_ALT,
    KEY_L_SUPER = GLFW_KEY_LEFT_SUPER,
    KEY_R_SHIFT = GLFW_KEY_RIGHT_SHIFT,
    KEY_R_CONTROL = GLFW_KEY_RIGHT_CONTROL,
    KEY_R_ALT = GLFW_KEY_RIGHT_ALT,
    KEY_R_SUPER = GLFW_KEY_RIGHT_SUPER,
    KEY_MENU = GLFW_KEY_MENU;
	
	/**Boolean flags for key repeat*/
	private static boolean enableKeyRepeat;
	
	/**
	 * Returns if the key is currently down
	 * @param window the desired window
	 * @param mod the key to check
	 * @return true if key is currently down
	 */
	public static boolean isKeyDown(long window, int key) {
		return glfwGetKey(window, key) == PRESS;
	}
	
	/**
	 * Returns if the alt modifier is down
	 * @param mod Bit field modifier key
	 * @return true if alt modifier is down
	 */
	public static boolean isAltDown(int mod) {
		return (GLFW_MOD_ALT & mod) == GLFW_MOD_ALT;
	}
	
	/**
	 * Returns if the ctrl modifier is down
	 * @param mod Bit field modifier key
	 * @return true if ctrl modifier is down
	 */
	public static boolean isCtrlDown(int mod) {
		return (GLFW_MOD_CONTROL & mod) == GLFW_MOD_CONTROL;
	}
	
	/**
	 * Returns if the shift modifier is down
	 * @param mod Bit field modifier key
	 * @return true if shift modifier is down
	 */
	public static boolean isShiftDown(int mod) {
		return (GLFW_MOD_SHIFT & mod) == GLFW_MOD_SHIFT;
	}
	
	/**	
	 * Returns if the super modifier is down
	 * @param mod Bit field modifier key
	 * @return true if super modifier is down
	 */
	public static boolean isSuperDown(int mod) {
		return (GLFW_MOD_SUPER & mod) == GLFW_MOD_SUPER;
	}
	
	/**Enable or disable repeated key (a key kept pressed throw repeat events)*/
	public static void setEnableKeyRepeat(boolean b) {
		enableKeyRepeat = b;
	}
	
	/**Mouse buttons*/
	public static final int
	MOUSE_BTN_1 = GLFW_MOUSE_BUTTON_1,
    MOUSE_BTN_2 = GLFW_MOUSE_BUTTON_2,
	MOUSE_BTN_3 = GLFW_MOUSE_BUTTON_3,
	MOUSE_BTN_4 = GLFW_MOUSE_BUTTON_4,
	MOUSE_BTN_5 = GLFW_MOUSE_BUTTON_5,
	MOUSE_BTN_6 = GLFW_MOUSE_BUTTON_6,
	MOUSE_BTN_7 = GLFW_MOUSE_BUTTON_7,
	MOUSE_BTN_8 = GLFW_MOUSE_BUTTON_8,
	MOUSE_BTN_LEFT = GLFW_MOUSE_BUTTON_1,
	MOUSE_BTN_RIGHT = GLFW_MOUSE_BUTTON_2,
	MOUSE_BTN_MIDDLE = GLFW_MOUSE_BUTTON_3;
	
	private static DoubleBuffer mouseX;
	
	private static DoubleBuffer mouseY;
	
	/**Returns if the button is currently down*/
	public static boolean isMouseButtonDown(int button) {
		return glfwGetMouseButton(currWindow, button) == PRESS;
	}
	
	/**
	 * Return the x-coordinate of the mouse in the window
	 * @return int the x-coordinate of the mouse in the window
	 */
	public static int getMouseX() {
		return (int) mouseX.get(0);
	}
	
	/**
	 * Return the y-coordinate of the mouse in the window
	 * @return int the y-coordinate of the mouse in the window
	 */
	public static int getMouseY() {
		return (int) mouseY.get(0);
	}
	
	/**
	 * Return the virtual x-coordinate of the mouse in the render area.
	 * See {@link ResolutionHelper} for more details.
	 * @return int the x-coordinate of the mouse in the render area
	 */
	public static int getVirtMouseX() {
		return (int) ((((mouseX.get(0) + ResolutionHelper.RENDER_AREA_OFFX) * ResolutionHelper.NATIVE_WIDTH) / ResolutionHelper.RENDER_AREA_WIDTH));
	}
	
	/**
	 * Return the virtual y-coordinate of the mouse in the render area.
	 * See {@link ResolutionHelper} for more details.
	 * @return int the y-coordinate of the mouse in the render area
	 */
	public static int getVirtMouseY() {
		return (int) ((((mouseY.get(0) + ResolutionHelper.RENDER_AREA_OFFY) * ResolutionHelper.NATIVE_HEIGHT) / ResolutionHelper.RENDER_AREA_HEIGHT));
	}
	
	/**Joysticks*/
	public static final int
	JOYSTICK_1 = GLFW_JOYSTICK_1,
	JOYSTICK_2 = GLFW_JOYSTICK_2,
	JOYSTICK_3 = GLFW_JOYSTICK_3,
	JOYSTICK_4 = GLFW_JOYSTICK_4,
	JOYSTICK_5 = GLFW_JOYSTICK_5,
	JOYSTICK_6 = GLFW_JOYSTICK_6,
	JOYSTICK_7 = GLFW_JOYSTICK_7,
	JOYSTICK_8 = GLFW_JOYSTICK_8,
	JOYSTICK_9 = GLFW_JOYSTICK_9,
	JOYSTICK_10 = GLFW_JOYSTICK_10,
	JOYSTICK_11 = GLFW_JOYSTICK_11,
	JOYSTICK_12 = GLFW_JOYSTICK_12,
	JOYSTICK_13 = GLFW_JOYSTICK_13,
	JOYSTICK_14 = GLFW_JOYSTICK_14,
	JOYSTICK_15 = GLFW_JOYSTICK_15,
	JOYSTICK_16 = GLFW_JOYSTICK_16,
	JOYSTICK_LAST = GLFW_JOYSTICK_16;

	/**Default joystick index*/
	public static int JOYSTICK_DEFAULT = JOYSTICK_1;

	/**
	 * Joystick button mapping, default mapping correspond to Xbox 360 joystick.
	 * The variables are not final and can be mapped again.
	 */
	public static int
	JS_BUTTON_A = 0,
	JS_BUTTON_B = 1,
	JS_BUTTON_X = 2,
	JS_BUTTON_Y = 3,
	JS_BUTTON_LB = 4,
	JS_BUTTON_RB = 5,
	JS_BUTTON_BACK = 6,
	JS_BUTTON_START = 7,
	JS_BUTTON_LS = 8,
	JS_BUTTON_RS = 9,
	JS_BUTTON_DPAD_UP = 10,
	JS_BUTTON_DPAD_RIGHT = 11,
	JS_BUTTON_DPAD_DOWN = 12,
	JS_BUTTON_DPAD_LEFT = 13,
	JS_BUTTON_LAST = JS_BUTTON_DPAD_LEFT;
	
	/**
	 * Joystick axes mapping, default mapping correspond to Xbox 360 joystick.
	 * The variables are not final and can be mapped again.
	 */
	public static int
	JS_AXIS_LS_X = 0,
	JS_AXIS_LS_Y = 1,
	JS_AXIS_TRIGGER = 2,
	JS_AXIS_RS_X = 3,
	JS_AXIS_RS_Y = 4,
	JS_AXIS_LAST = JS_AXIS_RS_Y;
	
	/**Array of buffer containing the current state of all the joysticks' buttons*/
	private static ByteBuffer[] joysticksButtons;
	/**Array of buffer containing the current state of all the joysticks' axes*/
	private static FloatBuffer[] joysticksAxes;
	/**Array of buffer containing the previous state of all the joysticks' buttons*/
	private static ByteBuffer[] prevJsButtons;
	/**Array of buffer containing the previous state of all the joysticks' axes*/
	private static FloatBuffer[] prevJsAxes;
	
	/**Dead zone of the axes, no event will be fired under this value.</br>
	 * Default value is 0.1*/
	private static double joystickAxisDeadZone = 0.1;
	
	/**
	 * Set the joysticks dead zone value. No axes event will be fired under this value
	 * @param value of the dead zone
	 */
	public static void setJoystickDeadZone(double value) {
		joystickAxisDeadZone = value;
	}
	
	/**
	 * Returns if the button of the selected joystick is down
	 * @param joystick the desired joystick
	 * @param button the button to check on
	 */
	public static boolean isJoystickBtnDown(int joystick, int button) {
		//Check bounds for joystick
		if(joystick < 0 || joystick > JOYSTICK_LAST)
			return false;
		
		//Check bounds for button
		if(button < 0 || button > JS_BUTTON_LAST)
			return false;
		
		//Check if the joystick is present
		if(glfwJoystickPresent(joystick) == GL11.GL_FALSE || joysticksButtons[joystick] == null)
			return false;
		
		return joysticksButtons[joystick].get(button) == 1;
	}
	
	/**
	 * Returns if the button of the default joystick {@link #JOYSTICK_DEFAULT} is down
	 * @param button the button to check on
	 */
	public static boolean isJoystickBtnDown(int button) {
		return isJoystickBtnDown(JOYSTICK_1, button);
	}
	
	/**
	 * Returns the current axis value of the selected joystick
	 * 
	 * <p><b>Note: </b> the method will return 0 if the joystick or axis parameters are out of bounds, if the joystick is not present
	 * and if the value is inferior to the dead zone of the axis</p>
	 * @param joystick the desired joystick, see {@link #JOYSTICK_1}
	 * @param axis the desired axis, see {@link #JS_AXIS_LS_X}
	 * @return the current double value of the axis
	 */
	public static double getJoystickAxis(int joystick, int axis) {
		//Check bounds for joystick
		if(joystick < 0 || joystick > JOYSTICK_LAST)
			return 0;
		
		//Check bounds for axes
		if(axis < 0 || axis > JS_AXIS_LAST)
			return 0;
		
		//Check if the joystick is present
		if(glfwJoystickPresent(joystick) == GL11.GL_FALSE || joysticksAxes[joystick] == null)
			return 0;
		
		return Math.abs(joysticksAxes[joystick].get(axis)) < joystickAxisDeadZone ? 0 : joysticksAxes[joystick].get(axis);
	}
	
	/**
	 * Returns the current axis value of the default joystick {@link #JOYSTICK_DEFAULT}
	 * @param axis the desired axis, see {@link #JS_AXIS_LS_X}
	 * @return double value between -1.0 and 1.0
	 */
	public static double getJoystickAxis(int axis) {
		return getJoystickAxis(JOYSTICK_DEFAULT);
	}
	
	/**
	 * Returns the current left stick x-axis value of the selected joystick
	 * @param joystick the desired joystick, see {@link t#JOYSTICK_1}
	 * @return double value between -1.0 and 1.0
	 */
	public static double getJoystickLeftXAxis(int joystick) {
		return getJoystickAxis(joystick, JS_AXIS_LS_X);
	}
	
	/**
	 * Returns the current left stick x-axis value of the default joystick {@link #JOYSTICK_DEFAULT}
	 * @return double value between -1.0 and 1.0
	 */
	public static double getJoystickLeftXAxis() {
		return getJoystickAxis(JOYSTICK_DEFAULT, JS_AXIS_LS_X);
	}
	
	/**
	 * Returns the current left stick y-axis value of the selected joystick
	 * @param joystick the desired joystick, see {@link #JOYSTICK_1}
	 * @return double value between -1.0 and 1.0
	 */
	public static double getJoystickLeftYAxis(int joystick) {
		return getJoystickAxis(joystick, JS_AXIS_LS_Y);
	}
	
	/**
	 * Returns the current left stick y-axis value of the default joystick {@link #JOYSTICK_DEFAULT}
	 * @return double value between -1.0 and 1.0
	 */
	public static double getJoystickLeftYAxis() {
		return getJoystickAxis(JOYSTICK_DEFAULT, JS_AXIS_LS_Y);
	}

	/**
	 * Returns the current right stick x-axis value of the selected joystick
	 * @param joystick the desired joystick, see {@link #JOYSTICK_1}
	 * @return double value between -1.0 and 1.0
	 */
	public static double getJoystickRightXAxis(int joystick) {
		return getJoystickAxis(joystick, JS_AXIS_RS_X);
	}
	
	
	/**
	 * Returns the current right stick x-axis value of the default joystick {@link #JOYSTICK_DEFAULT}
	 * @return double value between -1.0 and 1.0
	 */
	public static double getJoystickRightXAxis() {
		return getJoystickAxis(JOYSTICK_DEFAULT, JS_AXIS_RS_X);
	}
	
	/**
	 * Returns the current right stick y-axis value of the selected joystick
	 * @param joystick the desired joystick, see {@link #JOYSTICK_1}
	 * @return double value between -1.0 and 1.0
	 */
	public static double getJoystickRightYAxis(int joystick) {
		return getJoystickAxis(joystick, JS_AXIS_RS_Y);
	}
	
	/**
	 * Returns the current right stick y-axis value of the default joystick {@link #JOYSTICK_DEFAULT}
	 * @param joystick the desired joystick, see {@link #JOYSTICK_1}
	 * @return double value between -1.0 and 1.0
	 */
	public static double getJoystickRightYAxis() {
		return getJoystickAxis(JOYSTICK_DEFAULT, JS_AXIS_RS_Y);
	}
	
	/**
	 * Returns the current trigger axis value of the selected joystick
	 * @param joystick the desired joystick, see {@link Input#JOYSTICK_1}
	 * @return double value between -1.0 and 1.0, between -1.0 and 0 is the left trigger, 
	 * between 0 and 1.0 is the right trigger, can be 0 when both trigger are done
	 */
	public static double getJoystickTriggerAxis(int joystick) {
		return getJoystickAxis(joystick, JS_AXIS_TRIGGER);
	}
	
	/**
	 * Returns the current trigger axis value of the default joystick {@link #JOYSTICK_DEFAULT}
	 * @param joystick the desired joystick, see {@link Input#JOYSTICK_1}
	 * @return double value between -1.0 and 1.0, between -1.0 and 0 is the right trigger, 
	 * between 0 and 1.0 is the left trigger, can be 0 when both trigger are down
	 */
	public static double getJoystickTriggerAxis() {
		return getJoystickAxis(JOYSTICK_DEFAULT, JS_AXIS_TRIGGER);
	}
	
	/**
	 * To be call before to use any other method of the Input class
	 * @param window the current window
	 */
	public static void init(long window) {
		if(init)
			return;
		
		init = true;
		currWindow = window;
		mouseX = BufferUtils.createDoubleBuffer(1);
		mouseY = BufferUtils.createDoubleBuffer(1);
		
		joysticksButtons = new ByteBuffer[GLFW_JOYSTICK_LAST + 1];
		joysticksAxes = new FloatBuffer[GLFW_JOYSTICK_LAST + 1];
		prevJsButtons = new ByteBuffer[GLFW_JOYSTICK_LAST + 1];
		prevJsAxes = new FloatBuffer[GLFW_JOYSTICK_LAST + 1];
		
		for(int i = 0; i < prevJsButtons.length; i++) {
			prevJsButtons[i] = BufferUtils.createByteBuffer(JS_BUTTON_LAST + 1); 
			prevJsAxes[i] = BufferUtils.createFloatBuffer(JS_AXIS_LAST + 1); 
		}
		
	
		updateJoystickBuffers();
		
		glfwSetKeyCallback(currWindow, keyCallback = new GLFWKeyCallback() {
			@Override
			public void invoke(long window, int key, int scancode, int action, int mods) {
				if(action != REPEAT || enableKeyRepeat) {
					KeyboardInputEvent event = new KeyboardInputEvent(key, scancode, action, mods);
					
					if(inputListener != null)
						inputListener.fireInput(event);
					
					if(keyboardListener != null)
						keyboardListener.fireInput(event);
				}
			}
		});
		
		glfwSetMouseButtonCallback(currWindow, mouseBtnCallback = new GLFWMouseButtonCallback() {
			@Override
			public void invoke(long window, int button, int action, int mods) {
				MouseInputEvent event = new MouseInputEvent(InputEventTypes.MOUSE_BUTTON, button, action, mods, mouseX.get(0), mouseY.get(0));
				
				if(inputListener != null)
					inputListener.fireInput(event);
				
				if(mouseListener != null)
					mouseListener.fireInput(event);
			}
		});
		
		glfwSetCursorPosCallback(currWindow, mousePosCallback = new GLFWCursorPosCallback() {
			@Override
			public void invoke(long window, double xpos, double ypos) {
				MouseInputEvent event = new MouseInputEvent(InputEventTypes.MOUSE_POS, -1, -1, -1, xpos, ypos);
				
				if(inputListener != null)
					inputListener.fireInput(event);
				
				if(mouseListener != null)
					mouseListener.fireInput(event);
			}
		});
		
		glfwSetScrollCallback(currWindow, scrollCallback = new GLFWScrollCallback() {
			@Override
			public void invoke(long window, double xoffset, double yoffset) {
				MouseInputEvent event = new MouseInputEvent(InputEventTypes.MOUSE_WHEEL, -1, -1, -1, xoffset, yoffset);
				
				if(inputListener != null)
					inputListener.fireInput(event);
				
				if(mouseListener != null)
					mouseListener.fireInput(event);
			}
		});
	}
	
	/**Set the current window*/
	public static void setWindow(long window) {
		currWindow = window;
		
		//Set the callback of the current window
		glfwSetKeyCallback(currWindow, keyCallback);
		glfwSetMouseButtonCallback(currWindow, mouseBtnCallback);
		glfwSetCursorPosCallback(currWindow, mousePosCallback);
		glfwSetScrollCallback(currWindow, scrollCallback);
	}
	
	/**Set the input listener*/
	public static void setInputListener(InputListener listener) {
		inputListener = listener;
	}
	
	/**Set the keyboard input listener*/
	public static void setKeyboardInputListener(KeyboardInputListener listener) {
		keyboardListener = listener;
	}
	
	/**Set the mouse input listener*/
	public static void setMouseInputListener(MouseInputListener listener) {
		mouseListener = listener;
	}
	
	/**Set the joystick input listener*/
	public static void setJoystickInputListener(JoystickInputListener listener) {
		joystickListener = listener;
	}
	
	/**Returns true if Input is already initialized*/
	public static boolean isInit() {
		return init;
	}
	
	/**
	 * The method looks for different between the previous and new value
	 * in the buffers and then fire new input event
	 */
	private static void checkJoystikEvent() {
		//Loop through all the joysticks
		for(int i = 0; i < prevJsButtons.length; i++) {
			if(joysticksButtons[i] == null)
				break;
			
			//Compare value then set current state to previous state for every button
			for(int btn = 0; btn <= JS_BUTTON_LAST; btn++){
				prevJsButtons[i].position(btn);
				if(prevJsButtons[i].get(btn) != joysticksButtons[i].get(btn)) {
					JoystickInputEvent event = new JoystickInputEvent(InputEventTypes.JOYSTICK_BUTTON, i, btn, joysticksButtons[i].get(btn));
					
					if(inputListener != null)
						inputListener.fireInput(event);
					
					if(joystickListener != null)
						joystickListener.fireInput(event);
				}
				
				prevJsButtons[i].put(joysticksButtons[i].get(btn));
			}
			
			//Compare value then set current state to previous state for every axis
			for(int axis = 0; axis <= JS_AXIS_LAST; axis++){
				prevJsAxes[i].position(axis);
				if(prevJsAxes[i].get(axis) != joysticksAxes[i].get(axis) && Math.abs(joysticksAxes[i].get(axis)) >= joystickAxisDeadZone) {
					JoystickInputEvent event = new JoystickInputEvent(InputEventTypes.JOYSTICK_AXIS, i, axis, joysticksAxes[i].get(axis));
					
					if(inputListener != null)
						inputListener.fireInput(event);
					
					if(joystickListener != null)
						joystickListener.fireInput(event);
				}
				
				prevJsAxes[i].put(joysticksAxes[i].get(axis));
			}
			
		}
	}
	
	/**
	 * Update the buffers by calling glfwGetJoystickButtons and 
	 * glfwGetJoystickAxes with the connected joystick
	 */
	private static void updateJoystickBuffers() {
		for(int i = 0; i < joysticksButtons.length; i++) {
			if(glfwJoystickPresent(i) == GL11.GL_TRUE) {
				joysticksButtons[i] = glfwGetJoystickButtons(i); 
				joysticksAxes[i] = glfwGetJoystickAxes(i); 
			}
			else {
				joysticksButtons[i] = null; 
				joysticksAxes[i] = null; 
			}
		}
	}
	
	/**Poll events and update inputs*/
	public static void update() {
		//Poll the events
		glfwPollEvents();
		
		//Update the mouse position
		glfwGetCursorPos(currWindow, mouseX, mouseY);
		
		//Update joystick buffers in case a joystick has been connected
		updateJoystickBuffers();
		//Check for joysticks events
		checkJoystikEvent();
	}
	
	/**Destroy the input, release callback*/
	public static  void destroy() {
		keyCallback.release();
		mouseBtnCallback.release();
		mousePosCallback.release();
		scrollCallback.release();
		init = false;
	}
}
