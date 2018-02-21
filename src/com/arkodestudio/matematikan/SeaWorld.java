package com.arkodestudio.matematikan;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import android.util.Log;

import com.arkodestudio.framework.Game;
import com.arkodestudio.framework.Graphics;
import com.arkodestudio.framework.Pixmap;

public class SeaWorld {	
	private final static int TRANSITION_SPEED = 20;
	private final static int MOST_TOP = 0;
	private final static int TOP = 70;
	private final static int BOTTOM = MatematIkan.FRAME_BUFFER_HEIGHT;
	private final static int LEFT = 0;
	private final static int RIGHT = MatematIkan.FRAME_BUFFER_WIDHT;
	private final static int TOTAL_IKAN = 3;
	private final static int FISH_SPACE = SeaWorld.BOTTOM - SeaWorld.TOP - (Ikan.HEIGHT+40);
	private Pixmap[] parralax = new Pixmap[3];
	private Game game;
	private int stagePointer;
	private LevelSelectScreen.Lvl levelPointer;
	private int slotPointer;	
	private Random rand = new Random();
	private Container tong;
	private Container[] kapalKecil;
	private Container kapalBesar;
	private int jumKapalKecil;
	private int xPosTong;
	private int[] xPosKapalKecil;
	private int xPosKapalBesar;
	private String operator;	
	private Sprite[] signOperator;
	private Sprite signEqual;
	private int[] yPosSignOperator;
	private int yPosSignEqual;
	private List<Ikan> ikanList = new ArrayList<Ikan>();	
	public Penyelam penyelam;
	public boolean isOnTransition = true;
	public boolean isNotificationShown = false;
	public boolean isCleared = false;
	public boolean isGameOver = false;
	public int starReceived = 0;
	public int scoreAkhir = Settings.EMPTY_INT;
	public int scoreTarget = Settings.EMPTY_INT;
	public int scoreBest = Settings.EMPTY_INT;
	
	
	public SeaWorld(Game game,LevelSelectScreen.Lvl levelPointer, int stagePointer) {
		this.game = game;
		this.stagePointer = stagePointer;
		this.levelPointer = levelPointer;
		this.slotPointer = 0;
		// tong -init- start here
		int x = SeaWorld.LEFT; // TONG desired Pos "0"
		int xAwal = 0 - (Assets.Container.tong.getWidth());
		int y = 0;
		switch (levelPointer) {
			case MUDAH:
				parralax[0] = Assets.Bg.levelMudah1;
				parralax[1] = Assets.Bg.levelMudah2;
				parralax[2] = Assets.Bg.levelMudah3;
				tong = new Container(Container.JenisContainer.TONG, xAwal, y, 
								Settings.Stage.Mudah.targetStage[stagePointer]);
				kapalKecil = new Container[Settings.Stage.Mudah.oprStage[stagePointer].slot];
				operator = Settings.Stage.Mudah.oprStage[stagePointer].operator;
				signOperator = new Sprite[Settings.Stage.Mudah.oprStage[stagePointer].numOpr];
				scoreBest =Settings.Stage.Mudah.bestScoreStage[stagePointer];
				break;
			case LUMAYAN:
				parralax[0] = Assets.Bg.levelLumayan1;
				parralax[1] = Assets.Bg.levelLumayan2;
				parralax[2] = Assets.Bg.levelLumayan3;
				tong = new Container(Container.JenisContainer.TONG, xAwal, y, 
						Settings.Stage.Lumayan.targetStage[stagePointer]);
				kapalKecil = new Container[Settings.Stage.Lumayan.oprStage[stagePointer].slot];
				operator = Settings.Stage.Lumayan.oprStage[stagePointer].operator;
				signOperator = new Sprite[Settings.Stage.Lumayan.oprStage[stagePointer].numOpr];
				scoreBest =Settings.Stage.Lumayan.bestScoreStage[stagePointer];
				break;			
			case SUSAH:
				parralax[0] = Assets.Bg.levelSusah1;
				parralax[1] = Assets.Bg.levelSusah2;
				parralax[2] = Assets.Bg.levelSusah3;
				tong = new Container(Container.JenisContainer.TONG, xAwal, y, 
							Settings.Stage.Susah.targetStage[stagePointer]);
				kapalKecil = new Container[Settings.Stage.Susah.oprStage[stagePointer].slot];
				operator = Settings.Stage.Susah.oprStage[stagePointer].operator;
				signOperator = new Sprite[Settings.Stage.Susah.oprStage[stagePointer].numOpr];
				scoreBest =Settings.Stage.Susah.bestScoreStage[stagePointer];
				break;		
		}
		this.scoreTarget = tong.score;
		xPosTong = x;
		// tong -init- ends here
		// --------------------
		// kapalBesar -init- start here
		x = SeaWorld.RIGHT - Assets.Container.kapalBesar.getWidth();
		xAwal = 0 - Assets.Container.kapalBesar.getWidth();
		y = SeaWorld.MOST_TOP;
		kapalBesar = new Container(Container.JenisContainer.KAPAL_BESAR,xAwal, y, 0);
		xPosKapalBesar = x;
		// kapalBesar -init- ends here
		// ----------------------------
		// kapalKecil -init- start here
		int len = kapalKecil.length;
		jumKapalKecil = len;
		xPosKapalKecil = new int[len];
		int increment = 540 / len; // kapal kecil total space (670-130) : jmlKapal ==> jarak antar kapal 
		x = 130; // 1st kapalKecil Pos
		xAwal = 0 - (Assets.Container.kapalKecil.getWidth());
		y = SeaWorld.MOST_TOP;
		for (int c = 0; c < len; c++) {
			kapalKecil[c] = new Container(Container.JenisContainer.KAPAL_KECIL,	xAwal, y, 0); // not filled yet
			xPosKapalKecil[c] = x;
			x += increment; // jarak antar Kapal
		}
		// kapalKecil -init- ends here
		// ----------------------------
		// singOperator -init- start here
		len = signOperator.length;
		yPosSignOperator = new int[len];
		int yAwal = 0 - (Assets.InGame.Operator.kali.getHeight());
		x = xPosKapalKecil[0]+(((xPosKapalKecil[1])-xPosKapalKecil[0]+Assets.Container.kapalKecil.getWidth()) / 2); // disired Pos for operator
		y = 35;
		for (int c = 0; c < len; c++) {
			if (this.operator.charAt(c) == '+') {
				signOperator[c] = new Sprite(Assets.InGame.Operator.tambah, x, yAwal);
			} 
			if (this.operator.charAt(c) == '-') {
				signOperator[c] = new Sprite(Assets.InGame.Operator.kurang, x, yAwal);
			}
			if (this.operator.charAt(c) == 'x') {
				signOperator[c] = new Sprite(Assets.InGame.Operator.kali, x, yAwal);
			}
			if (this.operator.charAt(c) == ':') {
				signOperator[c] = new Sprite(Assets.InGame.Operator.bagi, x, yAwal);
			}
			signOperator[c].setRefferencePoint(Sprite.RefferencePoint.MID_CENTER);			
			x += increment;
			yPosSignOperator[c] = y;
		}
		signEqual = new Sprite(Assets.InGame.Operator.samaDengan, x, yAwal);
		signEqual.setRefferencePoint(Sprite.RefferencePoint.MID_CENTER);
		yPosSignEqual = y;
		// signOperator -init- ends here
		// -----------------------------				
		penyelam = new Penyelam((SeaWorld.LEFT-Assets.Penyelam.act1.getWidth()), ((BOTTOM-TOP)/2)+70);
	}
	public void update(float deltaTime) {
		updateParralax(deltaTime);			
		penyelam.update(deltaTime);		
		if (isOnTransition) {
			int xInc = SeaWorld.TRANSITION_SPEED;
			int yInc = 0;
			boolean checker = true;
			boolean tempChecker = true;
			boolean containerTransition = true;
			int len = 0;
			// move everything
			penyelam.move(xInc+108, yInc+39); // adapting move() methoode at penyelam class
			if (containerTransition) {
				tong.move(xInc, yInc);
				kapalBesar.move(xInc, yInc);
				if (kapalBesar.xPos > 4) { // show kapal kecil when kapal besar had been shown
					len = kapalKecil.length;
					for (int c = 0; c < len; c++) {			
						kapalKecil[c].move(xInc, yInc);				
					}	
				}
				
				if (tong.xPos >= xPosTong) {
					tong.xPos = xPosTong;
					tempChecker &= true;
				} else {
					tempChecker &= false;
				}
				// start checking
				if (kapalBesar.xPos >= xPosKapalBesar) {
					kapalBesar.xPos = xPosKapalBesar;
					tempChecker &= true;
				} else {
					tempChecker &= false;
				}
				len = jumKapalKecil;
				for (int c = 0; c < len; c++) {
					if (kapalKecil[c].xPos >= xPosKapalKecil[c]) {
						kapalKecil[c].xPos = xPosKapalKecil[c];
						tempChecker &= true;
					} else {
						tempChecker &= false;
					}
					
				}
			}			
			if (tempChecker) {				
				containerTransition = false;
				len = signOperator.length;
				for (int c = 0; c < len; c++) {			
					signOperator[c].move(0,SeaWorld.TRANSITION_SPEED);				
				}
				signEqual.move(0, SeaWorld.TRANSITION_SPEED);
				len = signOperator.length;
				// start checking
				for (int c = 0; c < len; c++) {
					if (signOperator[c].yPos >= yPosSignOperator[c]) {
						signOperator[c].yPos = yPosSignOperator[c];
						checker &= true;
					} else {
						checker &= false;
					}
				}
				if (signEqual.yPos >= yPosSignEqual) {
					signEqual.yPos = yPosSignEqual;
					checker &= true;
				} else {
					checker &= false;
				}
			}
			
			
			if (penyelam.xPos >= SeaWorld.LEFT) {	
				penyelam.xPos = SeaWorld.LEFT;				
			} 			
			if (checker && !containerTransition && penyelam.xPos >= SeaWorld.LEFT) {
				isOnTransition = false;
			}
		} else {
			checkPenyelamPos();			
			for (int c = 0; c < ikanList.size(); c++) {
				Ikan ikan = ikanList.get(c);
				if (ikan.isVisible) {
					ikanList.get(c).update(deltaTime);
					if (ikan.xPosFloat+Ikan.WIDTH < SeaWorld.LEFT) {
						ikanList.get(c).isVisible = false;
						ikanList.remove(c);
					}
				} else {
					ikanList.remove(c); // do recycle here
				}
			}
			int ikanLen = 0;
			ikanLen = ikanList.size(); 
			if (ikanLen < SeaWorld.TOTAL_IKAN) {
				int xFish = 0;
				int yFish = 0;
				int fishNum = 0;
				if (ikanLen == 0) {
					Ikan.JenisIkan jenis = null;
					xFish = SeaWorld.RIGHT;
					yFish = 0;					
					fishNum = 0;			
					switch (rand.nextInt(4)) {
						case 0 :
							jenis = Ikan.JenisIkan.HIJAU;
							break;
						case 1 :
							jenis = Ikan.JenisIkan.KUNING;
							break;
						case 2 :
							jenis = Ikan.JenisIkan.MERAH;
							break;
						case 3 :
							jenis = Ikan.JenisIkan.UNGU;
							break;
					}					
					yFish = rand.nextInt(SeaWorld.FISH_SPACE) + SeaWorld.TOP;	
					fishNum = rand.nextInt(55)+ 1; // max fish value 55 // prevent fish with 0 number to come out
					ikanList.add(new Ikan(jenis, xFish, yFish, fishNum));
				} else {
					int last = ikanLen-1;
					Ikan lastIkan = ikanList.get(last); 
					if (lastIkan.xPos < SeaWorld.RIGHT-Ikan.WIDTH) {
						Ikan.JenisIkan jenis = null;
						xFish = SeaWorld.RIGHT;
						yFish = 0;					
						fishNum = 0;			
						switch (rand.nextInt(4)) {
							case 0 :
								jenis = Ikan.JenisIkan.HIJAU;
								break;
							case 1 :
								jenis = Ikan.JenisIkan.KUNING;
								break;
							case 2 :
								jenis = Ikan.JenisIkan.MERAH;
								break;
							case 3 :
								jenis = Ikan.JenisIkan.UNGU;
								break;
						}					
						yFish = rand.nextInt(SeaWorld.FISH_SPACE) + SeaWorld.TOP;	
						fishNum = rand.nextInt(55)+ 1; // max fish value 55 // prevent fish with 0 number to come out
						ikanList.add(new Ikan(jenis, SeaWorld.RIGHT, yFish, fishNum));
					}
				}
			}	
			ikanLen = ikanList.size();
			for (int c = 0; c < ikanLen; c++) {
				Ikan ikan = ikanList.get(c);
				if (penyelam.isCollideWith(ikan)) {
					ikanList.get(c).isVisible = false;
					penyelam.isVisible = false;
					isGameOver = true;
					if (Settings.isSoundEnabled) {
						Assets.Audio.Effect.collideFish.play(1);
					}
				} 
			}
			if (penyelam.isShooting) {
				penyelam.updatePeluru(deltaTime);
				int len = penyelam.peluru.size();
				for (int c = 0; c < len; c++) {
					Peluru peluru = penyelam.peluru.get(c);
					if (peluru.xPos > RIGHT) {
						peluru.isVisible = false;						
					}
					if (peluru.isVisible) {
						ikanLen = ikanList.size();
						for (int x = 0; x < ikanLen; x++) {
							Ikan ikan = this.ikanList.get(x);							
							if (peluru.isCollideWith(ikan)) {
								if (peluru.jenisPeluru == Peluru.JenisPeluru.TOMBAK) {									
									ikan.fishNumDecrement(Settings.SCORE_DEC);									
									if (ikan.fishNum <= 0) {
										ikan.isVisible = false;									
									} 
									peluru.isVisible = false;
									if (Settings.isSoundEnabled) {
										Assets.Audio.Effect.hitFishTombak.play(1);
									}									
								} else {
									ikan.isVisible = false;
									peluru.isVisible = false;	
									if (slotPointer < kapalKecil.length) { // make sure, "jaring" shoot number never surpass num of kapalKecil
										kapalKecil[slotPointer].setScore(ikan.fishNum);							
										slotPointer++;
									}
									if (Settings.isSoundEnabled) {
										Assets.Audio.Effect.hitFishJaring.play(1);
									}
								}
								ikanList.set(x, ikan);								
							}						
						}
					}
					penyelam.peluru.set(c,peluru);
				}
				kapalBesar.setScore(calculator(kapalKecil));
				if (slotPointer == jumKapalKecil) {
					int scoreAkhir = kapalBesar.score;
					int targetScore = 0;
					isCleared = true;
					switch (levelPointer) {
						case LUMAYAN: {
							this.scoreBest = Settings.Stage.Lumayan.bestScoreStage[stagePointer];
							targetScore = Settings.Stage.Lumayan.targetStage[stagePointer];
							if (scoreAkhir == targetScore) {
								Settings.Stage.Lumayan.stateStage[stagePointer] = Settings.Stage.State.STAR_3;														
								starReceived = 3;
							} else if (scoreAkhir <= targetScore+2 && scoreAkhir >= targetScore-2){							
								if (Settings.Stage.Lumayan.stateStage[stagePointer] != Settings.Stage.State.STAR_3)
									Settings.Stage.Lumayan.stateStage[stagePointer] = Settings.Stage.State.STAR_2;														
									starReceived = 2;
							} else if (scoreAkhir <= targetScore+4 && scoreAkhir >= targetScore-4) {
								if (Settings.Stage.Lumayan.stateStage[stagePointer] != Settings.Stage.State.STAR_2 &&
										Settings.Stage.Lumayan.stateStage[stagePointer] != Settings.Stage.State.STAR_3)
									Settings.Stage.Lumayan.stateStage[stagePointer] = Settings.Stage.State.STAR_1;								
									starReceived = 1;
							} else {
								isCleared = false;
								isGameOver = true;
							}
							if (isCleared) {
								if (stagePointer < 10-1) { // total stage (10) minus 1
									if (!Settings.Stage.Lumayan.isPlayableStage[stagePointer+1]) {
										Settings.Stage.Lumayan.isPlayableStage[stagePointer+1] = true;
										Settings.Stage.Lumayan.stateStage[stagePointer+1] = Settings.Stage.State.STAR_0;
									}
								} else {
									if (!Settings.Stage.Susah.isThisLevelPlayable) {
										Settings.Stage.Susah.isThisLevelPlayable =  true;
										Settings.Stage.Susah.isPlayableStage[0] = true;
										Settings.Stage.Susah.stateStage[0] = Settings.Stage.State.STAR_0;
									}
								}
							}						
							break;
						}
						case MUDAH: {
							this.scoreBest = Settings.Stage.Mudah.bestScoreStage[stagePointer];
							targetScore = Settings.Stage.Mudah.targetStage[stagePointer];
							if (scoreAkhir == targetScore) {
								Settings.Stage.Mudah.stateStage[stagePointer] = Settings.Stage.State.STAR_3;													
								starReceived = 3;
							} else if (scoreAkhir <= targetScore+2 && scoreAkhir >= targetScore-2){							
								if (Settings.Stage.Mudah.stateStage[stagePointer] != Settings.Stage.State.STAR_3)
									Settings.Stage.Mudah.stateStage[stagePointer] = Settings.Stage.State.STAR_2;														
									starReceived = 2;
							} else if (scoreAkhir <= targetScore+4 && scoreAkhir >= targetScore-4) {
								if (Settings.Stage.Mudah.stateStage[stagePointer] != Settings.Stage.State.STAR_2 &&
										Settings.Stage.Mudah.stateStage[stagePointer] != Settings.Stage.State.STAR_3)
									Settings.Stage.Mudah.stateStage[stagePointer] = Settings.Stage.State.STAR_1;								
									starReceived = 1;
							} else {
								isCleared = false;
								isGameOver = true;
							}
							if (isCleared) {
								if (stagePointer < 10-1) { // total stage (10) minus 1
									if (!Settings.Stage.Mudah.isPlayableStage[stagePointer+1] ) {
										Settings.Stage.Mudah.isPlayableStage[stagePointer+1] = true;
										Settings.Stage.Mudah.stateStage[stagePointer+1] = Settings.Stage.State.STAR_0;
									}
								} else {
									if (!Settings.Stage.Lumayan.isThisLevelPlayable) {
										Settings.Stage.Lumayan.isThisLevelPlayable =  true;
										Settings.Stage.Lumayan.isPlayableStage[0] = true;
										Settings.Stage.Lumayan.stateStage[0] = Settings.Stage.State.STAR_0;
									}
								}
							}
							break;
						}
						case SUSAH: {
							this.scoreBest = Settings.Stage.Susah.bestScoreStage[stagePointer];
							targetScore = Settings.Stage.Susah.targetStage[stagePointer];							
							if (scoreAkhir == targetScore) { 
								Settings.Stage.Susah.stateStage[stagePointer] = Settings.Stage.State.STAR_3;														
								starReceived = 3;
							} else if (scoreAkhir <= targetScore+2 && scoreAkhir >= targetScore-2){							
								if (Settings.Stage.Susah.stateStage[stagePointer] != Settings.Stage.State.STAR_3)
									Settings.Stage.Susah.stateStage[stagePointer] = Settings.Stage.State.STAR_2;														
									starReceived = 2;
							} else if (scoreAkhir <= targetScore+4 && scoreAkhir >= targetScore-4) {
								if (Settings.Stage.Susah.stateStage[stagePointer] != Settings.Stage.State.STAR_2 &&
										Settings.Stage.Susah.stateStage[stagePointer] != Settings.Stage.State.STAR_3)
									Settings.Stage.Susah.stateStage[stagePointer] = Settings.Stage.State.STAR_1;								
									starReceived = 1;
							} else {
								isCleared = false;
								isGameOver = true;
							}
							if (isCleared) {
								if (stagePointer < 10-1) { // total stage (10) minus 1
									if (!Settings.Stage.Susah.isPlayableStage[stagePointer+1]) {
										Settings.Stage.Susah.isPlayableStage[stagePointer+1] = true;
										Settings.Stage.Susah.stateStage[stagePointer+1] = Settings.Stage.State.STAR_0;
									}
								} 
							}	
							break;	
						}						
					}
					this.scoreAkhir = kapalBesar.score;							
					int delta_TargetAkhir = Math.abs(this.scoreTarget-this.scoreAkhir);
					int delta_TargetBest = Math.abs(this.scoreTarget-this.scoreBest);
					if (delta_TargetAkhir > delta_TargetBest) {
						if (scoreBest == 0) {
							scoreBest = scoreAkhir;
						}						
					} else if (delta_TargetAkhir < delta_TargetBest) {
						this.scoreBest = this.scoreAkhir;
					} else {
						// both are equal
						//this.scoreBest = this.scoreAkhir;
					}
					switch (levelPointer) {
						case MUDAH:
							Settings.Stage.Mudah.bestScoreStage[stagePointer] = scoreBest;
							break;
						case LUMAYAN:
							Settings.Stage.Lumayan.bestScoreStage[stagePointer] = scoreBest;
							break;					
						case SUSAH:
							Settings.Stage.Susah.bestScoreStage[stagePointer] = scoreBest;
							break;				
					}
					if (isCleared) {
						Settings.Achievement.checkAchievement();
						Settings.save(game.getFileIO());
						for (int c = 0; c < Settings.Achievement.TOTAL_ACHIEVEMENT; c++) {
							Log.i("Achievement",String.valueOf(Settings.Achievement.achievementStatusAtIndex[c]));
							if (Settings.Achievement.achievementStatusAtIndex[c] == 
									Settings.Achievement.Status.NEW_ACHIEVEMENT) {							
								this.isNotificationShown = true;
							}
						}
					}
				}
			}
		}		
	}
	public void present(float deltaTime, boolean isInteruppted) {
		Graphics g = game.getGraphics();
		drawParralax(g);			
		int len = jumKapalKecil;
		for (int c = 0; c < len; c++) {
			if (kapalKecil[c].isVisible) {				
				kapalKecil[c].drawSprite(g);
				if (isInteruppted) {
					kapalKecil[c].drawNumObfuscator(g);
				} 
			}
			if (c < len-1) {
				if (!isInteruppted) {
					if (signOperator[c].isVisible) {
						signOperator[c].drawSprite(g);
					}
				}
			}
		}
		if (kapalBesar.isVisible) {
			kapalBesar.drawSprite(g);
		}
		if (tong.isVisible) {
			tong.drawSprite(g);
		}	
		if (signEqual.isVisible) {
			signEqual.drawSprite(g);
		}
		if (penyelam.isVisible) { 
			penyelam.drawSpriteUpdated(g, deltaTime);
		}
		if (penyelam.isShooting) {
			len = this.penyelam.peluru.size();
			for (int c = 0; c< len; c++) {
				Peluru peluru = penyelam.peluru.get(c);
				if (peluru.isVisible) {
					peluru.drawSprite(g);
				}
			}
		}
		int ikanLen = ikanList.size();
		for (int c = 0; c < ikanLen; c++) {
			Ikan ikan = ikanList.get(c);
			if (ikan.isVisible) {
				ikan.drawSprite(g);
			}
		}
	}
	public void resetWorld() {
		this.slotPointer = 0;	
		this.isOnTransition = true;
		this.isCleared = false;
		this.isGameOver = false;
		this.isNotificationShown = false;
		this.starReceived = 0;
		this.scoreAkhir = Settings.EMPTY_INT;			
		int xAwal;
		int len;
		xAwal = 0 - (Assets.Container.tong.getWidth());
		this.tong.setPosition(xAwal, SeaWorld.MOST_TOP);
		xAwal = 0 - Assets.Container.kapalBesar.getWidth();
		this.kapalBesar.setPosition(xAwal, SeaWorld.MOST_TOP);
		this.kapalBesar.setScore(0);
		xAwal = 0 - (Assets.Container.kapalKecil.getWidth());
		len = this.jumKapalKecil;
		for (int c = 0; c < len; c++) {
			this.kapalKecil[c].score = 0;
			this.kapalKecil[c].isFilled = false;
			this.kapalKecil[c].setPosition(xAwal, SeaWorld.MOST_TOP);			
		}
		len = signOperator.length;	
		int increment = 540 / kapalKecil.length;
		int yAwal = 0 - (Assets.InGame.Operator.kali.getHeight());
		int x = xPosKapalKecil[0]+(((xPosKapalKecil[1])-xPosKapalKecil[0]+Assets.Container.kapalKecil.getWidth()) / 2); // disired Pos for operator	
		for (int c = 0; c < len; c++) {
			signOperator[c].setPosition(x, yAwal);						
			x += increment;			
		}
		signEqual.setPosition(x, yAwal);
		penyelam.isVisible = true;
		this.penyelam.setPos((SeaWorld.LEFT-Assets.Penyelam.act1.getWidth()), ((BOTTOM-TOP)/2)+70);
		if (penyelam.peluru.size() != 0) {
			penyelam.peluru.clear();
		}
		ikanList.clear();
		
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
	private void checkPenyelamPos() {	
		if (penyelam.yPos < SeaWorld.TOP) {
			penyelam.setPos(-1,SeaWorld.TOP);
		}
		if (penyelam.yPos+penyelam.heightSize+40 > SeaWorld.BOTTOM) { // 40 = tinggi parralax tanah
			penyelam.setPos(-1, SeaWorld.BOTTOM-(penyelam.heightSize+40));
		}
	}

	private int calculator(Container[] kapalKecil) {
		int len = kapalKecil.length;
		int filled = 0;
		int counter = 0;
		int hasil = 0;
		float[] nilai; 
		float[] tempNilai;		
		String operator = this.operator;
		int oprLen = 0;
		// init array
		for (int c = 0; c < len; c++) {
			if (kapalKecil[c].isFilled) {				
				filled++;
			}
		}
		if (filled == 1) {
			hasil = kapalKecil[0].score;
			return hasil;
		}
		oprLen = filled-1;
		nilai = new float[filled];
		for (int c = 0; c < filled; c++) {
			nilai[c] = kapalKecil[c].score;
		}
		// ends here
		for (int c = 0; c < oprLen; c++) {
			if (c == 0) {
				counter++;
			}
			if (operator.charAt(c) == '+' || operator.charAt(c) == '-') {
				counter++;
			}
		}		
		tempNilai = new float[counter];
		int tempNilaiLen = tempNilai.length;
		counter = 0;
		for (int c= 0; c < tempNilaiLen; c++) {
			if (c == 0) {
				tempNilai[0] = nilai[0];
				continue;
			}
			for(int d = counter; d < oprLen; d++) {				
				if (operator.charAt(d) == '+' ) {						
					tempNilai[c] = nilai[d+1];
					counter = d+1;
					break;
				} 
				if (operator.charAt(d) == '-') {
					tempNilai[c] = -nilai[d+1];
					counter = d+1;
					break;
				}
			}
		}
		/*for (int c= 0; c < tempNilaiLen; c++) {
			//System.out.println("Temp_" + c + " = " + tempNilai[c]);
		}*/
		counter = 0;
		for (int c = 0; c < tempNilaiLen; c++) {
			for (int x = counter; x < oprLen; x++) {
				if (operator.charAt(x) == '+' || operator.charAt(x) == '-') {
					counter = x+1;
					//System.out.println("_(counter++)_" + counter);
					break;
				} else {				
					if (operator.charAt(x) == ':') {
						tempNilai[c] /= nilai[x+1];						
						//System.out.println("_temp_"+c+" /= " + nilai[x+1]);
					} else {
						tempNilai[c] *= nilai[x+1];		
						//System.out.println("_temp_"+c+" /= " + nilai[x+1]);
					}
				}
			}
		}	
		for (int c = 0; c < tempNilaiLen; c++) {
			float desimal = tempNilai[c] - (int)tempNilai[c];
			if (desimal <= 0.40f) {
				//System.out.println(">???? " + desimal);
				tempNilai[c] -= desimal;
			} else  {
				//System.out.println(desimal);
				tempNilai[c] += (1f-desimal);
			}
		}		
		for (int c = 0; c < tempNilaiLen; c++) {
			//System.out.println("Temp_" + c + " = " + tempNilai[c]);
			hasil += tempNilai[c];
		}		
		//System.out.println(hasil);
		return hasil;
	}	
}
