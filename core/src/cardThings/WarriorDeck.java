package cardThings;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class WarriorDeck extends Deck {
	
	public WarriorDeck()
	{
		this.sprite = new Sprite(new Texture(Gdx.files.internal("Cards\\deck of cards.png")));
		this.sprite.setScale(.45f);
	}

}
