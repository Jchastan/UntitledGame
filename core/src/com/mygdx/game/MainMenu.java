package com.mygdx.game;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

import instances.Instance;
import space.earlygrey.shapedrawer.ShapeDrawer;

public class MainMenu implements InputProcessor {

    SpriteBatch batch;
	private Vector3 mouse_position;
	private BitmapFont font;
	private OrthographicCamera camera;
	//  a stage which has information such as the background, the music, and what enemies should be implemented.
	private Instance instance;
	private ShapeDrawer demoCombatButton;
	private boolean isInMainMenu;
	private Random random;
	
	public MainMenu(Instance instance, Random random, SpriteBatch batch, OrthographicCamera camera, BitmapFont font) {
		this.instance = instance;
		this.random = random;
		this.batch = batch;
		this.camera = camera;
		this.font = font;
		this.mouse_position = new Vector3(0,0,0);
		Pixmap pixmap = new Pixmap(1, 1, Format.RGBA8888);
		pixmap.setColor(Color.WHITE);
		pixmap.drawPixel(0, 0);
		Texture texture = new Texture(pixmap); //remember to dispose of later
		pixmap.dispose();
		TextureRegion region = new TextureRegion(texture, 0, 0, 1, 1);
		this.demoCombatButton = new ShapeDrawer(this.batch, region);
		this.isInMainMenu = true;
	}

	public void update(BitmapFont font) {
		this.instance.drawbackground(this.batch);
		this.updateMouseCoordinates();
		this.demoCombatButton.filledRectangle(70, 270, 240, 40, Color.BLACK);
		this.font.setColor(Color.BLACK);
		this.font.draw(this.batch, "That Which Has No Name", 700, 700, 400, 200, false);
		this.font.setColor(Color.WHITE);
		this.font.draw(this.batch, "DEMO COMBAT", 100, 300, 200, 100, false); 
		this.checkSelection();
	}

	public void checkSelection() {
		Rectangle tmp = new Rectangle(70,270,240,40);
		if (tmp.contains(mouse_position.x, mouse_position.y) && Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
			this.isInMainMenu = false;
		}
	}

	public boolean isInMainMenu() {
		return this.isInMainMenu;
	}

	public void updateMouseCoordinates() {
		 //mouse_position.set(Gdx.input.getX(), Gdx.input.getY(), 0);
		 this.camera.unproject(mouse_position.set(Gdx.input.getX(), Gdx.input.getY(), 0)); 
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
	public boolean scrolled(float yAmount, float xAmount) {
		// TODO Auto-generated method stub
		return false;
	}
	
}
