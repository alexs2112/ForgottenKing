package world;

import java.util.ArrayList;
import java.util.List;

import tools.Point;
import creatures.Creature;
import creatures.Tag;
import features.Entrance;
import features.Feature;
import items.Inventory;
import items.Item;
import javafx.scene.image.Image;

public class World {
	private Tile[][][] tiles;
	private int width;
	public int width() { return width; }
	private int height;
	public int height() { return height; }
	private int depth;
	public int depth() { return depth; }
	
	private Feature[][][] features;
	public Feature[][][] features() { return features; }
	public Feature feature(int x, int y, int z) { return features[x][y][z]; }
	public void setFeature(Feature f, int x, int y, int z) { features[x][y][z] = f; }
	
	private Inventory[][][] items;
	public Inventory[][][] items() { return items; }
	public Inventory items(int x, int y, int z) { return items[x][y][z]; }
	public void addItem(Item item, int x, int y, int z) {
		if (items[x][y][z] == null)
			items[x][y][z] = new Inventory();
		items[x][y][z].add(item); 
	}
	public void addAtEmptyLocation(Item item, int z) {
		addAtEmptyLocation(item, z, 1);
	}
	public void addAtEmptyLocation(Item item, int z, int amount) {
		if (z == -1)
			return;
	    Point n = getEmptyLocation(z);
	    addAtEmptyLocation(item, n.x, n.y, z, amount);
	}
	public void addAtEmptyLocation(Item item, int x, int y, int z, int amount) {
		for (int i = 0; i < amount; i++)
			addItem(item, x, y, z);
	}
	public void remove(Item item) {
		for (int z = 0; z < depth; z++) {
			for (int x = 0; x < width; x++) {
				for (int y = 0; y < height; y++) {
					if (items(x,y,z) != null && items(x,y,z).contains(item)) {
						items(x,y,z).remove(item);
						return;
					}
				}
			}
		}
	}
	public void removeInventory(int wx, int wy, int wz) {
		items[wx][wy][wz] = null;
	}
	
	private ArrayList<Creature> creatures;
	public ArrayList<Creature> creatures() { return creatures; }
	public Creature creature(int x, int y, int z){
	    for (Creature c : creatures){
	        if (c.x == x && c.y == y && c.z == z)
	            return c;
	    }
	    return null;
	}
	public void remove(Creature creature) {
		creatures.remove(creature);
	}

	public World(Tile[][][] tiles, Feature[][][] features){
		this.tiles = tiles;
		this.width = tiles.length;
		this.height = tiles[0].length;
		this.depth = tiles[0][0].length;
		this.creatures = new ArrayList<Creature>();
		this.items = new Inventory[width][height][depth];
		//this.features = new Feature[width][height][depth];
		this.features = features;
	}
	
	public Tile tile(int x, int y, int z){
		if (x < 0 || x >= width || y < 0 || y >= height)
			return Tile.BOUNDS;
		else
			return tiles[x][y][z];
	}

	public Image tileImage(int x, int y, int z){
		return tile(x, y, z).getImage(this, x, y, z);
	}
	
	public void dig(int x, int y, int z) {
	    if (tile(x,y,z) == Tile.CAVE_WALL)
	        tiles[x][y][z] = Tile.DIRT_FLOOR;
	}
	
	public void addAtEmptyLocation(Creature creature, int z){
	    Point p = getEmptyLocation(z);

	    creature.x = p.x;
	    creature.y = p.y;
	    creature.z = p.z;
	    creatures.add(creature);
	}
	
	public Point getEmptyLocation(int z) {
		int x = (int)(Math.random() * width);
	    int y = (int)(Math.random() * height);

	    while (!tile(x,y,z).isGround() || creature(x,y,z) != null || (feature(x,y,z) != null && feature(x,y,z).blockMovement() == true)) {
	        x = (int)(Math.random() * width);
	        y = (int)(Math.random() * height);
	    }
	    return new Point(x, y, z);
	}
	
	public void update(int z){
	    List<Creature> toUpdate = new ArrayList<Creature>(creatures);
	    for (Creature creature : toUpdate){
	    	if (creature.z == z && !creature.is(Tag.PLAYER)) {
	    		creature.modifyTime(-1);
	    		if (creature.time() <= 0) {
	    			creature.update();
	    			if (creature.time() <= 0)
	    				creature.modifyTime(creature.getMovementDelay());
	    		}
	    	}
	    }
	}
	
	public void setEntrance(int x, int y) {
		features[x][y][0] = new Entrance(x,y,0);
		tiles[x][y][0] = Tile.DUNGEON_FLOOR_CENTER;
	}
	
}
