package world;

import features.Feature;

public class Prefab {
	private char[][] chars;
	public char[][] chars() { return chars; }
	private Tile[][] tiles;
	public Tile[][] tiles() { return tiles; }
	public Tile tile(int x, int y) { return tiles[x][y]; }
	private Feature[][] features;
	public Feature[][] features() { return features; }
	public Feature feature(int x, int y) { return features[x][y]; }
	private int width;
	private int height;
	public int width() { return width; }
	public int height() { return height; }
	private int[][] nums;
	public int[][] nums() { return nums; }
	public int num(int x, int y) { return nums[x][y]; }
	
	public Prefab(char[][] chars, Tile[][] tiles, Feature[][] features) {
		this.chars = chars;
		this.tiles = tiles;
		this.features = features;
		this.width = chars.length;
		this.height = chars[0].length;
		nums = new int[width][height];
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				if (tiles[x][y].isGround())
					nums[x][y] = 0;
				else if (tiles[x][y].isPit())
					nums[x][y] = -1;
				else
					nums[x][y] = 1;
			}
		}
	}
	
	/**
	 * Copy this prefab and return a new rotated one
	 * Something isnt quite working here, this has to be redone in the future anyway
	 */
	public Prefab rotate() {
		int rotation = (int)(Math.random()*4);		//The number of clockwise turns are applied
		char[][] newChars;
		Tile[][] newTiles;
		Feature[][] newFeatures;
		if (rotation == 1) {
			newChars = new char[height][width];
			newTiles = new Tile[height][width];
			newFeatures = new Feature[height][width];
			for (int x = 0; x < width; x++) {
				for (int y = 0; y < height; y++) {
					newChars[height-1-y][x] = chars[x][y];
					newTiles[height-1-y][x] = tiles[x][y];
					newFeatures[height-1-y][x] = features[x][y];
				}
			}
			return new Prefab(newChars, newTiles, newFeatures);
		}
		if (rotation == 2) {
			newChars = new char[width][height];
			newTiles = new Tile[width][height];
			newFeatures = new Feature[width][height];
			for (int x = 0; x < width; x++) {
				for (int y = 0; y < height; y++) {
					newChars[width-1-x][height-1-y] = chars[x][y];
					newTiles[width-1-x][height-1-y] = tiles[x][y];
					newFeatures[width-1-x][height-1-y] = features[x][y];
				}
			}
			return new Prefab(newChars, newTiles, newFeatures);
		}
		if (rotation == 3) {
			newChars = new char[height][width];
			newTiles = new Tile[height][width];
			newFeatures = new Feature[height][width];
			for (int x = 0; x < width; x++) {
				for (int y = 0; y < height; y++) {
					newChars[height-1-y][width-1-x] = chars[x][y];
					newTiles[height-1-y][width-1-x] = tiles[x][y];
					newFeatures[height-1-y][width-1-x] = features[x][y];
				}
			}
			return new Prefab(newChars, newTiles, newFeatures);
		}
		return this;
	}
}
