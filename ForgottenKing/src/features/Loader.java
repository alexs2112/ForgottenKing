package features;

import javafx.scene.image.Image;
import tools.ImageCrop;

public final class Loader {

	private static Image doorClosedFull = new Image(Loader.class.getResourceAsStream("resources/door_closed_full.png"));
	private static Image doorOpenFull = new Image(Loader.class.getResourceAsStream("resources/door_open_full.png"));
	private static Image tileFull = new Image(Loader.class.getResourceAsStream("resources/tile_full.png"));
	
	public static Image closedDoorUp = ImageCrop.cropImage(doorClosedFull, 0, 0, 32, 32);
	public static Image closedDoorSide = ImageCrop.cropImage(doorClosedFull, 32, 0, 32, 32);
	public static Image openDoorUp = ImageCrop.cropImage(doorOpenFull, 0, 0, 32, 32);
	public static Image openDoorSide = ImageCrop.cropImage(doorOpenFull, 32, 0, 32, 32);
	public static Image upStairs = ImageCrop.cropImage(tileFull, 160, 96, 32, 32);
	public static Image downStairs = ImageCrop.cropImage(tileFull, 224, 96, 32, 32);
	public static Image entrance = new Image(Loader.class.getResourceAsStream("resources/entrance.gif"));
	
	
	/**
	 * Image handling for directional trees
	 * The X in direction means all 6-7 directions are the same, except those corners
	 * Otherwise, use cardinal directions only
	 */
	public static Image treeFull = new Image(Loader.class.getResourceAsStream("resources/trees_full.png"));
	/*public static Image tree = ImageCrop.cropImage(treeFull, 32,128, 32, 32);
	public static Image treeN = ImageCrop.cropImage(treeFull, 32,96, 32, 32);
	public static Image treeNE = ImageCrop.cropImage(treeFull, 64,96, 32, 32);
	public static Image treeE = ImageCrop.cropImage(treeFull, 64,128, 32, 32);
	public static Image treeES = ImageCrop.cropImage(treeFull, 64,160, 32, 32);
	public static Image treeS = ImageCrop.cropImage(treeFull, 32,160, 32, 32);
	public static Image treeSW = ImageCrop.cropImage(treeFull, 0,160, 32, 32);
	public static Image treeW = ImageCrop.cropImage(treeFull, 0,128, 32, 32);
	public static Image treeNW = ImageCrop.cropImage(treeFull, 0,96, 32, 32);
	public static Image treeXES = ImageCrop.cropImage(treeFull, 128,96, 32, 32);
	public static Image treeXSW = ImageCrop.cropImage(treeFull, 160,96, 32, 32);
	public static Image treeXNE = ImageCrop.cropImage(treeFull, 128,128, 32, 32);
	public static Image treeXNW = ImageCrop.cropImage(treeFull, 160,128, 32, 32);
	public static Image treeXNWES = ImageCrop.cropImage(treeFull, 128,160, 32, 32);
	public static Image treeXNESW = ImageCrop.cropImage(treeFull, 160,160, 32, 32);*/
	public static Image treeNESW = ImageCrop.cropImage(treeFull, 96,96, 32, 32);
	

}
