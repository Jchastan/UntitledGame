package cardThings;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

import characters.*;
import characters.Character;
import combatThings.DeckViewer;
import cardThings.*;
import gear.*;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.*;

public class Deck {
	
	// sprite of the deck of cards. 
	Sprite sprite;
	
	// the sprite which shows the enemy's intention. 
	Sprite intention;

	
	private DeckViewer viewer;
	
	
	/**
	 * all cards in the deck.
	 */
	ArrayList<Card> deck;
     
	// constructs a new Deck object.
	public Deck()
	{
	  this.deck = new ArrayList<Card>();
		this.viewer = new DeckViewer(this.deck);
	}
	
	
	/**
	 * @return card in the hashmap.
	 */
	public Card get(Integer key) {
		return this.deck.get(key);
	}

	/**
	 * @return the size of the deck hashmap in the deck class.
	 */
	public int size() {
		return this.deck.size();
	}
	
	@SuppressWarnings("unchecked")
  public ArrayList<Card> copy() {
		
		
		return (ArrayList<Card>) this.deck.clone();
	}
	
	/**
	 * @param card the card to be added to the deck.
	 */
	public void addCard(Card card)
	{
		this.deck.add((card));
	}
	
	
	
			/**
			 *  reshuffles the deck. Puts the cards in a random order.
			 *  Ripped algorithm online cause I'm lazy but it probably works.
			 */
			public void reshuffleDeck(Random random)
			{
				
			  Collections.shuffle(this.deck, random);
			
			}
			
			/**
			 * @param index the index of the card you want to remove.
			 * this removes a card of the given key to the deck.
			 * pray that this doesn't cause a concurrent modification exception.
			 */
			public Card removeCard(int index)
			{	
			
			  return this.deck.remove(index);
			   
			}
			
			public Sprite getDeckVisual()
			{
				return this.sprite;
			}
			
			public void drawDeckVisual(SpriteBatch batch)
			{
				
				this.sprite.draw(batch);
			}
			
			public void showCards(SpriteBatch batch)
			{
				this.viewer.update(this.deck);
				this.viewer.showCards(batch);
			}
			
			public void stopShowingCards()
			{
				this.viewer.stopShowingCards();
			}
			

}
