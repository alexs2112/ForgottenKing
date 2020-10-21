package assembly;

import javafx.scene.image.Image;
import tools.ImageCrop;

public final class Loader {
	private static Image effectsFull = new Image(ItemFactory.class.getResourceAsStream("resources/effects/effects_full.png"));
	
	public static Image healingIcon = ImageCrop.cropImage(effectsFull, 0, 0, 32, 32);
	public static Image poisonedIcon = ImageCrop.cropImage(effectsFull, 32, 0, 32, 32);
	public static Image burningIcon = ImageCrop.cropImage(effectsFull, 64, 0, 32, 32);
	public static Image strongIcon = ImageCrop.cropImage(effectsFull, 96, 0, 32, 32);
	public static Image slowedIcon = ImageCrop.cropImage(effectsFull, 128, 0, 32, 32);
	public static Image glowingIcon = ImageCrop.cropImage(effectsFull, 160, 0, 32, 32);
	
	private static Image runestonesFull = new Image(ItemFactory.class.getResourceAsStream("resources/items/rune_stones_full.png"));
	public static Image victoryItemIcon = ImageCrop.cropImage(runestonesFull, 0, 0, 32, 32);
}
