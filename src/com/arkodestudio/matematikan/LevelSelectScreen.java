package com.arkodestudio.matematikan;

import java.util.List;
import com.arkodestudio.framework.Game;
import com.arkodestudio.framework.Graphics;
import com.arkodestudio.framework.Input.TouchEvent;
import com.arkodestudio.framework.Pixmap;
import com.arkodestudio.framework.Screen;

public class LevelSelectScreen extends Screen {
	public enum Lvl {
		MUDAH, LUMAYAN, SUSAH
	}
	private enum ScreenList {
		STAGE_MUDAH, STAGE_LUMAYAN, STAGE_SUSAH, MAIN_MENU
	}
	private static final int TRANSITION_SPEED = 15;
	private Sprite levelMudahButton;
	private Sprite levelLumayanButton;
	private Sprite levelSusahButton;
	private Sprite backButton;
	private int levelMudahXPos;
	private int levelLumayanXPos;
	private int levelSusahXPos;
	private int backXPos;
	private ScreenList requestScreen;
	private boolean isOnTransitionOut = false;
	private boolean isOnTransitionIn = true;
	private boolean isReadyToNextScreen = false;
	private static Pixmap[] parralax =	{Assets.Bg.menu1,
										 Assets.Bg.menu2,
										 Assets.Bg.menu3
										};
	
	public LevelSelectScreen(Game game) {
		super(game);
		levelMudahButton = new Sprite (Assets.Button.Level.mudah, MatematIkan.FRAME_BUFFER_WIDHT,40);
		levelLumayanButton = new Sprite (Assets.Button.Level.lumayan,MatematIkan.FRAME_BUFFER_WIDHT,150);
		levelSusahButton = new Sprite (Assets.Button.Level.susah,MatematIkan.FRAME_BUFFER_WIDHT,260);
		backButton = new Sprite (Assets.Button.back,MatematIkan.FRAME_BUFFER_WIDHT,360);
		levelMudahXPos = 520;
		levelLumayanXPos = 520;
		levelSusahXPos = 520;
		backXPos = 680;
		if (Settings.isSoundEnabled) {
			if (Assets.Audio.BGM.menuGeneral.isStopped()) {
				Assets.Audio.BGM.menuGeneral.setLooping(true);
				Assets.Audio.BGM.menuGeneral.play();
			}						
		}
		
	}

	@Override
	public void update(float deltaTime) {
		updateParralax(deltaTime);
		if (isOnTransitionIn && !isOnTransitionOut) {
			if (levelMudahButton.xPos > levelMudahXPos) {
				levelMudahButton.move(-TRANSITION_SPEED, 0);
			} else if (levelMudahButton.xPos < levelMudahXPos) {
				levelMudahButton.xPos = levelMudahXPos;
			}
			if (levelLumayanButton.xPos > levelLumayanXPos) {
				levelLumayanButton.move(-TRANSITION_SPEED, 0);
			} else if (levelLumayanButton.xPos < levelLumayanXPos) {
				levelLumayanButton.xPos = levelLumayanXPos;
			}
			if (levelSusahButton.xPos > levelSusahXPos) {
				levelSusahButton.move(-TRANSITION_SPEED, 0);
			} else if (levelSusahButton.xPos < levelSusahXPos) {
				levelSusahButton.xPos = levelSusahXPos;
			}
			if (backButton.xPos > backXPos) {
				backButton.move(-TRANSITION_SPEED, 0);
			} else if (backButton.xPos < backXPos) {
				backButton.xPos = backXPos;
			}			
		}
		if (!isOnTransitionOut && ! isOnTransitionIn) {
			List<TouchEvent> touchEvents = game.getInput().getTouchEvents();
			int len = touchEvents.size();
			for (int c = 0; c < len; c++) {
				TouchEvent event = touchEvents.get(c);
				if (event.type == TouchEvent.TOUCH_UP) {
					if (inBounds(event, levelMudahButton.xPos, levelMudahButton.yPos, levelMudahButton.widthSize, levelMudahButton.heightSize)) {
						// this level always playable
						if (Settings.Stage.Mudah.isThisLevelPlayable) {	
							isOnTransitionOut = true;
							requestScreen = ScreenList.STAGE_MUDAH;
							if (Settings.isSoundEnabled) {
								// play click
								Assets.Audio.Effect.click.play(1);
							}
						}
					}
					if (inBounds(event, levelLumayanButton.xPos, levelLumayanButton.yPos, levelLumayanButton.widthSize, levelLumayanButton.heightSize)) {												
						if (Settings.Stage.Lumayan.isThisLevelPlayable) {	
							isOnTransitionOut = true;
							requestScreen = ScreenList.STAGE_LUMAYAN;
							if (Settings.isSoundEnabled) {
								// play click
								Assets.Audio.Effect.click.play(1);
							}
						} 
					}
					if (inBounds(event, levelSusahButton.xPos, levelSusahButton.yPos, levelSusahButton.widthSize, levelSusahButton.heightSize)) {										
						if (Settings.Stage.Susah.isThisLevelPlayable) {	
							isOnTransitionOut = true;
							requestScreen = ScreenList.STAGE_SUSAH;
							if (Settings.isSoundEnabled) {
								// play click
								Assets.Audio.Effect.click.play(1);
							}
						} 
					}
					if (inBounds(event, backButton.xPos, backButton.yPos, backButton.widthSize, backButton.heightSize)) {
						isOnTransitionOut = true;
						requestScreen = ScreenList.MAIN_MENU;
						if (Settings.isSoundEnabled) {							
							Assets.Audio.Effect.click.play(1);							
						}
					}
				}
			}
		}
		if (isOnTransitionOut && !isOnTransitionIn) {			
			if (backButton.isVisible) {
				backButton.move(TRANSITION_SPEED, 0);
			}
			if (levelMudahButton.isVisible) {
				levelMudahButton.move(TRANSITION_SPEED, 0);
			}
			if (levelLumayanButton.isVisible) {
				levelLumayanButton.move(TRANSITION_SPEED, 0);
			}
			if (levelSusahButton.isVisible) {
				levelSusahButton.move(TRANSITION_SPEED, 0);
			}
			
		}
		checkSpritePos();
		if (isReadyToNextScreen) {	
			switch (requestScreen){
				case MAIN_MENU:
					game.setScreen(new MainMenuScreen(game));
					break;
				case STAGE_LUMAYAN:
					game.setScreen(new StageSelectScreen(game, Lvl.LUMAYAN));
					if (Assets.Audio.BGM.menuGeneral.isPlaying()) {
						Assets.Audio.BGM.menuGeneral.pause();
						Assets.Audio.BGM.menuGeneral.stop();
					}
					break;
				case STAGE_MUDAH:
					game.setScreen(new StageSelectScreen(game, Lvl.MUDAH));
					if (Assets.Audio.BGM.menuGeneral.isPlaying()) {
						Assets.Audio.BGM.menuGeneral.pause();
						Assets.Audio.BGM.menuGeneral.stop();
					}
					break;
				case STAGE_SUSAH:
					game.setScreen(new StageSelectScreen(game, Lvl.SUSAH));
					if (Assets.Audio.BGM.menuGeneral.isPlaying()) {
						Assets.Audio.BGM.menuGeneral.pause();
						Assets.Audio.BGM.menuGeneral.stop();
					}
					break;						
			}
			
		}
		
	}

	@Override
	public void present(float deltaTime) {
		Graphics g = game.getGraphics();
		drawParralax(g);
		if (levelMudahButton.isVisible) {
			levelMudahButton.drawSprite(g);
			if (!Settings.Stage.Mudah.isThisLevelPlayable) {
				g.drawPixmap(Assets.Button.Level.locked,levelMudahButton.xPos, levelMudahButton.yPos); 				
			}
		}
		if (levelLumayanButton.isVisible) {
			levelLumayanButton.drawSprite(g);
			if (!Settings.Stage.Lumayan.isThisLevelPlayable) {
				g.drawPixmap(Assets.Button.Level.locked,levelLumayanButton.xPos, levelLumayanButton.yPos); 
			}
		}
		if (levelSusahButton.isVisible) {
			levelSusahButton.drawSprite(g);
			if (!Settings.Stage.Susah.isThisLevelPlayable) {
				g.drawPixmap(Assets.Button.Level.locked,levelSusahButton.xPos, levelSusahButton.yPos);				
			}
		}
		if (backButton.isVisible) {
			backButton.drawSprite(g);
		}
	}

	@Override
	public void pause() {
		
	}

	@Override
	public void resume() {
		update(0);
		present(0);		
		if (Settings.isSoundEnabled) {
			if (Assets.Audio.BGM.menuGeneral.isStopped()) {				
				Assets.Audio.BGM.menuGeneral.play();
				Assets.Audio.BGM.menuGeneral.setLooping(true);
			}						
		}
	}

	@Override
	public void dispose() {
		levelMudahButton = null;
		levelLumayanButton = null;
		levelSusahButton = null;
		backButton = null;		
	}
	@Override
	public void backButton() {
		if (!isOnTransitionOut && !isOnTransitionIn) { 
			isOnTransitionOut = true;
			requestScreen = ScreenList.MAIN_MENU;
		} 
		
	}
	private boolean inBounds(TouchEvent event, int x, int y, int width, int height) {
		if (event.x > x && event.x < x + width - 1 && event.y > y
				&& event.y < y + height - 1)
			return true;
		else
			return false;
	}
	private void checkSpritePos() {
		int scrWidth = MatematIkan.FRAME_BUFFER_WIDHT;
		if (isOnTransitionIn && !isOnTransitionOut) {
			if (levelMudahButton.xPos == levelMudahXPos && levelLumayanButton.xPos == levelLumayanXPos &&
					levelSusahButton.xPos == levelSusahXPos && backButton.xPos == backXPos) {
				this.isOnTransitionIn = false;
			}
		}
		/*if (!isOnTransitionIn && !isOnTransitionOut) {
			
		}*/
		if (isOnTransitionOut && !isOnTransitionIn) {
			if (backButton.xPos > scrWidth) {
				backButton.isVisible = false;
			}
			if (levelMudahButton.xPos > scrWidth) {
				levelMudahButton.isVisible = false;
			}
			if (levelLumayanButton.xPos > scrWidth) {
				levelLumayanButton.isVisible = false;
			}
			if (levelSusahButton.xPos > scrWidth) {
				levelSusahButton.isVisible = false;
			}
			if (!levelMudahButton.isVisible && !levelLumayanButton.isVisible &&
					!levelLumayanButton.isVisible && !levelSusahButton.isVisible) {
				isReadyToNextScreen = true;
			}
		}
	}
	private void updateParralax(float deltaTime) {		
		for (int c = 0; c < 3; c++) {
			Settings.menuBgMoveX[c] -= Settings.menuMoveSpeed[c]*deltaTime;
			Settings.menuBgNewMoveX[c] = parralax[c].getWidth() - (-Settings.menuBgMoveX[c]);
		}		
	}
	private void drawParralax(Graphics g) {
		for (int c = 0; c < 3 ;c++) {
			if (Settings.menuBgNewMoveX[c] <= 0) {
				Settings.menuBgMoveX[c] = 0;
				g.drawPixmap(parralax[c], Settings.menuBgMoveX[c], Settings.menuBgStaticY[c]);
			} else {
				g.drawPixmap(parralax[c], Settings.menuBgMoveX[c], Settings.menuBgStaticY[c]);
				g.drawPixmap(parralax[c], Settings.menuBgNewMoveX[c], Settings.menuBgStaticY[c]);
			}
		}		
	}

}
