package net.gildargaming.pacmanx.entity;

import java.util.*;

import com.badlogic.gdx.math.Vector2;

import net.gildargaming.pacmanx.Direction;
import net.gildargaming.pacmanx.ai.Node;
import net.gildargaming.pacmanx.screens.GameScreen;
import net.gildargaming.pacmanx.util.Vector2i;
import net.gildargaming.pacmanx.world.Level;

public class Ghost extends Mob {
	private List<Node> path;
	public Ghost(float x, float y, GameScreen screen, String regionName, int[] animInfo, float animSpeed, Level level) {
		super(x, y, screen, regionName, animInfo, animSpeed, level);
		
	}
	
	public void findPathToPlayer(Vector2i playerPos) {
		Vector2i pos = new Vector2i((this.xPos + 0) / 16, (this.yPos + 0) / 16);
		
		path = level.findPath(pos, playerPos);


		
		
		
	}

	public void update(float delta) {

		if (path == null) return;
		Vector2i pos = new Vector2i((this.xPos + 8 ) / 16, (this.yPos + 8) / 16);
		if (path.size() > 0) {
			Vector2i nextPos = path.get(path.size() - 1).nodePos;			
			if ((int)nextPos.x * 16 > (int)xPos +0  ) this.direction = Direction.RIGHT;
			if ((int)nextPos.x * 16 < (int)xPos - 0) this.direction = Direction.LEFT;
			if ((int)nextPos.y * 16 > (int)yPos + 0 ) this.direction = Direction.UP;			
			if ((int)nextPos.y * 16 < (int)yPos - 0 ) this.direction = Direction.DOWN;			
			System.out.println(this.direction);
			System.out.print(nextPos.x * 16 + " ");
			System.out.println(nextPos.y * 16);
			System.out.print(pos.x + " ");
			System.out.println(pos.y);
			System.out.print(this.xPos + " ");
			System.out.println(this.yPos);
			
			//path.remove(path.size() - 1);
			//System.out.println("Updating");
		}
		super.update(delta);
	}

}
