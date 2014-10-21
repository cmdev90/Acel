/**
 * Gather data about the phones screen size/dimensions and make this data publicly available
 * to extending objects. Also retrieves a drawing context to allow drawing to the phone screen
 * by extending methods. Objects using this class should extend the Screen class to ensure all required methods are 
 * implemented.
 * 
 */
package com.acel;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Canvas;
import android.graphics.RectF;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.EditText;

public class PhoneScreen extends SurfaceView implements SurfaceHolder.Callback {

	private static PhoneScreen phoneScreen = null;
	protected float width, height;
	protected boolean screenReady;
	protected GameThread gameThread = null;
	private Screen activeScreen = null;
	private List<Screen> screenLst;
	private Activity activity;

	private PhoneScreen(Activity activity) {
		super(activity);
		getHolder().addCallback(this);
		screenLst = new ArrayList<Screen>();
		this.activity = activity;
	}

	public static PhoneScreen getInstance(Activity activity) {
		if (phoneScreen == null)
			phoneScreen = new PhoneScreen(activity);
		return phoneScreen;
	}

	public void draw(Canvas canvas) {
		// TODO Auto-generated method stub
		if (activeScreen != null)
			activeScreen.draw(canvas);
	}

	public void update() {
		// TODO Auto-generated method stub
		if (activeScreen != null)
			activeScreen.update();
	}


	/**
	 * Returns a RectF representation of the drawable phone screen region.
	 * @return
	 */
	public RectF getWindow() {
		return new RectF(0, 0, this.getWidth(), this.getHeight());
	}

	/**
	 * Returns the current active screen to the calling object
	 * @return
	 */
	public Screen getActiveScreen() {
		return activeScreen;
	}

	/**
	 * Sets the Active Screen to be displayed by specifying the screen ScreenID
	 * @param screenID
	 */
	public void setActiveScreen(int screenID) {
		this.activeScreen = screenLst.get(screenID);
		activeScreen.ready();
	}

	/**
	 * Pauses the running game thread
	 */
	public void pause() {
		if (gameThread != null) {
			gameThread.pleaseWait = true;
			activeScreen.pause();
		}
	}

	/**
	 * Resumes the running game thread
	 */
	public void resume() {
		if (gameThread != null) {
			synchronized (gameThread) {
				gameThread.pleaseWait = false;
				gameThread.notify();
			}
		}
	}

	/**
	 * Ends the currently running gameThread
	 */
	public void endGame() {
		if (gameThread != null) {
			gameThread.shutDown();
			gameThread = null;
		}
		activity.finish();
	}

	public void startTiming() {
		gameThread.startTime();
	}

	public long getGameTime() {
		return gameThread.getTimeElapsed() / 1000;
	}

	/**
	 * Triggers the active screen's touch event
	 */
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		if (this.activeScreen != null)
			this.activeScreen.processTouchInput(event);
		return super.onTouchEvent(event);
	}
	
	/**
	 * Activates the active screen's accelerometer handling method
	 * @param x
	 * @param y
	 */
	public void sendSensorData(float x, float y) {
		if (this.activeScreen != null)
			activeScreen.processMotionInput(x, y);
	}

	protected final Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			AlertDialog.Builder alert = new AlertDialog.Builder(
					phoneScreen.getContext());

			alert.setTitle("HighSocres");
			alert.setMessage("Enter your name");

			// Set an EditText view to get user input
			final EditText input = new EditText(phoneScreen.getContext());
			alert.setView(input);

			alert.setPositiveButton("Ok",
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog,
								int whichButton) {
							Editable value = input.getText();
							// Do something with value!
							HighScoreScreen.getInstance(null).setSumting(value.toString());
						}
					});

			alert.show();
		}
	};

	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		// TODO Auto-generated method stub

	}

	public void surfaceCreated(SurfaceHolder holder) {
		if (gameThread == null) {
			gameThread = new GameThread(this);

			gameThread.start();

			width = this.getWidth() * 0.01f;
			height = this.getHeight() * 0.01f;

			screenReady = true;

			screenLst.add(StartScreen.getInstance(activity));
			screenLst.add(PlayScreen.getInstance(activity));
			screenLst.add(HighScoreScreen.getInstance(activity));

			setActiveScreen(ScreenID.STARTSCREEN);
		}
	}

	public void surfaceDestroyed(SurfaceHolder holder) {
		// TODO Auto-generated method stub

	}
}
