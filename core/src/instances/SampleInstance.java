package instances;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector3;

import enemies.*;
import space.earlygrey.shapedrawer.ShapeDrawer;

public class SampleInstance extends Instance {

	
	public SampleInstance(SpriteBatch batch, Random random, OrthographicCamera camera)
	{
		super(batch, random, camera);
		healthBarPositions = new Vector3[1];
		healthBarWH = new Vector3[2];
		healthTextPositions = new Vector3[1];

		FileHandle[] musicSource = Gdx.files.internal("assets\\Music").list();
		this.background = new Sprite (new TextureRegion(new Texture("assets\\Backgrounds\\background 1.png"), 0, 0, 1600, 944));
		this.music = Gdx.audio.newMusic(musicSource[random.nextInt(musicSource.length)]);
		this.enemies.put((Integer)0, new ShadowWench(random, camera));
		this.enemies.get((Integer)0).setPosition(1075, 290);
		
		
	    this.intentionPositions = new Vector3[this.enemies.size()];

		//todo: have the enemy determine the intention position because only they know how tall they are lmao
	   this.intentionPositions[0] = new Vector3(300, 130, 0);
	  // this.camera.project(this.intentionPositions[0]);
	   
	   this.healthBarPositions[0] = new Vector3(1000, 250, 0);
		this.healthBarWH[0] = new Vector3(150f, 20, 0);
		this.healthBarWH[1] = new Vector3(150f * this.enemies.get(0).getHealth() / this.enemies.get(0).getMaxHealth(), 20f, 0);
		
		for(int i = 0; i < this.healthBarWH.length; i++)
		{
			//this.camera.project(this.healthBarWH[i]);
		}
	//	this.camera.project(this.healthBarPositions[0]);
	    
	   
	}

	@Override
	public void drawEnemyHealth(ShapeDrawer drawer, SpriteBatch batch, BitmapFont font ) {
		
		Vector3 blah = this.healthBarPositions[0];
		
		Vector3 bleh = this.healthBarWH[0];
		Vector3 bleeh = this.healthBarWH[1];
		
		Pixmap pixmap = new Pixmap(1, 1, Format.RGBA8888);
		pixmap.setColor(Color.WHITE);
		pixmap.drawPixel(0, 0);
		Texture texture = new Texture(pixmap); //remember to dispose of later
		pixmap.dispose();
		TextureRegion region = new TextureRegion(texture, 0, 0, 1, 1);
		drawer = new ShapeDrawer(batch, region);
		// p1 health container
		if(this.enemies.get(0).isAlive())
		{
		drawer.filledRectangle(blah.x, blah.y, bleh.x , bleh.y, new Color(Color.BLACK));
		// p1 health as a ratio of current health to max health
		drawer.filledRectangle(blah.x, blah.y,  bleh.x * this.enemies.get(0).getHealth() / this.enemies.get(0).getMaxHealth(), bleeh.y, new Color(Color.RED));
		
		font.draw(batch,this.enemies.get(0).getHealthVisual(), 1035, 272);	
		}
	}
		
	
	
}
