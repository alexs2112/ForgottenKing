package assembly;

import creatures.Type;
import items.Item;
import items.ItemType;
import javafx.scene.image.Image;
import tools.ImageCrop;
import world.World;

public class AmmoFactory {
	private World world;
	private Image rangedFull = new Image(this.getClass().getResourceAsStream("resources/items/ranged_full.png"));
	
	private Image arrowImage = ImageCrop.cropImage(rangedFull, 0, 64, 32, 32);
	
	public AmmoFactory(World world) {
		this.world = world;
	}

	public Item newArrow(int z) {
		Item item = new Item("arrow", ItemType.ARROW, arrowImage);
		item.setDamageType(Type.PIERCING);
		item.setRangedDamage(2, 2);
		int amount = (int)(Math.random()*5 + 2);
		world.addAtEmptyLocation(item, z, amount);
		return item;
	}
	
	
}
