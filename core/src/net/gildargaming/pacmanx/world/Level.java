package net.gildargaming.pacmanx.world;

import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Scaling;


import net.gildargaming.pacmanx.MainGame;
import net.gildargaming.pacmanx.entity.Ghost;
import net.gildargaming.pacmanx.screens.GameScreen;

public class Level {

	private TmxMapLoader mapLoader;
	private TiledMap map;
	private OrthogonalTiledMapRenderer renderer;
	public GameScreen screen;
	public TiledMapTileLayer  walls;
	public Ghost[] ghosts; 
	
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
		
	    ghosts[0] = new Ghost(xy[0], xy[1], screen, "Ghosts", animInfo, ghostAnimSpeed);
	    //Get the tile map player to use for collision and stuff.
	    walls = (TiledMapTileLayer)map.getLayers().get("walls");
	    
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
	
	public void update(float delta) {
	    renderer.setView(screen.cam);		
	    ghosts[0].update(delta);
	}
	
	public void render(float dt) {
		renderer.render();

	}
}
