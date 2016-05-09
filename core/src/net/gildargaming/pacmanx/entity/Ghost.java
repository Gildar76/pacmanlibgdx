package net.gildargaming.pacmanx.entity;

import java.util.*;

import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;

import net.gildargaming.pacmanx.Direction;
import net.gildargaming.pacmanx.ai.AIMode;
import net.gildargaming.pacmanx.ai.Node;
import net.gildargaming.pacmanx.screens.GameScreen;
import net.gildargaming.pacmanx.util.Vector2i;
import net.gildargaming.pacmanx.world.Level;


public class Ghost extends Mob {
	protected List<Node> path;
	protected AIMode mode;
	

	public Ghost(float x, float y, GameScreen screen, String regionName, int[] animInfo, float animSpeed, Level level) {
		super(x, y, screen, regionName, animInfo, animSpeed, level);

		
	}
	
	public void findPathToPlayer(Vector2i targetPos) {
		Vector2i pos = new Vector2i((this.boundary.x + 0) / 8, (this.boundary.y + 0) / 8);
		if (canTurn()) path = level.findPath(pos, targetPos);						
	}
	
	public boolean canTurn() {
		//if (direction == Direction.LEFT || direction == Direction.RIGHT) {
			
		//}
		return true;
	}
	
	public Circle getBountary() {
		return boundary;
		
	}

	public void update(float delta) {
		
		if (path == null) return;
		Vector2i pos = new Vector2i((this.xPos + 8 ) / 8, (this.yPos + 8) / 8);
		if (path.size() > 0) {
			Vector2i nextPos = path.get(path.size() - 1).nodePos;			
			if ((int)nextPos.x * 8 > (int)xPos + 0  ) {
				setDirection(Direction.RIGHT);
			}
			else if ((int)nextPos.x * 8 < (int)xPos - 0) {
				setDirection(Direction.LEFT);
			}
			else if ((int)nextPos.y * 8 > (int)yPos + 0 ) {
				setDirection(Direction.UP);			
			}
			else if ((int)nextPos.y * 8 < (int)yPos - 0 ) {
				setDirection(Direction.DOWN);			
			}
			//System.out.println(this.direction);
			//System.out.print(nextPos.x * 16 + " ");
			//System.out.println(nextPos.y * 16);
			//System.out.print(pos.x + " ");
			//System.out.println(pos.y);
			//System.out.print(this.xPos + " ");
			//System.out.println(this.yPos);
			
			//path.remove(path.size() - 1);
			//System.out.println("Updating");
		}
		boundary.setPosition(xPos + (float)animInfo[2] / 2, yPos + (float)animInfo[2] / 2);
		super.update(delta);
	}

}
