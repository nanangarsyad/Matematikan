package com.arkodestudio.matematikan;

import java.util.ArrayList;
import java.util.List;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Rect;


import com.arkodestudio.framework.Graphics;
import com.arkodestudio.framework.Input.TouchEvent;
import com.arkodestudio.framework.Pixmap;
import com.arkodestudio.framework.impl.AndroidPixmap;

public class Penyelam extends Sprite {
	private final static int X = 0;
	private final static int Y = 1;
	private final static int BULLET_SPEED_PER_SECOND = 550;
	//private final static int MAX_MOVE_SPEED = 50;
	public List<Peluru> peluru;
	private Pixmap[] penyelamAnim = new Pixmap[4];	
	private int currentAnimPointer = 0;
	private float animTime = 0f;
	private int[] requestedPos = new int[2]; // menentukan posisi dimana penyelam akan dipindahkan
	private int touchPointer = -1;
	private boolean isTouched = false; 	
	public boolean isShooting = false;
	
	public Penyelam(int x, int y) {
		super(null,x, y);
		penyelamAnim[0] = (Assets.Penyelam.act1);
		penyelamAnim[1] = (Assets.Penyelam.act2);
		penyelamAnim[2] = (Assets.Penyelam.act3);
		penyelamAnim[3] = (Assets.Penyelam.act2);
		this.setPixmap(penyelamAnim[currentAnimPointer]);		
		requestedPos[X] = xPos+108;
		requestedPos[Y] = yPos+39;
		peluru = new ArrayList<Peluru>();
	}
	public boolean isCollideWith(Sprite sprite) {
		Rect rectA, rectB;
		rectA = new Rect(this.xPos,this.yPos,this.xPos+this.widthSize, this.yPos+this.heightSize);
		rectB = new Rect(sprite.xPos,sprite.yPos,sprite.widthPos, sprite.heightPos);
		if (Rect.intersects(rectA, rectB)) {
			if (Rect.intersects(rectA, rectB)) {			 
				Bitmap bitmapA = ((AndroidPixmap)this.pixmap).getPixmap();
				Bitmap bitmapB = ((AndroidPixmap)sprite.pixmap).getPixmap();			
				int[] dataA = new int[bitmapA.getWidth()*bitmapA.getHeight()];
				int[] dataB = new int[bitmapB.getWidth()*bitmapB.getHeight()];
				bitmapA.getPixels(dataA, 0, bitmapA.getWidth(), 0, 0, bitmapA.getWidth(), bitmapA.getHeight());
				bitmapB.getPixels(dataB, 0, bitmapB.getWidth(), 0, 0, bitmapB.getWidth(), bitmapB.getHeight());			
	            int top = Math.max(rectA.top, rectB.top); 
	            int bottom = Math.min(rectA.bottom, rectB.bottom); 
	            int left = Math.max(rectA.left, rectB.left); 
	            int right = Math.min(rectA.right, rectB.right);	 
	            for (int y = top; y < bottom; y++) { 
	                for (int x = left; x < right; x++) { 
	                    int pixelA = dataA[(x - rectA.left) + (y - rectA.top) * rectA.width()]; 
	                    int pixelB = dataB[(x - rectB.left) + (y - rectB.top) * rectB.width()]; 
	                    if (Color.alpha(pixelA) != Color.TRANSPARENT && 
	                    		Color.alpha(pixelB) != Color.TRANSPARENT) { 
	                        return true; 
	                    } 
	                }
	            } 	            
	        }
			return false; 
		}
		return false;
	}
	public void shoot(Peluru.JenisPeluru jenis) {
		int len = peluru.size();
		int last = len-1;	
		int xShoot = this.xPos + this.widthSize;		
		int yShoot = 0;
		if (len == 0) {			
			xShoot = this.xPos + this.widthSize;		
			yShoot = 0;
			switch (jenis) {
				case JARING:
					yShoot = (this.yPos + 50) - (20); // center posisition of "jaring" bullet
					peluru.add(new Peluru(jenis, xShoot, yShoot));
					break;
				case TOMBAK:
					yShoot = this.yPos + 52 - (5); // center posisition of "tombak" bullet
					peluru.add(new Peluru(jenis, xShoot, yShoot));
					break;
			}
		} else {						
			Peluru lastPeluru = this.peluru.get(last);
			xShoot = this.xPos + this.widthSize;		
			yShoot = 0;
			if (lastPeluru.xPos > (this.xPos+this.widthSize+Peluru.WIDHT + 20)) { // 10 space antar peluru				
				switch (jenis) {
					case JARING:
						yShoot = (this.yPos + 50) - (20); // center posisition of "jaring" bullet
						this.peluru.add(new Peluru(jenis, xShoot, yShoot));
						break;
					case TOMBAK:
						yShoot = this.yPos + 52 - (5); // center posisition of "tombak" bullet
						this.peluru.add(new Peluru(jenis, xShoot, yShoot));
						break;
				}
			} else if (lastPeluru.yPos != this.yPos) {
				int jarakY = Math.abs(lastPeluru.yPos-this.yPos);				
				if (jarakY > Peluru.JARING_HEIGHT) {
					switch (jenis) {
					case JARING:
						yShoot = (this.yPos + 50) - (20); // center posisition of "jaring" bullet
						this.peluru.add(new Peluru(jenis, xShoot, yShoot));
						break;
					case TOMBAK:
						yShoot = this.yPos + 52 - (5); // center posisition of "tombak" bullet
						this.peluru.add(new Peluru(jenis, xShoot, yShoot));
						break;
					}
				} 				
			}
		}	
		if (!this.isShooting) {
			this.isShooting = true;
		}
	}
	
	public void update(float deltaTime) {
		//int centerPosX = (xPos+108), centerPosY = (yPos+39); // coordinate where the "penyelam" gonna be touched
		/*
		 * int xVar = 0, yVar = 0;
		int slowDownX = 0, slowDownY = 0;
		int moveSpeedX = 0, moveSpeedY = 0;
		*/
		animTime += deltaTime;
		if (animTime >= 1.0f) {	
			animTime = 0.0f;
			this.setPixmap(penyelamAnim[currentAnimPointer]);			
			currentAnimPointer++;
			if (currentAnimPointer > 3) {
				currentAnimPointer = 0;
			}			
		}
		// - request Move start here -			
		/*
		if (requestedPos[X] == centerPosX && requestedPos[Y] == centerPosY) {
			if (!isTouched) {
				isTouched = true;
			}
		} else {  
			if (isTouched) {
				isTouched = false;
			}
			// -currenly not used-
			int absX = Math.abs(requestedPos[X] - centerPosX);
			int absY = Math.abs(requestedPos[Y] - centerPosY);						
			if (absX < absY) {				
				moveSpeedX = 16;
				int xSteps = absX / moveSpeedX; // count total step till reach the last coordinat (requested coordinat)
				//System.out.println(absX + " x "+ absY+ " [<absX:absY>;moveSpeedX;xSteps] "+ moveSpeedX + " x " + xSteps);
				if (xSteps == 0) {
					xSteps = 1;
				}
				moveSpeedY = absY/xSteps;
				if (moveSpeedY > MAX_MOVE_SPEED && xSteps < 3) {
					moveSpeedY = MAX_MOVE_SPEED;
				}
			} else if (absX > absY) {
				moveSpeedY = 16;
				int ySteps = absY / moveSpeedY; // count total step till reach the last coordinat (requested coordinat)
				//System.out.println(absX + " x "+ absY+ " [<absX:absY>;moveSpeedY;ySteps] "+moveSpeedY + " x " + ySteps);
				if (ySteps == 0) {
					ySteps = 1;
				}
				moveSpeedX = absX/ySteps ;
				if (moveSpeedX > MAX_MOVE_SPEED && ySteps < 3) {
					moveSpeedX = MAX_MOVE_SPEED;
				}
			} else {
				// ??
				moveSpeedX = 16;
				moveSpeedY = 16;
			}		
			// -Slowing down-	
			if (absX < moveSpeedX) {
				slowDownX = moveSpeedX-1;					
			} else if (absX < (moveSpeedX*2)) {
				slowDownX = moveSpeedX/2;
				animTime -= deltaTime;
			}
			if ( absY < moveSpeedY) {
				slowDownY = moveSpeedY-1;					
			} else if (absY < (moveSpeedY*2)) {
				slowDownY = moveSpeedY/2;
				animTime -= deltaTime;
			}
			// -end here-
			if (requestedPos[X] < centerPosX) {
				xVar = -1;
				xPos += (moveSpeedX - slowDownX) * xVar;	
				animTime += (1.5f*deltaTime);
			} else if (requestedPos[X] > centerPosX) {
				xVar = 1;
				xPos += (moveSpeedX - slowDownX) * xVar;
				animTime += (1.5f*deltaTime);
			} 
			if (requestedPos[Y] < centerPosY) {
				yVar = -1;
				yPos += (moveSpeedY - slowDownY) * yVar;
				animTime += (1.5f*deltaTime);
			} else if (requestedPos[Y] > centerPosY) {
				yVar = 1;
				yPos += (moveSpeedY - slowDownY) * yVar;	
				animTime += (1.5f*deltaTime);
			}
		}
		// - request-move end here -
*/				
	}
	public void updatePeluru(float deltaTime) {
		if (peluru.size() == 0) {
			this.isShooting = false;
		} else {
			this.isShooting = true;
		}
		if (this.isShooting) {
			for(int c = 0; c < peluru.size(); c++) {
				Peluru peluru = this.peluru.get(c);
				if (peluru.isVisible) {
					peluru.move(BULLET_SPEED_PER_SECOND*deltaTime, 0);
					this.peluru.set(c, peluru);
					// do recycle
				} else {
					this.peluru.remove(c);					
				}
				
			}
		}
	}
	@Override
	public void move(int xInc, int yInc) {		
		this.xPos = (xPos + xInc) - 108; // place the touch coordinat at x=108 
		this.yPos = (yPos + yInc) - 39; // place the touch coordinat at y=39
		this.widthPos = this.xPos + this.widthSize;
		this.heightPos = this.yPos + this.heightSize;
		requestedPos[X] = this.xPos + 108;
		requestedPos[Y] = this.yPos + 39;		
	}	
	@Override
	public void drawSpriteUpdated(Graphics g, float deltaTime) {
		animTime += deltaTime;
		if (animTime >= 1.0f) {	
			animTime = 0.0f;
			this.setPixmap(penyelamAnim[currentAnimPointer]);			
			currentAnimPointer++;
			if (currentAnimPointer > 3) {
				currentAnimPointer = 0;
			}			
		}
		drawSprite(g);
	}
	public void setTouchedCond(boolean isTouched,TouchEvent event) {
		this.touchPointer =  event.pointer;
		this.isTouched = isTouched;		
		if (!this.isTouched) {
			this.touchPointer = -1;
		}
		requestedPos[X] = this.xPos+108;
		requestedPos[Y] = this.yPos+39;
	}
	public boolean isTouched(TouchEvent event) {
		if (event.pointer == this.touchPointer && isTouched)  {
			return true;
		}
		return false;
	}
	public void requestMove(int x, int y) {
		requestedPos[X] = x;
		requestedPos[Y] = y;		
 	}
	public void setPos(int x, int y) {
		if (x == -1) {
			yPos = y;
			requestedPos[X] = this.xPos + 108;
			requestedPos[Y] = this.yPos + 39;
			return;
		}
		if (y == -1) {
			xPos = x;
			requestedPos[X] = this.xPos + 108;
			requestedPos[Y] = this.yPos + 39;
			return;
		} 
		if (y != -1 && x != -1) {
			yPos = y;
			xPos = x;
			requestedPos[X] = this.xPos + 108;
			requestedPos[Y] = this.yPos + 39;
			return;
		}
	}
}

class Peluru extends Sprite{	
	public enum JenisPeluru {
		TOMBAK, JARING
	}	
	
	public static int WIDHT = Assets.Penyelam.jaring.getWidth();
	public static int TOMBAK_HEIGHT = Assets.Penyelam.tombak.getHeight();
	public static int JARING_HEIGHT = Assets.Penyelam.jaring.getHeight(); 
	public JenisPeluru jenisPeluru;	
	public Peluru(JenisPeluru jenis, int x, int y) {		
		super(null, x, y);
		switch (jenis) {
			case JARING:
				this.setPixmap(Assets.Penyelam.jaring);
				this.jenisPeluru = jenis;
				break;
			case TOMBAK:
				this.setPixmap(Assets.Penyelam.tombak);
				this.jenisPeluru = jenis;
				break;			
		}
		
	}
	
	public boolean isCollideWith(Sprite sprite) {
		Rect rectA, rectB;
		rectA = new Rect(this.xPos,this.yPos,this.widthPos, this.heightPos);
		rectB = new Rect(sprite.xPos,sprite.yPos,sprite.widthPos, sprite.heightPos);		
		if (Rect.intersects(rectA, rectB)) {			 
			Bitmap bitmapA = ((AndroidPixmap)this.pixmap).getPixmap();
			Bitmap bitmapB = ((AndroidPixmap)sprite.pixmap).getPixmap();			
			int[] dataA = new int[bitmapA.getWidth()*bitmapA.getHeight()];
			int[] dataB = new int[bitmapB.getWidth()*bitmapB.getHeight()];;
			bitmapA.getPixels(dataA, 0, bitmapA.getWidth(), 0, 0, bitmapA.getWidth(), bitmapA.getHeight());
			bitmapB.getPixels(dataB, 0, bitmapB.getWidth(), 0, 0, bitmapB.getWidth(), bitmapB.getHeight());			
            int top = Math.max(rectA.top, rectB.top); 
            int bottom = Math.min(rectA.bottom, rectB.bottom); 
            int left = Math.max(rectA.left, rectB.left); 
            int right = Math.min(rectA.right, rectB.right);	 
            for (int y = top; y < bottom; y++) { 
                for (int x = left; x < right; x++) { 
                    int pixelA = dataA[(x - rectA.left) + (y - rectA.top) * rectA.width()]; 
                    int pixelB = dataB[(x - rectB.left) + (y - rectB.top) * rectB.width()]; 
                    if (Color.alpha(pixelA) != Color.TRANSPARENT && 
                    		Color.alpha(pixelB) != Color.TRANSPARENT) { 
                        return true; 
                    } 
                }
            } 
            return false; 
        } 		
		return false;
	}
	
}