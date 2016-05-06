package net.gildargaming.pacmanx.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;

public class Debug {

	ShapeRenderer shape;
	int[][] grid;
	
	public Debug(int[][] grid) {
		this.grid = grid;
		shape = new ShapeRenderer();
	}
	
	public void render() {
		  // Gridlines
	    shape.begin(ShapeType.Line);
	    shape.setColor(Color.WHITE);
	    for (int i = 0; i < Gdx.graphics.getHeight() / 32; i++) {
	        shape.line(0, i * 32, Gdx.graphics.getWidth(), i * 32);
	    }
	    for (int i = 0; i < Gdx.graphics.getWidth() / 32; i++) {
	        shape.line(i * 32, 0, i * 32, Gdx.graphics.getHeight());
	    }
	    shape.end();
	}
}
