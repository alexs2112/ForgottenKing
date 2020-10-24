package world;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

import features.*;

public class PrefabLoader {
	private File file;
	private Scanner reader;
	private ArrayList<Prefab> prefabs;
	public ArrayList<Prefab> prefabs() { return prefabs; }
	private ArrayList<char[][]> charSheets;
	private String type;
	private boolean canLoad;
	public boolean canLoad() { return canLoad; }
	
	/**
	 * Prefabs must be written as
	 * [start]
	 * width as an int
	 * height as an int
	 * #########
	 * #########
	 * #########
	 *
	 * Must have the same width and height as defined
	 * Spaces are used as empty tiles and do not overwrite anything
	 * @param type:
	 *  - Village: Houses and stuff
	 */
	
	public PrefabLoader(String type) {
		prefabs = new ArrayList<Prefab>();
		charSheets = new ArrayList<char[][]>();
		this.type = type;
		canLoad = load();
		if (canLoad)
			loadRoomsPrefabs();
	}
	
	/**
	 * Reads the resource file and sets up a bunch of char[][] arrays of prefabs
	 * These chars are later loaded and turned into tiles based on specific chars
	 */
	private boolean load() {
		try {
			file = new File(Prefab.class.getResource("resources/rooms.prefab").getPath());
			System.out.println(file.getAbsolutePath());
			reader = new Scanner(file);
			while (reader.hasNextLine()) {
		        String data = reader.nextLine();
		        if (data.length() == 0)
		        	continue;
		        if (data.length() > 1)
		        	if (data.charAt(0) == '/' && data.charAt(1) == '/')
		        		continue;
		        if (data.contains("[start]")) {	//Start a new prefab
		        	String[] size = reader.nextLine().split(":");
		        	int width = Integer.valueOf(size[0]);
		        	int height = Integer.valueOf(size[1]);
		        	char[][] chars = new char[width][height];
		        	data = reader.nextLine();
		        	for (int y = 0; y < height; y++) {
		        		for (int x = 0; x < width; x++) {
		        			chars[x][y] = data.charAt(x);
		        		}
		        		data = reader.nextLine();
		        	}
		        	charSheets.add(chars);
		        }
		        
		      }
			return true;
		} catch (FileNotFoundException e) {
			//e.printStackTrace();
			System.out.println("Cannot load prefabs: file not found");
			return false;
		}
	}
	
	private void loadRoomsPrefabs() {
		for (char[][] chars : charSheets) {
			Tile[][] tiles = new Tile[chars.length][chars[0].length];
			Feature[][] features = new Feature[chars.length][chars[0].length];
			for (int x = 0; x < chars.length; x++) {
				for (int y = 0; y < chars[0].length; y++) {
					if (chars[x][y] != ' ')
						features[x][y] = null;
					if (chars[x][y] == '.')
						tiles[x][y] = Tile.DUNGEON_FLOOR;
					else if (chars[x][y] == '=')
						tiles[x][y] = Tile.DUNGEON_PIT;
					else if (chars[x][y] == '#')
						tiles[x][y] = Tile.DUNGEON_WALL;
					else if (chars[x][y] == '|') {
						tiles[x][y] = Tile.DUNGEON_FLOOR;
						features[x][y] = new Door(0);
					} else if (chars[x][y] == '-') {
						tiles[x][y] = Tile.DUNGEON_FLOOR;
						features[x][y] = new Door(1);
					}
				}
			}
			prefabs.add(new Prefab(chars, tiles, features));
		}
	}
	
	public Prefab getRandomPrefab() {
		return prefabs.get((int)(Math.random()*prefabs.size()));
	}
}
