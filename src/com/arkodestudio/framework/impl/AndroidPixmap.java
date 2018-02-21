package com.arkodestudio.framework.impl;

import android.graphics.Bitmap;

import com.arkodestudio.framework.Graphics.PixmapFormat;
import com.arkodestudio.framework.Pixmap;

public class AndroidPixmap implements Pixmap {
	private Bitmap bitmap;
	private PixmapFormat pixmapFormat;
	
	public AndroidPixmap(Bitmap bitmap, PixmapFormat pixmapFormat) {
		this.bitmap = bitmap;
		this.pixmapFormat = pixmapFormat;
	}
	
	final public Bitmap getPixmap() {
		return bitmap;
	}
		
	@Override
	public int getWidth() {
		return bitmap.getWidth();
	}

	@Override
	public int getHeight() {
		return bitmap.getHeight();
	}

	@Override
	public PixmapFormat getFormat() {
		return this.pixmapFormat;
	}

	@Override
	public void dispose() {
		bitmap.recycle();		
	}

}
