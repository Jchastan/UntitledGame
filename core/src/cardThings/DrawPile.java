package cardThings;

import java.util.Collections;
import java.util.HashMap;
import java.util.Random;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.UntitledGame;
import enemies.Enemy;

public class DrawPile extends Deck {

	private Sprite displayBackground;
	private characters.Character player;
	
	/**
	 * creates a new drawpile initalized randomly with all the character's cards.
	 */
	public DrawPile(characters.Character player, Random random)
	{
	    this.player = player;
		this.deck = player.getDeck().copy();
		this.reshuffleDeck(random);
	}
	
	
	/**
	 * @param enemy the enemy who's drawpile will be created
	 * @param random the rng
	 */
	public DrawPile(Enemy enemy, Random random)
	{
		this.deck = enemy.getDeck().copy();
		this.reshuffleDeck(random);
		
	}
	
	/**
	 * @return draws the top card from the deck.
	 */
	public Card drawCard()
	{
	  // shuffle discard pile cards  into deck
	    if(this.deck.size() == 0)
	    {
	      this.deck.addAll(this.player.getDiscardPile().getCards());
	      this.player.getDiscardPile().clear();
	      this.reshuffleDeck(UntitledGame.random);
	    }
	    Card card;
	    if (this.player.equals(UntitledGame.playerOne)) {
          this.player.getHand().addCard(this.get(0));
          card = this.removeCard(0);
          this.player.getHand().get(this.player.getHand().getSize() - 1).getSprite().setSize(140, 140);
          this.player.getHand().get(this.player.getHand().getSize() - 1).getSprite().setCenter(800f, 0);
          this.player.getHand().get(this.player.getHand().getSize() - 1).getSprite().draw(UntitledGame.batch);
      } else {
          this.player.getHand().addCard(this.get(0));
          card = this.removeCard(0);
          Sprite newCard = this.player.getHand().get(0).getSprite();
          newCard.setScale(.55555f);
          newCard.setCenter(-675, 0);
          System.out.println("uh oh, there is no player 2");
      }
	    
	    return card;
	  
	}

}
