package enemies;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import characters.*;
import characters.Character;
import com.badlogic.gdx.math.Vector3;
import enemyCards.BonkCard;
import enemyCards.BonkHeal;
import cardThings.*;
import gear.*;
import com.mygdx.game.*;

public class BasicEnemy extends Enemy  {

	private Vector3 position;
	
	/**
	 *  constructs a basic enemy with an idle animation, health, and x y coordinates. the key for the idle animation is "idle"
	 */
	public BasicEnemy(Random random, OrthographicCamera camera)
	{
		super(random, camera);
		this.idle = new TextureAtlas(Gdx.files.internal("Basic Enemy demo\\EnemyBasic.atlas"));

		Vector3 position = new Vector3(0,0,0);

		this.deck.addCard(new BonkCard(this));
		this.deck.addCard(new BonkCard(this));
		this.deck.addCard(new BonkCard(this));
		this.deck.addCard(new BonkCard(this));
		this.deck.addCard(new BonkCard(this));
		this.deck.addCard(new BonkHeal(this));
		
		

		this.animations.put("idle", new Animation(0.15f,idle.getRegions()));
		this.maxHealth = 50;
		this.health = this.maxHealth;	
		this.drawPile= new DrawPile(this, this.random);	
		this.discardPile = new DiscardPile();
		this.strength = 1;
	}

	/**
	 *
	 * @param batch the spritebatch to draw the character to
	 * @param elapsedTime time elapsed since the last draw call
	 */
	@Override
	public void drawIdle(SpriteBatch batch, float elapsedTime) {
		batch.draw(this.getAnimations().get("idle").getKeyFrame(elapsedTime, true), this.position.x, this.position.y);
	}
	@Override
	public void setPosition(float x, float y) {
		this.position.x = x;
		this.position.y = y;
	}

	@Override
	public void autoTarget() {
	  Card topCard = this.drawPile.get(0);
			if(topCard instanceof BonkHeal  == true)
			{
			  this.target = this;
			}
	
			else
			{
			  super.autoTarget();
			}
		
	}
	
	
	
		
		
		

		
		
	


	
	
	

}
	
		
		
		

		
		
	


	
	
	


