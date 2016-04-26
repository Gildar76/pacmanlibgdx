package net.gildargaming.pacmanx.entity;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

import net.gildargaming.pacmanx.Direction;
import net.gildargaming.pacmanx.graphics.SpriteHandler;
import net.gildargaming.pacmanx.screens.GameScreen;
import net.gildargaming.pacmanx.world.Level;

public class Mob extends Entity {

	private boolean isMoving;
	protected Direction direction;
	protected Animation animation;
	protected float animSpeed;
	protected TextureRegion currentFrame;
	protected float stateTimer;
	private Sprite sprite;
	protected GameScreen screen;
	private float movementSpeed;
	
	
	public Mob(float x, float y, GameScreen screen, String regionName, int[] animInfo, float animSpeed) {
		super(x,y);
		this.animSpeed = animSpeed;
		this.screen = screen;
		stateTimer = 0f;
		movementSpeed = 0f; 
		TextureRegion region = screen.getAtlas().findRegion(regionName);

		Array<TextureRegion> frames = new Array<TextureRegion>();
		//Read the animation as told by the array AnimInfo. Not pretty!
		//animInfo[0] = startTileX;
		//animInfo[1] = startTileY;
		//animInfo[2] = tileSizeX;
		//animInfo[3] = tileSizeY;
		//animInfo[4] = endTileX;
		//animInfo[5] = endTileY;
		
		for (int i = animInfo[0]; i <= animInfo[4]; i++) {
			for (int j = animInfo[1]; j <= animInfo[5]; j++ ) {
				frames.add(new TextureRegion(region, i*animInfo[2], j*animInfo[3], animInfo[2],animInfo[3]));
				//System.out.println(i);
				//System.out.println(j);
				
			}
		}
		animation = new Animation(animSpeed, frames );
		frames.clear();
		currentFrame = new TextureRegion();
		currentFrame = animation.getKeyFrame(stateTimer, true);
		sprite = new Sprite(currentFrame);
	}
	
	public float getMovementSpeed() {
		return this.movementSpeed;
	}
	
	public void setMovementSpeed(float ms) {
		movementSpeed = ms;
		
	}
	
	
	public void update(float delta)  {
		//Update position, if collision occurs, restore position to previous one
		float oldxPos = xPos;
		float oldYpos = yPos;
//		System.out.println(this.movementSpeed);
//		System.out.println(this.direction);
//		System.out.println(xPos);
		if (direction == Direction.RIGHT) {
			xPos += movementSpeed * delta;
		} else if (direction == Direction.LEFT) {
			xPos -= movementSpeed * delta;
		} else if (direction == Direction.DOWN) {
			yPos -= movementSpeed * delta;
		} else if (direction == Direction.UP) {
			yPos += movementSpeed * delta;
		}
		System.out.print(this.direction);
		if (screen.level.checkTileIntersect(xPos + this.sprite.getWidth() / 2, yPos + this.sprite.getHeight() / 2)) {
			xPos = oldxPos;
			yPos = oldYpos;
			System.out.println("blocked");

		}
		


		
	}
	
	public void setDirection(Direction dir) {
		direction = dir;
		
	}
	
	public void render(SpriteBatch batch, float delta) {

		currentFrame = animation.getKeyFrame(stateTimer, true);
		if (this.direction == Direction.LEFT) {
			//currentFrame.flip(true, false);
		}
		batch.draw(currentFrame, xPos, yPos, currentFrame.getRegionWidth(), currentFrame.getRegionHeight());
		stateTimer += delta;
		
	}
}
