package net.gildargaming.pacmanx.util;

public class Vector2i {
	public int x,y;
	
	public Vector2i() {
		
	}
	
	public Vector2i(int x, int y) {
		this.x = x;
		this.y = y;
		
	}
	
	public Vector2i(float x, float y) {
		this.x = (int)x;
		this.y = (int)y;
		
	}
}
