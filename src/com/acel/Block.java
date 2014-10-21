package com.acel;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.RectF;

public class Block {

	private RectF rect;
	float x, y, w, h;
	private int c;
	private float dy;
	private Bitmap img;
	private float widthAdj, heightAdj;
	private boolean safe;

	public Block(float x, float y, float w, float h, int c, Bitmap b,
			float widthAdj, float heightAdj, boolean safe, float speed) {
		this.w = w*widthAdj;
		this.h = h*heightAdj;
		this.x = x;
		this.y = y;
		this.c = c;
		rect = new RectF(x, y, x + w, y + h);
		this.img = b;
		this.widthAdj = widthAdj;
		this.heightAdj = heightAdj;
		this.safe = safe;
		this.dy = speed;
	}

	public void drawBlock(Canvas canvas) {
		Paint paint = new Paint();
		paint.setStrokeWidth(1);
		paint.setStyle(Style.STROKE);
		paint.setColor(c);
		if (img != null)
			canvas.drawBitmap(img, null, rect, paint);
		else
			canvas.drawRect(getCollisionRect(), paint);
	}

	public void changeSpeed(float speed) {
		dy = speed;
	}

	public void update() {
		y = y - (dy*this.heightAdj);
		rect = new RectF(x, y, x + w, y + h);
	}

	public RectF getCollisionRect() {
		return new RectF(x + (2 * widthAdj), y + (1 * heightAdj), x + w
				- (2 * widthAdj), y + h);
	}

	public RectF getRec() {
		return rect;
	}

	public boolean hasCollided(RectF r) {
		return RectF.intersects(rect, r);
	}

	public boolean isSafe() {
		return safe;
	}
}