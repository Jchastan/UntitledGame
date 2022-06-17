package enemyCards;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.mygdx.game.Combat;

import cardThings.Card;
import combatThings.Targetable;
import enemies.Enemy;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class BonkHeal extends Card {

	
	
	public BonkHeal(Targetable owner)
	{
		super(owner);
		this.intention = new Sprite(new Texture(Gdx.files.internal("Cards\\healing intention.png")));
		this.intention.setScale(0.5f);
	}
	@Override
	public void playCard(Combat combat, Random random) {
		this.owner.getTarget().heal(7);
		
	}

	

}
