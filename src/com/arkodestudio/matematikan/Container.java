package com.arkodestudio.matematikan;

import com.arkodestudio.framework.Graphics;
import com.arkodestudio.framework.Pixmap;
import com.arkodestudio.framework.impl.AndroidPixmap;

public class Container extends Sprite{
	public enum JenisContainer {
		KAPAL_BESAR, KAPAL_KECIL, TONG
	}
	
	private final static int X = 0;
	private final static int Y = 1;
	private final static int FONT_WIDTH = 18;	
	private int[] pixCenter = new int[2];
	private Pixmap pixmap;	
	private int[] scoreCenter = new int[2];
	private int scoreWidth = 0;
	private int scoreHeight = 25;
	private int kapalKecilColor;
	private boolean isKapalKecil;	
	public boolean isFilled;	
	public int score;	
		
	public Container(JenisContainer jenis, int x, int y, int score) {
		super(null, x, y);
		this.score = score;
		int scoreLength = 0;
		switch (jenis) {
			case KAPAL_BESAR:
				pixmap = Assets.Container.kapalBesar;	
				isFilled = true;
				break;
			case KAPAL_KECIL:
				pixmap = Assets.Container.kapalKecil;
				this.isKapalKecil = true;				
				isFilled = false;
				break;
			case TONG:
				pixmap = Assets.Container.tong;
				isFilled = true;
				break;		
		}
		if (isKapalKecil) {
			pixCenter[X] = pixmap.getWidth()/2;
			pixCenter[Y] = 35;
		} else {
			pixCenter[X] = pixmap.getWidth()/2;
			pixCenter[Y] = pixmap.getHeight()/2;
		}	
		if (this.isKapalKecil) {
			this.kapalKecilColor = ((AndroidPixmap)pixmap).getPixmap().getPixel(pixCenter[X], pixCenter[Y]);
		}
		this.widthPos = this.xPos + pixmap.getWidth();
		this.heightPos = this.yPos + pixmap.getHeight();
		if (score < 0) {
			scoreLength = 1;
			score *= -1;
		}
		while (true) {
			score /= 10;
			scoreLength++;
			if (score < 1) {
				break;
			}
		}
		scoreWidth = Container.FONT_WIDTH * scoreLength;
		scoreCenter[X] = scoreWidth/2;
		scoreCenter[Y] = scoreHeight/2;
	}

	public void setScore(int score) {
		isFilled = true;
		this.score = score;
		scoringTool(this.score);
		
	}	
	@Override
	public void drawSprite(Graphics g) {
		int x, y;
		x = this.xPos+(this.pixCenter[X])-(scoreCenter[X]);
		y = this.yPos+(this.pixCenter[Y])-(scoreCenter[Y]);
		g.drawPixmap(pixmap, this.xPos, this.yPos);
		if (this.isFilled) {
			drawText(g, String.valueOf(this.score), x, y);
		}
	}
	
	public void drawNumObfuscator(Graphics g) {
		int x, y;
		x = this.xPos+(this.pixCenter[X])-(scoreCenter[X]);
		y = this.yPos+(this.pixCenter[Y])-(scoreCenter[Y]);
		g.drawRectV2(x, y, scoreWidth, scoreHeight, this.kapalKecilColor);
	}
	private void drawText(Graphics g, String line, int x, int y) {   
	  	int srcX, srcY = 0, srcWidth, srcHeight = 25;    	
	   	int len = line.length();        
	   	for (int i = 0; i < len; i++) {            
	   		char character = line.charAt(i);
	   		srcX = 0;
	   		if (character == '-') {
	   			srcX = 180; // minus sign coordinat
	   		} else {
	   			srcX = (character - '0') * 18;
	   		}	   		            
	   		srcWidth = 18;            	   		    		   		
	   		g.drawPixmap(Assets.Font.container, x, y, srcX, srcY, srcWidth, srcHeight);            
	   		x += srcWidth;        
	   	}    
	}
	private void scoringTool(int score) {
		int scoreLength = 0;
		if (score < 0) {
			scoreLength = 1;
			score *= -1;
		}
		while (true) {
			score /= 10;
			scoreLength++;
			if (score < 1) {
				break;
			}
		}
		scoreWidth = Container.FONT_WIDTH * scoreLength;
		scoreCenter[X] = scoreWidth/2;
		scoreCenter[Y] = scoreHeight/2;
	}

}
