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
	
	private static Image itemTagsFull = new Image(Item.class.getResourceAsStream("resources/item_tags_full.png"));
	public static Image lightArmorTag = ImageCrop.cropImage(itemTagsFull, 0,0, 32, 32);
	public static Image mediumArmorTag = ImageCrop.cropImage(itemTagsFull, 32,0, 32, 32);
	public static Image heavyArmorTag = ImageCrop.cropImage(itemTagsFull, 64,0, 32, 32);
	public static Image throwingTag = ImageCrop.cropImage(itemTagsFull, 96,0, 32, 32);
	public static Image cleavingTag = ImageCrop.cropImage(itemTagsFull, 128,0, 32, 32);
	public static Image lightWeaponTag = ImageCrop.cropImage(itemTagsFull, 160,0, 32, 32);
	public static Image highCritTag = ImageCrop.cropImage(itemTagsFull, 192,0, 32, 32);
	public static Image shieldTag = ImageCrop.cropImage(itemTagsFull, 224,0, 32, 32);
	public static Image twoHandedTag = ImageCrop.cropImage(itemTagsFull, 0,32, 32, 32);
	public static Image versatileTag = ImageCrop.cropImage(itemTagsFull, 32,32, 32, 32);
	public static Image victoryItemTag = ImageCrop.cropImage(itemTagsFull, 64,32, 32, 32);
}
