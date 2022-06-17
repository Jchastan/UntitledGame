package cardThings;

import java.util.HashMap;
import java.util.Random;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector3;

import characters.*;
import characters.Character;
import combatThings.Targetable;
import enemies.Enemy;
import cardThings.*;
import gear.*;
import com.mygdx.game.*;

public abstract class Card {
	
	// the energy cost to play this card.
	protected int energyCost;
	// the sprite which represents this card.
	protected Sprite sprite;
	
	
	// the character this card belongs to. null if it is an enemy card.
	protected Character character;
	
	protected Sprite intention;
	
	// the Sprite which represents the card as a display if you were scrolling through a drawpile.
	protected Sprite display;

	protected Targetable owner;

	//this logical hitbox shape
	protected Polygon hitBox;
	
	//the speed of the x translation
	float xSpeed;

	//the speed of the y translation
	float ySpeed;
	
	public Card(Targetable owner)
	{
		this.owner = owner;
	}
	
	// where the card should be when done translating
	protected Vector3 destination = new Vector3(0,0,0);
	private boolean isDragging;
	
	
	// plays the card. Does whatever the card subclass will do.
	public abstract void playCard(Combat combat, Random random);
	

	
	
	// return where the card should be when done translating
	public Vector3 getDestination() {
		return this.destination;
	}

	// return where the card should be when done translating
	public void setDestination(Vector3 newDestination) {
		this.destination = newDestination;
	}

	public void setDestination(float x, float y, float z) {
		Vector3 newDestination = new Vector3(x,y,z);
		this.destination = newDestination;
	}

	// translates the card a distance determined by its total distance to its destination
	public void updatePosition(boolean sethYouFuck, int speed) {
		if (sethYouFuck) {

			this.getDisplay().setOriginCenter();
			Vector3 delta = new Vector3(0,0,-0);
			delta.x = this.destination.x - Combat.getSpriteCenterX(this.getDisplay());
			delta.y = this.destination.y - Combat.getSpriteCenterY(this.getDisplay());
			delta.z = this.destination.z - this.getDisplay().getRotation();

			switch (speed) {
			case 0: 
				xSpeed = delta.x/20;
				ySpeed = delta.y/20;
				break;
			case 1:
				xSpeed = delta.x/10;
				ySpeed = delta.y/10;
				break;
			}

			if (xSpeed > 0.01 || xSpeed < -0.01) {
				this.getDisplay().translateX(xSpeed);
			}
			else {this.getDisplay().setCenterX(this.destination.x);}
			
			if (ySpeed > 0.01 || ySpeed < -0.01) {
				this.getDisplay().translateY(ySpeed);
			}
			else {this.getDisplay().setCenterY(this.destination.y);}

			if (delta.z/200 > 0.001 || delta.z/200 < -0.001) {
				this.getDisplay().rotate(delta.z/200);
			}
			else {this.getDisplay().setRotation(this.destination.z);}
		}

		else {

			this.getSprite().setOriginCenter();
			Vector3 delta = new Vector3(0,0,-0);
			delta.x = this.destination.x - Combat.getSpriteCenterX(this.getSprite());
			delta.y = this.destination.y - Combat.getSpriteCenterY(this.getSprite());
			delta.z = this.destination.z - this.getSprite().getRotation();

			switch (speed) {
			case 0: 
				xSpeed = delta.x/20;
				ySpeed = delta.y/20;
				break;
			case 1:
				xSpeed = delta.x/5;
				ySpeed = delta.y/5;
				break;
			}

			if (xSpeed > 0.01 || xSpeed < -0.01) {
				this.getSprite().translateX(xSpeed);
			}
			else {this.getSprite().setCenterX(this.destination.x);}
			
			if (ySpeed > 0.01 || ySpeed < -0.01) {
				this.getSprite().translateY(ySpeed);
			}
			else {this.getSprite().setCenterY(this.destination.y);}

			if (delta.z/50 > 0.001 || delta.z/50 < -0.001) {
				this.getSprite().rotate(delta.z/50);
			}
			else {this.getSprite().setRotation(this.destination.z);}
		}
	}
		

	// return the sprite.
	public Sprite getSprite()
	{
		return this.sprite;
	}
	
	// makes a polygon clone of the sprite and checks for collision in it given a vector point
	public boolean checkCollision(float x, float y) {
		float[] insert = {this.getSprite().getVertices()[0], this.getSprite().getVertices()[1],
								  this.getSprite().getVertices()[5], this.getSprite().getVertices()[6],
								  this.getSprite().getVertices()[10], this.getSprite().getVertices()[11],
								  this.getSprite().getVertices()[15], this.getSprite().getVertices()[16]};
		hitBox = new Polygon(insert);
		if (this.hitBox.contains(x,y)) {return true;}
		else {return false;}
		/*
		float x = this.getSprite().getX();
		float y = this.getSprite().getY();
		float width = this.getSprite().getWidth();
		float height = this.getSprite().getHeight();
		float[] tmp = {x, y, x + width, y, x + width, y + height, x, y + height};
		this.hitBox = new Polygon(tmp);
		*/
	}

	// return the energyCost to play a card.
	public int getEnergyCost()
	{
		return this.energyCost;
	}
	
	public Sprite getIntention()
	{
		return this.intention;
	}
	
	/**
	 * sets the energy cost for a card.
	 * @param cost
	 */
	public void setEnergyCost(int cost)
	{
		this.energyCost = cost;
	}
	
	/**
	 * @param batch the SpriteBatch to be drawn to
	 * @param x the x coordinate of where this card should be drawn.
	 * @param y the y coordinate of where this card should be drawn.
	 */
	public void drawCard(SpriteBatch batch)
	{
		this.sprite.draw(batch);
	}
	
	public Sprite getDisplay()
	{
		return this.display;
	}
	
	/**
	 *  removes this card from the hand it belongs to. does not add it to the appropriate discard pile.
	 */
	public void removeCardfromHand()
	{
		
	}
	

	public void setDragging(boolean isDragging) {
		this.isDragging = isDragging;
	}

	public boolean isDragging() {
		return isDragging;
	}

	/**
	 * discards this card from the appropriate hand and  puts it in the appropriate discard pile.
	 */
	public void discard(Combat combat, int[] discards)
	{
		for (int discard : discards) {
			this.owner.getHand().removeCard(discard);
		}
		this.owner.getDiscardPile().addCard(this);
		this.owner.getHand().setSize(this.owner.getHand().getSize() - 1);
	}
	
	

}

