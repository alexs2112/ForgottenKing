package world;

import features.DownStair;
import features.Feature;
import features.UpStair;
import tools.Point;

public class WorldBuilder {
	private int width;
	private int height;
	private int depth;
	private Tile[][][] tiles;
	private Feature[][][] features;
	private PrefabLoader loader;

	public WorldBuilder(int width, int height, int depth) {
		this.width = width;
		this.height = height;
		this.depth = depth;
		this.tiles = new Tile[width][height][depth];
		this.features = new Feature[width][height][depth];
		loader = new PrefabLoader("Dungeon");
	}

	/**
	 * Turns the worldbuilder into a proper world
	 * Also sets up all features that need to know their locations
	 */
	public World build() {
		World w = new World(tiles, features);
		w.setUpFeatureLocations();
		return w;
	}
	
	/**
	 * The basic dungeon generation algorithm intializer, tries to place 300 rooms and then adds 3 stairs each level
	 */
	public WorldBuilder makeDungeon() {
		return generateRooms(300).addStairs(3);
	}
	
	/**
	 * An algorithm that tries to place a ton of rooms, either rectangular ones or ones from a prefab file
	 * This should be cut down in the future
	 * @param trials: The number of times it tries to place a room, once it runs out of trials then the algorithm halts
	 */
	public WorldBuilder generateRooms(int trials) {
		int roomMin = 6;
		int roomMod = 4;
		for (int z = 0; z < depth; z++) {
			int[][] nums = new int[width][height];
			int[][] rooms = new int[width][height];	//Keeps track of where rooms are, so when deleting dead ends it doesnt delete vaults
			for (int x = 0; x < width; x++)
				for (int y = 0; y < height; y++)
					nums[x][y] = 1;
			for (int i = 0; i < trials; i++) {
				int sx = (int)(Math.random() * width - 5);
				int sy = (int)(Math.random() * height - 5);
				
				double chanceToLoad = 0.3;
				if (!loader.canLoad())
					chanceToLoad = 0;
				if (Math.random() < chanceToLoad) {
					Prefab n = loader.getRandomPrefab();
					int successes = 0;
					for (int x = 0; x < n.width(); x++)
						for (int y = 0; y < n.height(); y++)
							if (x+sx > 0 && x+sx < width && y+sy > 0 && y+sy < height)
								if (nums[x+sx][y+sy] == 1)
									successes++;
					if (successes == n.width()*n.height())
						for (int x = 0; x < n.width(); x++)
							for (int y = 0; y < n.height(); y++) {
								tiles[x+sx][y+sy][z] = n.tile(x,y);
								nums[x+sx][y+sy] = n.num(x, y);
								if (n.feature(x,y) != null)
									features[x+sx][y+sy][z] = n.feature(x,y).clone();
								if (n.num(x,y) != 1)
									rooms[x+sx][y+sy] = 1;
							}
				} else {
					//Create a basic square room
					int cx = (int)(Math.random() * roomMod) + roomMin;
					int cy = (int)(Math.random() * roomMod) + roomMin;

					//First check every tile of the new room, if none of them overlap with a floor, turn it into a room
					int successes = 0;
					for (int x = 0; x < cx; x++)
						for (int y = 0; y < cy; y++)
							if (x+sx > 0 && x+sx < width && y+sy > 0 && y+sy < height)
								if (nums[x+sx][y+sy] == 1)
									successes++;
					if (successes == cx * cy)
						for (int x = 1; x < cx-1; x++)
							for (int y = 1; y < cy-1; y++) {
								nums[x+sx][y+sy] = 0;
								rooms[x+sx][y+sy] = 1;
							}
				}
			}
			
			GenerateDungeon g = new GenerateDungeon(nums, rooms);
			nums = g.generateDungeon();
			for (int x = 0; x < width; x++)
				for (int y = 0; y < height; y++) {	
					if (nums[x][y] == 0)
						tiles[x][y][z] = Tile.DUNGEON_FLOOR;
					else if (nums[x][y] == -1)
						tiles[x][y][z] = Tile.DUNGEON_PIT;
					else
						tiles[x][y][z] = Tile.DUNGEON_WALL;
					if (g.feat(x,y) != null)
						features[x][y][z] = g.feat(x,y).clone();
				}
		}
		
		return this;
	}
	
	/**
	 * Basic cave builder intializer, randomizes all the tiles and then smooths a bunch of times before adding stairs
	 */
	public WorldBuilder makeCaves() {
	    return randomizeTiles().smooth(8).addStairs(3);
	}
	
	/**
	 * Goes through every single tile and randomly makes it either a dirt floor or a cave wall
	 */
	private WorldBuilder randomizeTiles() {
		for (int z = 0; z < depth; z++) {
			for (int x = 0; x < width; x++) {
				for (int y = 0; y < height; y++) {
					tiles[x][y][z] = Math.random() < 0.5 ? Tile.DIRT_FLOOR : Tile.CAVE_WALL;
				}
			}
		}
		return this;
	}
	
	/**
	 * Goes across every tile and tries to smooth it to match its neighbours
	 * @param times: The number of times it smooths the cave
	 */
	private WorldBuilder smooth(int times) {
		Tile[][][] tiles2 = new Tile[width][height][depth];
		for (int time = 0; time < times; time++) {
			for (int z = 0; z < depth; z++) {
				for (int x = 0; x < width; x++) {
					for (int y = 0; y < height; y++) {
						int floors = 0;
						int rocks = 0;

						for (int ox = -1; ox < 2; ox++) {
							for (int oy = -1; oy < 2; oy++) {
								if (x + ox < 0 || x + ox >= width || y + oy < 0
										|| y + oy >= height)
									continue;

								if (tiles[x + ox][y + oy][z] == Tile.DIRT_FLOOR)
									floors++;
								else
									rocks++;
							}
						}
						tiles2[x][y][z] = floors >= rocks ? Tile.DIRT_FLOOR : Tile.CAVE_WALL;
					}
				}
			}
			tiles = tiles2;
		}
		return this;
	}
	
	
	/**
	 * Go through each level a few times to place a staircase leading down, and then a connected staircase leading up on
	 * the next level
	 * @param amount: Amount of stairs per floor
	 */
	private WorldBuilder addStairs(int amount) {
		for (int z = 0; z < depth-1; z++) {
			for (int i = 0; i < amount; i++) {
				Point p1 = getEmptyLocation(z);
				DownStair down = new DownStair(null, p1.x, p1.y, p1.z);
				features[p1.x][p1.y][p1.z] = down;
				tiles[p1.x][p1.y][p1.z] = Tile.DUNGEON_FLOOR_CENTER; 
				Point p2 = getEmptyLocation(z+1);
				UpStair up = new UpStair(down, p2.x, p2.y, p2.z);
				features[p2.x][p2.y][p2.z] = up;
				tiles[p2.x][p2.y][p2.z] = Tile.DUNGEON_FLOOR_CENTER;
				down.setUpStair(up);
			}
		}
		return this;
	}
	
	/**
	 * Randomly finds a point that is on the ground or has a non-null feature
	 * @param z: Z-level to search
	 */
	private Point getEmptyLocation(int z) {
		int x = (int)(Math.random() * width);
	    int y = (int)(Math.random() * height);

	    while (!tiles[x][y][z].isGround() || features[x][y][z] != null) {
	        x = (int)(Math.random() * width);
	        y = (int)(Math.random() * height);
	    }
	    return new Point(x, y, z);
	}

}
