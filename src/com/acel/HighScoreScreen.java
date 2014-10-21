package com.acel;

import android.app.Activity;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.RectF;
import android.os.Message;
import android.view.MotionEvent;

import com.acel.test.R;

public class HighScoreScreen extends Screen {

	private static HighScoreScreen hss = null;
	private GraphicButton mainmenuBtn, newgameBtn, scoreBtn;
	private boolean check;
	String str = "";
	private Scores scores;
	private Background background;

	private HighScoreScreen(Activity activity) {
		super(activity);
		loadContent();
	}

	public static HighScoreScreen getInstance(Activity activity) {
		if (hss == null)
			hss = new HighScoreScreen(activity);
		return hss;
	}

	@Override
	public void loadContent() {
		mainmenuBtn = new GraphicButton(BitmapFactory.decodeResource(
				activity.getResources(), R.drawable.mainmenu), 1, 75, 50, 95);

		newgameBtn = new GraphicButton(BitmapFactory.decodeResource(
				activity.getResources(), R.drawable.newgame), 50, 75, 1, 95);

		scoreBtn = new GraphicButton(BitmapFactory.decodeResource(
				activity.getResources(), R.drawable.over), 1, 1, 100, 75);

		background = new Background(BitmapFactory.decodeResource(
				context.getResources(), R.drawable.clouds), phoneScreen);
	}

	@Override
	public void draw(Canvas canvas) {
		background.renderBG(canvas);
		if (check) {
			if (scores.isHighScore(Ball.getInstance().getScore())) {
				Message msg = phoneScreen.handler.obtainMessage();
				phoneScreen.handler.sendMessage(msg);
				phoneScreen.pause();
			}
			this.check = false;
		} else {

			mainmenuBtn.drawButton(canvas);
			newgameBtn.drawButton(canvas);
			scoreBtn.drawButton(canvas);
			Paint p = new Paint();

			canvas.drawBitmap(BitmapFactory.decodeResource(
					activity.getResources(), R.drawable.over), null, new RectF(
					3 * phoneScreen.width, 2 * phoneScreen.height,
					97 * phoneScreen.width, 75 * phoneScreen.height), p);

			p.setColor(Color.BLACK);
			p.setTextAlign(Align.CENTER);
			p.setTextSize(9 * phoneScreen.height);
			canvas.drawText("HighScores", phoneScreen.getWidth() / 2,
					12 * phoneScreen.height, p);

			p.setTextAlign(Align.LEFT);
			p.setTextSize(3 * phoneScreen.height);
			int x = 20;
			for (int i = 0; i < scores.getScoreList().size() && i < 8; i++) {
				canvas.drawText((i + 1) + ". "
						+ scores.getScoreList().get(i).getName(),
						20 * phoneScreen.width, x * phoneScreen.height, p);
				canvas.drawText("" + scores.getScoreList().get(i).getScore(),
						70 * phoneScreen.width, x * phoneScreen.height, p);
				x = x + 6;
			}

		}

	}

	public void setSumting(String str) {
		scores.addToList(str, Ball.getInstance().getScore());
		phoneScreen.resume();
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub

	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void ready() {
		// TODO Auto-generated method stub
		scores = new Scores(this.context);
	}

	@Override
	public boolean processTouchInput(MotionEvent event) {
		if (mainmenuBtn.touchyMe(event)) {
			phoneScreen.setActiveScreen(ScreenID.STARTSCREEN);
			this.check = false;
		}
		if (newgameBtn.touchyMe(event)) {
			phoneScreen.setActiveScreen(ScreenID.PLAYSCREEN);
			this.check = false;
		}
		return false;
	}

	@Override
	public boolean processMotionInput(float x, float y) {
		return false;
	}

	public void setCheck(boolean b) {
		this.check = b;
	}

}
