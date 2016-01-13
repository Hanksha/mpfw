package com.calderagames.mpfw.inputs;

public class MouseInputEvent extends InputEvent {

	/**Mouse button pressed or released, see {@link Input#MOUSE_BTN_1}*/
	public int button;
	/**Action can be {@link Input#PRESS} or {@link Input#RELEASE}*/
	public int action;
	/**Bit field describing which modifiers keys were held down*/
	public int mods;
	/**For {@link InputEventTypes#MOUSE_POS} mouse x-coordinate in the screen. 
	 * For {@link InputEventTypes#MOUSE_WHEEL} the scroll offset along the x-axis*/
	public double valueX;
	/**For {@link InputEventTypes#MOUSE_POS} mouse y-coordinate in the screen. 
	 * For {@link InputEventTypes#MOUSE_WHEEL} the scroll offset along the y-axis*/
	public double valueY;

	/**
	 * Constructor for a new Mouse Input Event
	 * @param eventType type of mouse event. See {@link InputEventTypes#MOUSE_BUTTON}, {@link InputEventTypes#MOUSE_POS}, {@link InputEventTypes#MOUSE_WHEEL}
	 * @param button the mouse button that was pressed or released
	 * @param action the button action. See {@link Input#PRESS}, {@link Input#RELEASED}, {@link Input#REPEAT}
	 * @param mods bit field describing which modifiers keys were held down
	 * @param valueX For {@link InputEventTypes#MOUSE_POS} mouse x-coordinate in the screen. 
	 * For {@link InputEventTypes#MOUSE_WHEEL} the scroll offset along the x-axis
	 * @param valueY For {@link InputEventTypes#MOUSE_POS} mouse y-coordinate in the screen. 
	 * For {@link InputEventTypes#MOUSE_WHEEL} the scroll offset along the y-axis
	 */
	public MouseInputEvent(InputEventTypes eventType, int button, int action, int mods, double valueX, double valueY) {
		super(InputEventSources.MOUSE, eventType);
		this.button = button;
		this.action = action;
		this.mods = mods;
		this.valueX = valueX;
		this.valueY = valueY;
	}

	@Override
	public String toString() {
		return super.toString() + "button: " + button + " action: " + action + " mods: " + mods + " value x: " + valueX + " value y: " + valueY;
	}
}
