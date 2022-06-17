package cardThings;

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
	protected HashMap<Integer, Card> deck = new HashMap<Integer, Card>();
     
	// constructs a new Deck object.
	public Deck()
	{
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
	
	public  HashMap<Integer, Card> copy() {
		HashMap<Integer, Card> buh = new HashMap<Integer, Card>();
		buh.putAll(this.deck);
		return buh;
	}
	
	/**
	 * @param card the card to be added to the deck.
	 */
	public void addCard(Card card)
	{
		this.deck.put((Integer)this.deck.size(), card);
	}
	
	
	
			/**
			 *  reshuffles the deck. Puts the cards in a random order.
			 *  Ripped algorithm online cause I'm lazy but it probably works.
			 */
			public void reshuffleDeck(Random random)
			{
				for(int i = 0; i < this.deck.size(); i++)
				{
					// random for remaining positions.
					Integer r = i + random.nextInt(this.deck.size() - i);
					
					//swapping the elements.
					Card temp = this.deck.get(r);
					this.deck.put(r, this.deck.get(i));
					this.deck.put(i, temp);
				}
			
			}
			
			/**
			 * @param key the key of the card you want to remove.
			 * this removes a card of the given key to the deck.
			 * pray that this doesn't cause a concurrent modification exception.
			 */
			public Card removeCard(Integer key)
			{	
				Card buh = this.deck.get(key);
				for(int i = 0; i < this.deck.size(); i++)
				{
					Integer temp = i - 1;
					if( i > key)
					{
						this.deck.replace(temp, this.deck.get((Integer)i));
					}
				}
						
				this.deck.remove(this.deck.size() - 1);
				
				return buh;
			   
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
