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
		this.enemies.put((Integer)1, new SpookyBlobBoi(random, camera));
		this.enemies.get((Integer)0).setPosition(1075, 290);
		this.enemies.get((Integer)1).setPosition(1400, 290);

	   	this.healthBarPositions[0] = new Vector3(1000, 250, 0);
		this.healthBarWH[0] = new Vector3(150f, 20, 0);
		this.healthBarWH[1] = new Vector3(150f * this.enemies.get(0).getHealth() / this.enemies.get(0).getMaxHealth(), 20f, 0);
		
		for(int i = 0; i < this.healthBarWH.length; i++)
		{
			//this.camera.project(this.healthBarWH[i]);
		}
	//	this.camera.project(this.healthBarPositions[0]);
	}
}
