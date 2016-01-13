package com.calderagames.mpfw;

/**
 * Utility class for handling resolutions with different ratio
 */
public abstract class ResolutionHelper {
	
	/**Native resolution width of the game, default: 1280*/
	public static int NATIVE_WIDTH = 1280;
	/**Native resolution height of the game, default: 720*/
	public static int NATIVE_HEIGHT = 720;
	
	/**Current window resolution width*/
	public static float CURRWIN_WIDTH = 1280;
	/**Current window resolution height*/
	public static float CURRWIN_HEIGHT = 720;
	
	/**Scale between native and current resolution*/
	public static float SCALE = CURRWIN_WIDTH / NATIVE_WIDTH;

	/**
	 * Width of the render area, 
	 * this is the width you should draw your frame buffer.
	 * NATIVE_WIDTH * SCALE 
	 */
	public static float RENDER_AREA_WIDTH = NATIVE_WIDTH * SCALE;
	/**
	 * Height of the render area, 
	 * this is the height you should draw your frame buffer.
	 * NATIVE_HEIGHT * SCALE 
	 */
	public static float RENDER_AREA_HEIGHT = NATIVE_HEIGHT * SCALE;

	/**
	 * Offset x from the top-left corner of the window of the render area*/
	public static float RENDER_AREA_OFFX = 0;
	/**
	 * Offset y from the top-left corner of the window of the render area*/
	public static float RENDER_AREA_OFFY = 0;

	/**
	 * Update the render area resolution ({@link #RENDER_AREA_WIDTH}, {@link #RENDER_AREA_HEIGHT}) 
	 * and render area offset ({@link #RENDER_AREA_OFFX}, {@link #RENDER_AREA_OFFY}).
	 * <p>The algorithm is the following: if the width and height provided 
	 * follow a 16:9, 16:10 or 4:3 ratio we compute the scale by dividing the width by the native width.
	 * Then multiply the native resolution by the scale, compute for the render area offset
	 * by dividing by 2 the difference between the current resolution and the render area resolution.</p>
	 * <p><b>Notes:</b> Don't forget to set {@link #NATIVE_WIDTH} and {@link #NATIVE_HEIGHT} to your native resolution. 
	 * This method is automatically called in the Window class when creating a window.</p>
	 * @param width the new window width
	 * @param height the new window height
	 */
	public static void update(float width, float height) {
		CURRWIN_WIDTH = width;
		CURRWIN_HEIGHT = height;

		if(CURRWIN_WIDTH / CURRWIN_HEIGHT == 16f / 9f || CURRWIN_WIDTH / CURRWIN_HEIGHT == 16f / 10f || CURRWIN_WIDTH / CURRWIN_HEIGHT == 4f / 3f) {
			SCALE = CURRWIN_WIDTH / NATIVE_WIDTH;
			RENDER_AREA_WIDTH = NATIVE_WIDTH * SCALE;
			RENDER_AREA_HEIGHT = NATIVE_HEIGHT * SCALE;
			RENDER_AREA_OFFX = Math.abs(CURRWIN_WIDTH - RENDER_AREA_WIDTH) / 2;
			RENDER_AREA_OFFY = Math.abs(CURRWIN_HEIGHT - RENDER_AREA_HEIGHT) / 2;
		}
		else {

			SCALE = (float) ((Math.floor(CURRWIN_WIDTH / 16f) * 16f) / NATIVE_WIDTH);

			if(NATIVE_WIDTH * SCALE >= CURRWIN_WIDTH)
				SCALE = (float) ((Math.floor(CURRWIN_HEIGHT / 9f) * 9f) / NATIVE_HEIGHT);

			RENDER_AREA_WIDTH = NATIVE_WIDTH * SCALE;
			RENDER_AREA_HEIGHT = NATIVE_HEIGHT * SCALE;
			RENDER_AREA_OFFX = Math.abs(CURRWIN_WIDTH - RENDER_AREA_WIDTH) / 2;
			RENDER_AREA_OFFY = Math.abs(CURRWIN_HEIGHT - RENDER_AREA_HEIGHT) / 2;
		}
	}

	public static float getPosX(float x) {
		return x + RENDER_AREA_OFFX;
	}

	public static float getPosY(float y) {
		return y + RENDER_AREA_OFFY;
	}
}
