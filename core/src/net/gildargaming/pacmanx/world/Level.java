package net.gildargaming.pacmanx.world;

import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Scaling;

import net.gildargaming.pacmanx.MainGame;
import net.gildargaming.pacmanx.screens.GameScreen;

public class Level {

	private TmxMapLoader mapLoader;
	private TiledMap map;
	private OrthogonalTiledMapRenderer renderer;
	public GameScreen screen;
	public TiledMapTileLayer  walls;
	
	public Level(String levelName, GameScreen screen) {
		this.screen = screen;
		mapLoader = new TmxMapLoader();
	    map = mapLoader.load(levelName);
	    renderer = new OrthogonalTiledMapRenderer(map);
	    TiledMapTileLayer layer0 = (TiledMapTileLayer) map.getLayers().get(0);
	    Vector3 center = new Vector3(screen.game.WIDTH / 2, layer0.getHeight() * layer0.getTileHeight() / 2, 0);
	    screen.cam.position.set(center);		

	    //Get the tile map player to use for collision and stuff.
	    walls = (TiledMapTileLayer)map.getLayers().get("walls");
	    
	}
	
	public boolean checkTileIntersect(float xPos, float yPos) {
		return (walls.getCell((int)(xPos / walls.getTileWidth()), (int)(yPos / walls.getTileHeight())) != null ) ? true : false;
	}
	
	public void update(float delta) {
	    renderer.setView(screen.cam);		
	}
	
	public void render(float dt) {
		renderer.render();
		
	}
}
