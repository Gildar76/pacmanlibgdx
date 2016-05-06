package net.gildargaming.pacmanx.ai;

import com.badlogic.gdx.math.Vector2;

import net.gildargaming.pacmanx.util.Vector2i;

public class Node {
	public Vector2i nodePos;
	public Node parent;
	public double fCost, gCost, hCost;
	
	public Node(Vector2i v, double gCost, double hCost, Node parent) {
		this.nodePos = v;
		this.nodePos.x = this.nodePos.x;
		this.nodePos.y = this.nodePos.y;
		
		this.gCost = gCost;
		this.hCost = hCost;
		this.fCost = this.gCost + this.hCost;
		this.parent = parent;
		
	}
	
}
