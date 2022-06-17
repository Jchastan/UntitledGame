package cardThings;

import java.util.HashMap;
import java.util.Random;

import com.badlogic.gdx.graphics.g2d.Sprite;

import enemies.Enemy;

public class DrawPile extends Deck {

	private Sprite displayBackground;
	
	/**
	 * creates a new drawpile initalized randomly with all the character's cards.
	 */
	public
 DrawPile(characters.Character player, Random random)
	{
		this.deck = (HashMap<Integer, Card>) player.getDeck().copy();
		this.reshuffleDeck(random);
	}
	
	
	/**
	 * @param enemy the enemy who's drawpile will be created
	 * @param random the rng
	 */
	public DrawPile(Enemy enemy, Random random)
	{
		this.deck = (HashMap<Integer, Card>) enemy.getDeck().copy();
		this.reshuffleDeck(random);
		
	}

}
