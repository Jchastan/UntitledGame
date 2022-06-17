package characters;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import cardThings.*;
import gear.*;
import com.mygdx.game.*;

public class Mage extends Character{
	
	// Mage constructor.
		public Mage(int playernum, OrthographicCamera camera)
		{
			super(playernum, camera);
			this.maxHealth = 60;
			this.health = maxHealth;
			
			this.deck = new MageDeck();
			
			 this.idle = new TextureAtlas(Gdx.files.internal("Wizard idle\\MageIdle.atlas"));
			 		
			 this.animations.put("idle", new Animation(0.20f, idle.getRegions()) );
			 
			 // initializes the amount of energy a player gets per turn.
			 this.energyperTurn = 3;
			 
				
				
				
				
				
		}

	}


