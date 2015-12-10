package com.calderagames.mpfw.inputs;

public class InputEvent {
	
	/**Input source type*/
	public enum InputEventSources {
		KEYBOARD,
		MOUSE,
		JOYSTICK
	}
	
	/**Source type of the event*/
	public InputEventSources eventSource;
	
	/**Input event types*/
	public enum InputEventTypes {
		KEYBOARD_KEY,
		MOUSE_BUTTON,
		MOUSE_POS,
		MOUSE_WHEEL,
		JOYSTICK_BUTTON,
		JOYSTICK_AXIS,
	}
	
	/**Type of event*/
	public InputEventTypes eventType;
	
	/**
	 * Create a new input event
	 * @param eventSource source type of the event, see {@link InputEventSources}
	 * @param eventType type of the event, see {@link InputEventTypes}
	 */
	public InputEvent(InputEventSources eventSource, InputEventTypes eventType) {
		this.eventSource = eventSource;
		this.eventType = eventType;
	}
	
	@Override
	public String toString() {
		return "source: " + eventSource + " type: " + eventType + " ";
	}
}
