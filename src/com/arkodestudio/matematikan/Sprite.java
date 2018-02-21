package com.arkodestudio.matematikan;

import android.graphics.Paint;

import com.arkodestudio.framework.Graphics;
import com.arkodestudio.framework.Pixmap;

public class Sprite {
	public enum RefferencePoint {
		MID_CENTER, LEFT_UP_CORNER
	}
	public Pixmap pixmap;
	private RefferencePoint refferencePoint = null;
	public int xPos, yPos;
	private float deltaFloatX, deltaFloatY;
	public int widthPos, heightPos;
	public int widthSize, heightSize;
	public boolean isVisible;
	public float xPosFloat, yPosFloat;
	
	public Sprite(Pixmap pixmap, int x, int y) {
		if (pixmap == null) {
			this.pixmap = null; 
			this.xPos = x;
			this.yPos = y;
			this.widthPos = x;
			this.heightPos = y;
		} else {
			this.pixmap = pixmap; 
			this.xPos = x;
			this.yPos = y;
			this.widthPos = x+pixmap.getWidth();
			this.heightPos = y+pixmap.getHeight();
		}
		this.widthSize = widthPos - xPos;		
		this.heightSize = heightPos - yPos; 
		this.isVisible =  true;
		this.xPosFloat = this.xPos;
		this.yPosFloat = this.yPos;
		this.refferencePoint = RefferencePoint.LEFT_UP_CORNER;		
	}	
	public void setPosition(int x, int y) {
		RefferencePoint currReffPoint = this.refferencePoint;
		this.refferencePoint = RefferencePoint.LEFT_UP_CORNER;
		if (pixmap == null) {			 
			this.xPos = x;
			this.yPos = y;
			this.widthPos = x;
			this.heightPos = y;
		} else {			 
			this.xPos = x;
			this.yPos = y;
			this.widthPos = x+pixmap.getWidth();
			this.heightPos = y+pixmap.getHeight();
		}
		this.xPosFloat = this.xPos;
		this.yPosFloat = this.yPos;
		this.widthSize = widthPos - xPos;		
		this.heightSize = heightPos - yPos; 
		this.isVisible =  true;		
		setRefferencePoint(currReffPoint);
	}
	public void setPixmap(Pixmap pixmap) {
		this.pixmap = pixmap;
		this.widthPos = pixmap.getWidth() + this.xPos;
		this.heightPos = pixmap.getHeight() + this.yPos;
		this.widthSize = widthPos - xPos;		
		this.heightSize = heightPos - yPos;
	}
	public void setRefferencePoint(RefferencePoint newReffPoint) {
		if (newReffPoint == RefferencePoint.MID_CENTER && refferencePoint != RefferencePoint.MID_CENTER) {
			if (refferencePoint == RefferencePoint.LEFT_UP_CORNER) {		
				xPos -= (widthSize/2);
				yPos -= (heightSize/2);
				widthPos -= (widthSize/2);
				heightPos -= (heightSize/2);
				refferencePoint = RefferencePoint.MID_CENTER;
			}			
		} else if (newReffPoint == RefferencePoint.LEFT_UP_CORNER && refferencePoint != RefferencePoint.LEFT_UP_CORNER) {
			if (refferencePoint == RefferencePoint.MID_CENTER) {	
				xPos += (widthSize/2);
				yPos += (heightSize/2);
				widthPos += (widthSize/2);
				heightPos += (heightSize/2);
				refferencePoint = RefferencePoint.LEFT_UP_CORNER;
			}
		}
	}
	public void move(int xInc, int yInc) {		
		deltaFloatX = 0;
		deltaFloatY = 0;
		this.xPosFloat = this.xPos;
		this.yPosFloat = this.yPos;
		this.xPos += xInc;
		this.yPos += yInc;	
		this.widthPos = xPos + this.widthSize;
		this.heightPos = yPos + this.heightSize;
	}
	public void move(float xInc, float yInc) {		
		deltaFloatX = xInc - ((int)xInc);
		deltaFloatY = yInc - ((int)yInc);
		this.xPosFloat = this.xPos;
		this.yPosFloat = this.yPos;
		this.xPos += xInc;
		this.yPos += yInc;	
		this.widthPos = xPos + this.widthSize;
		this.heightPos = yPos + this.heightSize;
	}
	
	public void drawSprite(Graphics g) {		
		if (xPos == 0) {
			g.drawPixmap(pixmap, xPos, yPosFloat);
			return;
		} else if (yPos == 0) {
			g.drawPixmap(pixmap, xPosFloat, yPos);
			return;
		} else {
			g.drawPixmap(pixmap, xPos+deltaFloatX, yPos+deltaFloatY);
			return;
		}
	}
	public void drawSprite(Graphics g, Paint paint) {
		g.drawPixmap(pixmap, xPos+deltaFloatX, yPos+deltaFloatY,paint);		
	}
	public void drawSpriteUpdated(Graphics g, float deltaTime) {
		g.drawPixmap(pixmap, xPos, yPos);
	}
}
