package com.acel;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.view.MotionEvent;

public abstract class Screen {
	
	protected Context context;
	protected Activity activity;
	protected PhoneScreen phoneScreen;
	
	public Screen(Activity activity){
		this.context = activity;
		this.activity = activity;
		phoneScreen = PhoneScreen.getInstance(activity);
	}

	public abstract void loadContent();

	public abstract void draw(Canvas canvas);

	public abstract void update();
	
	public abstract void pause();
	
	public abstract void ready();

	public abstract boolean processTouchInput(MotionEvent event);
	
	public abstract boolean processMotionInput(float x, float y); 

}
