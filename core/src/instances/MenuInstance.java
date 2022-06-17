package instances;


import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.game.Combat;
import com.badlogic.gdx.graphics.Texture;

import enemies.*;
import space.earlygrey.shapedrawer.ShapeDrawer;

public class MenuInstance extends Instance {
	public MenuInstance(SpriteBatch batch, Random random, OrthographicCamera camera) {
		super(batch, random, camera);
		
		this.background = new Sprite(new Texture(Gdx.files.internal("Sprites\\white pixel.png")));
		 this.background.setScale(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		 this.background.setCenter(800f, 450f);
		 this.background.scale(10f);
	}
}
