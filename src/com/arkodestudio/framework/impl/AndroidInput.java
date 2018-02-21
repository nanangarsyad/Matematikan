package com.arkodestudio.framework.impl;

import java.util.List;

import android.content.Context;
import android.view.View;

import com.arkodestudio.framework.Input;

public class AndroidInput implements Input{
	
	private TouchHandler touchHandler;
	private KeyboardHandler keyHandler;
	
	public AndroidInput(Context context, View view, float scaleX, float scaleY) {
		touchHandler = new MultiTouchHandler(view, scaleX, scaleY);
		keyHandler = new KeyboardHandler(view);
	}	
	public boolean isTouchDown(int pointer) {
		return touchHandler.isTouchDown(pointer);
	}
	public int getTouchX(int pointer) {
		return touchHandler.getTouchX(pointer);
	}
	public int getTouchY(int pointer) {
		return touchHandler.getTouchY(pointer);
	}
	
	public List<TouchEvent> getTouchEvents() {
		return touchHandler.getTouchEvents();
	}
	
	public boolean isKeyPressed(int keyCode) {
		return keyHandler.isKeyPressed(keyCode);
	}
	public List <KeyEvent> getKeyEvents() {
		return keyHandler.getKeyEvents();
	}
	
	/* "NOT USED, YET"
	public float getAccelX() {
		return accelHandler.getAccelX();
	}
	public floatgetAccelY() {
		return accelHandler.getAccelY();
	}
	public float getAccelZ() {
		return accelHandler.getAccelZ();
	}

	*/
	
		

}
