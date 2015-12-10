package com.calderagames.mpfw;

public class WindowEvent {

	/**Enum of window event type*/
	public enum WindowEventType {
		CLOSE,
		FOCUS,
		ICONIFY,
		POSITION,
		REFRESH,
		SIZE,
		FRAMEBUFFER_SIZE,
	}
	
	/**Type of window event*/
	public WindowEventType eventType;
	/**Boolean flag used for {@link WindowEventType#FOCUS} and {@link WindowEventType#ICONIFY}*/
	public boolean action;
	/**
	 * x coordinate for {@link WindowEventType#POSITION} and width
	 * for {@link WindowEventType#SIZE} and {@link WindowEventType#FRAMEBUFFER_SIZE}
	 */
	public int value1;
	/**
	 * y coordinate for {@link WindowEventType#POSITION} and height
	 * for {@link WindowEventType#SIZE} and {@link WindowEventType#FRAMEBUFFER_SIZE}
	 */
	public int value2;
	
	/**
	 * Create a new Window Event
	 * @param eventType the type of window event, see {@link WindowEventType}
	 * @param action boolean flag used for {@link WindowEventType#FOCUS} and {@link WindowEventType#ICONIFY}
	 * @param value1 x coordinate for {@link WindowEventType#POSITION} and width
	 * for {@link WindowEventType#SIZE} and {@link WindowEventType#FRAMEBUFFER_SIZE}
	 * @param value2 y coordinate for {@link WindowEventType#POSITION} and height 
	 * for {@link WindowEventType#SIZE} and {@link WindowEventType#FRAMEBUFFER_SIZE}
	 */
	public WindowEvent(WindowEventType eventType, boolean action, int value1, int value2) {
		this.eventType = eventType;
		
		this.action = action;
		this.value1 = value1;
		this.value2 = value2;
	}
}
