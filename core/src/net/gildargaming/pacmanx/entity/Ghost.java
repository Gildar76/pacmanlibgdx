package net.gildargaming.pacmanx.entity;

import java.util.*;

import com.badlogic.gdx.math.Vector2;

import net.gildargaming.pacmanx.Direction;
import net.gildargaming.pacmanx.ai.Node;
import net.gildargaming.pacmanx.screens.GameScreen;
import net.gildargaming.pacmanx.world.Level;

public class Ghost extends Mob {
	private List<Node> path;
	public Ghost(float x, float y, GameScreen screen, String regionName, int[] animInfo, float animSpeed, Level level) {
		super(x, y, screen, regionName, animInfo, animSpeed, level);
		
	}
	
	public void findPathToPlayer(Vector2 playerPos) {
		Vector2 pos = new Vector2(this.xPos / 16, this.yPos / 16);
		
		path = level.findPath(pos, playerPos);


		
		
		
	}

	public void update(float delta) {

		if (path == null) return;
		Vector2 pos = new Vector2(this.xPos / 16, this.yPos / 16);
		if (path.size() > 0) {
			Vector2 nextPos = path.get(path.size() - 1).nodePos;			
			if ((int)nextPos.x > (int)pos.x) this.direction = Direction.RIGHT;
			if ((int)nextPos.x < (int)pos.x) this.direction = Direction.LEFT;
			if ((int)nextPos.y > (int)pos.y) this.direction = Direction.UP;			
			if ((int)nextPos.y < (int)pos.y) this.direction = Direction.DOWN;			
			System.out.print(this.direction);
			System.out.print(nextPos.x);
			System.out.print(nextPos.y);
			System.out.print(pos.x);
			System.out.print(pos.y);
			
			//path.remove(path.size() - 1);
			System.out.println("Updating");
		}
		super.update(delta);
	}

}
