package cardThings;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class DiscardPile {

	// Hashmap contaning the cards in the discard pile.
     ArrayList<Card> cards;
	
	
	/**
	 *  constructs a new empty discard Pile.
	 */
	public DiscardPile()
	{
		cards = new ArrayList<Card>();
	}
	
	/**
	 *  empty's all cards from this discard pile.
	 */
	public void clear()
	{
		this.cards.clear();
	
	}
	
	/**
	 * Adds a card to the end of the discard pile
	 * @param card the card to be added to the end of the discard pile
	 */
	public void addCard(Card card)
	{
		this.cards.add(card);
	}
	
	/**
	 * @return the HashMap of cards in the discardpile.
	 */
	public ArrayList<Card>getCards()
	{
		return this.cards;
	}
	
	

}