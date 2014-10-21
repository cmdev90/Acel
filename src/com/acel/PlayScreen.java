package com.acel;

import java.util.ArrayList;
import java.util.Random;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.Paint.Align;
import android.view.MotionEvent;

import com.acel.test.R;

public class PlayScreen extends Screen {

	private static PlayScreen ps = null;

	private ArrayList<Block> blockLst = new ArrayList<Block>();
	private Ball ball;
	private Bitmap b1 = null, b2 = null;
	private float gameSpeed;
	private boolean started, ended, paused;
	private Background background;

	private PlayScreen(Activity activity) {
		super(activity);
		loadContent();
	}

	public static PlayScreen getInstance(Activity activity) {
		if (ps == null)
			ps = new PlayScreen(activity);
		return ps;
	}

	@Override
	public void loadContent() {
		ball = Ball.getInstance(BitmapFactory.decodeResource(
				context.getResources(), R.drawable.ball), phoneScreen
				.getWindow(), phoneScreen.width, phoneScreen.height);
		b1 = BitmapFactory.decodeResource(context.getResources(),
				R.drawable.safe);
		b2 = BitmapFactory.decodeResource(context.getResources(),
				R.drawable.unsafe);
		background = new Background(BitmapFactory.decodeResource(
				context.getResources(), R.drawable.ng), phoneScreen);
		started = false;
		ended = false;
		paused = false;
		gameSpeed = 0.2f;
		startGame();
	}

	private void startGame() {
		int r = (int) ((new Random())
				.nextInt((int) (phoneScreen.getWindow().right - (35 * phoneScreen.width))) + phoneScreen
				.getWindow().left);
		blockLst.add(new Block(r, phoneScreen.getHeight(), 40, 7, Color.GREEN,
				b1, phoneScreen.width, phoneScreen.height, true, gameSpeed));
		started = true;

		phoneScreen.startTiming();
		ball.setAlive(true);
	}

	public void ready() {
		blockLst.clear();
		this.started = false;
		this.ended = false;
		this.gameSpeed = 0.2f;
		ball.reset();
		this.startGame();
	}

	@Override
	public void draw(Canvas canvas) {
		background.renderBG(canvas);

		Paint p = new Paint();
		p.setColor(Color.BLACK);
		p.setTextAlign(Align.CENTER);
		p.setTextSize(5 * phoneScreen.height);

		if (started && !ended) {
			if (ball.isAlive())
				ball.drawBall(canvas);

			for (int i = 0; i < blockLst.size(); i++)
				blockLst.get(i).drawBlock(canvas);

			if (!ball.isAlive())
				canvas.drawText("Touch to Countinue",
						phoneScreen.getWidth() / 2, 50 * phoneScreen.height, p);

			if (paused)
				canvas.drawText("Touch to Resume", phoneScreen.getWidth() / 2,
						50 * phoneScreen.height, p);

			p.setColor(Color.BLACK);
			p.setTextAlign(Align.LEFT);
			p.setTypeface(Typeface.DEFAULT_BOLD);
			p.setTextSize(4 * phoneScreen.height);
			canvas.drawText("Lives: " + ball.getLives(), 4 * phoneScreen.width,
					6 * phoneScreen.height, p);
			canvas.drawText("Score: " + ball.getScore(),
					40 * phoneScreen.width, 6 * phoneScreen.height, p);
			p.setColor(Color.RED);
			canvas.drawRect(0, 0, (phoneScreen.getGameTime())
					* phoneScreen.width, 3 * phoneScreen.height, p);
		}
	}

	@Override
	public void update() {
		if (started && !paused) {
			// check is game over
			if (ball.getLives() == 0) {
				this.ended = true;
				HighScoreScreen.getInstance(null).setCheck(true);
				phoneScreen.setActiveScreen(ScreenID.HIGHSCORESCREEN);
			}

			if (!ended) {
				// add new block to screen
				if (blockLst.get(blockLst.size() - 1).y < 80 * phoneScreen.height) {
					int r = (int) ((new Random()).nextInt((int) (phoneScreen
							.getWindow().right - (40 * phoneScreen.width))) + phoneScreen
							.getWindow().left);

					int t = (new Random()).nextInt(10);

					if (t < 8) {
						blockLst.add(new Block(r, (phoneScreen.getHeight()),
								40, 7, Color.GREEN, b1, phoneScreen.width,
								phoneScreen.height, true, gameSpeed));
					} else {
						blockLst.add(new Block(r, (phoneScreen.getHeight()),
								40, 10, Color.RED, b2, phoneScreen.width,
								phoneScreen.height, false, gameSpeed));
					}
				}

				// draw the ball
				if (ball.isAlive())
					ball.update();
				else
					phoneScreen.startTiming(); // ball is dead so keep resetting
												// the timer till it lives again

				// check for collisions and clean block list
				boolean collide = false;

				for (int i = 0; i < blockLst.size(); i++) {
					if (blockLst.get(i).getCollisionRect().bottom < phoneScreen
							.getWindow().top) {
						blockLst.remove(i);
					} else {
						Block block = blockLst.get(i);
						block.update();
						if (!collide && ball.isAlive()) {
							if (ball.hasCollided(block.getCollisionRect())) {
								if (!block.isSafe()) {
									ball.loseLife();
									ball.setAlive(false);
									ball.restBall();
								}
								collide = true;
								ball.setFalling(false);
							} else {
								ball.setFalling(true);
							}
						}
					}
				}
			}

			if (phoneScreen.getGameTime() > 25) {
				gameSpeed = gameSpeed + 0.1f;
				for (int i = 0; i < blockLst.size(); i++)
					blockLst.get(i).changeSpeed(gameSpeed);
				phoneScreen.startTiming();
			}
		}
	}

	@Override
	public boolean processTouchInput(MotionEvent event) {
		if (!ball.isAlive()) {
			this.ball.setBallPosition(event.getX(), event.getY());
			ball.setAlive(true);
		}

		if (paused)
			paused = false;
		return false;
	}

	@Override
	public boolean processMotionInput(float x, float y) {
		if (ball != null) {
			ball.ballMotion(x);
		}
		return false;
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		paused = true;
	}

}
