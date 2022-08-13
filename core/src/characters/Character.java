package characters;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector3;

import cardThings.*;
import combatThings.Targetable;
import gear.*;
import space.earlygrey.shapedrawer.ShapeDrawer;

import com.mygdx.game.*;/**
 * @author Seth Roper(and maybe John Chastanet)
 *
 */
	public  class Character implements Targetable {
		// a Hashmap representing a character's deck. 
		protected Deck deck = new Deck();
	
		private HitBox hitbox;
	   	
		private OrthographicCamera camera;
		protected boolean alive;
		
		// the character's current hand.
		protected Hand hand;
		// Represents their Texture Atlas for idle animation
		TextureAtlas idle;
		// character's health.
		protected int health;
		// character's maximum health.
		protected int maxHealth;
		// protected int armor;
		// represents how much armor you have. to be determined if implemented.
		
		// the character's current target.
		
		protected int cardsPerTurn;
	
		protected DrawPile drawPile;
		
		protected DiscardPile discardPile;
		
		protected Vector3 position;
		protected Targetable target;
		
		// stores character animations. Characters will only have one animation object(idle) and will be initialized in the constructor.
		protected HashMap<String, Animation<TextureRegion>> animations = new HashMap<String, Animation<TextureRegion>>();
		// the chacter's gear which they have obtained.
		protected GearList gearList;
		// this increases player damage by 1 per stat. a negative number decreases damage.
		protected int strength;
		// This represents how much a character is blocking.
		protected int block;
		// this increases character block by 1 per stat. negative number decreases this.
		protected int proficiency;
		// stores the size of the deck at the start of combat.
		protected int sizeOfDeckAtStart;
		
	
		// how much energy the character has.
		protected int energy;
		// a String which represents this character's health.
		protected String healthDisplay;
		// Player one or Player2
		protected int playerNumber;
		
		// the amount of energy a player gets per turn.  to be initalized in the subclass constructors.
		protected int energyperTurn;
		
		// Initialize character with stats of zero and maximum health. also intialzies whether this character is controlled
		// by player1 or player 2.
	
		public Character(int playernum, OrthographicCamera camera)
		{
			alive = true;
			this.cardsPerTurn = 5;
			this.position = new Vector3(0, 0, 0);
			
			this.camera = camera;
			if(playernum == 1)
			{
				this.playerNumber = 1;
			}
			else if(playernum == 2)
			{
				this.playerNumber = 2;
			}
			
			else
			{
				throw new IllegalArgumentException("player number must be one or two, you dummy");
			}
		  this.health = this.maxHealth;
		  this.strength = 0;
		  this.block = 0;
		  this.proficiency = 0;
		  
		  if(this.playerNumber == 1)
		  {
			  this.position.set(200, 15, 0);
			//  this.camera.project(this.position);
			  
		  }
		  else if(this.playerNumber == 2)
		  {
			  this.position.set(-30, 30, 15);
			  //this.camera.project(this.position);
			  
		  }
		  
		}
		
		public boolean isAlive()
		{
		  return this.alive;
		}
		
		// The following methods are self explanatory getter methods.
		public int getHealth()
		{
			return this.health;
		}
		
		public int getMaxHealth()
		{
			return this.maxHealth;
		}
		public int getCardsPerTurn()
		{
		  return this.cardsPerTurn;
		}
		
		public Vector3 getPosition(Vector3 position)
		{
			return this.position;
		}
		
	   public TextureAtlas getIdleAtlas()
	   {
		   return this.idle;
	   }
		
		
		
		public int getPlayerNumber()
		{
			return this.playerNumber;
		}
		
		public int getEnergy()
		{
			return this.energy;
		}
		public HashMap<String, Animation<TextureRegion>> getAnimations()
		{
			return this.animations;
		}
		
		public String getHealthDisplay()
		{
			this.healthDisplay = "" + this.health + " / " + this.maxHealth;
			return this.healthDisplay;
		}
	
		
		public GearList getGearList()
		{
			return this.gearList;
		}
		
		public int getStrength()
		{
			return this.strength;
		}
		
		@Override
		public Deck getDeck()
		{
			return this.deck;
		}
		
		public int getBlock()
		{
			return this.block;
		}
		
		@Override
		public int getEnergyPerTurn()
		{
			return this.energyperTurn;
		}
		
		@Override
		public void setEnergyPerTurn(int energy)
		{
			this.energyperTurn = energy;
		}
		
		public int  getProficiency()
		{
			return this.proficiency;
		}
		
		@Override
		public void subtractEnergy(int energy)
		{
			this.energy -= energy;
		}
		
		public void setEnergyToEnergyPerTurn()
		{
			this.energy = this.energyperTurn;
		}
	
		// reset All instance values such as strength and dexterity. to be called once the fight ends.
		public void resetAll()
		{
			this.strength = 0;
			this.block = 0;
			this.proficiency = 0;
			
			
			/*
			 * This alogorithm may be removed. it removes cards temporarily added.
			Iterator starPlatinum = this.deck.entrySet().iterator();
			
			while(starPlatinum.hasNext())
					{
				      Map.Entry<String, Card> entry = starPlatinum.next();
				      if(Integer.parseInt(entry.getKey()) >= this.sizeOfDeckAtStart)
				      {
				    	  starPlatinum.remove();
				      }
					}
			
			 this.sizeOfDeckAtStart = this.deck.size();
			 */
		   
			
			
		}
		
		public HitBox getHitBox()
		{
			return this.hitbox;
		}
		
		// Draws the character's idle animation.
		public void drawIdle(SpriteBatch batch, float elapsedTime)
		{
			
			batch.draw(this.getAnimations().get("idle").getKeyFrame(elapsedTime, true), this.position.x, this.position.y);
		}
		
		
		// set values such as health, strength, efficiency, etc.
		
		public void setHealth(int health)
		{
			this.health = health;
		}
		
		public void setBlock(int block)
		
		{
			this.block = block;
		}
		
		public void setProficiency(int proficiency)
		{
			this.proficiency = proficiency;
		}
		
		public void setMaxHealth(int maxHealth)
		{
			this.maxHealth = maxHealth;
		}
		
		public void setsizeOfDeckInitally(int size)
		{
			this.sizeOfDeckAtStart = size;
		}
		
		public void setPlayerNumber(int number)
		{
			this.playerNumber = number;
		}
		
		public void setHealthDisplay()
		{
			this.healthDisplay = "" + this.getMaxHealth() + " " + this.getHealth();
		}

		@Override
		public void setTarget(Targetable target) 
		{
			this.target = target;
		}

		@Override
		public void doDamage(int damage) {	
			this.health = this.health - damage;
			if(this.health <= 0)
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

		@Override
		public void addBlock(int block) {
			int trueBlock = this.proficiency + block;
			this.block += trueBlock;
			
		}

		@Override
		public void addEnergy(int energy) {
			this.energy += energy;
			
		}
		
		@Override
		public Targetable getTarget()
		{
			return this.target;
		}

		@Override
		public DrawPile getDrawPile() {
			// TODO Auto-generated method stub
			return this.drawPile;
		}

		@Override
		public DiscardPile getDiscardPile() {
			// TODO Auto-generated method stub
			return this.discardPile;
		}
		
		
		public void setDrawPile(DrawPile pile)
		{
			this.drawPile  = pile;
		}
		
		public void setDiscardPile(DiscardPile pile)
		{
			this.discardPile = pile;
		}
		
		public Hand getHand()
		{
			return this.hand;
		}
		
		public void setHand(Hand hand)
		{
			this.hand = hand;
		}
			
			
		
		
	
		

}