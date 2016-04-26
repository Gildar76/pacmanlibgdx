package net.gildargaming.pacmanx.graphics;

import java.lang.Object;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import net.gildargaming.pacmanx.screens.GameScreen;



public class SpriteHandler extends Sprite {
	private TextureRegion spriteRegion;
	
	
	public SpriteHandler(GameScreen screen, String regionName, int x, int y, int width, int height, int xOffset, int yOffset) {
		super(screen.getAtlas().findRegion(regionName));
		spriteRegion = new TextureRegion(this.getTexture(), xOffset, yOffset, width, height );
		setBounds(x, y, 16,16);
		System.out.print(x);
		System.out.print(y);
		System.out.print(width);
		System.out.print(height);
		
		setRegion(spriteRegion);
		
	}
}
