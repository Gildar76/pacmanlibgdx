package net.gildargaming.pacmanx;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import net.gildargaming.pacmanx.screens.GameScreen;

public class MainGame extends Game {
	public SpriteBatch batch;
	public static int WIDTH, HEIGHT;
	public GameScreen screeen;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		WIDTH = 320;
		HEIGHT = 320;
		screen = new GameScreen(this);
		setScreen(screen);
		
	}

	@Override
	public void render () {
		super.render();
	}
}
