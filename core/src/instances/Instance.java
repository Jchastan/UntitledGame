package instances;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.game.Combat;

import enemies.*;
import space.earlygrey.shapedrawer.ShapeDrawer;

// represents a specifc "Instance" of a combat, meaning what enemies music background etc will be set up.
public abstract class Instance {
	
	
	protected Vector3[] healthBarPositions;
	protected Vector3[] healthBarWH;
	protected Vector3[] healthTextPositions;
	
	
	//Hash map containing all enemies.
	protected HashMap<Integer, Enemy> enemies = new HashMap<Integer, Enemy>();
	// the background texture.
	protected Sprite background;
	// the music which will be played on loop.
	protected Music music;
	
	protected SpriteBatch batch;
	
    protected Vector3[] intentionPositions;
    
    protected OrthographicCamera camera;
    
    protected Random random;
	
	
	public Instance(SpriteBatch batch, Random random, OrthographicCamera camera)
	{
		this.camera = camera;
		this.batch = batch;
		this.random = random;
	}
	
	
	
	
	// getter methods.
	public HashMap<Integer, Enemy> getEnemies()
	{
		return this.enemies;
	}
	
	public Sprite getBackground()
	{
		return this.background;
	}
	
	public Music getMusic()
	{
		return this.music;
	}
	
	// add an enemy
	public void addEnemy(Enemy enemy)
	{
		this.enemies.put((Integer)this.enemies.size(), enemy);
	}
	
	// removes an enemy. TO BE CHANGED DO NOT USE.
	public void removeEnemy(String key)
	{
		Iterator starPlatinum = this.enemies.entrySet().iterator();
		
		while(starPlatinum.hasNext())
				{
			      Map.Entry  entry = (Map.Entry)starPlatinum.next();
			      if(entry.getKey().equals(key))
			      {
			    	  starPlatinum.remove();
			      }
				}
		
		
		 
	   
	}
	
	/**
	 * @param batch the batch to which the background will be drawn to
	 */
	public void drawbackground(SpriteBatch batch)
	{
		
		this.background.draw(batch);
	}
	
	/**
	 *  draws all enemies that currently have health above 0.
	 */
	public void drawEnemies(SpriteBatch batch, float elapsedTime)
	{
		for(int i = 0; i < this.enemies.size(); i++)
		{
			Enemy tmp = this.enemies.get(i);
			if(tmp.getHealth() > 0) {
				tmp.drawIdle(batch, elapsedTime);
				tmp.drawIntent(batch);
			}
		}
	}
	
	public void playMusic()
	{
		this.music.setLooping(true);
		this.music.play();
	}
	
	/**
	 * @param drawer the shapedrawer
	 * 
	 * draws all enemy health bars in the instance.
	 */
	public void drawEnemyHealth(ShapeDrawer drawer, SpriteBatch batch, BitmapFont font) {
	}
	
	/**
	 * plays the top card of every enemy within an instance.
	 */
	public void attack(Combat combat)
	{
		for(int i = 0; i < this.enemies.size(); i++)
		this.enemies.get(i).doAction(combat);
	}
	
	public void setEnemyTargets()
	{
		for(int i = 0; i < this.enemies.size(); i++)
		{
			this.enemies.get(i).autoTarget();
		}
	}
	
	
	
	public void showEnemyIntentions()
	{
		for(int i = 0; i < this.enemies.size(); i++)
		{
			if(this.enemies.get(i).isAlive())
			{
				this.enemies.get(i).showIntention().setX(this.intentionPositions[i].x);
				this.enemies.get(i).showIntention().setY(this.intentionPositions[i].y);
				this.enemies.get(i).showIntention().draw(this.batch);
			}
		}
	}	
	
	/**
	 * updates enemies alive status based on their current health.
	 */
	public void updateEnemiesAlive()
	{
		for(int i = 0; i < this.enemies.size(); i++)
		{
			if(this.enemies.get(i).getHealth() <= 0)
			{
				this.enemies.get(i).setAlive(false);
			}
		}
	}

	
	

}
