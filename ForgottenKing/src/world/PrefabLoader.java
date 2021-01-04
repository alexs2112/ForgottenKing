package world;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Scanner;

import features.*;

public class PrefabLoader {
	private ArrayList<Prefab> prefabs;
	public ArrayList<Prefab> prefabs() { return prefabs; }
	private boolean canLoad;
	public boolean canLoad() { return canLoad; }
	private Type type;
	
	/**
	 * Prefabs must be written as
	 * [start]
	 * size=width:height
	 * #########
	 * #########
	 * #########
	 *
	 * Must have the same width and height as defined, otherwise an exception is thrown
	 */
	public PrefabLoader(Type type) {
		prefabs = new ArrayList<Prefab>();
		this.type = type;
		canLoad = load();
	}
	
	public static enum Type {
		GENERAL,
		LIZARDCULT,
		ORCPIRATES;
	}
	
	/**
	 * Read the specified resource final and load the prefab into a list of prefabs. This list is then read
	 * and selected from during dungeon generation
	 */
	private boolean load() {
		InputStream stream = Prefab.class.getResourceAsStream(type.name() + ".prefab");
		if (stream == null) {
			System.out.println(type.name() + ".prefab not located.");
			return false;
		}
		Scanner reader = new Scanner(stream);
		while (reader.hasNextLine()) {
			String data = reader.nextLine();
			if (data.length() == 0)
				continue;
			if (data.length() > 1)
				if (data.charAt(0) == '/' && data.charAt(1) == '/')
					//A comment
					continue;
			if (data.contains("[start]")) {	//Start a new prefab
				String[] newline = reader.nextLine().split("=");
				
				//The final thing before the prefab map has to be the size
				if (newline[0].contains("size")) {
					String[] size = newline[1].split(":");
					int width = Integer.valueOf(size[0]);
					int height = Integer.valueOf(size[1]);
					char[][] chars = new char[width][height];
					for (int y = 0; y < height; y++) {
						data = reader.nextLine();
						for (int x = 0; x < width; x++) {
							chars[x][y] = data.charAt(x);
						}
					}
					if (type == Type.LIZARDCULT)
						loadLizardPrefab(chars);
					else if (type == Type.ORCPIRATES)
						loadOrcPrefab(chars);
				}
			}
		}
		reader.close();
		return true;
	}
	
	/**
	 * A bunch of methods that specify the loading of different groups of prefabs
	 * There is almost certainly a better way to do this
	 * @param chars
	 */
	private void loadLizardPrefab(char[][] chars) {
		Tile[][] tiles = new Tile[chars.length][chars[0].length];
		Feature[][] features = new Feature[chars.length][chars[0].length];
		for (int x = 0; x < chars.length; x++) {
			for (int y = 0; y < chars[0].length; y++) {
				if (chars[x][y] != ' ')
					features[x][y] = null;
				if (chars[x][y] == '.')
					tiles[x][y] = Tile.DUNGEON_FLOOR;
				else if (chars[x][y] == ',')
					tiles[x][y] = Tile.WOOD_FLOOR;
				else if (chars[x][y] == ';')
					tiles[x][y] = Tile.DIRT_FLOOR;
				else if (chars[x][y] == '=')
					tiles[x][y] = Tile.DUNGEON_PIT;
				else if (chars[x][y] == '#')
					tiles[x][y] = Tile.DUNGEON_WALL;
				else if (chars[x][y] == '$')
					tiles[x][y] = Tile.WOOD_WALL;
				else if (chars[x][y] == 'T') {
					tiles[x][y] = Tile.DIRT_FLOOR;
					features[x][y] = new Block(Block.Type.TREE);
				} else if (chars[x][y] == '+') {
					tiles[x][y] = Tile.DUNGEON_FLOOR;
					features[x][y] = new Door();
				}
			}
		}
		Prefab p = new Prefab(chars, tiles, features);
		prefabs.add(p);
	}
	
	private void loadOrcPrefab(char[][] chars) {
		Tile[][] tiles = new Tile[chars.length][chars[0].length];
		Feature[][] features = new Feature[chars.length][chars[0].length];
		for (int x = 0; x < chars.length; x++) {
			for (int y = 0; y < chars[0].length; y++) {
				if (chars[x][y] != ' ')
					features[x][y] = null;
				if (chars[x][y] == '.')
					tiles[x][y] = Tile.DIRT_FLOOR;
				else if (chars[x][y] == ',')
					tiles[x][y] = Tile.WOOD_FLOOR;
				else if (chars[x][y] == '#')
					tiles[x][y] = Tile.CAVE_WALL;
				else if (chars[x][y] == '$')
					tiles[x][y] = Tile.MINE_WALL;
				else if (chars[x][y] == '/')
					tiles[x][y] = Tile.WOOD_WALL;
				else if (chars[x][y] == '=')
					tiles[x][y] = Tile.STONE_PIT;
				else if (chars[x][y] == 'w')
					tiles[x][y] = Tile.CAVE_POND;
				else if (chars[x][y] == 'v') { 
					tiles[x][y] = Tile.DIRT_FLOOR;
					features[x][y] = new Chest(Chest.ChestType.BARREL);
				} else if (chars[x][y] == '+') {
					tiles[x][y] = Tile.DIRT_FLOOR;
					features[x][y] = new Door();
				} else {
					System.out.println("Unreadable character: " + chars[x][y]);
				}
			}
		}
		Prefab p = new Prefab(chars, tiles, features);
		prefabs.add(p);
	}
	
	public Prefab getRandomPrefab() {
		return prefabs.get((int)(Math.random()*prefabs.size())).rotate();
	}
}
