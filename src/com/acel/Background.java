package com.acel;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

public class Background {
	private Bitmap background;
	private int bgX, bgY;
	private boolean scrollable;
	private PhoneScreen screen;
	
	public Background(Bitmap b, PhoneScreen s){
		this.background = b;
		 bgX = 0;
		 bgY = 0;
		 this.screen = s;
	}
	
	private void offsetBackGround(float x) {
		if(scrollable){
			if (x > 0 && bgX < (background.getWidth() - screen.getWindow().width()))
				bgX++;
			if (x < 0 && bgX > 0)
				bgX--;
		}
	}

	public void renderBG(Canvas canvas) {
		Rect src = new Rect(bgX, bgY, (int) (bgX + screen.getWindow().width()),
				(int) (bgY + background.getHeight()));
		canvas.drawBitmap(background, src, screen.getWindow(), null);
	}
	
	public void renderBG(Canvas canvas, float x) {
		offsetBackGround(x);
		Rect src = new Rect(bgX, bgY, (int) (bgX + screen.getWindow().width()),
				(int) (bgY + background.getHeight()));
		canvas.drawBitmap(background, src, screen.getWindow(), null);
	}

}
