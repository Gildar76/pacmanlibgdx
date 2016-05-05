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

import net.gildargaming.pacmanx.Direction;
import net.gildargaming.pacmanx.MainGame;
import net.gildargaming.pacmanx.ai.Node;
import net.gildargaming.pacmanx.entity.Ghost;
import net.gildargaming.pacmanx.entity.Player;
import net.gildargaming.pacmanx.screens.GameScreen;

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
	    float xy[] = this.getGhostStartPosition();
		float ghostAnimSpeed = 0.5f;
	    int[] animInfo = {0,0,16,16,2,0};
		//testSprite = new SpriteHandler(this, "AIPac", 100, 100, 16, 16, 16, 0);
		//animInfo[0] = startTileX;
		//animInfo[1] = startTileY;
		//animInfo[2] = tileSizeX;
		//animInfo[3] = tileSizeY;
		//animInfo[4] = endTileX;
		//animInfo[5] = endTileY;

	    ghosts[0] = new Ghost(xy[0], xy[1], screen, "Ghosts", animInfo, ghostAnimSpeed, this);
	    //Get the tile map player to use for collision and stuff.
	    xy = this.getPlayerStartPosition();
	    player = new Player(xy[0], xy[1], screen, "AIPac", animInfo, 0.1f, this);
		player.setMovementSpeed(20f);
		player.setDirection(Direction.RIGHT);
	    walls = (TiledMapTileLayer)map.getLayers().get("walls");
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
	}
	
	public float[] getPlayerStartPosition() {
		float[] xy = new float[2];
		MapLayer layer = map.getLayers().get("playerstartpos");
		RectangleMapObject startPos = (RectangleMapObject)layer.getObjects().get(0);
		Rectangle rect = startPos.getRectangle();
		
		xy[0] = rect.getX();
		xy[1] = rect.getY();

		return xy;
	}
	
	public float[] getGhostStartPosition() {
		float[] xy = new float[2];
		MapLayer layer = map.getLayers().get("ghoststartpos");
		RectangleMapObject startPos = (RectangleMapObject)layer.getObjects().get(0);
		Rectangle rect = startPos.getRectangle();
		
		xy[0] = rect.getX();
		xy[1] = rect.getY();
		return xy;
	}
	public boolean checkTileIntersect(float xPos, float yPos) {
		return (walls.getCell((int)(xPos / walls.getTileWidth()), (int)(yPos / walls.getTileHeight())) != null ) ? true : false;
	}
	
	public List<Node> findPath(Vector2 start, Vector2 end) {
		List<Node> openList = new ArrayList<Node>();
		end.x = Math.round(end.x);
		end.y = Math.round(end.y);
		start.x = Math.round(start.x);
		start.y = Math.round(start.y);
		List<Node> closedList = new ArrayList<Node>();		
		Node current = new Node(start, 0, getDistance(start, end), null);
		
		openList.add(current);
		
		while (openList.size() > 0) {
			
			openList.sort(nodeSort);
			current = openList.get(0);
			//if (current.parent != null) System.out.println(current.parent.nodePos.toString());
			
			if (current.nodePos.x == end.x && current.nodePos.y == end.y) {
				System.out.println(end.toString());
				System.out.println(current.nodePos.toString());				
				List<Node> path = new ArrayList<Node>();

				while (current.parent != null) {
					path.add(current);

					current = current.parent;
					
					
				}
				openList.clear();
				closedList.clear();
				for (Node n : path) {
					System.out.println(n.nodePos.toString());
				}
				return path;
			}
			openList.remove(current);
			closedList.add(current);
			for (int i = 0; i < 9; i++) {

				//Skip diagonals since we don't want to allow diagonal movement right now.
				if (i == 0 || i == 2 || i == 4 || i == 6 || i == 8) continue;
				
				float x = current.nodePos.x;
				float y = current.nodePos.y;
				
				int xd = (i % 3) - 1;
				int yd = (i / 3) - 1;
				if (grid[(int)x + xd][(int)y+yd] == 1) continue;
				
				Vector2 v = new Vector2((float)xd + Math.round(x), (float)yd + Math.round(y));
				
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
	
	private boolean vectorInNodeList(Vector2 v, List<Node> l) {
		for (Node n : l) {
			if (n.nodePos.equals(v)) return true;
			
		}
		return false;
	}
	
	public double getDistance(Vector2 v1, Vector2 v2) {
		return Math.sqrt(Math.pow(v1.x - v2.x, 2) + Math.pow(v1.y - v2.y, 2));
		
	}
	
	public void update(float delta) {
	    renderer.setView(screen.cam);
	    aiUpdate += delta;
	    
		if (aiUpdate >= 1.0f) {
			ghosts[0].findPathToPlayer(new Vector2(player.xPos / 16, player.yPos / 16));
			aiUpdate = 0;
		}
	    ghosts[0].update(delta);
		player.update(delta);


	}
	
	public void render(float dt) {
		renderer.render();

	}
}
