package combatThings;

import cardThings.Deck;
import cardThings.DiscardPile;
import cardThings.DrawPile;
import cardThings.Hand;

/**
 * @author John Chastanet and Seth Roper
 * 
 * objects that are targetable and can target other objects implement this interface.
 *  
 *
 */
public interface Targetable {
	
	public void setTarget(Targetable target);
	
	public void doDamage(int damage);
	
	public void heal(int healing);

	public int getStrength();
	
	public int getHealth();
	
	public int getBlock();
	
	public void addBlock(int block);
	
	public int getEnergy();
	
	/**
	 * don't use with enemy cards.
	 * @param energy how much energy to add.
	 */
	public void addEnergy(int energy);
	
	/**
	 * @param energy how much energy to subtract.
	 * don't use with enemy cards.
	 */
	public void subtractEnergy(int energy);
	
	public int getEnergyPerTurn();
	
	public void setEnergyPerTurn(int energy);
	
	public Targetable getTarget();
	
	public Deck getDeck();

	public DrawPile getDrawPile();
	
	public DiscardPile getDiscardPile();
	
	/**
	 * @return the hand of this targetable player. if called on an enemy, it will return null.
	 * unless we want to make an enemy that has a hand. we can totally do that because java interfaces can be implemented differently with subclasses
	 * pretty fucking cool. fuck non object oriented languages. they are bad. mostly. c++ isnt technically one but like it is i think so it's fine
	 * but like fuck the ones that dont do inheritance good. why do people hate on java? that's like, so mean. anyway it's currently 8/6/2020 as I am writing this
	 * and i really need a fucking hair cut. If you're a recruiter and are seeing this comment from github or some shit, uhhh, ignore it.
	 */
	public Hand getHand();
	
	


}
