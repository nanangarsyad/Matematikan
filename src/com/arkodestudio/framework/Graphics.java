package com.arkodestudio.framework;

import android.graphics.Paint;

public interface Graphics {
    public static enum PixmapFormat {
        ARGB8888, ARGB4444, RGB565
    }

    public Pixmap newPixmap(String fileName, PixmapFormat pixMapFormat);
    
    public Pixmap slicePixmap(Pixmap pixmap, int x, int y, int width, int height);
    
    public Pixmap slicePixmap(Pixmap pixmap, int[] pos);
    
    // Not Used Yet
    // public Pixmap dividePixmap(Pixmap pixmap, int xDivider,int yDivider, int chunkIndex);

    public void clearScreen(int color);
    
    public void drawPixel(int x, int y, int color);

    public void drawLine(int x, int y, int x2, int y2, int color);

    public void drawRect(int x, int y, int width, int height, int color);
    
    void drawRectV2(int x, int y, int width, int height, int color);

    public void drawPixmap(Pixmap pixmap, int x, int y, int srcX, int srcY,
            int srcWidth, int srcHeight);

    public void drawPixmap(Pixmap pixmap, int x, int y);
    
    public void drawPixmap(Pixmap pixmap, int x, int y, Paint paint);
    
    public void drawPixmap(Pixmap pixmap, float x, float y);
    
    public void drawPixmap(Pixmap pixmap, float x, float y, Paint paint);
    
    public void drawString(String text, float x, float y,Paint paint);

    public int getWidth();

    public int getHeight();

    public void drawARGB(int A, int R, int G, int B);

	

}