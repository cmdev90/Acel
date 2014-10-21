package com.acel;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.view.MotionEvent;

public class GraphicButton {

	private RectF buttonBounds;
	private Bitmap buttonImg = null;

	public GraphicButton(Bitmap img, RectF bound) {
		buttonBounds = bound;
		buttonImg = img;
	}

	public GraphicButton(Bitmap img, float left, float top, float right,
			float bottom) {
		PhoneScreen phoneScreen = PhoneScreen.getInstance(null);
		RectF s = phoneScreen.getWindow();

		buttonBounds = new RectF((s.left + (left * phoneScreen.width)),
				(s.top + (top * phoneScreen.height)),
				(s.right - (right * phoneScreen.width)),
				(s.top + (bottom * phoneScreen.height)));

		buttonImg = img;
	}

	public void drawButton(Canvas canvas) {
		if (buttonImg != null)
			canvas.drawBitmap(buttonImg, null, buttonBounds, null);
		else {
			Paint paint = new Paint();
			canvas.drawRect(buttonBounds, paint);
		}
	}

	public void update() {
	}

	public boolean touchyMe(MotionEvent event) {
		return buttonBounds.contains(event.getX(), event.getY());
	}

}
