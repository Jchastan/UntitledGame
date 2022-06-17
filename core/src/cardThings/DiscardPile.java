package cardThings;

import java.util.HashMap;
import java.util.Iterator;

public class DiscardPile {

	// Hashmap contaning the cards in the discard pile.
	private HashMap<Integer, Card> cards = new HashMap<Integer, Card>();
	
	
	/**
	 *  constructs a new empty discard Pile.
	 */
	public DiscardPile()
	{
		
	}
	
	/**
	 *  empty's all cards from this discard pile.
	 */
	public void reset()
	{
		Iterator starPlatinum = this.cards.entrySet().iterator();
	    
		while(starPlatinum.hasNext())
		{
			starPlatinum.remove();
		}
	
	}
	
	/**
	 * Adds a card to the end of the discard pile
	 * @param card the card to be added to the end of the discard pile
	 */
	public void addCard(Card card)
	{
		this.cards.put(this.cards.size(), card)	;
	}
	
	/**
	 * @return the HashMap of cards in the discardpile.
	 */
	public HashMap<Integer, Card> getCards()
	{
		return this.cards;
	}
	
	

}