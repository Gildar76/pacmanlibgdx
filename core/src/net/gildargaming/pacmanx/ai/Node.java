package net.gildargaming.pacmanx.ai;

import com.badlogic.gdx.math.Vector2;

public class Node {
	public Vector2 nodePos;
	public Node parent;
	public double fCost, gCost, hCost;
	
	public Node(Vector2 pos, double gCost, double hCost, Node parent) {
		this.nodePos = pos;
		this.nodePos.x = Math.round(this.nodePos.x);
		this.nodePos.y = Math.round(this.nodePos.y);
		
		this.gCost = gCost;
		this.hCost = hCost;
		this.fCost = this.gCost + this.hCost;
		this.parent = parent;
		
	}
	
}
