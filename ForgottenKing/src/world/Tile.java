package world;

import creatures.Creature;
import creatures.Tag;
//import algorithms.GetTileDirection;
import javafx.scene.image.Image;
import tools.GetTileDirection;
import tools.ImageCrop;

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
	DIRT_FLOOR_CENTER(ImageCrop.cropImage(new Image(Tile.class.getResourceAsStream("resources/floor_full.png")), 32, 608,32,32), TileType.FLOOR, Group.CAVE, "The floor"),
	WOOD_FLOOR(TileType.FLOOR, Group.WOOD, "The floor", 224, 576),
	DUNGEON_WALL(TileType.WALL, Group.DUNGEON, "The wall", 0, 192),
	DUNGEON_FLOOR(TileType.FLOOR, Group.DUNGEON, "The floor", 0, 192),
	DUNGEON_FLOOR_CENTER(ImageCrop.cropImage(new Image(Tile.class.getResourceAsStream("resources/floor_full.png")), 32, 224,32,32), TileType.FLOOR, Group.DUNGEON, "The floor"),
	DUNGEON_PIT(TileType.PIT, Group.DUNGEON, "A pit leading to nothing", 0, 64),
	WOOD_WALL(TileType.WALL, Group.WOOD, "The wall", 224, 192),
	GRASS_FLOOR(TileType.FLOOR, Group.MISC, "The floor", 224, 192),
	MINE_WALL(TileType.WALL, Group.CAVE, "The wall", 448, 192),
	STONE_PIT(TileType.PIT, Group.CAVE, "A pit", 0, 128),
	CAVE_POND(TileType.WATER, Group.CAVE, "A pool of water", 0, 320),
	
	UNKNOWN("resources/unknown.png", null, null, "Anything could be out there"),
	FLOOR_DEFAULT("resources/default/floor.png", TileType.FLOOR, Group.MISC, "The floor"),
	WALL_DEFAULT("resources/default/wall.png", TileType.WALL, Group.MISC, "The wall");
	
	private static final long serialVersionUID = 7769423305067121315L;
	private TileType type;
	public TileType type() { return type; }
	private Group group;
	public Group group() { return group; }
	private String description;
	public String desc() { return description; }
	private Image icon;
	public Image icon() { return icon; }
	private Image image;
	private Image floorImage = new Image(Tile.class.getResourceAsStream("resources/floor_full.png"));
	private Image wallImage = new Image(Tile.class.getResourceAsStream("resources/wall_full.png"));
	private Image pitImage = new Image(Tile.class.getResourceAsStream("resources/pits_full.png"));
	
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
	public Image ALL;
	public Image N;
	public Image E;
	public Image NE;
	public Image S;
	public Image NS;
	public Image ES;
	public Image NES;
	public Image W;
	public Image NW;
	public Image EW;
	public Image NEW;
	public Image SW;
	public Image NSW;
	public Image ESW;
	public Image NESW;
	
	//Three additional directions for pits
	public Image XNE;
	public Image XNW;
	public Image XNEW;
	
	Tile(String path, TileType type, Group group, String description) {
		this(new Image(Tile.class.getResourceAsStream(path)), type, group, description);
	}
	Tile(Image image, TileType type, Group group, String description) {
		this.type = type;
		this.description = description;
		icon = image;
		this.image = image;
		this.group = group;
	}
	Tile (TileType type, Group group, String description, int x, int y) {
		this.type = type;
		this.description = description;
		this.group = group;
		if (type == TileType.WALL) {
			this.ALL = ImageCrop.cropImage(wallImage, x+96, y+0, 32, 32);
			this.N = ImageCrop.cropImage(wallImage, x+32, y+32, 32, 32);
			this.NE = ImageCrop.cropImage(wallImage, x+0, y+64, 32, 32);
			this.NS = ImageCrop.cropImage(wallImage, x+0, y+32, 32, 32);
			this.ES = ImageCrop.cropImage(wallImage, x+0, y+0, 32, 32);
			this.NES = ImageCrop.cropImage(wallImage, x+96, y+32, 32, 32);
			this.NW = ImageCrop.cropImage(wallImage, x+64, y+64, 32, 32);
			this.EW = ImageCrop.cropImage(wallImage, x+32, y+0, 32, 32);
			this.NEW = ImageCrop.cropImage(wallImage, x+128, y+64, 32, 32);
			this.SW = ImageCrop.cropImage(wallImage, x+64, y+0, 32, 32);
			this.NSW = ImageCrop.cropImage(wallImage, x+160, y+32, 32, 32);
			this.ESW = ImageCrop.cropImage(wallImage, x+128, y+0, 32, 32);
			this.NESW = ImageCrop.cropImage(wallImage, x+128, y+32, 32, 32);
		}
		if (type == TileType.FLOOR) {
			this.ALL = ImageCrop.cropImage(floorImage, x+32, y+32, 32, 32);
			this.N = ImageCrop.cropImage(floorImage, x+32, y+0, 32, 32);
			this.E = ImageCrop.cropImage(floorImage, x+64, y+32, 32, 32);
			this.NE = ImageCrop.cropImage(floorImage, x+64, y+0, 32, 32);
			this.S = ImageCrop.cropImage(floorImage, x+32, y+64, 32, 32);
			this.NS = ImageCrop.cropImage(floorImage, x+160, y+32, 32, 32);
			this.ES = ImageCrop.cropImage(floorImage, x+64, y+64, 32, 32);
			this.NES = ImageCrop.cropImage(floorImage, x+192, y+32, 32, 32);
			this.W = ImageCrop.cropImage(floorImage, x+0, y+32, 32, 32);
			this.NW = ImageCrop.cropImage(floorImage, x+0, y+0, 32, 32);
			this.EW = ImageCrop.cropImage(floorImage,x+96,y+32,32,32);
			this.NEW = ImageCrop.cropImage(floorImage,x+96, y+0, 32, 32);
			this.SW = ImageCrop.cropImage(floorImage, x+0, y+64, 32, 32);
			this.NSW = ImageCrop.cropImage(floorImage, x+128, y+32, 32, 32);
			this.ESW = ImageCrop.cropImage(floorImage, x+96, y+64, 32, 32);
			this.NESW = ImageCrop.cropImage(floorImage, x+160, y+0, 32, 32);
		}
		if (type == TileType.PIT || type == TileType.WATER) {
			this.ALL = ImageCrop.cropImage(pitImage, x+32, y+32, 32, 32);
			this.N = ImageCrop.cropImage(pitImage, x+32, y+0, 32, 32);
			this.E = ImageCrop.cropImage(pitImage, x+64, y+32, 32, 32);
			this.NE = ImageCrop.cropImage(pitImage, x+64, y+0, 32, 32);
			this.S = ALL;
			this.NS = N;
			this.ES = E;
			this.NES = NE;
			this.W = ImageCrop.cropImage(pitImage, x+0, y+32, 32, 32);
			this.NW = ImageCrop.cropImage(pitImage, x+0, y+0, 32, 32);
			this.EW = ImageCrop.cropImage(pitImage,x+128,y+32,32,32);
			this.NEW = ImageCrop.cropImage(pitImage,x+128, y+0, 32, 32);
			this.SW = W;
			this.NSW = NW;
			this.ESW = EW;
			this.NESW = NEW;
			this.XNE = ImageCrop.cropImage(pitImage, x+160, y+0, 32, 32);
			this.XNEW = ImageCrop.cropImage(pitImage, x+192, y+0, 32, 32);
			this.XNW = ImageCrop.cropImage(pitImage, x+224, y+0, 32, 32);
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
		if (image != null)
			return icon;
		if (type == TileType.FLOOR)
			return GetTileDirection.handleFloor(world, this, x, y, z);
		else if (type == TileType.PIT || type == TileType.WATER)
			return GetTileDirection.handlePit(world, this, x, y, z);
		else
			return GetTileDirection.handleWall(world, this, x, y, z);
			
	}
	
	public boolean isEqual(Tile tile) {
		return (this == tile ||
				(this.type == tile.type() && this.group == tile.group()));
	}

}
