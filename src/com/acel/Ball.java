package com.acel;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;

public class Ball {

	private Bitmap ballImg;
	private float xPos, yPos;
	private double score;
	private float dx;
	private RectF screen, rect;
	private int lives;
	private boolean isAlive, falling;
	private float widthAdj, heightAdj;

	private boolean isFalling() {
		return falling;
	}

	public void setFalling(boolean falling) {
		System.out.println("tried setting falling: "+falling);
		this.falling = falling;
	}

	private Ball(Bitmap img, RectF r, float widthAdj, float heightAdj) {
		this.ballImg = img;
		this.screen = r;
		this.xPos = (screen.right / 2);
		this.yPos = (screen.top);
		this.score = 0;
		this.lives = 3;
		this.isAlive = false;
		this.heightAdj = heightAdj;
		this.widthAdj = widthAdj;
	}
	
	private static Ball b = null;
	
	public static Ball getInstance(Bitmap img, RectF r, float widthAdj, float heightAdj){
		if(b == null)
			b = new Ball(img, r, widthAdj, heightAdj);
		return b;
	}
	
	public static Ball getInstance(){
		return b;
	}

	public void ballMotion(float x) {
		this.dx = (float) (x*(this.widthAdj*0.98));
	}

	public int getLives() {
		return this.lives;
	}

	public void loseLife() {
		lives--;
	}

	public void gainLives() {
		lives++;
	}

	public int getScore() {
		return (int) this.score;
	}

	public void drawBall(Canvas canvas) {
		Paint p = new Paint();
		p.setColor(Color.YELLOW);
		canvas.drawBitmap(ballImg, null, this.getRect(), null);
	}

	public void update() {
		this.xPos = (this.xPos - dx);
		this.yPos = this.yPos + (1*this.heightAdj);
		this.rect = this.getRect();

		if (rect.left < screen.left) {
			this.xPos = screen.left;
		}

		if (rect.bottom < screen.top) {
			this.restBall();
			this.loseLife();
			this.setAlive(false);
		}

		if (rect.right > screen.right) {
			this.xPos = (screen.right - rect.width());
		}

		if (rect.bottom > screen.bottom) {
			this.restBall();
			this.loseLife();
			this.setAlive(false);
		}

		if (this.isFalling()) {
			score = score + 1;
		}
		System.out.println("Falling :" + falling);
	}

	public void restBall() {
		this.xPos = (screen.right / 2);
		this.yPos = (screen.top);
	}

	public RectF getRect() {
		return new RectF(this.xPos, this.yPos,
				this.xPos + (4 * this.heightAdj), this.yPos
						+ (4 * this.heightAdj));
	}

	public void setBallPosition(float x, float y) {
		this.xPos = x;
		this.yPos = y;
	}

	public boolean hasCollided(RectF r) {
		boolean collide = false;
		while (RectF.intersects(getRect(), r)) {
			this.yPos = this.yPos - 1;
			collide = true;
		}
		return collide;
	}

	public boolean isAlive() {
		return isAlive;
	}

	public void setAlive(boolean isAlive) {
		this.isAlive = isAlive;
	}
	
	public void reset(){
		this.xPos = (screen.right / 2);
		this.yPos = (screen.top);
		this.score = 0;
		this.lives = 3;
		this.isAlive = false;
	}

}