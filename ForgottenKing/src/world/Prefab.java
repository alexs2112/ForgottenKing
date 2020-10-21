package world;

import features.Feature;

public class Prefab {
	private char[][] chars;
	public char[][] chars() { return chars; }
	private Tile[][] tiles;
	public Tile[][] tiles() { return tiles; }
	private Feature[][] features;
	public Feature[][] features() { return features; }
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
}
