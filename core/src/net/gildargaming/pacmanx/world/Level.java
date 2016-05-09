package net.gildargaming.pacmanx.world;



import java.util.*;

import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Scaling;
import com.sun.org.apache.bcel.internal.generic.INSTANCEOF;

import net.gildargaming.pacmanx.Direction;
import net.gildargaming.pacmanx.MainGame;
import net.gildargaming.pacmanx.ai.Node;
import net.gildargaming.pacmanx.entity.Ghost;
import net.gildargaming.pacmanx.entity.Mob;
import net.gildargaming.pacmanx.entity.Player;
import net.gildargaming.pacmanx.screens.GameScreen;
import net.gildargaming.pacmanx.util.Debug;
import net.gildargaming.pacmanx.util.Vector2i;

public class Level {

	public Player player;
	private TmxMapLoader mapLoader;
	private TiledMap map;
	private OrthogonalTiledMapRenderer renderer;
	public GameScreen screen;
	public TiledMapTileLayer  walls;
	public Ghost[] ghosts; 
	private int[][] grid;
	public float aiUpdate = 0;
	public Debug dbg;
	public int numFood;
	
	public TiledMapTileLayer food;	
	private Comparator<Node> nodeSort = new Comparator<Node>() {
		public int compare(Node n1, Node n2) {
			if (n1.fCost > n2.fCost) return 1;
			if (n2.fCost < n1.fCost) return -1;
			return 0;
		}
	};

	public Level(String levelName, GameScreen screen) {
		this.screen = screen;
		mapLoader = new TmxMapLoader();
	    map = mapLoader.load(levelName);
	    renderer = new OrthogonalTiledMapRenderer(map);
	    TiledMapTileLayer layer0 = (TiledMapTileLayer) map.getLayers().get(0);
	    
	    Vector3 center = new Vector3(screen.game.WIDTH / 2, layer0.getHeight() * layer0.getTileHeight() / 2, 0);
	    screen.cam.position.set(center);		
	    ghosts = new Ghost[4];
	    float xy[] = this.getGhostStartPosition(0);
		float ghostAnimSpeed = 0.5f;
	    int[] animInfo = {0,0,8,8,7,0};
		//testSprite = new SpriteHandler(this, "AIPac", 100, 100, 16, 16, 16, 0);
		//animInfo[0] = startTileX;
		//animInfo[1] = startTileY;
		//animInfo[2] = tileSizeX;
		//animInfo[3] = tileSizeY;
		//animInfo[4] = endTileX;
		//animInfo[5] = endTileY;

	    ghosts[0] = new Ghost(xy[0], xy[1], screen, "Ghosts", animInfo, ghostAnimSpeed, this);
	    xy = this.getGhostStartPosition(1);
		ghostAnimSpeed = 0.5f;
	    animInfo[0] = 8;
	    animInfo[1] = 0;
	    animInfo[4] = 15;
	    animInfo[5] = 0;
	    ghosts[1] = new Ghost(xy[0], xy[1], screen, "Ghosts", animInfo, ghostAnimSpeed, this);	    
	    xy = this.getGhostStartPosition(2);
		ghostAnimSpeed = 0.5f;
	    animInfo[0] = 16;
	    animInfo[1] = 0;
	    animInfo[4] = 23;
	    animInfo[5] = 0;
	    ghosts[2] = new Ghost(xy[0], xy[1], screen, "Ghosts", animInfo, ghostAnimSpeed, this);	    
	    xy = this.getGhostStartPosition(3);
		ghostAnimSpeed = 0.5f;
	    animInfo[0] = 24;
	    animInfo[1] = 0;
	    animInfo[4] = 31;
	    animInfo[5] = 0;
	    ghosts[3] = new Ghost(xy[0], xy[1], screen, "Ghosts", animInfo, ghostAnimSpeed, this);	    
	    //Get the tile map player to use for collision and stuff.
	    animInfo[0] = 0;
	    animInfo[1] = 0;
	    animInfo[4] = 2;
	    animInfo[5] = 0;
	    xy = this.getPlayerStartPosition();
	    player = new Player(xy[0]+3, xy[1]+3, screen, "AIPac", animInfo, 0.1f, this);
		player.setMovementSpeed(20f);
		player.setDirection(Direction.RIGHT);
	    walls = (TiledMapTileLayer)map.getLayers().get("walls");
	    food = (TiledMapTileLayer)map.getLayers().get("FoodMap");
	    //Build a grid (used by AI)
	    float gridTileWidth = walls.getTileWidth();
	    float gridTileHeight = walls.getTileHeight();
	    int numTilesX = screen.game.WIDTH / (int)gridTileWidth;
	    int numTilesY = screen.game.HEIGHT / (int)gridTileHeight;
	    grid = new int[numTilesX][numTilesY];
	    for (int x = 0; x < numTilesX; x++) {
	    	for (int y = 0; y < numTilesY; y++) {
	    		grid[x][y] = (walls.getCell(x, y) != null) ? 1 : 0;
	    	}
	    }

		ghosts[0].setMovementSpeed(20f);
		ghosts[1].setMovementSpeed(20f);
		ghosts[2].setMovementSpeed(20f);
		ghosts[3].setMovementSpeed(20f);
		dbg = new Debug(grid);
		
	}
	
	public float[] getPlayerStartPosition() {
		float[] xy = new float[2];
		MapLayer layer = map.getLayers().get("playerStartPos");
		RectangleMapObject startPos = (RectangleMapObject)layer.getObjects().get(0);
		Rectangle rect = startPos.getRectangle();
		
		xy[0] = rect.getX();
		xy[1] = rect.getY();

		return xy;
	}
	
	public float[] getGhostStartPosition(int num) {
		float[] xy = new float[2];
		MapLayer layer = map.getLayers().get("ghostStartPos");
		RectangleMapObject startPos = (RectangleMapObject)layer.getObjects().get(num);
		Rectangle rect = startPos.getRectangle();
		
		xy[0] = rect.getX();
		xy[1] = rect.getY();
		return xy;
	}

	public void eatFood(float xPos, float yPos) {
		if (food.getCell((int)(xPos / food.getTileWidth()), (int)(yPos / food.getTileHeight())) != null ) {
			food.getCell((int)(xPos / food.getTileWidth()), (int)(yPos / food.getTileHeight())).setTile(null);
		}
	}	
	
	public boolean isWall(float xPos, float yPos) {
		return (walls.getCell((int)(xPos / walls.getTileWidth()), (int)(yPos / walls.getTileHeight())) != null ) ? true : false;
	}
	
	
	public boolean checkTileCollision(Mob mob) {
		if ( mob instanceof Player || mob instanceof Ghost) {
			//System.out.print(mob.getBountary().x + mob.getBountary().radius -4);
			//System.out.println("Is an instance of player or ghost");
			if (isWall(mob.getBountary().x + mob.getBountary().radius - 2, mob.getBountary().y) || 
					isWall(mob.getBountary().x - mob.getBountary().radius + 2, mob.getBountary().y) ||
					isWall(mob.getBountary().x, mob.getBountary().y + mob.getBountary().radius -2) ||
					isWall(mob.getBountary().x, mob.getBountary().y - mob.getBountary().radius + 2) 
					) {

				//System.out.print(mob.getBountary().x + mob.getBountary().radius -4);
				return true;
			}
		}
		return false;
		
	}
	
	public List<Node> findPath(Vector2i start, Vector2i end) {
		List<Node> openList = new ArrayList<Node>();
		//end.x = Math.round(end.x);
		//end.y = Math.round(end.y);
		//start.x = Math.round(start.x);
		//start.y = Math.round(start.y);
		List<Node> closedList = new ArrayList<Node>();		
		Node current = new Node(start, 0, getDistance(start, end), null);
		
		openList.add(current);
		
		while (openList.size() > 0) {
			
			openList.sort(nodeSort);
			current = openList.get(0);
			//if (current.parent != null) System.out.println(current.parent.nodePos.toString());
			
			if (current.nodePos.x == end.x && current.nodePos.y == end.y) {
				//System.out.println(end.toString());
				//System.out.println(current.nodePos.toString());				
				List<Node> path = new ArrayList<Node>();

				while (current.parent != null) {
					path.add(current);

					current = current.parent;
					
					
				}
				openList.clear();
				closedList.clear();
				for (Node n : path) {
					//System.out.println(n.nodePos.toString());
				}
				return path;
			}
			openList.remove(current);
			closedList.add(current);
			for (int i = 0; i < 9; i++) {

				//Skip diagonals since we don't want to allow diagonal movement right now.
				if (i == 0 || i == 2 || i == 4 || i == 6 || i == 8) continue;
				
				int x = current.nodePos.x;
				int y = current.nodePos.y;
				
				int xd = (i % 3) - 1;
				int yd = (i / 3) - 1;
				if (x+xd < 0 || x + xd > grid.length) continue;
				if (y+yd < 0 || y + yd > grid.length) continue;
				if (grid[Math.floorMod(x + xd, grid.length)][Math.floorMod(y+yd, grid.length)] == 1) continue;
				
				Vector2i v = new Vector2i(xd + x, yd + y);
				
				double gCost = current.gCost + getDistance(current.nodePos, v);
				
				double hCost = getDistance(v, end);
				Node node = new Node(v, gCost, hCost, current);
				
				if (vectorInNodeList(v, closedList) && gCost >= node.gCost) continue;
				if (!vectorInNodeList(v, openList) || gCost < node.gCost) openList.add(node);
				for (Node n : openList) {
					
					//System.out.println(n.nodePos.toString());
				}
			}
		}
		return null;
	}
	
	private boolean vectorInNodeList(Vector2i v, List<Node> l) {
		for (Node n : l) {
			if (n.nodePos.x == v.x && n.nodePos.y == v.y) return true;
			
		}
		return false;
	}
	
	public double getDistance(Vector2i v1, Vector2i v2) {
		return Math.sqrt(Math.pow(v1.x - v2.x, 2) + Math.pow(v1.y - v2.y, 2));
		
	}
	
	public void update(float delta) {
	    renderer.setView(screen.cam);
	    aiUpdate += delta;
	    //System.out.println(grid[12][16]);
		if (aiUpdate >= 0.2f) {
			ghosts[0].findPathToPlayer(new Vector2i((player.xPos + 4) / 8, (player.yPos + 4) / 8));
			ghosts[1].findPathToPlayer(new Vector2i((player.xPos + 4) / 8, (player.yPos + 4) / 8));
			ghosts[2].findPathToPlayer(new Vector2i((player.xPos + 4) / 8, (player.yPos + 4) / 8));
			ghosts[3].findPathToPlayer(new Vector2i((player.xPos + 4) / 8, (player.yPos + 4) / 8));
			aiUpdate = 0;
		}
		for (int i = 0; i < ghosts.length; i++) {
		    ghosts[i].update(delta);			
		}

		player.update(delta);


	}
	
	public void render(float dt) {
		renderer.render();
		dbg.render();
	}
}
