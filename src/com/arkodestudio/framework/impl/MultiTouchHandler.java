package com.arkodestudio.framework.impl;

import java.util.ArrayList;
import java.util.List;

import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.arkodestudio.framework.Input;
import com.arkodestudio.framework.Input.TouchEvent;
import com.arkodestudio.framework.Pool;
import com.arkodestudio.framework.Pool.PoolObjectFactory;

public class MultiTouchHandler implements TouchHandler {
	
	private final static int POOL_MAX_NUMBER = 100;
	private final static int MAX_TOUCHPOINTS = 10;
	private boolean[] isTouched = new  boolean[10];
	private int[] touchX = new int[10];
	private int[] touchY = new int[10];
	private int[] id = new int[10];
	private Pool<TouchEvent> touchEventPool;
	private List<TouchEvent> touchEventList = new ArrayList<Input.TouchEvent>();
	private List<TouchEvent> touchEventListBuffer = new ArrayList<Input.TouchEvent>();
	private float scaleX;
	private float scaleY;
			
	public MultiTouchHandler(View view, float scaleX, float scaleY) {
		this.scaleX = scaleX;
		this.scaleY = scaleY;
		Pool.PoolObjectFactory<TouchEvent> poolFactory = new PoolObjectFactory<Input.TouchEvent>() {
			@Override
			public TouchEvent createObject() {
				return new TouchEvent();			
			}
		};
		touchEventPool = new Pool<Input.TouchEvent>(poolFactory, POOL_MAX_NUMBER);
		view.setOnTouchListener(this);
	}
	
	@Override
	public boolean onTouch(View v, MotionEvent event) {
		synchronized (this) {
			int action = event.getAction() & MotionEvent.ACTION_MASK;
			int pointerIndex = (event.getAction() & MotionEvent.ACTION_POINTER_INDEX_MASK) >> MotionEvent.ACTION_POINTER_INDEX_SHIFT;
			int pointerCount = event.getPointerCount();
			TouchEvent newTouchEvent;
			for (int c = 0; c < MAX_TOUCHPOINTS; c++) {
				if (c >= pointerCount) {
					id[c] = -1;
					isTouched[c] = false;
					continue;
				}
				int pointerId = event.getPointerId(c);
				if(event.getAction() != MotionEvent.ACTION_MOVE && c != pointerIndex) {
					Log.i("ACTION_???","???_TOUCH");
					Log.i("ACTION_???",String.valueOf(event.getAction()+ "::" + pointerIndex));
					continue;
				}
				switch(action) {
					case MotionEvent.ACTION_POINTER_DOWN :
					case MotionEvent.ACTION_DOWN:					
						newTouchEvent = touchEventPool.newObject();
						newTouchEvent.type = TouchEvent.TOUCH_DOWN;
						newTouchEvent.pointer = pointerId;
						newTouchEvent.x = touchX[c] = (int) (event.getX(c) * scaleX);
						newTouchEvent.y = touchY[c] = (int) (event.getY(c) * scaleY);						
						isTouched[c] = true;
						id[c] = pointerId;
						Log.i("ACTION_DOWN",String.valueOf(touchX[c] + " x "+ touchY[c]));
						touchEventListBuffer.add(newTouchEvent);
						break;
					case MotionEvent.ACTION_UP:
					case MotionEvent.ACTION_POINTER_UP:
					case MotionEvent.ACTION_CANCEL:						
						newTouchEvent = touchEventPool.newObject();
						newTouchEvent.type = TouchEvent.TOUCH_UP;
						newTouchEvent.pointer = pointerId;
						newTouchEvent.x = touchX[c] = (int) (event.getX(c) * scaleX);
						newTouchEvent.y = touchY[c] = (int) (event.getY(c) * scaleY);						
						isTouched[c] = false;
						id[c] = -1;
						Log.i("ACTION_UP",String.valueOf(touchX[c] + " x "+ touchY[c]));
						touchEventListBuffer.add(newTouchEvent);
						break;
					case MotionEvent.ACTION_MOVE:						
						newTouchEvent = touchEventPool.newObject();
						newTouchEvent.type = TouchEvent.TOUCH_DRAGGED;
						newTouchEvent.pointer = pointerId;
						newTouchEvent.x = touchX[c] = (int) (event.getX(c) * scaleX);
						newTouchEvent.y = touchY[c] = (int) (event.getY(c) * scaleY);						
						isTouched[c] = true;
						id[c] = pointerId;
						Log.i("ACTION_MOVE",String.valueOf(touchX[c] + " x "+ touchY[c]));
						touchEventListBuffer.add(newTouchEvent);
						break;
				}				
			}
			return true;
		}
		
	}

	@Override
	public boolean isTouchDown(int pointer) {
		synchronized(this) {
			int index = getIndex(pointer);
			if(index < 0 || index >= MAX_TOUCHPOINTS)
				return false;
			else
			return isTouched[index];
		}
			
	}

	@Override
	public int getTouchX(int pointer) {
		synchronized(this) {
			int index = getIndex(pointer);
			if(index < 0 || index >= MAX_TOUCHPOINTS)
				return 0;
			else
				return touchX[index];
		}
	}

	@Override
	public int getTouchY(int pointer) {
		synchronized(this) {
			int index = getIndex(pointer);
			if(index < 0 || index >= MAX_TOUCHPOINTS)
				return 0;
			else
				return touchY[index];
		}
	}

	@Override
	public List<TouchEvent> getTouchEvents() {
		synchronized(this) {
			int len = touchEventList.size();
			for(int i = 0; i < len; i++)
				touchEventPool.free(touchEventList.get(i));
				touchEventList.clear();
				touchEventList.addAll(touchEventListBuffer);
				touchEventListBuffer.clear();
				return touchEventList;
			}
	}
	
	private int getIndex(int pointerId) {
		for (int i = 0; i < MAX_TOUCHPOINTS; i++) {
			if(id[i] == pointerId) {
				return i;
			}
		}
		return-1;
	}
	

}
