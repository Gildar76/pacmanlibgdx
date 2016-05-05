package net.gildargaming.pacmanx.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import net.gildargaming.pacmanx.Direction;
import net.gildargaming.pacmanx.MainGame;
import net.gildargaming.pacmanx.entity.Mob;
import net.gildargaming.pacmanx.entity.Player;
import net.gildargaming.pacmanx.graphics.SpriteHandler;
import net.gildargaming.pacmanx.world.Level;

public class GameScreen implements Screen {


	public OrthographicCamera cam;
	public Viewport vp;
	public MainGame game;
	public Level level;
	private TextureAtlas atlas;
	private SpriteHandler testSprite;

	
	
	public GameScreen(MainGame game) {
		this.game = game;
		cam = new OrthographicCamera();
		vp = new FitViewport(game.WIDTH, game.HEIGHT, cam);
		atlas = new TextureAtlas("pm2.pack");
		level = new Level("pmbaselevel.tmx", this);

		
		int[] animInfo = {0,0,16,16,2,0};
		//testSprite = new SpriteHandler(this, "AIPac", 100, 100, 16, 16, 16, 0);
		//animInfo[0] = startTileX;
		//animInfo[1] = startTileY;
		//animInfo[2] = tileSizeX;
		//animInfo[3] = tileSizeY;
		//animInfo[4] = endTileX;
		//animInfo[5] = endTileY;
		//animInfo[6] = animationspeed
		//testMob = new Mob(100, 100, this, "AIPac", animInfo, 0.1f);

		float[] xy = level.getPlayerStartPosition();

		
	}
	
	public TextureAtlas getAtlas() {
		return atlas;
	}
	
	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void resize(int width, int height) {
		vp.update(width, height);
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}
	
	public void update(float delta) {
		level.update(delta);
		//testMob.update(delta);

		
	}
	
	public void render(float delta) {
		update(delta);
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		game.batch.setProjectionMatrix(cam.combined);
		level.render(delta);
		game.batch.begin();
		level.ghosts[0].render(game.batch, delta);
		//game.batch.draw(img, 0, 0);
		level.player.render(game.batch, delta);
		//testSprite.draw(game.batch);
		//testMob.render(game.batch, delta);
		game.batch.end();
	}
	

}
