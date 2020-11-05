package world;

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
	BOUNDS("resources/default/bounds.png", "Bounds", "This doesn't exist"),
	CAVE_WALL("Wall", "The wall", 0, 576),
	DIRT_FLOOR("Floor", "The floor", 0, 576),
	DIRT_FLOOR_CENTER(ImageCrop.cropImage(new Image(Tile.class.getResourceAsStream("resources/floor_full.png")), 32, 608,32,32), "Floor", "The floor"),
	WOOD_FLOOR("Floor", "The floor", 224, 576),
	DUNGEON_WALL("Wall", "The wall", 0, 192),
	DUNGEON_FLOOR("Floor", "The floor", 0, 192),
	DUNGEON_FLOOR_CENTER(ImageCrop.cropImage(new Image(Tile.class.getResourceAsStream("resources/floor_full.png")), 32, 224,32,32), "Floor", "The floor"),
	DUNGEON_PIT("Chasm", "A yawning chasm", 0, 64),
	WOOD_WALL("Wall", "The wall", 224, 192),
	GRASS_FLOOR("Floor", "The floor", 224, 192),
	
	UNKNOWN("resources/unknown.png", "Unknown", "Anything could be out there"),
	FLOOR_DEFAULT("resources/default/floor.png", "Floor", "The floor"),
	WALL_DEFAULT("resources/default/wall.png", "Wall", "The wall");
	
	private String type;
	public String type() { return type; }
	private String description;
	public String desc() { return description; }
	private Image icon;
	public Image icon() { return icon; }
	private Image image;
	private Image floorImage = new Image(Tile.class.getResourceAsStream("resources/floor_full.png"));
	private Image wallImage = new Image(Tile.class.getResourceAsStream("resources/wall_full.png"));
	private Image pitImage = new Image(Tile.class.getResourceAsStream("resources/pits_full.png"));
	
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
	
	Tile(String path, String type, String description) {
		this(new Image(Tile.class.getResourceAsStream(path)), type, description);
	}
	Tile(Image image, String type, String description) {
		this.type = type;
		this.description = description;
		icon = image;
		this.image = image;
	}
	Tile (String type, String description, int x, int y) {
		this.type = type;
		this.description = description;
		if (type.equals("Wall")) {
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
		if (type.equals("Floor")) {
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
		if (type.equals("Chasm")) {
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
	    return this.type().equals("Floor");
	}
	public boolean isWall() {
		return this.type().equals("Wall");
	}
	public boolean isPit() {
		return this.type().equals("Chasm");
	}
	
	public Image getImage(World world, int x, int y, int z) {
		if (image != null)
			return icon;
		if (type.equals("Floor"))
			return GetTileDirection.handleFloor(world, this, x, y, z);
		else if (type.equals("Chasm"))
			return GetTileDirection.handleChasm(world, this, x, y, z);
		else
			return GetTileDirection.handleWall(world, this, x, y, z);
			
	}

}
