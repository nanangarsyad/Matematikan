package com.arkodestudio.framework.impl;

import java.io.IOException;
import java.io.InputStream;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Rect;
import android.util.Log;

import com.arkodestudio.framework.Graphics;
import com.arkodestudio.framework.Pixmap;

public class AndroidGraphics implements Graphics{
	
	private AssetManager assetManager;
	private Bitmap frameBuffer;
	private Canvas canvas;
	private Paint paint;
	private Rect srcRect = new Rect();
	private Rect dstRect = new Rect();
	
	public AndroidGraphics(AssetManager assetManager, Bitmap frameBuffer) {
		this.assetManager = assetManager;
		this.frameBuffer = frameBuffer;
		this.canvas = new Canvas(frameBuffer);
		this.paint = new Paint();		
	}
	
	@Override
	public Pixmap newPixmap(String fileName, PixmapFormat pixmapFormat) {
		Config config = null;
		if (pixmapFormat == PixmapFormat.RGB565) {
			config = Config.RGB_565;
		} else if (pixmapFormat == PixmapFormat.ARGB4444) {
			config = Config.ARGB_4444;
		} else {
			config = Config.ARGB_8888;
		}
		Options options = new Options();
		options.inPreferredConfig = config;				
		InputStream in = null;
		Bitmap bitmap = null;
		
		try {
			in = assetManager.open(fileName);
			bitmap = BitmapFactory.decodeStream(in,null,options);			
			if (bitmap == null) {
				Log.e("[ERROR]AndroidGraphics.class","Unnable to load image file");
				throw new RuntimeException("Cannot open Bitmap on " + fileName);				
			}			
		} catch (IOException e) {
			Log.e("[ERROR]AndroidGraphics.class:58","Unnable to load image file");
			throw new RuntimeException("Cannot open Bitmap on " + fileName);			
		} finally {
			try{
				in.close();
			} catch (IOException e) {
				Log.e("[ERROR]AndroidGraphics.class","Unnable to close InputStream");
				throw new RuntimeException("Unnable to close InputStream");				
			}
		}
		if (bitmap.getConfig() == Config.ARGB_4444) {
			pixmapFormat = PixmapFormat.ARGB4444;			
		} else if (bitmap.getConfig() == Config.RGB_565) {
			pixmapFormat = PixmapFormat.RGB565;			
		} else {
			pixmapFormat = PixmapFormat.ARGB8888;
		}						
		
		Log.i("::"+fileName, bitmap.getConfig().toString());
		return new AndroidPixmap(bitmap, pixmapFormat);
	}
	
	@Override
	public Pixmap slicePixmap(Pixmap pixmap, int x, int y, int width, int height) {	
		Bitmap newBitmap = ((AndroidPixmap)pixmap).getPixmap();
		int h = newBitmap.getHeight();
		int w = newBitmap.getWidth();
		if (x < 0 || x > w || y < 0 || y > h || 
				width < 0 || width > w || height < 0 || height > h) {
			Log.e("[ERORR]AndroidGrapahics.class::89::slicePixmap()","OUT_OF_BOUNDARY");
			throw new RuntimeException("OUT_OF_BOUNDARY");			
		}
		
		Bitmap bitmap = Bitmap.createBitmap(((AndroidPixmap)pixmap).getPixmap(), x, y, width, height);
		return new AndroidPixmap(bitmap,pixmap.getFormat());
	}
	
	@Override
	public Pixmap slicePixmap(Pixmap pixmap, int[] pos) {
		if (pos.length > 4 || pos.length < 4) {
			Log.e("[ERORR]AndroidGrapahics.class::slicePixmap()","ARRAY_OUT_OF_BOUNDARY");
			throw new RuntimeException("ARRAY_OUT_OF_BOUNDARY");	
		}
		
		final int X1 = 0;
		final int Y1 = 1;
		final int X2 = 2;
		final int Y2 = 3;
		Bitmap newBitmap = ((AndroidPixmap)pixmap).getPixmap();
		int h = newBitmap.getHeight();
		int w = newBitmap.getWidth();
		if (pos[X2] < pos[X1] || pos[Y2] < pos[Y2]) {
			Log.e("[ERORR]AndroidGrapahics.class::slicePixmap()","ERROR_OF_BOUNDARY");
			throw new RuntimeException("ERROR_OF_BOUNDARY");
		}
		if (pos[X1] < 0 || pos[X1] > w || pos[Y1] < 0 || pos[Y1]  > h || 
				pos[X2] < 0 || pos[X2] > w || pos[Y2] < 0 || pos[Y2] > h) {
			Log.e("[ERORR]AndroidGrapahics.class::slicePixmap()","OUT_OF_BOUNDARY");
			throw new RuntimeException("OUT_OF_BOUNDARY");			
		}
		int width = pos[X2] - pos[X1];
		int height = pos[Y2]- pos[Y1];
		Bitmap bitmap = Bitmap.createBitmap(((AndroidPixmap)pixmap).getPixmap(), pos[X1], pos[Y1], width, height);
		return new AndroidPixmap(bitmap,pixmap.getFormat());
	}
	@Override
	public void clearScreen(int color) {
		canvas.drawRGB((color & 0xff0000) >> 16, (color & 0xff00) >> 8,	(color & 0xff));		
	}

	@Override
	public void drawPixel(int x, int y, int color) {
		paint.setColor(color);
		canvas.drawPoint(x, y, paint);
		
	}

	@Override
	public void drawLine(int x, int y, int x2, int y2, int color) {
		paint.setColor(color);
		canvas.drawLine(x, y, x2, y2, paint);
		
	}

	@Override
	public void drawRect(int x, int y, int width, int height, int color) {
		paint.setColor(color);
		paint.setStyle(Style.FILL);
		canvas.drawRect(x, y, x + width - 1, y + height - 1, paint);
		
	}
	@Override
	public void drawRectV2(int x, int y, int width, int height, int color) {
		paint.setColor(color);
		paint.setStyle(Style.FILL);
		canvas.drawRect(x, y, x + width , y + height , paint);
		
	}

	@Override
	public void drawPixmap(Pixmap pixmap, int x, int y, int srcX, int srcY,	int srcWidth, int srcHeight) {
		srcRect.left = srcX;
		srcRect.top = srcY;
		srcRect.right = srcX + srcWidth ;
		srcRect.bottom = srcY + srcHeight;
		dstRect.left = x;
		dstRect.top = y;
		dstRect.right = x + srcWidth;
		dstRect.bottom = y + srcHeight;
		canvas.drawBitmap(((AndroidPixmap)pixmap).getPixmap(), srcRect, dstRect, null);
		
	}

	@Override
	public void drawPixmap(Pixmap pixmap, int x, int y) {
		canvas.drawBitmap(((AndroidPixmap)pixmap).getPixmap(),x, y, null);		
	}
	
	@Override
	public void drawPixmap(Pixmap pixmap, float x, float y) {
		canvas.drawBitmap(((AndroidPixmap)pixmap).getPixmap(), x, y, null);
		
	}	
	@Override
	public void drawPixmap(Pixmap pixmap, int x, int y, Paint paint) {
		canvas.drawBitmap(((AndroidPixmap)pixmap).getPixmap(),x, y, paint);
	}
	@Override
	public void drawPixmap(Pixmap pixmap, float x, float y, Paint paint) {
		canvas.drawBitmap(((AndroidPixmap)pixmap).getPixmap(), x, y, paint);
		
	}

	@Override
	public int getWidth() {
		return frameBuffer.getWidth();
	}

	@Override
	public int getHeight() {		
		return frameBuffer.getHeight();
	}

	@Override
	public void drawARGB(int A, int R, int G, int B) {
		paint.setStyle(Style.FILL);
	    canvas.drawARGB(A, R, G, B);
	}
	
	@Override
	/**	  
	 * @param paint may null
	 */
	public void drawString(String text, float x, float y,Paint paint) {
		if (paint == null) {
			canvas.drawText(text, x, y, this.paint);
		} else {
			canvas.drawText(text, x, y, paint);
		}
	}

}
