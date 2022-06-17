package characters;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import cardThings.*;
import gear.*;
import warriorCards.HitCard;

import com.mygdx.game.*;

public class Warrior extends Character {

	
	// Warrior constructor.  initalizes the idle animation.
	public Warrior(int playernum, OrthographicCamera camera)
	{
	
		super(playernum, camera);
		this.maxHealth = 70;
		this.health = this.maxHealth;
		this.deck = new WarriorDeck();
		 this.idle = new TextureAtlas(Gdx.files.internal("opaque atlas\\test2.atlas"));
		 
			this.animations.put("idle", new Animation(0.20f, idle.getRegions()) );
			
		// add 20 hit cards to player one's deck.
			for (int i = 0; i < 20; i++)
			{
		this.deck.addCard(new HitCard(this));	
			}
			// initalizes the amount of energy this player gets per turn.
		this.energyperTurn = 3;	
			
	}

}
