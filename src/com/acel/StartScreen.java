package com.acel;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.RectF;
import android.view.MotionEvent;

import com.acel.test.R;

public class StartScreen extends Screen {

	private static StartScreen ss = null;

	private Bitmap start, highScore, exit;
	private GraphicButton startbtn, highBtn, exitBtn;
	private Background background;

	private StartScreen(Activity activity) {
		super(activity);
		loadContent();
	}

	public static StartScreen getInstance(Activity activity) {
		if (ss == null)
			ss = new StartScreen(activity);
		return ss;
	}

	@Override
	public void loadContent() {
		background = new Background(BitmapFactory.decodeResource(
				context.getResources(), R.drawable.clouds), phoneScreen);

		start = BitmapFactory.decodeResource(context.getResources(),
				R.drawable.start);
		highScore = BitmapFactory.decodeResource(context.getResources(),
				R.drawable.highscores);
		exit = BitmapFactory.decodeResource(context.getResources(),
				R.drawable.exit);

		RectF s = this.phoneScreen.getWindow();

		startbtn = new GraphicButton(start, new RectF(
				(s.left + (10 * phoneScreen.width)),
				(s.top + (10 * phoneScreen.height)),
				(s.right - (10 * phoneScreen.width)),
				(s.top + (40 * phoneScreen.height))));
		highBtn = new GraphicButton(highScore, new RectF(
				(s.left + (10 * phoneScreen.width)),
				(s.top + (40 * phoneScreen.height)),
				(s.right - (10 * phoneScreen.width)),
				(s.top + (70 * phoneScreen.height))));
		exitBtn = new GraphicButton(exit, new RectF(
				(s.left + (10 * phoneScreen.width)),
				(s.top + (70 * phoneScreen.height)),
				(s.right - (10 * phoneScreen.width)),
				(s.top + (100 * phoneScreen.height))));
	}

	@Override
	public void draw(Canvas canvas) {
		background.renderBG(canvas);
		startbtn.drawButton(canvas);
		highBtn.drawButton(canvas);
		exitBtn.drawButton(canvas);
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean processTouchInput(MotionEvent event) {
		if (startbtn.touchyMe(event))
			phoneScreen.setActiveScreen(ScreenID.PLAYSCREEN);
		if (highBtn.touchyMe(event)) {
			HighScoreScreen.getInstance(null).setCheck(false);
			phoneScreen.setActiveScreen(ScreenID.HIGHSCORESCREEN);
		} else if (exitBtn.touchyMe(event))
			phoneScreen.endGame();
		return false;
	}

	/**
	 * Not to be implemented
	 */
	@Override
	public boolean processMotionInput(float x, float y) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void ready() {
		// TODO Auto-generated method stub

	}
}
