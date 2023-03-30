package com.mygdx.game;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector3;

import cardThings.Card;
import cardThings.Deck;
import cardThings.DiscardPile;
import cardThings.DrawPile;
import cardThings.Hand;
import characters.Character;
import combatThings.EndTurnButton;
import combatThings.Targetable;
import enemies.Enemy;
import instances.Instance;
import space.earlygrey.shapedrawer.ShapeDrawer;

import static java.lang.Math.abs;

public class Combat implements InputProcessor {

	// represents if it's the player's turn or the enemy's turn.
	private boolean playerTurn;
	//
	private boolean enemyTurn;
	// the enemy to be target by single hit cards by player 1.
	public Enemy p1Target;
	// the enemy to be targeted by single hit cards by player 2.
	public Enemy p2Target;
	// represents if it is the start of the player turn.
	private boolean startTurn;

	private ShapeDrawer HealthBars;

	private TextureAtlas PlayerTurnTexture = new TextureAtlas(Gdx.files.internal("combat animations\\player turn Animation\\player turn animation.atlas"));

	private Animation<TextureRegion> playerTurnAnimation = new Animation<TextureRegion>(0.15f, this.PlayerTurnTexture.getRegions());

	private boolean allowCombatInputs;

	private boolean viewingDeck;

	private int highlightedCard;

	private Deck tmpDeck;


	private int turnCount;


	// the font.
	private BitmapFont font;


	// represents the two player characters.
	private Character playerOne;
	private Character playerTwo;
	//  a stage which has information such as the background, the music, and what enemies should be implemented.
	private Instance instance;

	// changes to true when the fight is over and the enemies died
	boolean isWon;
	// change to false when the fight is over because both players have died.
	boolean isLost;
	// A random number generator.
	private Random random;

	// player one's hand, draw pile, and discard pile.
	private Hand p1Hand;
	private DrawPile p1DrawPile;
	private DiscardPile p1DiscardPile;

	//player two's hand, draw pile, and discard pile.

	private Hand p2Hand;
	private DrawPile p2DrawPile;
	private DiscardPile p2DiscardPile;

	// the font that displays the health of enemies and players
	private BitmapFont healthFont;

	// camera
	private OrthographicCamera camera;

	// the vector storing the mouse coordinates.
	private Vector3 mouse_position;

	// the spriteBatch the main class will render. must be passed from the main loop.
	SpriteBatch batch;

	private EndTurnButton endTurnButton;

	private Sprite shader;


	private Sprite p1Energy = new Sprite(new Texture((Gdx.files.internal("combat assets\\player 1 energy temp sprite.png"))));

	// the hit box to view player one's cards.
	private HitBox p1DeckBox;


	private HitBox exitHitBox;

	private Sprite exitSprite;

	// How many cards to draw initially
	private int p1HandSize = UntitledGame.playerOne.getCardsPerTurn();

	// How much time should have passed when the next card is drawn
	private float cardDrawDeltaTime = 0.5f;

	// Determines if the deck should be updating position
	private boolean pauseHand = false;


	// constructs a new combat object.
	public Combat(Character p1, Character p2, Instance instance, Random random, SpriteBatch batch, OrthographicCamera camera, BitmapFont font) {
		this.instance = instance;
		this.playerOne = p1;
		this.playerTwo = p2;
		this.random = random;
		this.batch = batch;
		this.camera = camera;

		// initialize hands draw piles and discard piles

		this.p1Hand = new Hand(UntitledGame.playerOne);
		this.p2Hand = new Hand(UntitledGame.playerTwo);

		Pixmap pixmap = new Pixmap(1, 1, Format.RGBA8888);
		pixmap.setColor(Color.WHITE);
		pixmap.drawPixel(0, 0);
		Texture texture = new Texture(pixmap); //remember to dispose of later
		pixmap.dispose();
		TextureRegion region = new TextureRegion(texture, 0, 0, 1, 1);
		this.HealthBars = new ShapeDrawer(this.batch, region);

		this.playerOne.setHand(this.p1Hand);
		this.playerTwo.setHand(this.p2Hand);
		
		// create a new discard pile for this combat
        this.p1DiscardPile = new DiscardPile();
        this.p2DiscardPile = new DiscardPile();

		// makes a new draw pile for this combat instance for both characters
		this.p1DrawPile = new DrawPile(this.playerOne, this.random);
		this.p2DrawPile = new DrawPile(this.playerTwo, this.random);
		this.playerOne.setDrawPile(p1DrawPile);
		this.p2DrawPile = new DrawPile(this.playerTwo, this.random);

		// give both characters a reference to this new DrawPile
		this.playerOne.setDrawPile(this.p1DrawPile);
		this.playerTwo.setDrawPile(this.p2DrawPile);

		// create a new discard pile for this combat
		this.p1DiscardPile = new DiscardPile();
		this.p2DiscardPile = new DiscardPile();

		// give both characters a reference to this DiscardPille
		this.playerOne.setDiscardPile(this.p1DiscardPile);
		this.playerTwo.setDiscardPile(this.p2DiscardPile);

		// initalize the players' energy to their energy per turn.
		this.playerOne.setEnergyToEnergyPerTurn();
		this.playerTwo.setEnergyToEnergyPerTurn();


		this.mouse_position = new Vector3(0, 0, 0);
		//mouse_position.set(Gdx.input.getX(), Gdx.input.getY(), 0);
		//this.camera.unproject(mouse_position.set(Gdx.input.getX(), Gdx.input.getY(), 0));

		this.font = font;
		this.endTurnButton = new EndTurnButton(this, this.camera);
		this.allowCombatInputs = true;
		this.shader = new Sprite(new Texture(Gdx.files.internal("Sprites\\black pixel.png")));
		this.shader.setScale(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		this.shader.setCenter(800f, 450f);
		this.shader.scale(10f);
		this.shader.setAlpha(0.7f);
		this.viewingDeck = false;
		this.p1DeckBox = new HitBox(this.camera, 19f, 56f, 167f, 202f);
		this.exitHitBox = new HitBox(this.camera, 1420f, 810f, 1600f, 900f);
		this.exitSprite = new Sprite(new Texture(Gdx.files.internal("combat assets\\x mark.png")));
		this.exitSprite.setCenter(1500, 830);
		this.exitSprite.setScale(.4f);

		this.p1Energy.setSize(1250, 1250);
		this.p1Energy.setCenter(200, 700);

		this.turnCount = 1;

		this.playerTurn = true;
	}

	// updates game state based on player input. to be called within the render function of the main game.
	public boolean update(float elapsedTime, BitmapFont font) {
		if (!playerOne.isAlive() && playerTwo.isAlive()) {
			return false;
		}

		this.instance.playMusic();
		this.instance.drawbackground(this.batch);
		this.instance.drawEnemies(this.batch, elapsedTime);
		this.playerOne.drawIdle(this.batch, elapsedTime);
		this.playerTwo.drawIdle(this.batch, elapsedTime);
		this.updateMouseCoordinates();
		this.instance.updateEnemiesAlive();
		this.endTurnButton.drawEndTurnButton();
		this.endTurnButton.checkButton(this.mouse_position);
		this.drawHealth(batch, font);
		//this.drawHealthbars();
		//this.drawPlayerOneCard(this.batch);
		this.drawP1Deck();
		if (p1HandSize > 0 && elapsedTime > cardDrawDeltaTime) {
			cardDrawDeltaTime = cardDrawDeltaTime + 0.1f;
			this.drawCard(0, 1);
			p1HandSize--;
		}
		this.drawP1Hand();
		this.drawP2Deck();
		this.playPlayerTurnAnimation(elapsedTime);

		this.font.draw(this.batch, "X: " + this.mouse_position.x + " Y: " + this.mouse_position.y,
				this.mouse_position.x + 30, this.mouse_position.y);
		this.probep1Deckinput();

		this.drag();


		this.p1Energy.draw(this.batch);
		this.font.draw(this.batch, "" + this.playerOne.getEnergy() + " / " + this.playerOne.getEnergyPerTurn(), 150, 800);
		// if enemy turn, they attac
		if (!this.playerTurn) {
			Iterator<Integer> enemyIterator = this.instance.getEnemies().keySet().iterator();
			while (enemyIterator.hasNext()) {

				Enemy currentEnemy = this.instance.getEnemies().get(enemyIterator.next());
				if (currentEnemy.isAlive()) {
					currentEnemy.autoTarget();
					currentEnemy.doAction(this);
				}
			}
			this.startPlayerTurn();
		}

		return true;
	}


	/**
	 * @param batch the spritebatch to be drawn to.
	 * @param font  the font which will be used displaying health text.
	 */
	public void drawHealth(SpriteBatch batch, BitmapFont font) {
		// p1 health container
		this.HealthBars.filledRectangle(200, 250, 150f, 20, new Color(Color.BLACK));
		// p1 health as a ratio of current health to max health
		this.HealthBars.filledRectangle(200, 250, 150f * this.playerTwo.getHealth() / this.playerTwo.getMaxHealth(), 20, new Color(Color.CYAN));
		// p2 health container
		this.HealthBars.filledRectangle(450, 250, 150f, 20, new Color(Color.BLACK));
		// p1 health as a ratio of current health to max health.
		this.HealthBars.filledRectangle(450, 250, 150f * this.playerOne.getHealth() / this.playerOne.getMaxHealth(), 20, new Color(Color.CHARTREUSE));

		this.instance.drawEnemyHealth(HealthBars, batch, font);
		// enemy health bars.
		font.draw(this.batch, this.playerTwo.getHealthDisplay(), 220, 272);
		font.draw(this.batch, this.playerOne.getHealthDisplay(), 470, 272);


		// Vector3 mouse_position = new Vector3(0,0,0);
		//mouse_position.set(Gdx.input.getX(), Gdx.input.getY(), 0);
		//this.camera.unproject(mouse_position.set(Gdx.input.getX(), Gdx.input.getY(), 0));
		//font.draw(this.batch,"X: " + mouse_position.x + " Y: " + mouse_position.y, 700,  500);

		//  Rectangle blah = new Rectangle(mouse_position.x, mouse_position.y, .1f, .1f);
		//Rectangle bleh = new Rectangle(this.endTurn.getBoundingRectangle().getX(), this.endTurn.getBoundingRectangle().getY(), 500, 700);

		//    if(this.mouse_position.x >= this.endTurn.getBoundingRectangle().getX() + 420)
		//	 {
		//	 font.draw(batch, "OKAY NOW THIS IS EPIC", 900, 700);
		//}


	}

	/**
	 *
	 */
	public void drag() {
		for (int i = p1Hand.getSize() - 1; i >= 0; i--) {
			if (this.p1Hand.get(i).checkCollision(this.mouse_position.x, this.mouse_position.y)) {
				this.pauseHand = true;
				highlightedCard = i;
				break;
			} else {
				if (this.p1Hand.get(i).getSprite().getHeight() <= 140) {
					this.p1Hand.get(i).getSprite().setSize(140, 140);
				} else {
					this.p1Hand.get(i).getSprite().setSize(this.p1Hand.get(i).getSprite().getHeight() - 10,
							this.p1Hand.get(i).getSprite().getWidth() - 10);
				}
				this.pauseHand = false;
			}
		}
		if (highlightedCard < 0) return;
		if (highlightedCard < this.p1Hand.getSize() && this.p1Hand.get(highlightedCard).checkCollision(mouse_position.x, mouse_position.y)) {
			this.p1Hand.setHandDestination(batch, 30);
			for (int i = 0; i < highlightedCard; i++) {
				this.p1Hand.get(i).setDestination(this.p1Hand.get(i).getDestination().add(-100, 0, 0));
				if (this.p1Hand.get(i).getSprite().getHeight() <= 140) {
					this.p1Hand.get(i).getSprite().setSize(140, 140);
				} else {
					this.p1Hand.get(i).getSprite().setSize(this.p1Hand.get(i).getSprite().getHeight() - 10,
							this.p1Hand.get(i).getSprite().getWidth() - 10);
				}
			}

			for (int i = highlightedCard + 1; i < p1Hand.getSize(); i++) {
				this.p1Hand.get(i).setDestination(this.p1Hand.get(i).getDestination().add(100, 0, 0));
				if (this.p1Hand.get(i).getSprite().getHeight() <= 140) {
					this.p1Hand.get(i).getSprite().setSize(140, 140);
				} else {
					this.p1Hand.get(i).getSprite().setSize(this.p1Hand.get(i).getSprite().getHeight() - 10,
							this.p1Hand.get(i).getSprite().getWidth() - 10);
				}
			}

			this.p1Hand.get(highlightedCard).getSprite().setSize(280, 280);
			this.p1Hand.get(highlightedCard).setDestination(this.p1Hand.get(highlightedCard).getDestination().x, 140, 0);
		} else if (highlightedCard < this.p1Hand.getSize()) {
			if (this.p1Hand.get(highlightedCard).getSprite().getHeight() <= 140) {
				this.p1Hand.get(highlightedCard).getSprite().setSize(140, 140);
			} else {
				this.p1Hand.get(highlightedCard).getSprite().setSize(this.p1Hand.get(highlightedCard).getSprite().getHeight() - 10,
						this.p1Hand.get(highlightedCard).getSprite().getWidth() - 10);
			}
		}

		if (this.pauseHand && Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
			this.p1Hand.get(highlightedCard).getSprite().setCenter(this.mouse_position.x, this.mouse_position.y);
			this.p1Hand.get(highlightedCard).setDestination(new Vector3(this.mouse_position.x, this.mouse_position.y, 0));
			this.p1Hand.get(highlightedCard).setDragging(true);
		} else if (highlightedCard < this.p1Hand.getSize() && this.p1Hand.get(highlightedCard).isDragging() &&
				this.p1Hand.get(highlightedCard).getSprite().getY() > 250 &&
				//TODO: make this set targets based on proximity to enemy sprites.
				this.playerOne.getEnergy() >= this.p1Hand.get(highlightedCard).getEnergyCost()) {
			ArrayList<Enemy> enemies = new ArrayList<Enemy>(this.instance.getEnemies().values());
			float dist = Float.MAX_VALUE;
			Enemy newTarget = enemies.get(0);
			for (int i = 0; i < enemies.size(); i++) {
				if (!enemies.get(i).isAlive()) { continue;}
				float newDist = Math.abs(enemies.get(i).getX() - this.p1Hand.get(highlightedCard).getSprite().getX());
				if (newDist < dist) {
					dist = newDist;
					newTarget = enemies.get(i);
				}
			}
			this.playerOne.setTarget(newTarget);
			this.p1Hand.get(highlightedCard).playCard(this, this.random);
			//TODO: make card discard itself.
		}

	}

	/**
	 * @return the current target of player1.
	 */
	public Enemy getP1Target() {
		return this.p1Target;
	}

	public Enemy getP2Target() {
		return this.p2Target;
	}

	public Random getRandom() {
		return this.random;
	}

	public int getHighlightedCard() {
		return this.highlightedCard;
	}

	public boolean getAllowCombatInputs() {
		return this.allowCombatInputs;
	}

	/**
	 * @return player one.
	 */
	public Character getPlayerOne() {
		return this.playerOne;
	}

	public Character getPlayerTwo() {
		return this.playerTwo;
	}


	/**
	 * @return the spritebatch sprites will be rendered to.
	 */
	public SpriteBatch getSpriteBatch() {
		return this.batch;
	}

	public OrthographicCamera getCamera() {
		return this.camera;
	}

	/**
	 * @return the font being used.
	 */
	public BitmapFont getFont() {
		return this.font;
	}


	public Instance getInstance() {
		return this.instance;
	}

	/**
	 * @return the vector which stores the current mouse coordinates relative to the camera's unprojected view.
	 */
	public Vector3 getMouseCoordinates() {
		return this.mouse_position;
	}

	/**
	 * draws player one's hand.
	 * sprite.setRotation() could be very useful for these algorithms.
	 */
	public void drawP1Hand() {
		if (this.playerTurn) {
			if (!pauseHand) {
				this.p1Hand.setHandDestination(batch, 30);
			}
			for (int i = 0; i < this.p1Hand.getSize(); i++) {
				this.p1Hand.get(i).updatePosition(false, 1);
				this.p1Hand.get(i).getSprite().draw(batch);
			}
			if (highlightedCard > -1 && highlightedCard < this.p1Hand.getSize() && this.p1Hand.get(highlightedCard).checkCollision(mouse_position.x, mouse_position.y)) {
				this.p1Hand.get(highlightedCard).getSprite().draw(batch);
			}
		}
	}

	/**
	 * draws player 2's hand.
	 */
	public void drawP2Hand() {

	}

	/**
	 * updates the current mouse coordinates.
	 */
	public void updateMouseCoordinates() {
		//mouse_position.set(Gdx.input.getX(), Gdx.input.getY(), 0);
		this.camera.unproject(mouse_position.set(Gdx.input.getX(), Gdx.input.getY(), 0));
	}

	/**
	 * draws a card from the top of the draw pile. displays it going from the
	 * draw pile to the hand.
	 */
	public void drawPlayerOneCard(SpriteBatch batch) {
		Sprite bruh = this.playerOne.getDeck().get(0).getSprite();
		bruh.setSize(280, 280);

		bruh.setCenter(800f, 100);

		bruh.rotate(30f);
		this.playerOne.getDeck().get(0).drawCard(batch);
	}

	/**
	 * plays the animation that signals the player turn.
	 */
	public void playPlayerTurnAnimation(float elapsedTime) {
		if (!this.playerTurnAnimation.isAnimationFinished(elapsedTime)) {
			this.batch.draw(this.playerTurnAnimation.getKeyFrame(elapsedTime, false), 0, 0);

		}
		//this.batch.draw(this.playerTurnAnimation.getKeyFrame(elapsedTime, false), 0, 0);

	}

	/**
	 * draws a specific card from the drawpile and displays it going from the drawpile to the hand.
	 *
	 * @param index the index of the card to be drawn.
	 */
	public void drawCard(int index, int whichPlayer) {
		if (whichPlayer == 1) {
			this.p1Hand.addCard(this.p1DrawPile.get(index));
			this.p1DrawPile.removeCard(index);
			this.p1Hand.get(this.p1Hand.getSize() - 1).getSprite().setSize(140, 140);
			this.p1Hand.get(this.p1Hand.getSize() - 1).getSprite().setCenter(800f, 0);
			this.p1Hand.get(this.p1Hand.getSize() - 1).getSprite().draw(batch);
		} else if (whichPlayer == 2) {
			this.p2Hand.addCard(this.p2DrawPile.get(index));
			this.p2DrawPile.removeCard(index);
			Sprite newCard = this.p2Hand.get(index).getSprite();
			newCard.setScale(.55555f);
			newCard.setCenter(-675, 0);
			System.out.println("uh oh, there is no player 2");
		}
	}


	/**
	 * draws player one's deck.
	 */
	public void drawP1Deck() {
		this.playerOne.getDeck().getDeckVisual().setPosition(-700, -350);
		this.playerOne.getDeck().drawDeckVisual(this.batch);
	}

	/**
	 *  ends the player's turn. 
	 */ {
		this.playerTurn = false;

	}

	/**
	 * plays the top card of every enemy. also sets their target to a random player based on the rng seed.
	 */
	public void performEnemyActions() {
		for (int i = 0; i < this.instance.getEnemies().size(); i++) {
			this.instance.getEnemies().get(i).doAction(this);
		}


	}

	/**
	 * handles start of turn things.
	 */
	public void startPlayerTurn() {
		this.playerOne.setEnergyToEnergyPerTurn();
		for(int i = 0; i < UntitledGame.playerOne.getCardsPerTurn(); i++)
		{
		this.playerOne.getDrawPile().drawCard();
		}
		this.playerTwo.setEnergyToEnergyPerTurn();
		this.playerTurn = true;
	}

	/**
	 * draws player two's deck.
	 */
	public void drawP2Deck() {
		this.playerTwo.getDeck().getDeckVisual().setPosition(-675, 0);
		this.playerTwo.getDeck().drawDeckVisual(this.batch);
	}

	/**
	 * draws health bars on the screen as a ratio of player's current health to max health.
	 */
	public void drawHealthbars() {
		Pixmap pixmap = new Pixmap(1, 1, Format.RGBA8888);
		pixmap.setColor(Color.WHITE);
		pixmap.drawPixel(0, 0);
		Texture texture = new Texture(pixmap); //remember to dispose of later
		pixmap.dispose();
		TextureRegion region = new TextureRegion(texture, 0, 0, 1, 1);
		this.HealthBars = new ShapeDrawer(this.batch, region);
		// p1 health container
		this.HealthBars.filledRectangle(200, 250, 150f, 20, new Color(Color.BLACK));
		// p1 health as a ratio of current health to max health
		this.HealthBars.filledRectangle(200, 250, 150f * this.playerOne.getHealth() / this.playerOne.getMaxHealth(), 20, new Color(Color.CYAN));
		// p2 health container
		this.HealthBars.filledRectangle(450, 250, 150f, 20, new Color(Color.BLACK));
		// p1 health as a ratio of current health to max health.
		this.HealthBars.filledRectangle(450, 250, 150f * this.playerTwo.getHealth() / this.playerTwo.getMaxHealth(), 20, new Color(Color.SCARLET));

	}


	public void shadeBackground() {
		this.allowCombatInputs = false;
		this.shader.draw(this.batch);
	}

	public void showP1DrawPile() {
		this.shadeBackground();
		this.p1DrawPile.showCards(this.batch);
	}

	public void probep1Deckinput() {
		if (this.p1DeckBox.checkCollision(this.mouse_position) && Gdx.input.isButtonJustPressed((Input.Buttons.LEFT)) || this.viewingDeck == true
		) {
			this.viewingDeck = true;
			this.showP1DrawPile();
			this.exitSprite.draw(this.batch);

		}

		if (this.viewingDeck == true && this.exitHitBox.checkCollision(this.mouse_position) && Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
			this.viewingDeck = false;
			this.p1DrawPile.stopShowingCards();
			this.allowCombatInputs = true;
		}

	}

	public static Float getSpriteCenterX(Sprite s) {
		Float centerX = s.getX() + s.getWidth() / 2;
		return centerX;
	}

	public static Float getSpriteCenterY(Sprite s) {
		Float centerY = s.getY() + s.getHeight() / 2;
		return centerY;
	}

	public void endPlayerTurn()	{
		this.p1Hand.clear();
		highlightedCard = -1;
		this.playerTurn = false;
	}

	@Override
	public boolean keyDown(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(float xAmount, float yAmount) {
		//if (!this.allowCombatInputs)
		// TODO Auto-generated method stub
		return false;
	}


	

}
