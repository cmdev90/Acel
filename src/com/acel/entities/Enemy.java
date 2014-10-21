package com.acel.entities;

import android.graphics.Canvas;

import com.acel.Sprite;

public class Enemy extends Entity{

	public Enemy(int id, String name, Sprite sprite, int xPos, int yPos) {
		super(id, name, sprite, xPos, yPos);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void draw(Canvas canvas) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean hasCollided(Entity entity) {
		// TODO Auto-generated method stub
		return false;
	}

}
