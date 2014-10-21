package com.acel.entities;

import android.graphics.Canvas;

import com.acel.Sprite;

public abstract class Entity {
	int id;
	String name;
	Sprite sprite;
	int xPos, yPos;
	
	public Entity(int id, String name, Sprite sprite, int xPos, int yPos){
		this.id = id;
		this.name = name;
		this.sprite = sprite;
		this.xPos = xPos;
		this.yPos = yPos;
	}
	
	public abstract void draw(Canvas canvas);
	
	public abstract void update();
	
	public abstract boolean hasCollided(Entity entity);
}
