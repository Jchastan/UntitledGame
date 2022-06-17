package enemies;

import java.util.HashMap;
import java.util.Random;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector3;

import characters.*;
import characters.Character;
import combatThings.Targetable;
import cardThings.*;
import gear.*;
import space.earlygrey.shapedrawer.ShapeDrawer;

import com.mygdx.game.*;

/**
 * @author John Chastanet and Seth Roper. 
 * this class represents an enemy.
 *
 */
public abstract class Enemy implements Targetable {
	
	
	
	// an enemy's animation
	HashMap<String, Animation<TextureRegion>> animations = new HashMap<String, Animation<TextureRegion>>();
	
	// an enemy's health
	protected int health;
	
	// stores the deck of cards the enemy will use
	protected Deck deck = new Deck();
	
	// the rng.
	protected Random random;
	
	// the enemy's draw pile
	protected DrawPile drawPile; 
	
	// the enemey's discard pile.
	protected DiscardPile discardPile;
	
	// the player or enemy being targeted.
	protected Targetable target;
		
	// an enemy's block
	
	protected int block;
	
	// an Enemy Sprite
	
	protected Sprite sprite;
	// Texture atlas that stores the idle position.
	protected TextureAtlas idle;

	// max health of enemy
	protected int maxHealth;
	
	protected Vector3 position;
	
	protected OrthographicCamera camera;
	
	protected int strength;
	
	
	// is the current enemy alive.
	protected boolean isAlive;

	
	/**
	 * constructs a new enemy that is alive by default.
	 */
	public Enemy(Random random, OrthographicCamera camera)
	{
		
		this.position = new Vector3(0, 0, 0);
		
		this.camera = camera;
		isAlive = true;
		
		this.random = random;
		
	//	this.camera.project(this.position);
		
		
		
	}
	
	// getter and setter Methods
	
	public Sprite getSprite() 
	{
		return this.sprite;	
	}
	
	
	
	
	
	public void setPosition(float x, float y)
	{
		this.position.x = x;
		this.position.y = y;
	//	this.camera.project(this.position);
	}
	
	public int getHealth()
	{
		return this.health;
	}
	
	public int getMaxHealth()
	{
		return this.maxHealth;
	}
	
	public HashMap<String, Animation<TextureRegion>> getAnimations()
	{
		return this.animations;
	}
	
	public int getBlock()
	{
		return this.block;
	}
	
	public boolean isAlive()
	{
		return this.isAlive;
	}
	
	public void setBlock(int block)
	{
		this.block = block;
	}
	
	
	
	
	public void setAlive(boolean b)
	{
		this.isAlive = b;
	}
	
	
	
	public void setMaxHealth(int maxHealth)
	{
		this.maxHealth = maxHealth;
	}
	
	@Override
	public Deck getDeck()
	{
		return this.deck;
	}
	
	@Override
	public DrawPile getDrawPile()
	{
		return this.drawPile;
	}
	
	@Override
	public DiscardPile getDiscardPile()
	{
		return this.discardPile;
	}
	
	public void setHealth(int health)
	{
		this.health = health;
	}

	
	/**
	 * makes the enemy play the top card on their draw pile and then discard it to their discard pile.
	 * this also automatically reshuffles the draw and discard pile when the draw pile is empty.
	 * @param combat the combat which the enemy will be doing the action
	 */
	public void doAction(Combat combat) {
		this.target = combat.getPlayerOne();
		this.drawPile.get(0).playCard(combat, this.random);
		this.discardPile.addCard(this.drawPile.get(0));
		
		
		this.drawPile.removeCard(0);
	    
		if(this.drawPile.size() <= 0)
		{
		  for(int i = 0; i < this.discardPile.getCards().size(); i++)	
		  {
			  this.drawPile.addCard(this.discardPile.getCards().get(i));
		  }
		  
		  this.discardPile.getCards().clear();
		  this.drawPile.reshuffleDeck(this.random);
		}
		}
	
	/**
	 * draws the current enemy
	 */
	public  void drawIdle(SpriteBatch batch, float elapsedTime)
	{
		batch.draw(this.getAnimations().get("idle").getKeyFrame(elapsedTime, true), this.position.x, this.position.y);

	}
	
	@Override
	public void setTarget(Targetable target)
	{
		this.target = target;
	}
	
	public String getHealthVisual()
	{
		
		return "" + this.health +" / " + this.maxHealth;
	}
	

	@Override
	public void doDamage(int damage) {
		this.health = this.health - damage;
		if(this.health < 0)
		{
			this.health = 0;
		}
		
	}

	@Override
	public void heal(int healing) {
		this.health = this.health + healing;
		if(this.health > this.maxHealth)
		{
			this.health = this.maxHealth;
		}
		
	}
	
	/**
	 * @return the sprite representing this enemy's intention.
	 */
	public Sprite showIntention()
	{
		return this.drawPile.get(0).getIntention();
	}
	
	
	/**
	 * sets the enemy's target to what it would be determined by their individual targeting system.
	 */
	public abstract void autoTarget();

	@Override
	public int getStrength()
	{
		return this.strength;
	}
	
	@Override
	public void addBlock(int block)
	{
		this.block += block;
	}
	
	// Enemy's don't have energy as of now, so this has to return 0. only implemented because  java interfaces hahaha.
	@Override
	public int getEnergy()
	{
		return 0;
	}
	
	@Override 
	public void addEnergy(int Energy)
	{
		
	}
	
	@Override
	public Targetable getTarget()
	{
		return this.target;
	}
	
	@Override public Hand getHand()
	{
		return null;
	}
	@Override
	public int getEnergyPerTurn()
	{
		return 0;
	}
	
	@Override
	public void setEnergyPerTurn(int energy)
	{
		
	}
	
	
	
	@Override
	public void subtractEnergy(int energy)
	{
		
	}
	
	public void setEnergyToEnergyPerTurn()
	{
		
	}
	
	
	

}
