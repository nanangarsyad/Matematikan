package com.arkodestudio.matematikan;

import java.util.List;

//import android.util.Log;

import android.graphics.Paint;

import com.arkodestudio.framework.Game;
import com.arkodestudio.framework.Graphics;
import com.arkodestudio.framework.Input.TouchEvent;
import com.arkodestudio.framework.Pixmap;
import com.arkodestudio.framework.Screen;

public class MainMenuScreen extends Screen {	
	private enum ScreenList {
		PLAY_SCREEN, SETTIINGS_SCREEN
	}
	private final static int TRANSITION_SPEED = 15; // frame dependent-movement
	private Sprite helpButton;	 
	private Sprite aboutButton;	// tukar karo sound
	private Sprite playButton;	
	private Sprite settingsButton;
	private Sprite achievementButton;
	private Sprite exitButton;
	private Sprite yesExit;
	private Sprite noExit;	
	private Sprite nextInfo;
	private Sprite toMenu;
	private Sprite logo;
	private int screenPointer;
	private int playXPos;
	private int aboutXPos;
	private int helpXPos;
	private int settingsXPos;
	private int achievementXPos;
	private int exitXPos;
	private int logoXPos;
	private int alphaChannel = 255;
	private boolean isOnTransitionOut = false;
	private boolean isOnTransitionIn = true;
	private boolean isReadyToNextScreen = false;	
	private boolean isShowingAchievment = false;
	private boolean isShowingAbout = false;
	private boolean isShowingExit = false;	
	private boolean isShowingHelp = false;
	private Paint newPaint = new Paint();
	private ScreenList requestScreen; 
	private static Pixmap[] parralax =	{Assets.Bg.menu1,
										 Assets.Bg.menu2,
										 Assets.Bg.menu3
									   	};
	
	public MainMenuScreen(Game game) {
		super(game);
		exitButton = new Sprite(Assets.Button.Main.exit, MatematIkan.FRAME_BUFFER_WIDHT, 20);
		helpButton = new Sprite(Assets.Button.Main.help, MatematIkan.FRAME_BUFFER_WIDHT, 180);		
		aboutButton = new Sprite(Assets.Button.Main.about, MatematIkan.FRAME_BUFFER_WIDHT, 260);		
		playButton = new Sprite(Assets.Button.Main.play, MatematIkan.FRAME_BUFFER_WIDHT, 340);		
		achievementButton = new Sprite(Assets.Button.Main.achievement, MatematIkan.FRAME_BUFFER_WIDHT, 400);
		settingsButton = new Sprite (Assets.Button.Main.settings, MatematIkan.FRAME_BUFFER_WIDHT, 400);				
		logo = new Sprite(Assets.gameLogo, 0-Assets.gameLogo.getWidth(),120); 
		aboutXPos = 720; 
		helpXPos = 720;
		playXPos = 660;
		settingsXPos = 580;	
		achievementXPos = 500;
		exitXPos = 720;
		logoXPos = 200;
		yesExit = new Sprite(Assets.Button.Confirmation.Game.yesExit,175,230);
		noExit = new Sprite(Assets.Button.Confirmation.Game.noExit,450,230);		
		nextInfo = new Sprite(Assets.Button.Confirmation.Help.berikutnya,240,410);
		toMenu = new Sprite(Assets.Button.Confirmation.Help.keMenu,20,410);
		screenPointer = 0;
		newPaint.setAlpha(0);
		if (Settings.isSoundEnabled) {
			if (Assets.Audio.BGM.menuGeneral.isStopped()) {				
				Assets.Audio.BGM.menuGeneral.play();
				Assets.Audio.BGM.menuGeneral.setLooping(true);
			}						
		}		
	}

	@Override
	public void update(float deltaTime) {
		updateParralax(deltaTime);
		if (isOnTransitionIn && !isOnTransitionOut) {
			if (exitButton.xPos > exitXPos) {
				exitButton.move(-TRANSITION_SPEED, 0);
			} else if (exitButton.xPos < exitXPos) {
				exitButton.xPos = exitXPos;
			}
			if (helpButton.xPos > helpXPos) {
				helpButton.move(-TRANSITION_SPEED, 0);
			} else if (helpButton.xPos < helpXPos) {
				helpButton.xPos = helpXPos;
			}
			if (aboutButton.xPos > aboutXPos) {
				aboutButton.move(-TRANSITION_SPEED, 0);
			} else if (aboutButton.xPos < aboutXPos) {
				aboutButton.xPos = aboutXPos;
			}
			if (playButton.xPos > playXPos) {
				playButton.move(-TRANSITION_SPEED, 0);
			} else if (playButton.xPos < playXPos) {
				playButton.xPos = playXPos;
			}
			if (settingsButton.xPos > settingsXPos) {
				settingsButton.move(-TRANSITION_SPEED,0);				
			} else if (settingsButton.xPos < settingsXPos) {
				settingsButton.xPos = settingsXPos;
			}			
			if (achievementButton.xPos > achievementXPos) {
				achievementButton.move(-TRANSITION_SPEED, 0);
			} else if (achievementButton.xPos < achievementXPos) {
				achievementButton.xPos = achievementXPos;
			}
			if (logo.xPos < logoXPos) {
				logo.move(TRANSITION_SPEED, 0);
			} else if (logo.xPos > logoXPos) {
				logo.xPos = logoXPos;
			}
			
		}
		if (!isOnTransitionOut && !isOnTransitionIn) {
			if (!isShowingAbout && !isShowingExit && !isShowingAchievment && !isShowingHelp) { // showing main screen
				List<TouchEvent> touchEvents = game.getInput().getTouchEvents();
				int len = touchEvents.size();
				for (int c = 0; c < len; c++) {
					TouchEvent event = touchEvents.get(c);					
					if (event.type == TouchEvent.TOUCH_UP) { 
						// help button						
						if (inBounds(event, helpButton.xPos, helpButton.yPos, helpButton.widthSize, helpButton.heightSize)) {
							isShowingHelp = true;													
							if (Settings.isSoundEnabled) {
								Assets.Audio.Effect.click.play(1);
							}
						}
						// play button
						if (inBounds(event, playButton.xPos,playButton.yPos, playButton.widthSize, playButton.heightSize)) {
							requestScreen = ScreenList.PLAY_SCREEN;
							isOnTransitionOut = true;						
							if (Settings.isSoundEnabled) {
								Assets.Audio.Effect.click.play(1);
							}
						}
						// exitButton
						if (inBounds(event,exitButton.xPos,exitButton.yPos,exitButton.widthSize,exitButton.heightSize)) {
							isShowingExit = true;
							if (Settings.isSoundEnabled) {
								Assets.Audio.Effect.click.play(1);
							}
						}
						// achievementButton
						if (inBounds(event,achievementButton.xPos,achievementButton.yPos,achievementButton.widthSize,achievementButton.heightSize)) {							
							Settings.Achievement.checkAchievement();
							isShowingAchievment = true;
							if (Settings.isSoundEnabled) {
								Assets.Audio.Effect.click.play(1);
							}
						}
						// reset button
						if (inBounds(event,aboutButton.xPos,aboutButton.yPos,aboutButton.widthSize,aboutButton.heightSize)) {
							isShowingAbout = true;
							if (Settings.isSoundEnabled) {
								Assets.Audio.Effect.click.play(1);
							}
						}				
						// sound button
						if (inBounds(event,settingsButton.xPos,settingsButton.yPos,settingsButton.widthSize,settingsButton.heightSize)) {
							requestScreen = ScreenList.SETTIINGS_SCREEN;
							isOnTransitionOut = true;						
							if (Settings.isSoundEnabled) {
								Assets.Audio.Effect.click.play(1);
							}							
						}
					}
				}
			} else if (isShowingAbout && !isShowingExit && !isShowingAchievment && !isShowingHelp) {
				List<TouchEvent> touchEvents = game.getInput().getTouchEvents();
				int len = touchEvents.size();
				for (int c = 0; c < len; c++) {
					TouchEvent event = touchEvents.get(c);
					if (event.type == TouchEvent.TOUCH_UP) {
						if (inBounds(event, nextInfo.xPos, nextInfo.yPos, nextInfo.widthSize, nextInfo.heightSize)) {
							screenPointer++;
							if (screenPointer > 2) {
								screenPointer = 0;
							}
							if (Settings.isSoundEnabled) {
								Assets.Audio.Effect.click.play(1);
							}
						} else if (inBounds(event, toMenu.xPos, toMenu.yPos, toMenu.widthSize, toMenu.heightSize)) {							
							isShowingAbout = false;
							this.screenPointer = 0;
							if (Settings.isSoundEnabled) {
								Assets.Audio.Effect.click.play(1);
							}
						} 
					}
				}
			} else if (isShowingExit && !isShowingAchievment && !isShowingAbout && !isShowingHelp) {
				List<TouchEvent> touchEvents = game.getInput().getTouchEvents();
				int len = touchEvents.size();
				for (int c = 0; c < len; c++) {
					TouchEvent event = touchEvents.get(c);
					if (event.type == TouchEvent.TOUCH_UP) {
						if (inBounds(event, yesExit.xPos, yesExit.yPos, yesExit.widthSize, yesExit.heightSize)) {
							// Exit confirmed // do exiting action							
							android.os.Process.killProcess(android.os.Process.myPid());
						} else if (inBounds(event, noExit.xPos, noExit.yPos, noExit.widthSize, noExit.heightSize)) {
							// cancel Exit, back to main screen
							isShowingExit = false;
							if (Settings.isSoundEnabled) {
								Assets.Audio.Effect.click.play(1);
							}
						} else  {
							// go back to main screen
							isShowingExit = false;
						}
					}
				}
			} else if (isShowingAchievment && !isShowingAbout && !isShowingExit && !isShowingHelp) {
				List<TouchEvent> touchEvents = game.getInput().getTouchEvents();
				int len = touchEvents.size();
				for (int c = 0; c < len; c++) {
					TouchEvent event = touchEvents.get(c);
					if (event.type == TouchEvent.TOUCH_UP) {
						if (inBounds(event, toMenu.xPos, toMenu.yPos, toMenu.widthSize, toMenu.heightSize)) {							
							isShowingAchievment = false;
							if (Settings.isSoundEnabled) {
								Assets.Audio.Effect.click.play(1);
							}
						} 
					}
				}
			} else if (isShowingHelp && !isShowingAchievment && !isShowingAbout && !isShowingExit ) {
				List<TouchEvent> touchEvents = game.getInput().getTouchEvents();
				int len = touchEvents.size();
				for (int c = 0; c < len; c++) {
					TouchEvent event = touchEvents.get(c);
					if (event.type == TouchEvent.TOUCH_UP) {
						if (inBounds(event, nextInfo.xPos, nextInfo.yPos, nextInfo.widthSize, nextInfo.heightSize)) {
							if (screenPointer == 0) {
								screenPointer = 1;
							} else {
								screenPointer = 0;
							}
							if (Settings.isSoundEnabled) {
								Assets.Audio.Effect.click.play(1);
							}
						} else if (inBounds(event, toMenu.xPos, toMenu.yPos, toMenu.widthSize, toMenu.heightSize)) {							
							isShowingHelp = false;
							this.screenPointer = 0;
							if (Settings.isSoundEnabled) {
								Assets.Audio.Effect.click.play(1);
							}
						} 
					}
				}
			}
		}
		if (isOnTransitionOut && !isOnTransitionIn) {			
			if (settingsButton.isVisible) {
				settingsButton.move(TRANSITION_SPEED, 0);
			}
			if (playButton.isVisible) {
				playButton.move(TRANSITION_SPEED, 0);
			}
			if (aboutButton.isVisible) {
				aboutButton.move(TRANSITION_SPEED, 0);
			}
			if (helpButton.isVisible) {
				helpButton.move(TRANSITION_SPEED, 0);
			}
			if (achievementButton.isVisible) {
				achievementButton.move(TRANSITION_SPEED,0);
			}
			if (exitButton.isVisible) {
				exitButton.move(TRANSITION_SPEED,0);
			}
			if (logo.isVisible) {
				alphaChannel -= 10;
				if (alphaChannel <= 0) {
					alphaChannel = 0;
				}
				newPaint.setAlpha(alphaChannel);
			}
			
		}
		checkSpritePos();		
		if (isReadyToNextScreen) {			
			switch (requestScreen){				
				case PLAY_SCREEN:					
					game.setScreen(new LevelSelectScreen(game));					
					break;
				case SETTIINGS_SCREEN:
					game.setScreen(new SettingsScreen(game));					
					break;									
			}
		}
	} 
	

	@Override
	public void present(float deltaTime) {		
		Graphics g = game.getGraphics();
		if (!isShowingHelp && !isShowingAbout) {
			drawParralax(g);		
			if (exitButton.isVisible) {
				exitButton.drawSprite(g);
			}
			if (settingsButton.isVisible) {
				settingsButton.drawSprite(g);
			}
			if (playButton.isVisible) {
				playButton.drawSprite(g);
			}
			if (aboutButton.isVisible) {
				aboutButton.drawSprite(g);	
			}
			if (helpButton.isVisible) {
				helpButton.drawSprite(g);
			}		
			if (achievementButton.isVisible) {
				achievementButton.drawSprite(g);
			}
			if (!isShowingAchievment & !isShowingExit & !isShowingHelp & !isShowingAbout) {
				if (logo.isVisible) {
					if (isOnTransitionOut && !isOnTransitionIn) {
						logo.drawSprite(g,newPaint);
					} else {
						logo.drawSprite(g);
					}
				}					
			} else if (isShowingExit && !isShowingAchievment && !isShowingAbout ) {
				g.drawARGB(192, 26, 122, 122);
				g.drawPixmap(Assets.Notification.exitGame, 75, 150);
				yesExit.drawSprite(g);
				noExit.drawSprite(g);
			} else if (isShowingAchievment && !isShowingAbout && !isShowingExit) {
				g.drawARGB(80, 26, 122, 122);
				toMenu.drawSprite(g);
				int xSignColo1 = 90;
				int xPlatColo1 = 155;
				int xSignColo2 = 420;
				int xPlatColo2 = 485;
				// Column 1
				if (Settings.Achievement.isClearingStage1) {
					g.drawPixmap(Assets.Achievement.achievedSign, xSignColo1, 80);
				} else {
					g.drawPixmap(Assets.Achievement.unAchievedSign, xSignColo1, 80);
				}
				g.drawPixmap(Assets.Achievement.clearingStage1, xPlatColo1, 80);				
				if (Settings.Achievement.isClearingMudah) {
					g.drawPixmap(Assets.Achievement.achievedSign, xSignColo1, 155);
				} else {
					g.drawPixmap(Assets.Achievement.unAchievedSign, xSignColo1, 155);
				}
				g.drawPixmap(Assets.Achievement.clearingMudah, xPlatColo1, 155);
				if (Settings.Achievement.isCompletingMudah) {
					g.drawPixmap(Assets.Achievement.achievedSign, xSignColo1, 230);
				} else {
					g.drawPixmap(Assets.Achievement.unAchievedSign, xSignColo1, 230);
				}
				g.drawPixmap(Assets.Achievement.completingMudah, xPlatColo1, 230);
				if (Settings.Achievement.isClearingLumayan) {
					g.drawPixmap(Assets.Achievement.achievedSign, xSignColo1, 305);
				} else {
					g.drawPixmap(Assets.Achievement.unAchievedSign, xSignColo1, 305);
				}
				g.drawPixmap(Assets.Achievement.clearingLumayan, xPlatColo1, 305);
				
				// Column 2
				if (Settings.Achievement.isCompletingLumayan) {
					g.drawPixmap(Assets.Achievement.achievedSign, xSignColo2, 80);
				} else {
					g.drawPixmap(Assets.Achievement.unAchievedSign, xSignColo2, 80);
				}	
				g.drawPixmap(Assets.Achievement.completingLumayan, xPlatColo2, 80);
				if (Settings.Achievement.isClearingSusah) {
					g.drawPixmap(Assets.Achievement.achievedSign, xSignColo2, 155);
				} else {
					g.drawPixmap(Assets.Achievement.unAchievedSign, xSignColo2, 155);
				}
				g.drawPixmap(Assets.Achievement.clearingSusah, xPlatColo2, 155);
				if (Settings.Achievement.isCompletingSusah) {
					g.drawPixmap(Assets.Achievement.achievedSign, xSignColo2, 230);
				} else {
					g.drawPixmap(Assets.Achievement.unAchievedSign, xSignColo2, 230);
				}
				g.drawPixmap(Assets.Achievement.completingSusah, xPlatColo2, 230);
				if (Settings.Achievement.isCompletingAll) {
					g.drawPixmap(Assets.Achievement.achievedSign, xSignColo2, 305);
				} else {
					g.drawPixmap(Assets.Achievement.unAchievedSign, xSignColo2, 305);
				}				
				g.drawPixmap(Assets.Achievement.completingAll, xPlatColo2, 305);
			}	
		} else if (isShowingHelp && !isShowingAbout) {
			if (screenPointer == 0) {
				g.drawPixmap(Assets.Bg.Tutorial.inHelp1, 0, 0);
			} else {
				g.drawPixmap(Assets.Bg.Tutorial.inHelp2, 0, 0);
			}			
			nextInfo.drawSprite(g);
			toMenu.drawSprite(g);
		} else if (isShowingAbout && !isShowingHelp) {		
			g.drawARGB(192, 26, 122, 122);
			if (screenPointer == 0) {
				g.drawPixmap(Assets.Bg.Credit.screen1,0,0);
			} else if (screenPointer == 1) {
				g.drawPixmap(Assets.Bg.Credit.screen2,0,0);
			} else if (screenPointer == 2) {
				g.drawPixmap(Assets.Bg.Credit.screen3,0,0);
			}
			nextInfo.drawSprite(g);
			toMenu.drawSprite(g);
		}
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub		
	}

	@Override
	public void resume() {
		this.update(0);
		this.present(0);	
		if (Settings.isSoundEnabled) {
			if (Assets.Audio.BGM.menuGeneral.isStopped()) {				
				Assets.Audio.BGM.menuGeneral.play();
				Assets.Audio.BGM.menuGeneral.setLooping(true);
			}						
		}
	}

	@Override
	public void dispose() {
		helpButton = null;	 
		aboutButton = null;	
		playButton = null;
		settingsButton = null;
		achievementButton = null;
		exitButton = null;
		yesExit = null;
		noExit = null;		
		nextInfo = null;
		toMenu = null;	
		logo = null;
	}
	
	@Override
	public void backButton() {
		if (!this.isOnTransitionOut && !this.isOnTransitionIn) {
			if (!isShowingAbout && !isShowingExit && !isShowingAchievment && !isShowingHelp) { // showing main screen
				Settings.save(game.getFileIO());
				isShowingExit = true;
				return;
			} else if (isShowingAbout && !isShowingExit && !isShowingAchievment && !isShowingHelp) { // showing main screen				
				isShowingAbout = false;
				return;
			} else if (isShowingExit && !isShowingAchievment && !isShowingAbout && !isShowingHelp) { // showing main screen				
				isShowingExit = false;
				return;
			} else if (isShowingAchievment && !isShowingAbout && !isShowingExit && !isShowingHelp) { // showing main screen				
				isShowingAchievment = false;
				return;
			} else if (isShowingHelp && !isShowingAbout && !isShowingExit && !isShowingAchievment) {
				isShowingHelp = false;
				return;
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
	private boolean inBounds(TouchEvent event, int x, int y, int width, int height) {
		if (event.x > x && event.x < x + width && event.y > y
				&& event.y < y + height)
			return true;
		else
			return false;
	}
	private void checkSpritePos() {
		int scrWidth = MatematIkan.FRAME_BUFFER_WIDHT;
		if (this.isOnTransitionIn && !this.isOnTransitionOut) {
			if (helpButton.xPos == helpXPos && aboutButton.xPos == aboutXPos 
					&& playButton.xPos == playXPos && settingsButton.xPos == settingsXPos
					&& achievementButton.xPos == achievementXPos && exitButton.xPos == exitXPos
					&& logo.xPos == logoXPos) {
				isOnTransitionIn = false;
			}
		}
		if (!this.isOnTransitionOut && !this.isOnTransitionIn) {
			
		}
		if (this.isOnTransitionOut && !this.isOnTransitionIn) {
			if (settingsButton.xPos > scrWidth) {
				settingsButton.isVisible = false;				
			} 
			if (helpButton.xPos > scrWidth) {
				helpButton.isVisible = false;				
			}			
			if (aboutButton.xPos > scrWidth) {
				aboutButton.isVisible = false;				
			} 
			if (playButton.xPos > scrWidth) {
				playButton.isVisible = false;				
			} 
			if (exitButton.xPos > scrWidth) {
				exitButton.isVisible = false;				
			} 
			if (achievementButton.xPos > scrWidth) {
				achievementButton.isVisible = false;				
			} 		
			if (newPaint.getAlpha() == 0) {
				logo.isVisible = false;
			}
			if ( !helpButton.isVisible && !settingsButton.isVisible 
					&& !playButton.isVisible && !aboutButton.isVisible
					&& !achievementButton.isVisible && !exitButton.isVisible
					&& !logo.isVisible) {
				isReadyToNextScreen = true;				
			}
		}		
	}
		
}
