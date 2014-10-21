package com.acel;

import android.graphics.Bitmap;
import android.graphics.Rect;

public class AnimatedSprite extends Sprite {

	private float frameWidth, frameHeight;
	private int currentFrameCount, currentFrame;
	private int[] duration;
	private int numFrames;
	private boolean isLooping, hasFinished;

	public AnimatedSprite(Bitmap img, float x, float y, float width,
			float height, PhoneScreen p, int numFrames, int[] duration) {
		super(img, x, y, width, height, p);
		// TODO Auto-generated constructor stub
	}

	public AnimatedSprite(Bitmap img, float x, float y, float width,
			float height, PhoneScreen p, int numFrames, int d) {
		super(img, x, y, width, height, p);
		// TODO Auto-generated constructor stub
		this.numFrames = numFrames;
		frameWidth = this.image.getWidth() / numFrames;
		frameHeight = this.image.getHeight();
		currentFrame = 0;
		currentFrameCount = 0;
		this.duration = new int[this.numFrames];

		for (int i = 0; i < this.numFrames; i++) {
			this.duration[i] = d / this.numFrames;
		}

		this.isLooping = true;
		this.hasFinished = false;
	}

	public void update() {
		if (!this.hasFinished) {
			currentFrameCount++;
			System.out.println("CurrentFrameCount: " + currentFrameCount);
			if (duration[currentFrame] == currentFrameCount) {// time to switch frames

				if (this.currentFrame + 1 == numFrames) {// at the last frame
					if (!this.isLooping) {// not looping
						this.hasFinished = true;
					}
				}

				currentFrame++;
				currentFrameCount = 0;
				currentFrame = currentFrame % numFrames;
			}
		}
		this.src = new Rect((int) (currentFrame * this.frameWidth), 0,
				(int) ((currentFrame + 1) * this.frameWidth),
				(int) (this.frameHeight));
	}
}
