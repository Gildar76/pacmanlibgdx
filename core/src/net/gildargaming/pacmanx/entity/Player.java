package net.gildargaming.pacmanx.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

import net.gildargaming.pacmanx.Direction;
import net.gildargaming.pacmanx.screens.GameScreen;
import net.gildargaming.pacmanx.world.Level;

public class Player extends Mob {

	private Animation goLeft, goRight, goUp, GoDown;
	private int rotation;

	
	public Player(float x, float y, GameScreen screen, String regionName, int[] animInfo, float animSpeed, Level level) {
		super(x, y, screen, regionName, animInfo, animSpeed, level);
		setPlayerAnimations(animInfo, regionName);
	}	

	private void setPlayerAnimations(int[] animInfo, String regionName) {
		TextureRegion region = screen.getAtlas().findRegion(regionName);


		//Read the animation as told by the array AnimInfo. Not pretty!
		//animInfo[0] = startTileX;
		//animInfo[1] = startTileY;
		//animInfo[2] = tileSizeX;
		//animInfo[3] = tileSizeY;
		//animInfo[4] = endTileX;
		//animInfo[5] = endTileY;
		Array<TextureRegion> frames = new Array<TextureRegion>();		
		for (int i = animInfo[0]; i <= animInfo[4]; i++) {
			for (int j = animInfo[1]; j <= animInfo[5]; j++ ) {
				frames.add(new TextureRegion(region, i*animInfo[2], j*animInfo[3], animInfo[2],animInfo[3]));				
			}
		}
		for (TextureRegion r : frames) {
			r.flip(true, false);
		}
		goLeft = new Animation(animSpeed, frames );
		frames.clear();		
		for (TextureRegion r : frames) {
			//r.rot(true, false);
		
		}
	
	}
	public void detectInput() {
		if (Gdx.input.isKeyPressed(Keys.A) || Gdx.input.isKeyPressed(Keys.LEFT) ) {
			this.setDirection(Direction.LEFT);
		}
		if (Gdx.input.isKeyPressed(Keys.D) || Gdx.input.isKeyPressed(Keys.RIGHT) ) {
			this.setDirection(Direction.RIGHT);
		}
		if (Gdx.input.isKeyPressed(Keys.W) || Gdx.input.isKeyPressed(Keys.UP) ) {
			this.setDirection(Direction.UP);
		}
		if (Gdx.input.isKeyPressed(Keys.S) || Gdx.input.isKeyPressed(Keys.DOWN) ) {
			this.setDirection(Direction.DOWN);
		}
	}

	public void setDirection(Direction dir) {
		super.setDirection(dir);
		if (dir == Direction.LEFT) rotation = 90;
		if (dir == Direction.RIGHT) rotation = 270;
		if (dir == Direction.UP) rotation = 0;
		if (dir == Direction.DOWN) rotation = 180;
	}
	public void update(float delta) {
		detectInput();
		level.eatFood(boundary.x, boundary.y);

		super.update(delta);
	}
	
	public void render(SpriteBatch batch, float delta) {

		currentFrame = animation.getKeyFrame(stateTimer, true);
		if (this.direction == Direction.LEFT) {
			//currentFrame.flip(true, false);
		}

		batch.draw(currentFrame, xPos, yPos,
			    currentFrame.getRegionWidth() / 2.0f,
			    currentFrame.getRegionHeight() / 2.0f, currentFrame.getRegionWidth(),
			    currentFrame.getRegionHeight(), 1f, 1f, rotation, false);
		stateTimer += delta;
		
	}
}
