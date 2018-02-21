package com.arkodestudio.framework.impl;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class AndroidFastRenderView extends SurfaceView implements Runnable{
	private AndroidGame game;
	private Bitmap frameBuffer;
	Thread renderThread = null;
	SurfaceHolder surfaceHolder;
	private volatile boolean running = false;	
	private volatile boolean firstInit = false;	
	
	public AndroidFastRenderView(AndroidGame game, Bitmap frameBuffer) {
		super(game);
		this.frameBuffer = frameBuffer;
		this.game = game;
		this.surfaceHolder = this.getHolder();	
	}
	
	public void resume() {		
		running = true;
		renderThread = new Thread(this);
		renderThread.start();
	}
	
	public void pause() {
		running = false;
		while(true) {
			try{
				renderThread.join();
				return;
			} catch(InterruptedException e) {
				// retry
			}
		}
	}


	@Override
	public void run() {
		Rect dstRect = new Rect();
		long startTime = System.nanoTime();
		while(running) {
			if (surfaceHolder.getSurface().isValid() == false) {
				continue;
			}			
			float deltaTime = (System.nanoTime()-startTime) / 1000000000.0f;
			startTime = System.nanoTime();
			game.getCurrentScreen().update(deltaTime);
			game.getCurrentScreen().present(deltaTime);
			Canvas canvas = surfaceHolder.lockCanvas();
			canvas.getClipBounds(dstRect);
			if (!firstInit) {
				firstInit = true;				
				System.out.println("[here2] "+dstRect.right + " x " + dstRect.bottom);
			}
			canvas.drawBitmap(frameBuffer, null, dstRect, null);
			surfaceHolder.unlockCanvasAndPost(canvas);	
		}
		
	}

}
