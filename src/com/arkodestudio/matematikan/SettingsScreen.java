package com.arkodestudio.matematikan;

import java.util.List;

import com.arkodestudio.framework.Game;
import com.arkodestudio.framework.Graphics;
import com.arkodestudio.framework.Pixmap;
import com.arkodestudio.framework.Screen;
import com.arkodestudio.framework.Input.TouchEvent;

public class SettingsScreen extends Screen {
	private static final int TRANSITION_SPEED = 15;
	private Sprite backButton;
	private Sprite resetButton;
	private Sprite soundButton;
	private Sprite yesReset;
	private Sprite noReset;	
	private int backXPos;
	private int resetXPos;
	private int soundXPos;
	private boolean isOnTransitionOut = false;
	private boolean isOnTransitionIn = true;
	private boolean isReadyToNextScreen = false;
	private boolean isShowingReset = false;
	private static Pixmap[] parralax =	{Assets.Bg.menu1,
										 Assets.Bg.menu2,
										 Assets.Bg.menu3
										};
	public SettingsScreen(Game game) {
		super(game);		
		if (Settings.isSoundEnabled) {
			soundButton = new Sprite (Assets.Button.Setttings.soundOn, MatematIkan.FRAME_BUFFER_WIDHT, 220);
		} else {   
			soundButton = new Sprite (Assets.Button.Setttings.soundOn, MatematIkan.FRAME_BUFFER_WIDHT, 220);
		}		
		backButton = new Sprite (Assets.Button.back,MatematIkan.FRAME_BUFFER_WIDHT,360);
		resetButton = new Sprite(Assets.Button.Setttings.reset,MatematIkan.FRAME_BUFFER_WIDHT,100);
		resetXPos = 520;
		soundXPos = 520;
		backXPos = 680;
		yesReset = new Sprite(Assets.Button.Confirmation.Game.yesReset,150,270);
		noReset = new Sprite(Assets.Button.Confirmation.Game.noReset,510,270);
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
			if (resetButton.xPos > resetXPos) {
				resetButton.move(-TRANSITION_SPEED, 0);
			} else if (resetButton.xPos < resetXPos) {
				resetButton.xPos = resetXPos;
			}
			if (soundButton.xPos > soundXPos) {
				soundButton.move(-TRANSITION_SPEED, 0);
			} else if (soundButton.xPos < soundXPos) {
				soundButton.xPos = soundXPos;
			}
			if (backButton.xPos > backXPos) {
				backButton.move(-TRANSITION_SPEED, 0);
			} else if (backButton.xPos < backXPos) {
				backButton.xPos = backXPos;
			}
		}
		if (!isOnTransitionIn && !isOnTransitionOut) {
			List<TouchEvent> touchEvents = game.getInput().getTouchEvents();
			int len = touchEvents.size();
			for (int c = 0; c < len; c++) {
				TouchEvent event = touchEvents.get(c);
				if (event.type == TouchEvent.TOUCH_UP) {
					if (isShowingReset) {
						if (inBounds(event, yesReset.xPos, yesReset.yPos, yesReset.widthSize, yesReset.heightSize)) {
							// reset confirmed // do reseting action
							Settings.resetSaveState();
							Settings.save(game.getFileIO());
							isShowingReset = false;
							if (Settings.isSoundEnabled) {
								Assets.Audio.Effect.click.play(1);
							}
						} else if (inBounds(event, noReset.xPos, noReset.yPos, noReset.widthSize, noReset.heightSize)) {
							// cancel reset, back to main screen
							isShowingReset = false;
							if (Settings.isSoundEnabled) {
								Assets.Audio.Effect.click.play(1);
							}
						} else  {
							// go back to main screen
							isShowingReset = false;
						}						
					} else { 
						if (inBounds(event, resetButton.xPos, resetButton.yPos, resetButton.widthSize, resetButton.heightSize)) {
							isShowingReset = true;
							if (Settings.isSoundEnabled) {
								Assets.Audio.Effect.click.play(1);
							}
						}
						if (inBounds(event, soundButton.xPos, soundButton.yPos, soundButton.widthSize, soundButton.heightSize)) {
							Settings.isSoundEnabled = !Settings.isSoundEnabled;						
							if (Settings.isSoundEnabled) {
								Assets.Audio.Effect.click.play(1);
								soundButton.setPixmap(Assets.Button.Setttings.soundOn);
								if (Assets.Audio.BGM.menuGeneral.isStopped()) {									
									Assets.Audio.BGM.menuGeneral.play();
									Assets.Audio.BGM.menuGeneral.setLooping(true);
								}
							} else  {
								soundButton.setPixmap(Assets.Button.Setttings.soundOff);
								if (Assets.Audio.BGM.menuGeneral.isPlaying()) {
									Assets.Audio.BGM.menuGeneral.pause();
									Assets.Audio.BGM.menuGeneral.stop();
								}
							}			
						}
						if (inBounds(event, backButton.xPos, backButton.yPos, backButton.widthSize, backButton.heightSize)) {
							isOnTransitionOut = true;						
							if (Settings.isSoundEnabled) {							
								Assets.Audio.Effect.click.play(1);							
							}
						}
					}
				}
			}
		} 
		if (isOnTransitionOut && !isOnTransitionIn) {
			if (backButton.isVisible) {
				backButton.move(TRANSITION_SPEED, 0);
			}
			if (soundButton.isVisible) {
				soundButton.move(TRANSITION_SPEED, 0);
			}
			if (resetButton.isVisible) {
				resetButton.move(TRANSITION_SPEED, 0);
			}
		}
		checkSpritePos();
		if (isReadyToNextScreen) {	
			game.setScreen(new MainMenuScreen(game));
		}
		
	}

	@Override
	public void present(float deltaTime) {
		Graphics g = game.getGraphics();
		drawParralax(g);
		if (isShowingReset) {
			g.drawARGB(192, 26, 122, 122);
			g.drawPixmap(Assets.Notification.resetGame, 50, 40);
			yesReset.drawSprite(g);
			noReset.drawSprite(g);
		} else {
			if(resetButton.isVisible) {
				resetButton.drawSprite(g);
			}
			if(soundButton.isVisible) {
				soundButton.drawSprite(g);
			}
			if(backButton.isVisible) {
				backButton.drawSprite(g);
			}	
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
		resetButton = null;
		backButton = null;
		soundButton = null;
	}

	@Override
	public void backButton() {
		if (!isOnTransitionOut && !isOnTransitionIn) { 
			isOnTransitionOut = true;			
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
			if (resetButton.xPos == resetXPos && soundButton.xPos == soundXPos
					&& backButton.xPos == backXPos) {
				this.isOnTransitionIn = false;
			}
		}
		/*if (!isOnTransitionIn && !isOnTransitionOut) {
			
		}*/
		if (isOnTransitionOut && !isOnTransitionIn) {
			if (backButton.xPos > scrWidth) {
				backButton.isVisible = false;
			}
			if (resetButton.xPos > scrWidth) {
				resetButton.isVisible = false;
			}
			if (soundButton.xPos > scrWidth) {
				soundButton.isVisible = false;
			}			
			if (!backButton.isVisible &&!resetButton.isVisible && !soundButton.isVisible) {
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
