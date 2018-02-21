package com.arkodestudio.matematikan;



import com.arkodestudio.framework.Game;
import com.arkodestudio.framework.Graphics;
import com.arkodestudio.framework.Graphics.PixmapFormat;
import com.arkodestudio.framework.Screen;

public class LoadSplashScreen extends Screen{
	
	private float currentTick;
	private int alphaChannel = 255; 
	
	
	public LoadSplashScreen(Game game) {
		super(game);	
		Graphics g = game.getGraphics();	
		Assets.splashScreen = g.newPixmap("screen_splash.png", PixmapFormat.ARGB4444); 
		Assets.loadingScreen = g.newPixmap("screen_loading.png", PixmapFormat.ARGB4444);
	}

	@Override
	public void update(float deltaTime) {					
		currentTick += deltaTime;
		if (currentTick <= 1.0f) {
			alphaChannel -= (int)((400) * deltaTime);
			if (alphaChannel <= 0) {
				alphaChannel = 0;
			}
		} 
		if (currentTick >= 2.0f && currentTick <= 3.0f) {
			alphaChannel += (int)((400) * deltaTime);
			if (alphaChannel >= 255) {
				alphaChannel = 255;
			}
		} 
		if (currentTick >= 3.0) {
			alphaChannel -= (int)((600) * deltaTime);
			if (alphaChannel <= 0) {
				alphaChannel = 0;
			}
		}
		if (currentTick >= 3.5f) {			
			game.setScreen(new LoadingScreen(game));
		}		
	}

	@Override
	public void present(float deltaTime) {
		Graphics g = game.getGraphics();
		
		if (currentTick <= 3.0f) {
			g.drawPixmap(Assets.splashScreen, 0, 0);
		} else {
			g.drawPixmap(Assets.loadingScreen, 0, 0);
		}
		g.drawARGB(alphaChannel, 0, 0, 0);
		
	}

	@Override
	public void pause() {
		
		
	}

	@Override
	public void resume() {
		
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void backButton() {
		// TODO Auto-generated method stub
		
	}

}
