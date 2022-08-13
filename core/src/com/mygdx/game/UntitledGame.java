package com.mygdx.game;


import java.util.Random;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;

import enemies.*;
import characters.*;
import characters.Character;
import cardThings.*;
import gear.*;
import instances.Instance;
import instances.MenuInstance;
import instances.SampleInstance;
import space.earlygrey.shapedrawer.ShapeDrawer;
import combatThings.DeckViewer;
public class UntitledGame extends ApplicationAdapter {
	public static SpriteBatch batch;
	Texture img;
	TextureAtlas textureAtlas;
    
    TextureRegion textureRegion;
    Animation<TextureRegion> animation;
    TextureRegion background;
    float elapsedTime;
    Pixmap pm;
    String myText;
    Music music;
    public static Combat combat;
    MainMenu mainMenu;
    TextureAtlas idle;
    public static Warrior playerOne;
    public static Mage playerTwo;
    BitmapFont font;
    public static Instance instance;
    public static Random random;
    private OrthographicCamera camera;
    StretchViewport viewport;
    Sprite rectangle;
    Pixmap shading;
    boolean isInMainMenu;
	private MenuInstance mainMenuInstance;
	@Override
	public void create () {
		
	 	camera = new OrthographicCamera(1600, 900);   
	 	float w = Gdx.graphics.getWidth();
		float h = Gdx.graphics.getHeight();
		
		camera.position.set(1600 / 2, 900 / 2, 0);
		viewport = new StretchViewport(1600, 900, camera);
	 	camera.update();
	 	
	 	
		batch = new SpriteBatch();
		img = new Texture("Backgrounds\\temp 1.jpg");
		pm = new Pixmap(Gdx.files.internal("cursor\\cool-cursor-png-8.png"));
		random = new Random();
		random.setSeed(this.random.nextLong());
	    
		myText = "This is a stink test";
		//music = Gdx.audio.newMusic(Gdx.files.internal("Music\\Masked Dedede - Super Smash Bros. Ultimate.mp3"));
	background = new TextureRegion(new Texture("Backgrounds\\temp 1.jpg"), 0, 0, 1600, 900);

	    // music.setLooping(true);
	     //music.play();
	     playerOne = new Warrior(1, this.camera);
	     playerTwo = new Mage(2, this.camera);
	     this.mainMenuInstance = new MenuInstance(this.batch, this.random, this.camera);
	    instance = new SampleInstance(this.batch,this.random, this.camera);
	     
	     font = new BitmapFont(Gdx.files.internal("texts\\healthtext.fnt"));
	     font.getData().setScale(0.77f);
	     
	    this.mainMenu = new MainMenu(this.mainMenuInstance, this.random, this.batch, this.camera, this.font);
	 	combat = new Combat(playerOne, playerTwo, this.instance, this.random, this.batch, this.camera, this.font);
	 
		Gdx.graphics.setCursor(Gdx.graphics.newCursor(pm, 10, 10));

		pm.dispose();

		
	}

	@Override
	public void render () {
		
		elapsedTime += Gdx.graphics.getDeltaTime();
		Gdx.gl.glClearColor(0, 0, 0, 0);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		camera.update();
		batch.setProjectionMatrix(camera.combined);
		
		
		batch.begin();
		//batch.draw(background, 0, 0);
		
		if (this.mainMenu.isInMainMenu()) {
			this.mainMenu.update(this.font);
		}
		else {
			combat.update(this.elapsedTime, this.font);
		}

		batch.end();
		
	}
	
	//This just doesn't do anything since it tries to dispose of only things that have already been disposed of
	//There aren't any null pointers anymore when I just get rid of it so yeah, it's going to stay like this until there's a problem
	/*
	@Override
	public void dispose () {
		batch.dispose();
		textureAtlas.dispose();
		playerOne.getIdleAtlas().dispose();
		pm.dispose();
		PlayerTwo.getIdleAtlas().dispose();
		this.font.dispose();
	}
	*/
}
