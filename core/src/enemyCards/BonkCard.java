package enemyCards;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.mygdx.game.Combat;

import cardThings.Card;
import combatThings.Targetable;
import enemies.Enemy;

public class BonkCard extends Card {
	
	
	public BonkCard(Targetable owner)
	{
		super(owner);
		this.intention = new Sprite(new Texture(Gdx.files.internal("Cards\\attacking intention.png")));
	}

	@Override
	public void playCard(Combat combat, Random random) {
	
		this.owner.getTarget().doDamage(7 + this.owner.getStrength());
      	
		
	}

	}
	
	


