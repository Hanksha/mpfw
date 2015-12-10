package com.calderagames.mpfw.inputs;

public class JoystickInputEvent extends InputEvent {

	/**Joystick index of the event*/
	public int joystick;
	/**
	 * For {@link InputEventTypes#JOYSTICK_BUTTON} button index of the event, see {@link Input#JS_BUTTON_A}.
	 * For {@link InputEventTypes#JOYSTICK_AXIS} axis index of the event, see {@link Input#JS_AXIS_LS_X}
	 */
	public int index;
	/**
	 * For {@link InputEventTypes#JOYSTICK_BUTTON} the value is either {@link Input#PRESS} or {@link Input#RELEASE}.
	 * For {@link InputEventTypes#JOYSTICK_AXIS} the value of that axis
	 */
	public double value;
	
	/**
	 * Constructor for a new Joystick Input Event
	 * @param eventType type of joystick event. See {@link InputEventTypes#JOYSTICK_BUTTON}, {@link InputEventTypes#JOYSTICK_AXIS}
	 * @param joystick the joystick index source of the event, see {@link Input#JOYSTICK_1} 
	 * @param index for {@link InputEventTypes#JOYSTICK_BUTTON} button index of the event, see {@link Input#JS_BUTTON_A}.
	 * For {@link InputEventTypes#JOYSTICK_AXIS} axis index of the event, see {@link Input#JS_AXIS_LS_X}
	 * @param value for {@link InputEventTypes#JOYSTICK_BUTTON} the value is either {@link Input#PRESS} or {@link Input#RELEASE}.
	 * For {@link InputEventTypes#JOYSTICK_AXIS} the value of that axis
	 */
	public JoystickInputEvent(InputEventTypes eventType, int joystick, int index, double value) {
		super(InputEventSources.JOYSTICK, eventType);
		this.joystick = joystick;
		this.index = index;
		this.value = value;
	}

	@Override
	public String toString() {
		return super.toString() + "joystick: " + joystick + " index: " + index + " value: " + value;
	}
}
