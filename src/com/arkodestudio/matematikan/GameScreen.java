package com.arkodestudio.matematikan;

import java.util.List;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Typeface;

import com.arkodestudio.framework.Game;
import com.arkodestudio.framework.Graphics;
import com.arkodestudio.framework.Input.TouchEvent;
import com.arkodestudio.framework.Screen;
import com.arkodestudio.matematikan.Peluru.JenisPeluru;
import com.arkodestudio.matematikan.Settings.Achievement;

public class GameScreen extends Screen {
	private enum GameState {
		PREPARING, RUNNING, PAUSED, GAME_FINISHED
	}
	private static final int TRANSITION_SPEED = 20;
	private GameState gameState = GameState.PREPARING;	
	private SeaWorld seaWorld;	
	private Sprite tembak = new Sprite(Assets.Button.Game.tembak,MatematIkan.FRAME_BUFFER_WIDHT, 380);
	private Sprite tangkap = new Sprite(Assets.Button.Game.tangkap,MatematIkan.FRAME_BUFFER_WIDHT, 380);
	private Sprite pause = new Sprite(Assets.Button.Game.pause,MatematIkan.FRAME_BUFFER_WIDHT,80);
	private int pauseXPos = 720;
	private int tembakXpos = 700;
	private int tangkapXPos = 600;
	private Sprite lanjutkan = new Sprite(Assets.Button.Confirmation.Game.lanjutkan,460,120);
	private Sprite ulangi = new Sprite(Assets.Button.Confirmation.Game.ulangi,520,210);
	private Sprite keMenu = new Sprite(Assets.Button.Confirmation.Game.keMenu, 520,300);
	private Sprite berikutnya = new Sprite(Assets.Button.Confirmation.Game.berikutnya,460,120);
	private Sprite mulaiGame = new Sprite(Assets.Button.Confirmation.Help.lanjutkan, 20,410);
	private Paint paint = new Paint();
	private int[] untouchZone = {550, 330, 250, 150}; // x,y,width, height
	//private boolean isHoldingTembak = false;
	//private boolean isHoldingTangkap = false;		
	private LevelSelectScreen.Lvl levelPointer;	
	private int stagePointer;
	private boolean isTutorial1Shown = false;
	private boolean isTutorial2Shown = false;
	
	public GameScreen(Game game,LevelSelectScreen.Lvl levelPointer, int stagePointer) {
		super(game);		
		this.levelPointer = levelPointer;
		this.stagePointer = stagePointer;
		seaWorld = new SeaWorld(game, levelPointer,stagePointer);
		if (levelPointer == LevelSelectScreen.Lvl.MUDAH && stagePointer == 1-1) { // stage 1 (becuse it's array, so start from 0)
			isTutorial1Shown = true;
		}
		if (levelPointer == LevelSelectScreen.Lvl.LUMAYAN && stagePointer == 3-1) { // stage 3 (becuse it's array, so start from 0)
			isTutorial2Shown = true;
		}
		paint.setAntiAlias(true);
		paint.setTypeface(Typeface.SANS_SERIF);		
		paint.setTextSize(40);
		paint.setColor(Color.GREEN);
		paint.setShadowLayer(4f, 4f, 4f, Color.BLACK);
		paint.setTextAlign(Align.CENTER);
	}

	@Override
	public void update(float deltaTime) {		
		List<TouchEvent> touchEvents = game.getInput().getTouchEvents();
		if (gameState == GameState.PREPARING) {
			updatePreparing(touchEvents,deltaTime);
		}
		if (gameState == GameState.RUNNING) {
			updateRunning(touchEvents, deltaTime);
		}
		if (gameState == GameState.PAUSED) {
			updatePaused(touchEvents);
		}
		if (gameState == GameState.GAME_FINISHED) {
			Settings.save(game.getFileIO()); // ???
			updateFinished(touchEvents);
		}				
	}

	@Override
	public void present(float deltaTime) {		
		Graphics g = game.getGraphics();
		if (gameState == GameState.PAUSED) {
			seaWorld.present(deltaTime, true);
		} else {
			seaWorld.present(deltaTime, false);
		}
		if (gameState == GameState.PREPARING) {
			drawPreparingUI(g);
		}
		if (gameState == GameState.RUNNING) {
			drawRunningUI(g);
		}
		if (gameState == GameState.PAUSED) {
			drawPausedUI(g);
		}
		if (gameState == GameState.GAME_FINISHED) {
			drawGameFinishedUI(g);				
		}			
	}

	@Override
	public void pause() {
		gameState = GameState.PAUSED;		
	}

	@Override
	public void resume() {		
		if (Settings.isSoundEnabled) {
			switch (levelPointer) {
				case MUDAH:
					if (Assets.Audio.BGM.menuMudah.isStopped()) {				
						Assets.Audio.BGM.menuMudah.play();
						Assets.Audio.BGM.menuMudah.setLooping(true);
					}
					break;	
				case LUMAYAN:
					if (Assets.Audio.BGM.menuLumayan.isStopped()) {				
						Assets.Audio.BGM.menuLumayan.play();
						Assets.Audio.BGM.menuLumayan.setLooping(true);
					}
					break;
				case SUSAH:
					if (Assets.Audio.BGM.menuSusah.isStopped()) {				
						Assets.Audio.BGM.menuSusah.play();
						Assets.Audio.BGM.menuSusah.setLooping(true);
					}
					break;							
			}										
		}
		
	}

	@Override
	public void dispose() {				
		
	}
	@Override
	public void backButton() {
		if (gameState == GameState.PAUSED ) {
			gameState = GameState.RUNNING;
			return;
		} 
		if (gameState == GameState.RUNNING) {
			gameState = GameState.PAUSED;
			return;
		} 	
	}
	private void updatePreparing(List<TouchEvent> touchEvents,float deltaTime) {
		if (seaWorld.isOnTransition) {
			seaWorld.update(deltaTime);
		}		
		if (tembak.xPos <= tembakXpos ) {
			tembak.xPos = tembakXpos;
		} else {
			tembak.move(-GameScreen.TRANSITION_SPEED, 0);
		}
		if (tangkap.xPos <= tangkapXPos) {
			tangkap.xPos = tangkapXPos;
		} else {
			tangkap.move(-GameScreen.TRANSITION_SPEED, 0);
		}
		if (pause.xPos <= pauseXPos) {
			pause.xPos = pauseXPos;
		} else {
			pause.move(-GameScreen.TRANSITION_SPEED, 0);
		}		
		if (tembak.xPos == tembakXpos && tangkap.xPos == tangkapXPos 
				&& pause.xPos == pauseXPos && !seaWorld.isOnTransition) {
			if (this.isTutorial1Shown) {
				int len = touchEvents.size();
				for (int c = 0; c < len ; c++) {
					TouchEvent event = touchEvents.get(c);
					if (event.type == TouchEvent.TOUCH_UP) {
						if (inBounds(event, mulaiGame.xPos, mulaiGame.yPos, mulaiGame.widthSize, mulaiGame.heightSize)) {
							isTutorial1Shown = false;
							if (Settings.isSoundEnabled) {
								Assets.Audio.Effect.click.play(1);
							}
						}
						
					}
				}
			
			} else if (this.isTutorial2Shown) {
				int len = touchEvents.size();
				for (int c = 0; c < len ; c++) {
					TouchEvent event = touchEvents.get(c);
					if (event.type == TouchEvent.TOUCH_UP) {
						if (inBounds(event, mulaiGame.xPos, mulaiGame.yPos, mulaiGame.widthSize, mulaiGame.heightSize)) {
							isTutorial2Shown = false;
							if (Settings.isSoundEnabled) {
								Assets.Audio.Effect.click.play(1);
							}
						}
					}
				}
			} else {				
				gameState = GameState.RUNNING;				
			}
		}		
	}
	
	private void updateRunning(List<TouchEvent> touchEvents, float deltaTime) {
		int len = touchEvents.size();		
		for (int c = 0; c < len ; c++) {
			TouchEvent event = touchEvents.get(c);
			if (event.type == TouchEvent.TOUCH_DOWN) {
				if (inBounds(event, tembak.xPos, tembak.yPos, tembak.widthSize, tembak.heightSize)) {
					//System.out.println("tembak");
					// for single shoot
					/*if (!seaWorld.penyelam.isShooting) {
						seaWorld.penyelam.isShooting = true;
						seaWorld.penyelam.shoot(JenisPeluru.TOMBAK);
					}*/
					seaWorld.penyelam.shoot(JenisPeluru.TOMBAK);
					if (seaWorld.penyelam.isTouched(event)) {
						seaWorld.penyelam.setTouchedCond(false,event);
					}
					if (Settings.isSoundEnabled) {
						Assets.Audio.Effect.shootTombak.play(1);
					}
					// hold button
					/*if (!isHoldingTembak) {
						isHoldingTembak = true;
					}*/
				} else if (inBounds(event, tangkap.xPos, tangkap.yPos, tangkap.widthSize, tangkap.heightSize)) {
					//System.out.println("tangkap");
					// for single shoot
					/*if (!seaWorld.penyelam.isShooting) {
						seaWorld.penyelam.isShooting = true;
						seaWorld.penyelam.shoot(JenisPeluru.JARING);
					}*/
					seaWorld.penyelam.shoot(JenisPeluru.JARING);
					if (seaWorld.penyelam.isTouched(event)) {
						seaWorld.penyelam.setTouchedCond(false,event);
					}
					if (Settings.isSoundEnabled) {
						Assets.Audio.Effect.shootJaring.play(1);
					}
					// hold button
					/*if (!isHoldingTangkap) {
						isHoldingTangkap = true;
					}*/
				} else if (inBounds(event, seaWorld.penyelam.xPos, seaWorld.penyelam.yPos, seaWorld.penyelam.widthSize, seaWorld.penyelam.heightSize)) {
					//System.out.println("penyelam");
					seaWorld.penyelam.setTouchedCond(true,event);					
				} else if (inBounds(event, untouchZone[0], untouchZone[1], untouchZone[2], untouchZone[3])) {
					if (seaWorld.penyelam.isTouched(event)) {
						seaWorld.penyelam.setTouchedCond(false,event);
					}
				} else {
					if (seaWorld.penyelam.isTouched(event)) {
						seaWorld.penyelam.setTouchedCond(false,event);
					}
					//System.out.println("requestMove");
					//seaWorld.penyelam.requestMove(event.x, event.y);						
				}
			}
			if (event.type == TouchEvent.TOUCH_DRAGGED) {
				if (inBounds(event, tangkap.xPos, tangkap.yPos, tangkap.widthSize, tangkap.heightSize)) {
					/*if (seaWorld.penyelam.isTouched(event)) {
						seaWorld.penyelam.setTouchedCond(false,event);
					}*/	
					// hold button
					/*if (!isHoldingTangkap) {
						isHoldingTangkap = true;
					}*/
				} else if (inBounds(event, tembak.xPos, tembak.yPos, tembak.widthSize, tembak.heightSize)) {
					/*if (seaWorld.penyelam.isTouched(event)) {
						seaWorld.penyelam.setTouchedCond(false,event);
					}*/
					// hold button
					/*if (!isHoldingTembak) {
						isHoldingTembak = true;
					}*/
				} /*else if (inBounds(event, untouchZone[0], untouchZone[1], untouchZone[2], untouchZone[3])){
					seaWorld.penyelam.setTouchedCond(false,event);
					// hold button
					isHoldingTangkap = false;
					isHoldingTembak = false;
				} else {
					// hold button
					seaWorld.penyelam.setTouchedCond(false,event);
					isHoldingTangkap = false;
					isHoldingTembak = false;
				}*/
				if(seaWorld.penyelam.isTouched(event)) {
					int xMove = event.x - seaWorld.penyelam.xPos;
					int yMove = event.y - seaWorld.penyelam.yPos;					
					seaWorld.penyelam.move(xMove, yMove);
				}
				
			}
			if (event.type == TouchEvent.TOUCH_UP) {
				if (seaWorld.penyelam.isTouched(event)) {
					seaWorld.penyelam.setTouchedCond(false,event);
				}
				if (inBounds(event, pause.xPos, pause.yPos, pause.widthSize, pause.heightSize)) {
					gameState = GameState.PAUSED;
					if (Settings.isSoundEnabled) {
						Assets.Audio.Effect.click.play(1);
					}
				}
				// hold button
				/*if (isHoldingTangkap) {
					isHoldingTangkap = false;
				}
				if (isHoldingTembak) {
					isHoldingTembak = false;
				}*/
			}
		}
		// -check held-botton-
		/*if (isHoldingTangkap) {
			if (!seaWorld.penyelam.isShooting) {
				seaWorld.penyelam.isShooting = true;
				seaWorld.penyelam.shoot(JenisPeluru.JARING);
			}
		}
		if (isHoldingTembak) {
			if (!seaWorld.penyelam.isShooting) {
				seaWorld.penyelam.isShooting = true;
				seaWorld.penyelam.shoot(JenisPeluru.TOMBAK);
			}
		}*/
		// -end here-		
		if (seaWorld.isCleared || seaWorld.isGameOver){
			gameState = GameState.GAME_FINISHED;
		} else {
			seaWorld.update(deltaTime);
		}
	}
	private void updatePaused(List<TouchEvent> touchEvents) {
		int len = touchEvents.size();
		for (int c = 0; c < len ; c++) {
			TouchEvent event = touchEvents.get(c);
			if (event.type == TouchEvent.TOUCH_UP) {
				if (inBounds(event, lanjutkan.xPos, lanjutkan.yPos, lanjutkan.widthSize, lanjutkan.heightSize)) {
					gameState = GameState.RUNNING;
					if (Settings.isSoundEnabled) {
						Assets.Audio.Effect.click.play(1);
					}
				} else if (inBounds(event, ulangi.xPos, ulangi.yPos, ulangi.widthSize, ulangi.heightSize)) {
					seaWorld.resetWorld();
					gameState = GameState.PREPARING;		
					if (Settings.isSoundEnabled) {
						Assets.Audio.Effect.click.play(1);
					}
				} else if (inBounds(event, keMenu.xPos, keMenu.yPos, keMenu.widthSize, keMenu.heightSize)) {
					game.setScreen(new StageSelectScreen(game, levelPointer));
					if (Settings.isSoundEnabled) {
						Assets.Audio.Effect.click.play(1);
					}
				} 
			}
		}
	}
	private void updateFinished(List<TouchEvent> touchEvents) {
		int len = touchEvents.size();
		for (int c = 0; c < len ; c++) {
			TouchEvent event = touchEvents.get(c);			
			if (event.type == TouchEvent.TOUCH_UP) {				
				if (seaWorld.isCleared ) {
					if (inBounds(event, berikutnya.xPos, berikutnya.yPos, berikutnya.widthSize, berikutnya.heightSize)) {
						Settings.save(game.getFileIO());
						switch (levelPointer) {
							case MUDAH:
								if (stagePointer < 10-1) { // stage (10) - 1
									game.setScreen(new GameScreen(game, LevelSelectScreen.Lvl.MUDAH, stagePointer+1));
								} else {
									game.setScreen(new LevelSelectScreen(game));
									if (Assets.Audio.BGM.menuMudah.isPlaying()) {
										Assets.Audio.BGM.menuMudah.pause();
										Assets.Audio.BGM.menuMudah.stop();
									}																	
								}
								break;
							case LUMAYAN:
								if (stagePointer < 10-1) { // stage (10) - 1
									game.setScreen(new GameScreen(game, LevelSelectScreen.Lvl.LUMAYAN, stagePointer+1));
								} else {
									game.setScreen(new LevelSelectScreen(game));
									if (Assets.Audio.BGM.menuLumayan.isPlaying()) {
										Assets.Audio.BGM.menuLumayan.pause();
										Assets.Audio.BGM.menuLumayan.stop();
									}
								}
								break;							
							case SUSAH:
								if (stagePointer < 10-1) { // stage (10) - 1
									game.setScreen(new GameScreen(game, LevelSelectScreen.Lvl.SUSAH, stagePointer+1));
								} else {
									game.setScreen(new LevelSelectScreen(game));
									if (Assets.Audio.BGM.menuSusah.isPlaying()) {
										Assets.Audio.BGM.menuSusah.pause();
										Assets.Audio.BGM.menuSusah.stop();
									}
								}
								break;												
						}
					}
					if (seaWorld.isNotificationShown ) {					
						for (int d = 0; d < Achievement.TOTAL_ACHIEVEMENT; d++) {
							if (Achievement.achievementStatusAtIndex[d] == 
									Achievement.Status.NEW_ACHIEVEMENT) {
								Achievement.achievementStatusAtIndex[d] = Achievement.Status.OLD_ACHIEVEMENT; 
							}
						}										
					}
				}
				if (inBounds(event, ulangi.xPos, ulangi.yPos, ulangi.widthSize, ulangi.heightSize)) {					
					if (seaWorld.isCleared) {
						Settings.save(game.getFileIO());
					}
					seaWorld.resetWorld();
					gameState = GameState.PREPARING;
					if (Settings.isSoundEnabled) {
						Assets.Audio.Effect.click.play(1);
					}
					if (seaWorld.isNotificationShown ) {						
						for (int d = 0; d < Achievement.TOTAL_ACHIEVEMENT; d++) {
							if (Achievement.achievementStatusAtIndex[d] == 
									Achievement.Status.NEW_ACHIEVEMENT) {
								Achievement.achievementStatusAtIndex[d] = Achievement.Status.OLD_ACHIEVEMENT; 
							}
						}										
					}
				} else if (inBounds(event, keMenu.xPos, keMenu.yPos, keMenu.widthSize, keMenu.heightSize)) {					
					if (seaWorld.isCleared) {
						Settings.save(game.getFileIO());
					}
					game.setScreen(new StageSelectScreen(game, levelPointer));
					if (Settings.isSoundEnabled) {
						Assets.Audio.Effect.click.play(1);
					}
					if (seaWorld.isNotificationShown ) {						
						for (int d = 0; d < Achievement.TOTAL_ACHIEVEMENT; d++) {
							if (Achievement.achievementStatusAtIndex[d] == 
									Achievement.Status.NEW_ACHIEVEMENT) {
								Achievement.achievementStatusAtIndex[d] = Achievement.Status.OLD_ACHIEVEMENT; 
							}
						}										
					}
				}
			}
			 
		}
	}
	private void drawPreparingUI(Graphics g) {
		tembak.drawSprite(g);
		tangkap.drawSprite(g);
		if (tembak.xPos == tembakXpos && tangkap.xPos == tangkapXPos 
				&& pause.xPos == pauseXPos && !seaWorld.isOnTransition) {
			if (this.isTutorial1Shown) {
				g.drawPixmap(Assets.Bg.Tutorial.inGame1, 0, 0);
			} else if (this.isTutorial2Shown) {
				g.drawPixmap(Assets.Bg.Tutorial.inGame2, 0, 0);
			} 
			mulaiGame.drawSprite(g);
			return; // prevent pauseButton to be drawn while showing tutor screen
		}
		pause.drawSprite(g);
	}
	private void drawRunningUI(Graphics g) {
		tembak.drawSprite(g);
		tangkap.drawSprite(g);
		pause.drawSprite(g);
	}
	private void drawPausedUI(Graphics g) {
		g.drawARGB(80, 26, 122, 122);
		lanjutkan.drawSprite(g);		
		ulangi.drawSprite(g);
		keMenu.drawSprite(g);
	}
	private void drawGameFinishedUI(Graphics g) {
		int x = 120;
		int y = 120;
		if (seaWorld.isCleared) {				
			switch (seaWorld.starReceived) {
				case 1 : 
					g.drawPixmap(Assets.InGame.Finished.clear1, x, y);
					break;
				case 2 :
					g.drawPixmap(Assets.InGame.Finished.clear2, x, y);
					break;
				case 3 :
					g.drawPixmap(Assets.InGame.Finished.clear3, x, y);
					break;
			}
			berikutnya.drawSprite(g);
		} else if (seaWorld.isGameOver) {
			g.drawPixmap(Assets.InGame.Finished.failed, x, y);
		}		
		ulangi.drawSprite(g);
		keMenu.drawSprite(g);
		g.drawPixmap(Assets.InGame.Finished.scoreInfo, 160, 265);
		g.drawPixmap(Assets.InGame.Finished.scoreBest, 80, 350);		
		drawText(g, String.valueOf(seaWorld.scoreTarget),270, 265);		
		if (seaWorld.scoreAkhir == Settings.EMPTY_INT) {
			drawText(g, "-", 270, 300);
		} else {
			drawText(g, String.valueOf(seaWorld.scoreAkhir), 270, 300);
		}		
		if (seaWorld.scoreBest == Settings.EMPTY_INT) {
			drawText(g, "-", 270, 350);
		} else {
			drawText(g, String.valueOf(seaWorld.scoreBest), 270, 350);
		}
		if (seaWorld.isNotificationShown) {					
			g.drawPixmap(Assets.Achievement.achievedSign, 220, 390);
		}
	}	
	private boolean inBounds(TouchEvent event, int x, int y, int width, int height) {
		if (event.x > x && event.x < x + width  && event.y > y
				&& event.y < y + height )
			return true;
		else
			return false;
	}	
	private void drawText(Graphics g, String line, int x, int y) {   
	  	int srcX, srcY = 0, srcWidth, srcHeight = 25;    	
	   	int len = line.length();        
	   	for (int i = 0; i < len; i++) {            
	   		char character = line.charAt(i);
	   		srcX = 0;
	   		if (character == '-') {
	   			srcX = 180; // 'minus sign' coordinat
	   		} else {
	   			srcX = (character - '0') * 18;
	   		}	   		            
	   		srcWidth = 18;            	   		    		   		
	   		g.drawPixmap(Assets.Font.general, x, y, srcX, srcY, srcWidth, srcHeight);            
	   		x += srcWidth;        
	   	}    
	}
}
