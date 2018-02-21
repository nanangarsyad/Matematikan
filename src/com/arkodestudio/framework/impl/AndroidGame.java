package com.arkodestudio.framework.impl;

import com.arkodestudio.framework.Audio;
import com.arkodestudio.framework.FileIO;
import com.arkodestudio.framework.Game;
import com.arkodestudio.framework.Graphics;
import com.arkodestudio.framework.Input;
import com.arkodestudio.framework.Screen;
import com.arkodestudio.matematikan.Assets;

import android.app.Activity;
import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.os.Bundle;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.util.Log;
import android.view.Display;
import android.view.Window;
import android.view.WindowManager;


public abstract class AndroidGame extends Activity implements Game{	

	private AndroidFastRenderView renderView;
	private Graphics graphics;
	private Audio audio;
	private Input input;
	private FileIO fileIO;
	private Screen screen;
	private WakeLock wakeLock;
	
	
	public abstract int getFrameBufferHeight();
	public abstract int getFrameBufferWidth();
	
	public AssetManager getAssetManger() {
		return this.getAssets();
	}
	
	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {	
		super.onCreate(savedInstanceState);			
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
		int frameBufferWidth  = getFrameBufferWidth();
		int frameBufferHeight = getFrameBufferHeight();
		Bitmap frameBuffer = Bitmap.createBitmap(frameBufferWidth, frameBufferHeight, Config.ARGB_8888);		
		float scaleX;
		float scaleY;
		Display display = getWindowManager().getDefaultDisplay();	
		scaleX = (float) (frameBufferWidth / (float)(display.getWidth()));
		scaleY = (float) (frameBufferHeight / (float)(display.getHeight()));
		Log.i("[INFO]AndroidGame.class::onCreate()::59::*ValueofWidth*",String.valueOf(display.getWidth()));
		Log.i("[INFO]AndroidGame.class::onCreate()::60::*ValueofHeight*",String.valueOf(display.getHeight()));
		Log.i("scaleX_scaleY",String.valueOf(scaleX + " x " + scaleY));
		/* GESER INPUT COORDINAT*/
		/*if (scaleX > scaleY) {
			scaleX = scaleY;
		} else if (scaleX < scaleY) {
			scaleY = scaleX;
		}*/
		Log.i("[NEW]scaleX_scaleY",String.valueOf(scaleX + " x " + scaleY));
		renderView = new AndroidFastRenderView(this, frameBuffer);
		graphics = new AndroidGraphics(this.getAssets(), frameBuffer);
		fileIO = new AndroidFileIO(this);
		audio = new AndroidAudio(this);
		input = new AndroidInput(this, renderView, scaleX, scaleY);
		screen = getLoadingScreen();
		setContentView(renderView);	
		PowerManager powerManager = (PowerManager) getSystemService(Context.POWER_SERVICE);
		wakeLock = powerManager.newWakeLock(PowerManager.FULL_WAKE_LOCK, "::AndroidGame.class::");
	}
	
	@Override
	protected void onResume() {		
		super.onResume();
		wakeLock.acquire();
		Assets.Audio.reload(audio);
		screen.resume();
		renderView.resume();			
	}
	
	@Override
	public void onPause() {
		super.onPause();
		wakeLock.release();
		renderView.pause();
		screen.pause();		
		Assets.Audio.dispose();
		if (isFinishing()){
			screen.dispose();
		}
	}
	
	public Input getInput() {
		return input;
	}
	
	public FileIO getFileIO() {
		return fileIO;
	}
	
	public Graphics getGraphics() {
		return graphics;
	}
	
	public Audio getAudio() {
		return audio;
	}
	public void setScreen(Screen screen) {
		if (screen == null)
			throw new IllegalArgumentException("Screen must not be null");
		this.screen.pause();
		this.screen.dispose();
		screen.resume();
		screen.update(0);
		this.screen = screen;
	}
	
	public Screen getCurrentScreen() {
		return screen;
	}
	@Override
	public void onBackPressed() {		
		getCurrentScreen().backButton();
	}
}
