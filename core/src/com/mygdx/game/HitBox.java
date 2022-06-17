package com.mygdx.game;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector3;

public class HitBox {

	Vector3 leftHitBox;
	Vector3 rightHitBox;

	OrthographicCamera camera;
	
	
	public HitBox(OrthographicCamera camera, float leftX, float bottomY, float rightX, float topY)
	{
		this.camera = camera;
		this.leftHitBox = new Vector3(leftX, bottomY, 0);
		this.rightHitBox = new Vector3(rightX, topY, 0);
		
	//	this.camera.project(leftHitBox);
	//	this.camera.project(rightHitBox);
		
	}
	
	
	
	public boolean checkCollision(Vector3 mouseCoordinates)
	{
		if(mouseCoordinates.x  >= this.leftHitBox.x && mouseCoordinates.x <= this.rightHitBox.x &&
				mouseCoordinates.y >= this.leftHitBox.y && mouseCoordinates.y <= this.rightHitBox.y)
		{
			return true;
		}
		
		return false;
	}
	
}
