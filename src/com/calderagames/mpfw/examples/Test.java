package com.calderagames.mpfw.examples;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL11;

import com.calderagames.mpfw.MPFW;
import com.calderagames.mpfw.Window;
import com.calderagames.mpfw.graphics.Sprite;
import com.calderagames.mpfw.graphics.SpriteBatch;
import com.calderagames.mpfw.graphics.Texture;
import com.calderagames.mpfw.graphics.TextureRegion;
import com.calderagames.mpfw.inputs.Input;
import com.calderagames.mpfw.inputs.InputEvent;
import com.calderagames.mpfw.inputs.InputEvent.InputEventSources;
import com.calderagames.mpfw.inputs.InputEvent.InputEventTypes;
import com.calderagames.mpfw.inputs.InputListener;
import com.calderagames.mpfw.inputs.KeyboardInputEvent;
import com.calderagames.mpfw.inputs.MouseInputEvent;
import com.calderagames.mpfw.math.Matrix4f;
import com.calderagames.mpfw.utils.Timer;

public class Test implements InputListener {
	
	//The window
	private Window window;
	
	private Sprite sprTroll;
	private Sprite sprLogo;
	private Texture tex;
	
	private int spriteCounter = 1;
	
	private boolean randMode;
	
	private double dt;
	
	private SpriteBatch sb;
	private Matrix4f proj;
	
	private int FPS, frameCounter;
	private Timer timerFPS = new Timer(1000);
	
	boolean apply;
	
	private void createWindow()	{
		//Create the a window and store it in window
		window = new Window(1280, 720, "Mithril Pants - Test");
		window.setVSync(false);
		Input.setInputListener(this);
		
		tex = new Texture(Test.class.getResource("mpfw-logo.png").getPath().replace("%20", " "), MPFW.MPFW_NEAREST, MPFW.MPFW_TEXTURE0);
		tex.load();
		
		sprTroll = new Sprite(64, 64, new TextureRegion(tex, 1, 601, 64, 64));
		sprLogo = new Sprite(910, 600, new TextureRegion(tex, 0, 0, 910, 600));
		
		sb = new SpriteBatch(1000);
		
		proj = new Matrix4f();
		proj.setOrtho(0, 1280, 720, 0, 1, -1);
		
		sb.setProjection(proj);
		sb.setColor(1f, 1f, 1f, 1f);
	}
	
	private void loop() {
		while(!window.shouldClose()) {
			
			if(apply) {
				sb.dispose();
				window.applyWindowHintsChanges();
				//sb = new SpriteBatch(1000);
				sb.setProjection(proj);
				sb.setColor(1f, 1f, 1f, 1f);
				apply = false;
			}
			
			GLFW.glfwSetTime(0);
			GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
			
			sb.begin(tex);
			
			for(int draw = 0; draw < spriteCounter; draw++)
			for(int i = 0; i < 20; i++)
				for(int j = 0; j < 11; j++)
				sb.draw(sprTroll.getTexRegion(), draw * 1 + i * 64, j * 64, sprTroll.getWidth(), sprTroll.getHeight(), 
				        1, 
				        1, 
				        0, 
				        false, false);
			
			sb.end();
			
			window.update();
			dt = GLFW.glfwGetTime();
			
			frameCounter++;
			
			if(timerFPS.tick()) {
				timerFPS.start();
				FPS = frameCounter;
				frameCounter = 0;
			}
			
			window.setTitle("Mithril Pants - Test - Num Sprite: " + spriteCounter * 220 + " - FPS: " + FPS + " - Render time: " + GLFW.glfwGetTime() * 1000);
		}
	}
	
	public Test() {
		//Create the window
		createWindow();
		//Start the game loop
		loop();
		
		tex.dispose();
		sb.dispose();
		window.destroy();
	}

	public static void main(String[] args) {
		new Test();
	}

	@Override
	public void fireInput(InputEvent event) {
		if(event.eventSource == InputEventSources.KEYBOARD) {
			KeyboardInputEvent kEvent = (KeyboardInputEvent) event;
			
			if(kEvent.action == Input.PRESS) {
    			if(kEvent.key == Input.KEY_F1) {
    				randMode = !randMode;
    			}
    			else if(kEvent.key == Input.KEY_F2) {
    				apply = true;
    			}
    			else if(kEvent.key == Input.KEY_F9) {
    				window.setDecorated(false);
    			}
    			else if(kEvent.key == Input.KEY_F10) {
    				window.setResizable(true);
    			}
    			if(kEvent.key == Input.KEY_F11) {
    				window.setFullscreen(true);
    			}
    			else if(kEvent.key == Input.KEY_F12) {
    				window.setFullscreen(false);
    			}
			}
		}
		else if(event.eventSource == InputEventSources.MOUSE) {
			MouseInputEvent mEvent = (MouseInputEvent) event;
			
			if(mEvent.eventType == InputEventTypes.MOUSE_WHEEL) {
				if(Input.isKeyDown(window.getHandle(), Input.KEY_L_ALT))
					spriteCounter = (int) Math.max(0, spriteCounter + 1 * mEvent.valueY);
				else
					spriteCounter = (int) Math.max(0, spriteCounter + 50 * mEvent.valueY);
				
			}
		}
	}
}
