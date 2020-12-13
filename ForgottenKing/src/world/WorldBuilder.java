package world;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
	private PrefabLoader lizardLoader;
	private PrefabLoader orcLoader;
	private World world;

	public WorldBuilder(int width, int height, int depth) {
		this.width = width;
		this.height = height;
		this.depth = depth;
		this.tiles = new Tile[width][height][depth];
		this.features = new Feature[width][height][depth];
		lizardLoader = new PrefabLoader(PrefabLoader.Type.LIZARDCULT);
		orcLoader = new PrefabLoader(PrefabLoader.Type.ORCPIRATES);
		world = new World(width, height, depth);
	}

	/**
	 * Turns the worldbuilder into a proper world
	 * Also sets up all features that need to know their locations
	 */
	public World build() {
		world.setTiles(tiles);
		world.setFeatures(features);
		world.setUpFeatureLocations();
		return world;
	}
	
	/**
	 * The basic dungeon generation algorithm initializer
	 * For each floor, determines its type and then finalizes the level through a variety of methods
	 */
	public WorldBuilder makeDungeon() {
		for (int z = 0; z < 5; z++) {
			lizardLevel(z);
		}
		for (int z = 5; z < depth; z++) {
			orcLevel(z);
		}
		addStairs(3);
		return this;
	}
	
	/**
	 * Some methods that handle different level themes specifically
	 */
	private void lizardLevel(int z) {
		initializeLevel(z, Tile.DUNGEON_WALL);
		finalizeLevel(generateRooms(z, 300, Tile.DUNGEON_FLOOR, Tile.DUNGEON_WALL, lizardLoader), z);
	}
	private void orcLevel(int z) {
		randomizeTiles(z, 0.55, Tile.DIRT_FLOOR, Tile.MINE_WALL);
		borderWall(z, 8, Tile.MINE_WALL);
		smooth(z,5, Tile.DIRT_FLOOR, Tile.MINE_WALL);
		borderWall(z, 1, Tile.MINE_WALL);
		randomizeWater(z, 0.45, Tile.CAVE_POND);
		smoothWater(z,5,Tile.DIRT_FLOOR,Tile.CAVE_POND);
		setUpCaveTiles(z, Tile.MINE_WALL, Tile.CAVE_WALL);
		deleteSmallRooms(z, 40, Tile.MINE_WALL);
		finalizeLevel(generateRooms(z, 300, Tile.DIRT_FLOOR, Tile.MINE_WALL, orcLoader), z);
	}
	
	/**
	 * An algorithm that tries to place a ton of rooms, either rectangular ones or ones from a prefab file
	 * This should be cut down in the future
	 * @param trials: The number of times it tries to place a room, once it runs out of trials then the algorithm halts
	 */
	public GenerateDungeon generateRooms(int z, int trials, Tile floor, Tile wall, PrefabLoader load) {
		int[][] rooms = new int[width][height];	//Keeps track of where rooms are, so when deleting dead ends it doesnt delete vaults
		for (int i = 0; i < trials; i++) {
			int sx = (int)(Math.random() * width - 5);
			int sy = (int)(Math.random() * height - 5);
			double chanceToLoad = 0.3;
			if (!load.canLoad())
				chanceToLoad = 0;
			if (Math.random() < chanceToLoad) {
				Prefab n = load.getRandomPrefab();
				rooms = tryPrefab(rooms, n, sx, sy, z);
			} else {
				//Create a basic square room
				rooms = tryRoom(rooms, floor, wall, sx, sy, z);
			}
		}
		return new GenerateDungeon(getDungeonSlice(z), rooms, floor, wall);
	}
	
	
	/**
	 * A method that tries to place a prefab in a specified location.
	 * If it succeeds, it places that prefab (changes all tiles in that area to the prefabs tile) and returns the 
	 * updated rooms array to account for it
	 */
	private int[][] tryPrefab(int[][] rooms, Prefab n, int sx, int sy, int z) {
		int successes = 0;
		for (int x = 0; x < n.width(); x++)
			for (int y = 0; y < n.height(); y++)
				if (x+sx > 0 && x+sx < width && y+sy > 0 && y+sy < height)
					if (tiles[x+sx][y+sy][z].isWall())
						successes++;
		if (successes != n.width()*n.height())
			return rooms;

		for (int x = 0; x < n.width(); x++)
			for (int y = 0; y < n.height(); y++) {
				tiles[x+sx][y+sy][z] = n.tile(x,y);
				if (n.feature(x,y) != null)
					features[x+sx][y+sy][z] = n.feature(x,y).clone();
				if (n.num(x,y) != 1)
					rooms[x+sx][y+sy] = 1;
			}
		return rooms;
	}
	
	/**
	 * A method that uses a similar process as above, however this just places a square room
	 */
	private int[][] tryRoom(int[][] rooms, Tile floor, Tile wall, int sx, int sy, int z) {
		int roomMin = 6;
		int roomMod = 4;
		int cx = (int)(Math.random() * roomMod) + roomMin;
		int cy = (int)(Math.random() * roomMod) + roomMin;
		int successes = 0;
		for (int x = 0; x < cx; x++)
			for (int y = 0; y < cy; y++)
				if (x+sx > 0 && x+sx < width && y+sy > 0 && y+sy < height)
					if (tiles[x+sx][y+sy][z].isWall())
						successes++;
		if (successes != cx * cy)
			return rooms;
		for (int x = 1; x < cx-1; x++)
			for (int y = 1; y < cy-1; y++) {
				tiles[x+sx][y+sy][z] = floor;
				rooms[x+sx][y+sy] = 1;
			}
		return rooms;
	}
	
	/**
	 * A method that goes across the entire level and sets each tile as the input tile
	 */
	private void initializeLevel(int z, Tile tile) {
		for (int x = 0; x < width; x++)
			for (int y = 0; y < height; y++)
				//This check allows us to place preliminary prefabs before generating the dungeon
				if (tiles[x][y][z] == null)
					tiles[x][y][z] = tile;
	}
	
	/**
	 * A method that finalizes the tiles of a level using the generated map
	 */
	private void finalizeLevel(GenerateDungeon g, int z) {
		Tile[][] map = g.getMap();
		Feature[][] feats = g.getFeatures();
		for (int x = 0; x < width; x++)
			for (int y = 0; y < height; y++) {
				tiles[x][y][z] = map[x][y];
				if (feats[x][y] != null && features[x][y][z] == null)
					features[x][y][z] = feats[x][y];
			}
	}
	
	/**
	 * Goes through every single tile and randomly makes it either a dirt floor or a cave wall
	 */
	private void randomizeTiles(int z, double chance, Tile floor, Tile wall) {	
		for (int x = 0; x < width; x++)
			for (int y = 0; y < height; y++)
				tiles[x][y][z] = Math.random() < chance ? floor : wall;
	}
	
	/**
	 * Goes across every tile and tries to smooth it to match its neighbours
	 * @param times: The number of times it smooths the cave
	 */
	private void smooth(int z, int times, Tile floor, Tile wall) {
		Tile[][] temp = new Tile[width][height];
		for (int time = 0; time < times; time++) {
			for (int x = 0; x < width; x++) {
				for (int y = 0; y < height; y++) {
					int floors = 0;
					int walls = 0;

					for (int ox = -1; ox < 2; ox++) {
						for (int oy = -1; oy < 2; oy++) {
							if (x + ox < 0 || x + ox >= width || y + oy < 0
									|| y + oy >= height)
								continue;

							if (tiles[x + ox][y + oy][z].isGround())
								floors++;
							else
								walls++;
						}
					}
					temp[x][y] = floors >= walls ? floor : wall;
				}
			}
			for (int x = 0; x < width; x++)
				for (int y = 0; y < height; y++)
					tiles[x][y][z] = temp[x][y];
		}
	}
	
	/**
	 * Generating water will be similar to generating caves
	 * First randomly generate water tiles on the ground, and then smooth them out
	 */
	private void randomizeWater(int z, double chance, Tile water) {
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				if (!tiles[x][y][z].isGround())
					continue;
				if (Math.random() < chance)
					tiles[x][y][z] = water;
			}
		}
	}
	private void smoothWater(int z, int times, Tile floor, Tile water) {
		Tile[][] temp = new Tile[width][height];
		for (int time = 0; time < times; time++) {
			for (int x = 0; x < width; x++) {
				for (int y = 0; y < height; y++) {
					if (tiles[x][y][z].isWall())
						continue;
					int floors = 0;
					int ponds = 0;
					for (int ox = -1; ox < 2; ox++) {
						for (int oy = -1; oy < 2; oy++) {
							if (x + ox < 0 || x + ox >= width || y + oy < 0
									|| y + oy >= height)
								continue;
							if (tiles[x + ox][y + oy][z].isGround())
								floors++;
							else if (tiles[x+ox][y+oy][z].isWater())
								ponds++;
						}
					}

					temp[x][y] = floors >= ponds ? floor : water;
				}
			}
			for (int x = 0; x < width; x++)
				for (int y = 0; y < height; y++)
					if (temp[x][y] != null)
						tiles[x][y][z] = temp[x][y];
		}
	}
	
	/**
	 * A method that calls a recursive function to remove small rooms from the map
	 * Keeps track of an open list of the points in the room it is currently considering, a closed list of points of
	 * rooms that have already been considered
	 */
	private void deleteSmallRooms(int z, int volume, Tile wall) {
        HashMap<Integer, Integer> values = new HashMap<Integer,Integer>();
        int[][] regions = createRegions(z);
        for (int x = 0; x < width; x++) {
        	for (int y = 0; y < height; y++) {
        		if (regions[x][y] != 0) {
        			int num = regions[x][y];
        			if (!values.containsKey(num))
        				values.put(num, 1);
        			else
        				values.put(num, values.get(num)+1);
        		}
        	}
        }
        
        //Then move across the keyset and find all the small rooms
        List<Integer> smallRegions = new ArrayList<Integer>();
        for (int k : values.keySet())
        	if (values.get(k) <= volume)
        		smallRegions.add(k);
        
        //Then scan the map once more and turn all tiles to walls if they are part of a small region
        for (int x = 0; x < width; x++) {
        	for (int y = 0; y < height; y++) {
        		if (regions[x][y] != 0 && smallRegions.contains(regions[x][y])) {
        			tiles[x][y][z] = wall;
        			regions[x][y] = 0;
        		}
        	}
        }
	}
	
	/**
	 * A method that finds individual regions, nearly identical to the algorithm in GenerateDungeon
	 */
	private int[][] buildRegion(int[][] regions, int x, int y, int z, int region) {
		List<Point> open = new ArrayList<Point>();
		open.add(new Point(x,y,z));
		regions[x][y] = region;
		
		while(!open.isEmpty()) {
			Point p = open.remove(0);

            for (Point neighbor : p.neighbors4()){
                if (regions[neighbor.x][neighbor.y] > 0
                  || tiles[neighbor.x][neighbor.y][z].isWall())
                    continue;

                regions[neighbor.x][neighbor.y] = region;
                open.add(neighbor);
            }
		}
		return regions;
	}
	
	/**
	 * A method that finds all regions in a specified z level of the world
	 * Again, nearly identical to the algorithm in GenerateDungeon
	 */
	private int[][] createRegions(int z) {
		int[][] regions = new int[width][height];
		int nextRegion = 1;
		for (int x = 0; x < width; x++)
			for (int y = 0; y < height; y++)
				if (tiles[x][y][z].isGround() && regions[x][y] == 0)
					regions = buildRegion(regions, x,y,z, nextRegion++);
		return regions;
	}
	
	/**
	 * A short method that draws a wall around the edge of the world
	 */
	private void borderWall(int z, int thickness, Tile wall) {
		if (thickness < 1)
			thickness = 1;
		for (int x = 0; x < width; x++)
			for (int y = 0; y < height; y++)
				if (x <= thickness - 1 || y <= thickness - 1 || x >= width-thickness || y >= height-thickness)
					tiles[x][y][z] = wall;
	}
	
	/**
	 * A method that goes and changes all walls that are adjacent to a floor to a cave wall instead
	 * @param z
	 */
	private void setUpCaveTiles(int z, Tile oldWall, Tile newWall) {
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				Point p = new Point(x,y,z);
				if (tiles[p.x][p.y][p.z] != oldWall)
					continue;
				List<Point> n = p.neighbors8();
				for (Point k : n) {
					if (k.x < 0 || k.x >= width || k.y < 0 || k.y >= height)
						continue;
					if (!tiles[k.x][k.y][k.z].isWall()) {
						tiles[p.x][p.y][p.z] = newWall;
						break;
					}
				}
			}
		}
	}
	
	/**
	 * Go through each level a few times to place a staircase leading down, and then a connected staircase leading up on
	 * the next level
	 * @param amount: Amount of stairs per floor
	 */
	private void addStairs(int amount) {
		for (int z = 0; z < depth-1; z++) {
			for (int i = 0; i < amount; i++) {
				if ((z + 1) % 5 == 0)
					continue;
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
	}
	
	/**
	 * Randomly finds a point that is on the ground or has a non-null feature
	 * @param z: Z-level to search
	 */
	private Point getEmptyLocation(int z) {
		int x = (int)(Math.random() * width);
	    int y = (int)(Math.random() * height);
	    
	    if (tiles[x][y][z] == null) {
	    	System.out.println("NULL:  x-" + x + "  y-" + y + "  z-" + z);
	    }

	    while (!tiles[x][y][z].isGround() || features[x][y][z] != null) {
	        x = (int)(Math.random() * width);
	        y = (int)(Math.random() * height);
	    }
	    return new Point(x, y, z);
	}
	
	/**
	 * A helper method that returns a single level of the dungeon, specified as a 2d array
	 */
	private Tile[][] getDungeonSlice(int z) {
		Tile[][] newTiles = new Tile[width][height];
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				newTiles[x][y] = tiles[x][y][z];
				if (newTiles[x][y] == null)
					newTiles[x][y] = Tile.DUNGEON_WALL;
			}
		}
		return newTiles;
	}
	
	@SuppressWarnings("unused")
	private void printMap(int z) {
		for (int y=0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				char c = ' ';
				if (tiles[x][y][z] != null) {
					if (tiles[x][y][z].isGround())
						c = '0';
					if (tiles[x][y][z].isWall())
						c = '1';
				} else {
					c = 'E';
				}
				System.out.print(c);
			}
			System.out.print("\n");
		}
		System.out.print("\n");
	}

}
