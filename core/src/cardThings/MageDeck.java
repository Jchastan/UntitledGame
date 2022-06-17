package cardThings;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class MageDeck extends Deck {
	
	public MageDeck()
	{
		this.sprite = new Sprite(new Texture(Gdx.files.internal("Cards\\Mage deck.png")));
		this.sprite.setScale(.36f);
	}

}
