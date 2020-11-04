package assembly;

import javafx.scene.image.Image;
import tools.ImageCrop;

public final class Loader {
	private static Image effectsFull = new Image(ItemFactory.class.getResourceAsStream("resources/icons/effects_full.png"));
	
	public static Image healingIcon = ImageCrop.cropImage(effectsFull, 0, 0, 32, 32);
	public static Image poisonedIcon = ImageCrop.cropImage(effectsFull, 32, 0, 32, 32);
	public static Image burningIcon = ImageCrop.cropImage(effectsFull, 64, 0, 32, 32);
	public static Image strongIcon = ImageCrop.cropImage(effectsFull, 96, 0, 32, 32);
	public static Image slowedIcon = ImageCrop.cropImage(effectsFull, 128, 0, 32, 32);
	public static Image glowingIcon = ImageCrop.cropImage(effectsFull, 160, 0, 32, 32);
	public static Image weakIcon = ImageCrop.cropImage(effectsFull, 192, 0, 32, 32);
	public static Image blindIcon = ImageCrop.cropImage(effectsFull, 224, 0, 32, 32);
	public static Image vulnerableIcon = ImageCrop.cropImage(effectsFull, 256, 0, 32, 32);
	public static Image ragingIcon = ImageCrop.cropImage(effectsFull, 288, 0, 32, 32);
	
	public static Image armorOfFrostIcon = ImageCrop.cropImage(effectsFull, 0, 32, 32, 32);
	public static Image shockedIcon = ImageCrop.cropImage(effectsFull, 32, 32, 32, 32);
	public static Image swiftIcon = ImageCrop.cropImage(effectsFull, 64, 32, 32, 32);
	public static Image stunnedIcon = ImageCrop.cropImage(effectsFull, 96, 32, 32, 32);
	public static Image confusedIcon = ImageCrop.cropImage(effectsFull, 128, 32, 32, 32);
	
	private static Image abilitiesFull = new Image(ItemFactory.class.getResourceAsStream("resources/icons/abilities_full.png"));
	public static Image rageIcon = ImageCrop.cropImage(abilitiesFull, 0, 0, 32, 32);
	public static Image reachAttackIcon = ImageCrop.cropImage(abilitiesFull, 32, 0, 32, 32);
	public static Image knockbackAllIcon = ImageCrop.cropImage(abilitiesFull, 64, 0, 32, 32);
	
	private static Image runestonesFull = new Image(ItemFactory.class.getResourceAsStream("resources/items/rune_stones_full.png"));
	public static Image victoryItemIcon = ImageCrop.cropImage(runestonesFull, 0, 0, 32, 32);
}
