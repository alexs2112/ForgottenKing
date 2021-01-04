package world;

import creatures.Creature;
import creatures.Tag;
import javafx.scene.image.Image;
import tools.Icon;
import tools.GetTileDirection;

/**
Direction of different tile
W S E N = new
0 0 0 0 = ALL
0 0 0 1 = N
0 0 1 0 = E
0 0 1 1 = NE
0 1 0 0 = S
0 1 0 1 = NS
0 1 1 0 = ES
0 1 1 1 = NES
1 0 0 0 = W
1 0 0 1 = NW
1 0 1 0 = EW
1 0 1 1 = NEW
1 1 0 0 = SW
1 1 0 1 = NSW
1 1 1 0 = ESW
1 1 1 1 = NESW*/

public enum Tile {
	BOUNDS("resources/default/bounds.png", null, null, "This doesn't exist"),
	CAVE_WALL(TileType.WALL, Group.CAVE, "The wall", 0, 576),
	DIRT_FLOOR(TileType.FLOOR, Group.CAVE, "The floor", 0, 576),
	DIRT_FLOOR_CENTER(new Icon("tiles/floor-full.png", 32,608), TileType.FLOOR, Group.CAVE, "The floor"),
	WOOD_FLOOR(TileType.FLOOR, Group.WOOD, "The floor", 224, 576),
	DUNGEON_WALL(TileType.WALL, Group.DUNGEON, "The wall", 0, 192),
	DUNGEON_FLOOR(TileType.FLOOR, Group.DUNGEON, "The floor", 0, 192),
	DUNGEON_FLOOR_CENTER(new Icon("tiles/floor-full.png", 32, 224), TileType.FLOOR, Group.DUNGEON, "The floor"),
	DUNGEON_PIT(TileType.PIT, Group.DUNGEON, "A pit leading to nothing", 0, 64),
	WOOD_WALL(TileType.WALL, Group.WOOD, "The wall", 224, 192),
	GRASS_FLOOR(TileType.FLOOR, Group.MISC, "The floor", 224, 192),
	MINE_WALL(TileType.WALL, Group.CAVE, "The wall", 448, 192),
	STONE_PIT(TileType.PIT, Group.CAVE, "A pit", 0, 128),
	CAVE_POND(TileType.WATER, Group.CAVE, "A pool of water", 0, 320),
	
	UNKNOWN("tiles/unknown.png", null, null, "Anything could be out there"),
	FLOOR_DEFAULT("tiles/default/floor.png", TileType.FLOOR, Group.MISC, "The floor"),
	WALL_DEFAULT("tiles/default/wall.png", TileType.WALL, Group.MISC, "The wall");
	
	private static final long serialVersionUID = 7769423305067121315L;
	private TileType type;
	public TileType type() { return type; }
	private Group group;
	public Group group() { return group; }
	private String description;
	public String desc() { return description; }
	private Icon icon;
	public Icon icon() { return icon; }
	private Image image() { return icon.image(); }
	private boolean staticImage;
	
	private enum TileType {
		UNKNOWN,
		FLOOR,
		WALL,
		WATER,
		PIT;
	}
	private enum Group {
		CAVE,
		DUNGEON,
		WOOD,
		MINE,
		MISC;
	}
	
	//The direction specified is the direction where the tile art "ends" (ie, adjacent to different tile)
	public Icon ALL;
	public Icon N;
	public Icon E;
	public Icon NE;
	public Icon S;
	public Icon NS;
	public Icon ES;
	public Icon NES;
	public Icon W;
	public Icon NW;
	public Icon EW;
	public Icon NEW;
	public Icon SW;
	public Icon NSW;
	public Icon ESW;
	public Icon NESW;
	
	//Three additional directions for pits
	public Icon XNE;
	public Icon XNW;
	public Icon XNEW;
	
	Tile(String path, TileType type, Group group, String description) {
		this(new Icon(path), type, group, description);
		this.staticImage = true;
	}
	Tile(Icon image, TileType type, Group group, String description) {
		this.type = type;
		this.description = description;
		icon = image;
		this.group = group;
		this.staticImage = true;
	}
	Tile (TileType type, Group group, String description, int x, int y) {
		this.type = type;
		this.description = description;
		this.group = group;
		if (type == TileType.WALL) {
			this.ALL = new Icon("tiles/wall-full.png", x+96, y+0);
			this.N = new Icon("tiles/wall-full.png", x+32, y+32);
			this.NE = new Icon("tiles/wall-full.png", x+0, y+64);
			this.NS = new Icon("tiles/wall-full.png", x+0, y+32);
			this.ES = new Icon("tiles/wall-full.png", x+0, y+0);
			this.NES = new Icon("tiles/wall-full.png", x+96, y+32);
			this.NW = new Icon("tiles/wall-full.png", x+64, y+64);
			this.EW = new Icon("tiles/wall-full.png", x+32, y+0);
			this.NEW = new Icon("tiles/wall-full.png", x+128, y+64);
			this.SW = new Icon("tiles/wall-full.png", x+64, y+0);
			this.NSW = new Icon("tiles/wall-full.png", x+160, y+32);
			this.ESW = new Icon("tiles/wall-full.png", x+128, y+0);
			this.NESW = new Icon("tiles/wall-full.png", x+128, y+32);
		}
		if (type == TileType.FLOOR) {
			this.ALL = new Icon("tiles/floor-full.png", x+32, y+32);
			this.N = new Icon("tiles/floor-full.png", x+32, y+0);
			this.E = new Icon("tiles/floor-full.png", x+64, y+32);
			this.NE = new Icon("tiles/floor-full.png", x+64, y+0);
			this.S = new Icon("tiles/floor-full.png", x+32, y+64);
			this.NS = new Icon("tiles/floor-full.png", x+160, y+32);
			this.ES = new Icon("tiles/floor-full.png", x+64, y+64);
			this.NES = new Icon("tiles/floor-full.png", x+192, y+32);
			this.W = new Icon("tiles/floor-full.png", x+0, y+32);
			this.NW = new Icon("tiles/floor-full.png", x+0, y+0);
			this.EW = new Icon("tiles/floor-full.png",x+96,y+32,32,32);
			this.NEW = new Icon("tiles/floor-full.png",x+96, y+0);
			this.SW = new Icon("tiles/floor-full.png", x+0, y+64);
			this.NSW = new Icon("tiles/floor-full.png", x+128, y+32);
			this.ESW = new Icon("tiles/floor-full.png", x+96, y+64);
			this.NESW = new Icon("tiles/floor-full.png", x+160, y+0);
		}
		if (type == TileType.PIT || type == TileType.WATER) {
			this.ALL = new Icon("tiles/pits-full.png", x+32, y+32);
			this.N = new Icon("tiles/pits-full.png", x+32, y+0);
			this.E = new Icon("tiles/pits-full.png", x+64, y+32);
			this.NE = new Icon("tiles/pits-full.png", x+64, y+0);
			this.S = ALL;
			this.NS = N;
			this.ES = E;
			this.NES = NE;
			this.W = new Icon("tiles/pits-full.png", x+0, y+32);
			this.NW = new Icon("tiles/pits-full.png", x+0, y+0);
			this.EW = new Icon("tiles/pits-full.png",x+128,y+32,32,32);
			this.NEW = new Icon("tiles/pits-full.png",x+128, y+0);
			this.SW = W;
			this.NSW = NW;
			this.ESW = EW;
			this.NESW = NEW;
			this.XNE = new Icon("tiles/pits-full.png", x+160, y+0);
			this.XNEW = new Icon("tiles/pits-full.png", x+192, y+0);
			this.XNW = new Icon("tiles/pits-full.png", x+224, y+0);
		}
		this.icon = ALL;
	}
	
	public boolean isGround() {
	    return this.type() == TileType.FLOOR;
	}
	public boolean isWall() {
		return this.type() == TileType.WALL;
	}
	public boolean isPit() {
		return this.type() == TileType.PIT;
	}
	public boolean isWater() {
		return this.type() == TileType.WATER;
	}
	public boolean canMoveOn(Creature c) {
		return isGround() ||
				(isPit() && c.is(Tag.FLYING)) ||
				isWater();
	}
	
	public Image getImage(World world, int x, int y, int z) {
		if (staticImage)
			return image();
		if (type == TileType.FLOOR)
			return GetTileDirection.handleFloor(world, this, x, y, z).image();
		else if (type == TileType.PIT || type == TileType.WATER)
			return GetTileDirection.handlePit(world, this, x, y, z).image();
		else
			return GetTileDirection.handleWall(world, this, x, y, z).image();
			
	}
	
	public boolean isEqual(Tile tile) {
		return (this == tile ||
				(this.type == tile.type() && this.group == tile.group()));
	}

}
