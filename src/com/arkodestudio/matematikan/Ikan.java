package com.arkodestudio.matematikan;

import java.util.ArrayList;
import java.util.List;
import com.arkodestudio.framework.Graphics;
import com.arkodestudio.framework.Pixmap;

public class Ikan extends Sprite {
	public enum JenisIkan {
		MERAH, HIJAU, KUNING, UNGU
	}
	public static int WIDTH = Assets.Ikan.hijauAction1.getWidth();
	public static int HEIGHT = Assets.Ikan.hijauAction1.getHeight();	
	private static final int MOVE_SPEED_PER_SECOND = 90; 
	private static final int X = 0;
	private static final int Y = 1;
	private static final int FONT_WIDTH = 14;	
	public JenisIkan jenisIkan;
	public int fishNum;	
	private int currentAnimPointer = 0;
	private float animTime = 0f;	
	private float currentTick = 0f;
	private Pixmap efxMin10Pixmap = Assets.Ikan.Effect.min10;
	private List<int[]> efxMin10PosList = new ArrayList<int[]>();
	private Pixmap[] ikanAnim = new Pixmap[4];	
	private int[] pixCenter = new int[2];
	private int[] fishNumCenter = new int[2];
	private int fishNumWidth = 0;
	private int fishNumHeight = 24;
	private boolean checkerBoolean = true;
	private boolean isDrawDecrement = false;
	
	
	public Ikan(JenisIkan jenisIkan, int x, int y, int fishNum) {
		super(null,x,y);
		this.jenisIkan = jenisIkan;
		this.fishNum = fishNum; 		
		int fishNumLen = 0;
		switch (jenisIkan) {
			case HIJAU:
				ikanAnim[0] = Assets.Ikan.hijauAction1;
				ikanAnim[1] = Assets.Ikan.hijauAction2;
				ikanAnim[2] = Assets.Ikan.hijauAction3;
				ikanAnim[3] = Assets.Ikan.hijauAction2;
				break;
			case KUNING:
				ikanAnim[0] = Assets.Ikan.kuningAction1;
				ikanAnim[1] = Assets.Ikan.kuningAction2;
				ikanAnim[2] = Assets.Ikan.kuningAction3;
				ikanAnim[3] = Assets.Ikan.kuningAction2;
				break;
			case MERAH:
				ikanAnim[0] = Assets.Ikan.merahAction1;
				ikanAnim[1] = Assets.Ikan.merahAction2;
				ikanAnim[2] = Assets.Ikan.merahAction3;
				ikanAnim[3] = Assets.Ikan.merahAction2;
				break;
			case UNGU:
				ikanAnim[0] = Assets.Ikan.unguAction1;
				ikanAnim[1] = Assets.Ikan.unguAction2;
				ikanAnim[2] = Assets.Ikan.unguAction3;
				ikanAnim[3] = Assets.Ikan.unguAction2;
				break;		
		}
		this.setPixmap(ikanAnim[currentAnimPointer]);
		pixCenter[X] = 60; // preffered pos to place number
		pixCenter[Y] = 32; // preffered pos to place number		
		this.widthPos = this.xPos +Ikan.WIDTH;
		this.heightPos = this.yPos +  Ikan.HEIGHT;
		
		while (true) {
			fishNum /= 10;
			fishNumLen++;
			if (fishNum < 1) {
				break;
			}
		}
		fishNumWidth = Ikan.FONT_WIDTH * fishNumLen;
		fishNumCenter[X] = fishNumWidth/2;
		fishNumCenter[Y] = fishNumHeight/2;
	}
	
    private void drawText(Graphics g, String line, int x, int y) {   
    	int srcX, srcY = 0, srcWidth, srcHeight = 24;    	
    	int len = line.length();        
    	for (int i = 0; i < len; i++) {            
    		char character = line.charAt(i);    		
    		srcX = 0;            
    		srcWidth = Ikan.FONT_WIDTH;            
    		srcX = (character - '0') * Ikan.FONT_WIDTH;    		   		
    		g.drawPixmap(Assets.Font.ikan, x, y, srcX, srcY, srcWidth, srcHeight);            
    		x += srcWidth;        
    	}    
    }
	public void update(float deltaTime) {
		float xMove = -(MOVE_SPEED_PER_SECOND * deltaTime);
		int yMove = 0;
		if (currentTick >= 0.15f) {
			currentTick = 0.0f;
		}
		if (animTime >= 1.7f) {
			if (currentTick >= 0.05f) {
				currentAnimPointer++;
				currentTick = 0.0f;
			}
			if (currentAnimPointer > 3) {
				currentAnimPointer = 0;
				animTime = 0.0f;
				currentTick = 0.0f;
			}
			this.setPixmap(ikanAnim[currentAnimPointer]);
			
		}	
		animTime += deltaTime;
		currentTick += deltaTime;
		if (xPos > 0) {
			move(xMove,yMove);
		} else {
			xMove = -(MOVE_SPEED_PER_SECOND *1.4f *deltaTime);
			move(xMove,yMove);
			
		}		
		
		if (efxMin10PosList.size() == 0) {
			isDrawDecrement = false;
		} else {
			for (int c = 0; c < efxMin10PosList.size(); c++) {
				int[] min10Pos = efxMin10PosList.get(c);
				min10Pos[Y] += (-10)*deltaTime;
				if (min10Pos[Y] <= this.yPos-5) { 
					efxMin10PosList.remove(c);
				} else {
					efxMin10PosList.set(c, min10Pos);
				}
			}
		}	
	}
	@Override
	public void drawSprite(Graphics g) {
		int x, y;
		x = this.xPos+(this.pixCenter[X])-(fishNumCenter[X]);
		y = this.yPos+(this.pixCenter[Y])-(fishNumCenter[Y]);
		super.drawSprite(g);
		if (fishNum < 10 && checkerBoolean) {
			checkerBoolean = false;
			fishNumberingTool(this.fishNum);
		}
		drawText(g, String.valueOf(fishNum), x, y);
		if (isDrawDecrement) {
			int len = efxMin10PosList.size();
			for (int c = 0; c < len; c++) {
				int[] min10Pos = efxMin10PosList.get(c);				
				g.drawPixmap(efxMin10Pixmap, min10Pos[X], min10Pos[Y]);
			}
		}
		
	}
	public void fishNumDecrement(int decrement) {
		int[] min10Pos = new int[2];
		min10Pos[X] = this.xPos+(this.pixCenter[X])-(fishNumCenter[X]);
		min10Pos[Y] = this.yPos+(this.pixCenter[Y])-(fishNumCenter[Y]);
		isDrawDecrement = true;
		efxMin10PosList.add(min10Pos);		
		this.fishNum -= decrement;	
	}

	private void fishNumberingTool(int fishNum) {
		int fishNumLen = 0;
		while (true) {
			fishNum /= 10;
			fishNumLen++;
			if (fishNum < 1) {
				break;
			}
		}
		fishNumWidth = Ikan.FONT_WIDTH * fishNumLen;
		fishNumCenter[X] = fishNumWidth/2;
		fishNumCenter[Y] = fishNumHeight/2;
	}
}
