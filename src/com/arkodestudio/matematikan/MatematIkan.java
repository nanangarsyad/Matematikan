package com.arkodestudio.matematikan;

import com.arkodestudio.framework.Screen;
import com.arkodestudio.framework.impl.AndroidGame;

public class MatematIkan extends AndroidGame {

	public static int FRAME_BUFFER_WIDHT = 800;
	public static int FRAME_BUFFER_HEIGHT = 480;
	
	@Override
	public Screen getLoadingScreen() {
		return new LoadSplashScreen(this);		
	}

	@Override
	public int getFrameBufferHeight() {	
		return FRAME_BUFFER_HEIGHT;
	}

	@Override
	public int getFrameBufferWidth() {		
		return FRAME_BUFFER_WIDHT;
	}	
	
}
