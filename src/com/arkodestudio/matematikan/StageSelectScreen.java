package com.arkodestudio.matematikan;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Color;


import com.arkodestudio.framework.Game;
import com.arkodestudio.framework.Graphics;
import com.arkodestudio.framework.Input.TouchEvent;
import com.arkodestudio.framework.Pixmap;
import com.arkodestudio.framework.Screen;
import com.arkodestudio.matematikan.LevelSelectScreen.Lvl;
import com.arkodestudio.matematikan.SpriteBuilder.PrefferedPos;

public class StageSelectScreen extends Screen {
	private enum ScreenList {
		LEVEL_SELECT, GAME_SCREEN
	}
	private final static int TRANSITION_SPEED = 15;
	private final static int TOTAL_STAGE = 10;	
	private ScreenList requestScreen;	
	private boolean[] isPlayableStage = new boolean[TOTAL_STAGE];
	private List<Sprite> spriteBatch;	
	private List<Pixmap> pixmapBatch;
	private Sprite back;
	private Sprite frame;
	private SpriteBuilder spriteTahukahAnda = new SpriteBuilder();
	private SpriteBuilder[] stage = new SpriteBuilder[TOTAL_STAGE];
	private int stagePointer = -1;
	private LevelSelectScreen.Lvl levelPointer;
	private int[] yPosStage = new int[TOTAL_STAGE];
	private int yPosBack;
	private int yPosFrame;
	private boolean isOnTransitionIn = true;
	private boolean isOnTransitionOut = false;
	private boolean isReadyToNextScreen = false;
	private float currentTick = 0f;	
	private int currentCounter = -1;
	private int alphaChanel = 255; //full transparent	
	private Pixmap[] parralax = new Pixmap[3];
	
	public StageSelectScreen(Game game, Lvl level) {
		super(game);	
		this.levelPointer = level;		
		spriteBatch = new ArrayList<Sprite>(TOTAL_STAGE);		
		spriteBatch.add(new Sprite(Assets.Button.Stage.ke1,40,-140)); //top of screen
		spriteBatch.add(new Sprite(Assets.Button.Stage.ke2,188,-140));
		spriteBatch.add(new Sprite(Assets.Button.Stage.ke3,336,-140));
		spriteBatch.add(new Sprite(Assets.Button.Stage.ke4,484,-140));
		spriteBatch.add(new Sprite(Assets.Button.Stage.ke5,632,-140));
		spriteBatch.add(new Sprite(Assets.Button.Stage.ke6,40,-140));
		spriteBatch.add(new Sprite(Assets.Button.Stage.ke7,188,-140));
		spriteBatch.add(new Sprite(Assets.Button.Stage.ke8,336,-140));
		spriteBatch.add(new Sprite(Assets.Button.Stage.ke9,484,-140));
		spriteBatch.add(new Sprite(Assets.Button.Stage.ke10,632,-140));		
		back = new Sprite(Assets.Button.back,680,MatematIkan.FRAME_BUFFER_HEIGHT); //480//bottom of screen
		frame = new Sprite(Assets.Knowledge.frame,40,MatematIkan.FRAME_BUFFER_HEIGHT); //480//bottom of screen
		yPosStage[0] = 40;
		yPosStage[1] = 40;
		yPosStage[2] = 40;
		yPosStage[3] = 40;
		yPosStage[4] = 40;
		yPosStage[5] = 200;
		yPosStage[6] = 200;
		yPosStage[7] = 200;
		yPosStage[8] = 200;
		yPosStage[9] = 200;
		yPosBack = 360;
		yPosFrame = 360;
		pixmapBatch = new ArrayList<Pixmap>();
		pixmapBatch.add(Assets.Knowledge.Content.info1);
		pixmapBatch.add(Assets.Knowledge.Content.info2);
		pixmapBatch.add(Assets.Knowledge.Content.info3);
		pixmapBatch.add(Assets.Knowledge.Content.info4);
		pixmapBatch.add(Assets.Knowledge.Content.info5);
		pixmapBatch.add(Assets.Knowledge.Content.info6);
		pixmapBatch.add(Assets.Knowledge.Content.info7);
		pixmapBatch.add(Assets.Knowledge.Content.info8);
		pixmapBatch.add(Assets.Knowledge.Content.info9);
		pixmapBatch.add(Assets.Knowledge.Content.info10);
		spriteTahukahAnda.addSprite(frame, null);		
		if (level == Lvl.MUDAH) {		
			parralax[0] = Assets.Bg.levelMudah1;
			parralax[1] = Assets.Bg.levelMudah2;
			parralax[2] = Assets.Bg.levelMudah3;
			for (int c = 0; c < TOTAL_STAGE; c++) {			
				if (Settings.Stage.Mudah.isPlayableStage[c]) {
					this.isPlayableStage[c] = true;
					stage[c] = new SpriteBuilder();
					stage[c].addSprite(spriteBatch.get(c), null);
					switch (Settings.Stage.Mudah.stateStage[c]) {
						case STAR_0:
							stage[c].addPixmap(Assets.Button.Stage.star0, PrefferedPos.BOTTOM);							
							break;
						case STAR_1:
							stage[c].addPixmap(Assets.Button.Stage.star1, PrefferedPos.BOTTOM);
							break;
						case STAR_2:
							stage[c].addPixmap(Assets.Button.Stage.star2, PrefferedPos.BOTTOM);
							break;
						case STAR_3:
							stage[c].addPixmap(Assets.Button.Stage.star3, PrefferedPos.BOTTOM);
							break;
						case STAR_LOCKED:
							// never goes here
							break;					
					}					
				} else {
					this.isPlayableStage[c] = false;
					stage[c] = new SpriteBuilder();
					spriteBatch.get(c).setPixmap(Assets.Button.Stage.locked);
					stage[c].addSprite(spriteBatch.get(c), null);
					stage[c].addPixmap(Assets.Button.Stage.starLocked, PrefferedPos.BOTTOM);					
				}
			}
			if (Settings.isSoundEnabled) {
				if (Assets.Audio.BGM.menuMudah.isStopped()) {
					Assets.Audio.BGM.menuMudah.setLooping(true);
					Assets.Audio.BGM.menuMudah.play();
				}						
			}
		} else if (level == Lvl.LUMAYAN) {		
			parralax[0] = Assets.Bg.levelLumayan1;
			parralax[1] = Assets.Bg.levelLumayan2;
			parralax[2] = Assets.Bg.levelLumayan3;
			for (int c = 0; c < TOTAL_STAGE; c++) {			
				if (Settings.Stage.Lumayan.isPlayableStage[c]) {
					this.isPlayableStage[c] = true;
					stage[c] = new SpriteBuilder();
					stage[c].addSprite(spriteBatch.get(c), null);
					switch (Settings.Stage.Lumayan.stateStage[c]) {
						case STAR_0:
							stage[c].addPixmap(Assets.Button.Stage.star0, PrefferedPos.BOTTOM);
							break;
						case STAR_1:
							stage[c].addPixmap(Assets.Button.Stage.star1, PrefferedPos.BOTTOM);
							break;
						case STAR_2:
							stage[c].addPixmap(Assets.Button.Stage.star2, PrefferedPos.BOTTOM);
							break;
						case STAR_3:
							stage[c].addPixmap(Assets.Button.Stage.star3, PrefferedPos.BOTTOM);
							break;
						case STAR_LOCKED:
							// never goes here
							break;					
					}
				} else {
					this.isPlayableStage[c] = false;
					stage[c] = new SpriteBuilder();
					spriteBatch.get(c).setPixmap(Assets.Button.Stage.locked);
					stage[c].addSprite(spriteBatch.get(c), null);
					stage[c].addPixmap(Assets.Button.Stage.starLocked, PrefferedPos.BOTTOM);
				}
			}
			if (Settings.isSoundEnabled) {
				if (Assets.Audio.BGM.menuLumayan.isStopped()) {
					Assets.Audio.BGM.menuLumayan.setLooping(true);
					Assets.Audio.BGM.menuLumayan.play();
				}						
			}
		} else if (level == Lvl.SUSAH) {	
			parralax[0] = Assets.Bg.levelSusah1;
			parralax[1] = Assets.Bg.levelSusah2;
			parralax[2] = Assets.Bg.levelSusah3;
			for (int c = 0; c < TOTAL_STAGE; c++) {			
				if (Settings.Stage.Susah.isPlayableStage[c]) {
					this.isPlayableStage[c] = true;
					stage[c] = new SpriteBuilder();
					stage[c].addSprite(spriteBatch.get(c), null);
					switch (Settings.Stage.Susah.stateStage[c]) {
						case STAR_0:
							stage[c].addPixmap(Assets.Button.Stage.star0, PrefferedPos.BOTTOM);
							break;
						case STAR_1:
							stage[c].addPixmap(Assets.Button.Stage.star1, PrefferedPos.BOTTOM);
							break;
						case STAR_2:
							stage[c].addPixmap(Assets.Button.Stage.star2, PrefferedPos.BOTTOM);
							break;
						case STAR_3:
							stage[c].addPixmap(Assets.Button.Stage.star3, PrefferedPos.BOTTOM);
							break;
						case STAR_LOCKED:
							// never goes here
							break;					
					}
				} else {
					this.isPlayableStage[c] = false;
					stage[c] = new SpriteBuilder();
					spriteBatch.get(c).setPixmap(Assets.Button.Stage.locked);
					stage[c].addSprite(spriteBatch.get(c), null);
					stage[c].addPixmap(Assets.Button.Stage.starLocked, PrefferedPos.BOTTOM);
				}
			}
			if (Settings.isSoundEnabled) {
				if (Assets.Audio.BGM.menuSusah.isStopped()) {
					Assets.Audio.BGM.menuSusah.setLooping(true);
					Assets.Audio.BGM.menuSusah.play();
				}						
			}
		}		
		
		
	}

	@Override
	public void update(float deltaTime) {		
		updateParralax(deltaTime);
		if (isOnTransitionIn && !isOnTransitionOut) {
			for (int c = 0; c < TOTAL_STAGE; c++) {
				if (stage[c].yPos < yPosStage[c]) {
					stage[c].move(0, TRANSITION_SPEED);
					//Log.i(String.valueOf("yPos_stage_"+c),String.valueOf(stage[c].yPos));
				} else if (stage[c].yPos > yPosStage[c]) {
					stage[c].yPos = yPosStage[c];
				}
			}
			if (back.yPos > yPosBack) {
				back.move(0, -TRANSITION_SPEED);
			} else if (back.yPos < yPosBack) {
				back.yPos = yPosBack;
			}
			if (spriteTahukahAnda.yPos > yPosFrame) {
				spriteTahukahAnda.move(0, -TRANSITION_SPEED);
			} else if (spriteTahukahAnda.yPos < yPosFrame) {
				spriteTahukahAnda.yPos = yPosFrame;
			}			
		}
		if (!isOnTransitionIn && !isOnTransitionOut) {
			List<TouchEvent> touchEvents = game.getInput().getTouchEvents();
			int len = touchEvents.size();
			int lenTotalStage = stage.length;	
			for (int c = 0; c < len; c++) {
				TouchEvent event = touchEvents.get(c);
				if (event.type == TouchEvent.TOUCH_UP) {
					if (inBounds(event, back.xPos, back.yPos, back.widthSize, back.heightSize)) {
						isOnTransitionOut = true;
						requestScreen = ScreenList.LEVEL_SELECT;						
					}
					for (int i = 0; i < lenTotalStage; i++) {
						if (this.isPlayableStage[i]) {
							if (inBounds(event, stage[i].xPos, stage[i].yPos, stage[i].widthSize, stage[i].heightSize)) {
								isOnTransitionOut = true;
								requestScreen = ScreenList.GAME_SCREEN;
								stagePointer = i;								
							}
						}
					}				
				}
			}
			if (currentCounter == -1) {
				currentCounter = 0;
				spriteTahukahAnda.addPixmap(pixmapBatch.get(currentCounter), PrefferedPos.OVERLAP_MID_CENTER);//
			}
			if (currentTick >= 5.0f) {
				currentTick = 0.0f;
				currentCounter++;
				if (currentCounter > 9) { // 10 (because array, so it;s 9) of info content
					currentCounter = 0;
				}
				spriteTahukahAnda.setPixmap(1, pixmapBatch.get(currentCounter)); // 1 = second sprite of spriteBuilder <array>
			}
			currentTick += deltaTime;		
		}
		if (isOnTransitionOut && !isOnTransitionIn) {
			for (int c = 0; c < TOTAL_STAGE; c++) {
				if (stage[c].isVisible) {
					stage[c].move(0, -TRANSITION_SPEED);
				}
			}
			if (back.isVisible) {
				back.move(0, TRANSITION_SPEED);
			}
			if (spriteTahukahAnda.isVisible) {
				spriteTahukahAnda.move(0, TRANSITION_SPEED);
			}			
		}
		checkSpritePos();
		if (isReadyToNextScreen) {
			switch (requestScreen) {
				case GAME_SCREEN:
					game.setScreen(new GameScreen(game,levelPointer,stagePointer));
					break;
				case LEVEL_SELECT:
					game.setScreen(new LevelSelectScreen(game));
					if (Assets.Audio.BGM.menuMudah.isPlaying()) {
						Assets.Audio.BGM.menuMudah.pause();
						Assets.Audio.BGM.menuMudah.stop();
						break;
					}  else if (Assets.Audio.BGM.menuLumayan.isPlaying()) {
						Assets.Audio.BGM.menuLumayan.pause();
						Assets.Audio.BGM.menuLumayan.stop();
						break;
					} else if (Assets.Audio.BGM.menuSusah.isPlaying()) {
						Assets.Audio.BGM.menuSusah.pause();
						Assets.Audio.BGM.menuSusah.stop();
						break;
					}
					break;			
			}
		}
		
	}

	@Override
	public void present(float deltaTime) {
		Graphics g = game.getGraphics();
		drawParralax(g);
		int lenStageTotal = stage.length;		
		for (int c = 0; c < lenStageTotal; c++) {
			if (stage[c].isVisible) {
				stage[c].drawSprite(g);
			} 
		}
		if (spriteTahukahAnda.isVisible) {
			spriteTahukahAnda.drawSprite(g);
			if (!isOnTransitionIn && !isOnTransitionOut) {
				int x = spriteTahukahAnda.xPos;
				int y = spriteTahukahAnda.yPos;
				int width = 489;
				int height = 54;
				if (currentTick <= 0.7f ) {
					if (alphaChanel >= 0) {
						alphaChanel -= (300 * deltaTime); //digoleki
						if (alphaChanel <= 0 || currentTick >= 0.6) {
							alphaChanel = 0;
						}
					}
				} else if (currentTick >= 4.3f) {
					if (alphaChanel <= 255) {
						alphaChanel += (200 * deltaTime); //digoleki
						if (alphaChanel >= 255 || currentTick <= 4.9) {
							alphaChanel = 255;
						}
					}
				}				
				g.drawRectV2(x+135, y+13, width , height, (Color.argb(alphaChanel,90,62,32)));
			}
		}
		if (back.isVisible) {
			back.drawSprite(g);
		}
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub		
	}
 
	@Override
	public void resume() {
		update(0);
		present(0);	
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
		int len = stage.length;		
		spriteBatch.clear();
		pixmapBatch.clear();
		spriteBatch = null;
		pixmapBatch = null;
		for (int c = 0; c < len; c++) {
			stage[c] = null;
		}
		stage = null;		
		frame = null;
		spriteTahukahAnda = null;
		back = null;
		
	}
	@Override
	public void backButton() {
		if (!isOnTransitionIn && !isOnTransitionOut) {
			isOnTransitionOut = true;
			requestScreen = ScreenList.LEVEL_SELECT;
		}
		
		
	}
	private boolean inBounds(TouchEvent event, int x, int y, int width, int height) {
		if (event.x > x && event.x < x + width - 1 && event.y > y
				&& event.y < y + height - 1)
			return true;
		else
			return false;
	}
	private void updateParralax(float deltaTime) {		
		for (int c = 0; c < 3; c++) {
			Settings.gameBgMoveX[c] -= Settings.gameMoveSpeed[c]*deltaTime;
			Settings.gameBgNewMoveX[c] = parralax[c].getWidth() - (-Settings.gameBgMoveX[c]);
		}		
	}
	private void drawParralax(Graphics g) {
		for (int c = 0; c < 3 ;c++) {
			if (Settings.gameBgNewMoveX[c] <= 0) {
				Settings.gameBgMoveX[c] = 0;
				g.drawPixmap(parralax[c], Settings.gameBgMoveX[c], Settings.gameBgStaticY[c]);
			} else {
				g.drawPixmap(parralax[c], Settings.gameBgMoveX[c], Settings.gameBgStaticY[c]);
				g.drawPixmap(parralax[c], Settings.gameBgNewMoveX[c], Settings.gameBgStaticY[c]);
			}
		}		
	}
	private void checkSpritePos() {
		int scrHeight = MatematIkan.FRAME_BUFFER_HEIGHT;
		if (isOnTransitionIn && !isOnTransitionOut) {
			boolean checker = true;
			for (int c = 0; c < TOTAL_STAGE; c++) {
				if (stage[c].yPos == yPosStage[c]) {
					checker &= true;
				} else {
					checker &= false;
				}
			}
			if (checker && (back.yPos == yPosBack) && (spriteTahukahAnda.yPos == yPosFrame)) {
				isOnTransitionIn = false;
			}
		}
		/*if (!isOnTransitionIn && !isOnTransitionOut) {
			
		}*/
		if(isOnTransitionOut && !isOnTransitionIn) {
			boolean checker = true;
			for (int c = 0; c < TOTAL_STAGE; c++) {
				if (stage[c].heightPos < 0) { 
					stage[c].isVisible = false;
					checker &= true;
				} else {
					checker &= false;
				}
			}
			if (back.yPos > scrHeight) {
				back.isVisible = false;
			}
			if (spriteTahukahAnda.yPos > scrHeight) {
				spriteTahukahAnda.isVisible = false;
			}
			if (checker && !back.isVisible && !spriteTahukahAnda.isVisible) {
				isReadyToNextScreen = true;
			}
		}
	}

}
