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
	    for (int i = 0; i < Gdx.graphics.getHeight() / 1; i++) {
	        //shape.line(0, i * 16, Gdx.graphics.getWidth(), i * 16);
	    }
	    for (int i = 0; i < Gdx.graphics.getWidth() / 1; i++) {
	        //shape.line(i * 16, 0, i * 16, Gdx.graphics.getHeight());
	    }
	    shape.end();
	}
}
