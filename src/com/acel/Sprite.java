package com.acel;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.RectF;

public class Sprite {

	public Bitmap image;
	protected float x, y, width, height;
	protected PhoneScreen p;
	protected Rect src;
	protected Rect dst;

	public Sprite(Bitmap img, float x, float y, float width, float height,
			PhoneScreen p) {
		this.image = img;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.p = p;
		this.dst = new Rect((int) (x * p.width), (int) (y * p.height),
				(int) ((x + width) * p.width), (int) ((y + height) * p.height));
		this.src = null;
	}

	public void draw(Canvas canvas) {
		// TODO Auto-generated method stub
		canvas.drawBitmap(image, src, this.getDst(), null);

	}

	public void update(float x, float y) {
		// TODO Auto-generated method stub
		this.x = x;
		this.y = y;
	}

	public RectF getDst() {
		return new RectF(x, y , x + (width * p.width), y
				+ (height * p.height));
	}

}
