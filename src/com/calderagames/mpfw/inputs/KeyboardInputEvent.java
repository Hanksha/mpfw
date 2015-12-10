package com.calderagames.mpfw.inputs;

public class KeyboardInputEvent extends InputEvent {
	/**The keyboard key that was pressed or released*/
	public int key;
	/**The system-specific scancode of the key*/
	public int scancode;
	/**Action the key action. See {@link Input#PRESS}, {@link Input#RELEASED}, {@link Input#REPEAT}*/
	public int action;
	/**Bit field describing which modifiers keys were held down*/
	public int mods;
	
	/**
	 * Constructor for a new Keyboard Input Event
	 * @param key the keyboard key that was pressed or released
	 * @param scancode the system-specific scancode of the key
	 * @param action the key action. See {@link Input#PRESS}, {@link Input#RELEASED}, {@link Input#REPEAT}
	 * @param mods bit field describing which modifiers keys were held down
	 */
	public KeyboardInputEvent(int key, int scancode, int action, int mods) {
		super(InputEventSources.KEYBOARD, InputEventTypes.KEYBOARD_KEY);
		this.key = key;
		this.scancode = scancode;
		this.action = action;
		this.mods = mods;
	}

	@Override
	public String toString() {
		return super.toString() + "key: " + key + " scancode: " + scancode + " action: " + action + " mods: " + mods;
	}
}
