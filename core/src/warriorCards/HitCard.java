package warriorCards;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.Combat;

import cardThings.Card;
import combatThings.Targetable;
import enemies.Enemy;

public class HitCard extends Card {
	
	
	public HitCard(Targetable owner)
	{
		super(owner);
		this.sprite = new Sprite(new Texture(Gdx.files.internal("Cards\\hit.png")));
		this.energyCost = 1;
		
		this.display = new Sprite(new Texture(Gdx.files.internal("Cards\\hit.png")));
	}

	@Override
	public void playCard(Combat combat, Random random) {
	  
	    // temporarily setting target.
		
		this.owner.setTarget(combat.getInstance().getEnemies().get(0));
		this.owner.getTarget().doDamage(7);
		this.owner.subtractEnergy(this.energyCost);
		int[] discards = {combat.getHighlightedCard()};
		this.discard(combat, discards);
	}

	

	
}
