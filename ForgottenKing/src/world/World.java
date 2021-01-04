package world;

import java.util.ArrayList;
import java.util.List;

import assembly.CreatureFactory;
import assembly.ItemFactory;
import tools.Point;
import creatures.Creature;
import creatures.Tag;
import features.Chest;
import features.Entrance;
import features.Feature;
import items.Inventory;
import items.Item;
import javafx.scene.image.Image;
import spells.Hazard;

public class World implements java.io.Serializable {
	private static final long serialVersionUID = 7769423305067121315L;
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
	
	private Hazard[][][] hazards;
	public Hazard hazard(int x, int y, int z) { return hazards[x][y][z]; }
	public void setHazard(Hazard h, int x, int y, int z) { hazards[x][y][z] = h.clone(); hazards[x][y][z].modifyTime((int)(Math.random()*h.variance()));}
	
	private Inventory[][][] items;
	public Inventory[][][] items() { return items; }
	public Inventory items(int x, int y, int z) { return items[x][y][z]; }
	public void addItem(Item item, int x, int y, int z) {
		if (tiles[x][y][z].isPit()) {
			notify(x,y,z,"The " + item.name() + " falls into the void.");
			return;
		}
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
		for (int z = 0; z < depth; z++)
			for (int x = 0; x < width; x++)
				for (int y = 0; y < height; y++)
					if (items(x,y,z) != null && items(x,y,z).contains(item)) {
						items(x,y,z).remove(item);
						return;
					}
	}
	public void remove(Item item, int x, int y, int z) {
		if (items(x,y,z) != null && items(x,y,z).contains(item)) {
			items(x,y,z).remove(item);
			return;
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
	
	/**
	 * Basic Constructor
	 * @param width of the world
	 * @param height of the world
	 * @param depth of the world, how many levels there are
	 */
	public World(int width, int height, int depth) {
		this.width = width;
		this.height = height;
		this.depth = depth;
		this.creatures = new ArrayList<Creature>();
		this.items = new Inventory[width][height][depth];
		this.hazards = new Hazard[width][height][depth];
	}
	public void setTiles(Tile[][][] tiles) {
		this.tiles = tiles;
	}
	public void setFeatures(Feature[][][] features) {
		this.features = features;
	}
	
	/**
	 * Returns the specified tile, if it is out of bounds return the BOUNDS tile
	 */
	public Tile tile(int x, int y, int z){
		if (x < 0 || x >= width || y < 0 || y >= height)
			return Tile.BOUNDS;
		else
			return tiles[x][y][z];
	}

	/**
	 * Return the specified tiles image
	 */
	public Image tileImage(int x, int y, int z){
		return tile(x, y, z).getImage(this, x, y, z);
	}
	
	/**
	 * Obsolete method, delete in the future
	 */
	public void dig(int x, int y, int z) {
	    if (tile(x,y,z) == Tile.CAVE_WALL)
	        tiles[x][y][z] = Tile.DIRT_FLOOR;
	}
	
	/**
	 * Return the pathfinding cost of a tile, currently 1 for everything unless it has a feature with a bump interaction,
	 * in which case return 2 (since it takes 2 moves to move through it)
	 */
	public int pathfindingCost(int x, int y, int z) {
		if (feature(x,y,z) != null && feature(x,y,z).type() == Feature.Type.BUMP)
			return 2;
		return 1;
	}
	
	/**
	 * Adds a creature at a randomly selected location of the designated z level
	 * Makes sure to fill their HP and mana pools in case there is something modifying that
	 */
	public void addAtEmptyLocation(Creature creature, int z){
	    Point p = getEmptyLocation(z);
	    creature.x = p.x;
	    creature.y = p.y;
	    creature.z = p.z;
	    creatures.add(creature);
	    creature.fillMana();
	    creature.fillHP();
	}
	
	/**
	 * A helper method that returns a point of an empty location of the designated z level
	 * Empty is defined as a ground tile that does not contain a creature and does not contain a feature that blocks movement
	 */
	public Point getEmptyLocation(int z) {
		int x = (int)(Math.random() * width);
	    int y = (int)(Math.random() * height);

	    while (!tile(x,y,z).isGround() 
	    		|| creature(x,y,z) != null 
	    		|| (feature(x,y,z) != null && feature(x,y,z).blockMovement() == true)) {
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
	    for (int x = 0; x < width; x++) {
	    	for (int y = 0; y < height; y++) {
	    		Hazard h = hazards[x][y][z];
	    		if (h != null) {
	    			h.update();
	    			if (h.time() <= 0)
	    				hazards[x][y][z] = null;
	    		}
	    	}
	    }
	}
	
	public void setEntrance(int x, int y) {
		features[x][y][0] = new Entrance(x,y,0);
		tiles[x][y][0] = Tile.DUNGEON_FLOOR_CENTER;
	}
	public void setUpFeatureLocations() {
		for (int z = 0; z < depth; z++) {
			for (int y = 0; y < height; y++) {
				for (int x = 0; x < width; x++) {
					if (features[x][y][z] != null)
						features[x][y][z].setLocation(this, x, y, z);
				}
			}
		}
	}
	
	/**
	 * A method similar to the doAction method in creatures. Each creature that can see the spot is notified
	 */
	public void notify(int x, int y, int z, String message) {
		int r = 9;
    	for (int ox = -r; ox < r+1; ox++){
    		for (int oy = -r; oy < r+1; oy++){
    			if (ox*ox + oy*oy > r*r)
    				continue;

    			Creature other = creature(x+ox, y+oy, z);

    			if (other == null)
    				continue;

    			if (other.canSee(x, y, z))
    				other.notify(message);
    		}
    	}
	}
	
	/**
	 * Set a noise somewhere and notify each creature who can hear it
	 */
	public void makeNoise(Noise n) {
		for (int ox = -n.volume(); ox < n.volume()+1; ox++){
    		for (int oy = -n.volume(); oy < n.volume()+1; oy++){
    			if (ox*ox + oy*oy > n.volume()*n.volume())
    				continue;

    			Creature other = creature(n.x+ox, n.y+oy, n.z);

    			if (other == null)
    				continue;

    			other.hearNoise(n);
    		}
    	}
	}
	
	
	/**
	 * A populate method to place all creatures and items
	 */
	public void populate(CreatureFactory creature, ItemFactory item) {
		addCreatures(creature);
		addItems(item);
	}
	private void addCreatures(CreatureFactory f) {
		for (int z = 0; z < depth; z++) {
			//Each level has 9 creatures of a lower level, 15 creatures of that level, and 6 creatures of a higher level
			if (z % 5 == 0) {
				for (int i = 0; i < 10; i++)
					f.newRandomCreature(z, z+1);
				for (int i = 0; i < 20; i++)
					f.newRandomCreature(z, z);
			} else if ((z+1) % 5 == 0) {
				for (int i = 0; i < 15; i++) {
					f.newRandomCreature(z, z-1);
					f.newRandomCreature(z, z);
				}
			} else {
				for (int i = 0; i < 9; i++)
					f.newRandomCreature(z, z-1);
				for (int i = 0; i < 15; i++)
					f.newRandomCreature(z, z);
				for (int i = 0; i < 6; i++)
					f.newRandomCreature(z, z+1);
			}
		}
		if (depth > 4)
			f.newGrisstok(4);
		if (depth >= 9)
			f.newUshnag(9);
	}
	private void addItems(ItemFactory f) {
		for (int z = 0; z < depth; z++) {
			for (int i = 0; i < 5; i++){
				addAtEmptyLocation(f.ammo().newRock(z), z);
			}
			for (int i = 0; i < 5; i++){
				addAtEmptyLocation(f.newRandomArmor(z), z);
				addAtEmptyLocation(f.newRandomWeapon(z), z);
			}
			for (int i = 0; i < 3; i++) {
				addAtEmptyLocation(f.newRandomPotion(z), z);
			}
			for (int i = 0; i < 2; i++) {
				addAtEmptyLocation(f.book().newRandomBook(z), z);
			}
			for (int i = 0; i < 3; i++) {
				Item item = f.ammo().newRandomAmmo(z);
				Point n = getEmptyLocation(z);
			    addAtEmptyLocation(item, n.x, n.y, z, item.spawnQuantity());
			}
			for (int i = 0; i < 3; i++) {
				addAtEmptyLocation(f.trinket().newRandomRing(z), z);
			}
			for (int i = 0; i < 1 + z / 3; i++) {
				Point p = getEmptyLocation(z);
				Chest c = new Chest(Chest.ChestType.CHEST);
				setFeature(c, p.x, p.y, z);
			}
			for (int i = 0; i < 6; i++) {
				Point p = getEmptyLocation(z);
				Chest c = new Chest(Chest.ChestType.BARREL);
				setFeature(c, p.x, p.y, z);
			}
		}
		fillChests(f);
	}
	
	/**
	 * Go across the entire world, filling chests when necessary
	 * Need to do it here to fill chests that are placed via prefab along with generation
	 */
	private void fillChests(ItemFactory f) {
		for (int z = 0; z < depth; z++) {
			for (int x = 0; x < width; x++) {
				for (int y = 0; y < height; y++) {
					if (feature(x,y,z) != null && feature(x,y,z) instanceof Chest) {
						Chest c = (Chest)(feature(x,y,z));
						f.fillChest(c, z);
						if (items(x,y,z) != null)
							for (Item i : items(x,y,z).getItems())
							//Then check that tiles space and move all items from the world into the chest
								c.addItem(i);
						items[x][y][z] = null;
					}
				}
			}
		}
	}
	
}
