package enemies;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import characters.*;
import characters.Character;
import enemyCards.BonkCard;
import enemyCards.BonkHeal;
import cardThings.*;
import gear.*;
import com.mygdx.game.*;

public class ShadowWench extends Enemy {

    /**
     *  constructs a basic enemy with an idle animation, health, and x y coordinates. the key for the idle animation is "idle"
     */
    public ShadowWench(Random random, OrthographicCamera camera) {
        super(random, camera);
        this.sprite = new Sprite(new Texture("Enemies\\spooky_wench.png"));

        this.deck.addCard(new BonkCard(this));
        this.deck.addCard(new BonkCard(this));
        this.deck.addCard(new BonkCard(this));
        this.deck.addCard(new BonkCard(this));
        this.deck.addCard(new BonkCard(this));
        this.deck.addCard(new BonkHeal(this));

        this.maxHealth = 100;
        this.health = this.maxHealth;
        this.drawPile= new DrawPile(this, this.random);
        this.discardPile = new DiscardPile();
        this.strength = 1;
        this.sprite.setSize(380,380);
    }
    @Override
    public void drawIdle(SpriteBatch batch, float elapsedTime) {
        //todo implement enemy idle animation
        this.sprite.setPosition(position.x - sprite.getWidth()/2,position.y);
        sprite.draw(batch);
    }

    @Override
    public void autoTarget() {
        //TODO: make character do thing specific
        Card topCard = this.drawPile.get(0);
        if(topCard instanceof BonkHeal  == true)
        {
            this.target = this;
        }

        else
        {
            super.autoTarget();
        }
    }
}
