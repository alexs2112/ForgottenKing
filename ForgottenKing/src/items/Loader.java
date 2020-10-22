package items;

import javafx.scene.image.Image;
import tools.ImageCrop;

public final class Loader {
	private static Image weaponIconsFull = new Image(Item.class.getResourceAsStream("resources/weapon_icons_full.png"));
	public static Image daggerIconGold = ImageCrop.cropImage(weaponIconsFull, 0, 64, 32, 32);
	public static Image swordIconGold = ImageCrop.cropImage(weaponIconsFull, 32, 64, 32, 32);
	public static Image axeIconGold = ImageCrop.cropImage(weaponIconsFull, 64, 64, 32, 32);
	public static Image maceIconGold = ImageCrop.cropImage(weaponIconsFull, 96, 64, 32, 32);
	public static Image polearmIconGold = ImageCrop.cropImage(weaponIconsFull, 0, 96, 32, 32);
	public static Image bowIconGold = ImageCrop.cropImage(weaponIconsFull, 32, 96, 32, 32);
	public static Image crossbowIconGold = ImageCrop.cropImage(weaponIconsFull, 64, 96, 32, 32);
	public static Image slingIconGold = ImageCrop.cropImage(weaponIconsFull, 96, 96, 32, 32);
}
