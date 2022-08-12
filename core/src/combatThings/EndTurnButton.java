package combatThings;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.game.Combat;
import com.mygdx.game.HitBox;

public class EndTurnButton {
	
	// takes the sprite representing the end turn button
	final private Sprite sprite = new Sprite (new Texture(Gdx.files.internal("combat assets\\end turn image.png")));
	// the combat object the end turn button will be utilizing.
	private Combat combat;
	
	private OrthographicCamera camera;
	
	private Vector3 center;
	
	private HitBox hitbox;
	
	
	/**
	 * @param combat the combat object the end turn button will be using.
	 */
	public EndTurnButton(Combat combat, OrthographicCamera camera)
	{
		this.combat = combat;
		this.camera = camera;
		this.center = new Vector3(0, 0, 0);	
		this.center.set(720f, 720f, 0);
		this.camera.project(this.center);
		this.sprite.setCenter(720f, 720f);
		this.sprite.setOriginCenter();
		this.sprite.setScale(.66f);
		this.hitbox = new HitBox(this.camera, 682f, 730f,
				880f, 768f);
	}
	
	
	/**
	 *  draws the sprite for the end turn button.
	 */
	public void drawEndTurnButton()
	{
		this.sprite.draw(this.combat.getSpriteBatch());
	}
	
	/**
	 *  checks if the mouse is over the end turn button.
	 */
	public void checkButton(Vector3 mouseCoordinates)
	{
		float mouseX = this.combat.getMouseCoordinates().x;
		float mouseY = this.combat.getMouseCoordinates().y;
		float leftX = this.sprite.getBoundingRectangle().getX() + 410;
		float rightX = this.sprite.getBoundingRectangle().getX() + 610;
		float topY = this.sprite.getBoundingRectangle().getY() + 260;
		float bottomY = this.sprite.getBoundingRectangle().getY() + 220;
		
		//  if(mouseX >= leftX && mouseX <= rightX && mouseY >= bottomY && mouseY <= topY
				  if(this.hitbox.checkCollision(mouseCoordinates) && Gdx.input.isButtonJustPressed((Input.Buttons.LEFT))
						  &&this.combat.getAllowCombatInputs())
			 {
				 //this.combat.getFont().draw(this.combat.getSpriteBatch(), "OKAY NOW THIS IS EPIC", 900, 700);
                 combat.endPlayerTurn();
			 }
	      
	}
	
	
	
	

}
