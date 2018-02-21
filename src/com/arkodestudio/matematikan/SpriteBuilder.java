package com.arkodestudio.matematikan;

import java.util.ArrayList;
import java.util.List;
import android.util.Log;

import com.arkodestudio.framework.Graphics;
import com.arkodestudio.framework.Pixmap;


public class SpriteBuilder {
	public enum PrefferedPos {
		RIGHT, BOTTOM, OVERLAP_MID_CENTER
	}
	private List<Sprite> sprites;
	public int widthPos, heightPos;
	public int xPos, yPos;
	public int widthSize, heightSize;
	public boolean isVisible;
	
	public SpriteBuilder(int x, int y){
		sprites = new ArrayList<Sprite>();
		this.xPos = x;
		this.yPos = y;
		this.widthPos = xPos;
		this.heightPos = yPos;
		this.isVisible =true;
	}
	public SpriteBuilder() {
		sprites = new ArrayList<Sprite>();
		this.xPos = 0;
		this.yPos = 0;
		this.widthPos = xPos;
		this.heightPos = yPos;
		this.isVisible =true;
	}
	public void setPos(int x, int y) {
		this.xPos = x;
		this.yPos = y;
		this.widthPos = xPos;
		this.heightPos = yPos;
	}
	
	public void addSprite(Sprite sprite, PrefferedPos prefferedPos) {
		int len = sprites.size();
		if (len == 0) {
			if (prefferedPos == null || prefferedPos == PrefferedPos.RIGHT || 
					prefferedPos == PrefferedPos.BOTTOM || prefferedPos == PrefferedPos.OVERLAP_MID_CENTER) {
				this.xPos = sprite.xPos;
				this.yPos = sprite.yPos;
				this.widthSize = sprite.widthPos - sprite.xPos;
				this.heightSize = sprite.heightPos - sprite.yPos;
			}
		} else  {
			if (prefferedPos == PrefferedPos.RIGHT) {
				sprite.xPos = sprites.get(len-1).widthPos;
				sprite.yPos = sprites.get(len-1).yPos;
				sprite.widthPos += sprite.xPos;
				sprite.heightPos += sprite.yPos;
				this.widthSize += sprite.widthPos - sprite.xPos; // there's a trouble here if the sprite got moved
				if (this.heightPos < sprite.heightPos) {
					this.heightSize = sprite.heightPos - this.yPos;
				} 
			} else if (prefferedPos == PrefferedPos.BOTTOM) {
				sprite.xPos = sprites.get(len-1).heightPos;
				sprite.yPos = sprites.get(len-1).xPos;
				sprite.widthPos += sprite.xPos;
				sprite.heightPos += sprite.yPos;
				this.heightSize += sprite.heightPos - sprite.yPos;
				if (this.widthPos < sprite.widthPos) {
					this.widthSize = sprite.widthPos - this.xPos;
				} 
			} else if (prefferedPos == PrefferedPos.OVERLAP_MID_CENTER) {
				int centerX, centerY;
				centerX = sprite.widthSize/2;
				centerY = sprite.heightSize/2;
				sprite.xPos = sprites.get(len-1).widthSize/2-centerX;
				sprite.yPos = sprites.get(len-1).heightSize/2-centerY;
				sprite.widthPos += sprite.xPos;
				sprite.heightPos += sprite.yPos;
				// no completed yet (if the new pixmap bigger than the lastest one, than it goes error)
			} else {
				Log.e("[E]SpriteBuilder.class::addSprite()::","argument out of PrefferedPos enum");
				throw new RuntimeException("POSITION_OUT_OF_CHOISE");				
			}
		}
		if (sprite.widthPos > this.widthPos) {
			this.widthPos = sprite.widthPos;
		}
		if (sprite.heightPos > this.heightPos) {
			this.heightPos = sprite.heightPos;
		}
		sprites.add(sprite);
	}
	public void addPixmap(Pixmap pixmap, PrefferedPos prefferedPos) {
		int len = sprites.size();
		int xPos = 0, yPos = 0;
		int widthPos; 
		int heightPos;
		Sprite newSprite;
		if (len == 0) {
			if (prefferedPos == null || prefferedPos == PrefferedPos.RIGHT || 
					prefferedPos == PrefferedPos.BOTTOM) {
				xPos = this.xPos;
				yPos = this.yPos;
				widthPos = pixmap.getWidth() + xPos;
				heightPos = pixmap.getHeight() + yPos;
				this.widthSize = widthPos - xPos;
				this.heightSize = heightPos - yPos;
			}
		} else  {
			if (prefferedPos == PrefferedPos.RIGHT) {
				xPos = sprites.get(len-1).widthPos;
				yPos = sprites.get(len-1).yPos;		
				widthPos = pixmap.getWidth() + xPos;
				heightPos = pixmap.getHeight() + yPos;
				this.widthSize += widthPos - xPos;
				if (this.heightPos < heightPos) {
					this.heightSize = heightPos - this.yPos;
				} 
			} else if (prefferedPos == PrefferedPos.BOTTOM) {
				xPos = sprites.get(len-1).xPos;
				yPos = sprites.get(len-1).heightPos;
				widthPos = pixmap.getWidth() + xPos;
				heightPos = pixmap.getHeight() + yPos;
				this.heightSize += heightPos - yPos;
				if (this.widthPos < widthPos) {
					this.widthSize = widthPos - this.xPos;
				}
			} else if (prefferedPos == PrefferedPos.OVERLAP_MID_CENTER) {
				Sprite spriteBefore = sprites.get(len-1);
				int centerX = pixmap.getWidth()/2;
				int centerY = pixmap.getHeight()/2;
				xPos = spriteBefore.xPos+((spriteBefore.widthSize/2)-centerX);
				yPos = spriteBefore.yPos+((spriteBefore.heightSize/2)-centerY);
				// no completed yet (if the new pixmap bigger than the lastest one, than it goes error)
			} else {
				Log.e("[E]SpriteBuilder.class::addSprite()::","argument out of PrefferedPos enum");
				throw new RuntimeException("POSITION_OUT_OF_CHOISE");				
			}
		}
		newSprite = new Sprite(pixmap,xPos, yPos);
		if (newSprite.widthPos > this.widthPos) {
			this.widthPos = newSprite.widthPos;
		}
		if (newSprite.heightPos > this.heightPos) {
			this.heightPos = newSprite.heightPos;
		}		
		sprites.add(newSprite);
	}
	public void setPixmap(int location, Pixmap newPixmap) {
		sprites.get(location).setPixmap(newPixmap);
	}
	public void drawSprite(Graphics g) {
		int len = sprites.size();
		for (int c = 0; c < len; c++) {
			Sprite sprite = sprites.get(c);
			sprite.drawSprite(g);
		}
	}
	public void move(int xInc, int yInc) {		
		int len = sprites.size();		
		for (int c = 0; c < len; c++) {
			Sprite sprite = sprites.get(c);
			sprite.move(xInc, yInc);
		}
		this.xPos = sprites.get(0).xPos;
		this.yPos = sprites.get(0).yPos;
		this.widthPos = this.xPos + this.widthSize;
		this.heightPos = this.yPos + this.heightSize;
	}
	
}
