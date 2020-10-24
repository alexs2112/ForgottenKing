package assembly;

import creatures.Type;
import items.Item;
import items.ItemType;
import javafx.scene.image.Image;
import world.World;

public class TrinketFactory {
	private World world;
	
	private Image ringFull = new Image(this.getClass().getResourceAsStream("resources/items/ring_full.png"));
	private Image poisonResistanceRingIcon = tools.ImageCrop.cropImage(ringFull, 0, 0, 32, 32);
	private Image devRingIcon = tools.ImageCrop.cropImage(ringFull, 32, 32, 32, 32);
	
	public TrinketFactory(World world) {
		this.world = world;
	}
	
	public Item newRingOfPoisonResistance(int z) {
		Item item = new Item("Ring of Poison Resistance", ItemType.RING, poisonResistanceRingIcon);
		item.addResistance(Type.POISON, 1);
		item.setDescription("A small brass ring, it is etched with a serpentine design and glows a slight green.");
		world.addAtEmptyLocation(item, z);
		return item;
	}
	
	public Item newDevRing(int z) {
		Item ring = new Item("Ring of the Dev", ItemType.RING, devRingIcon);
		ring.modifyAttackValue(10);
		ring.modifyArmorValue(10);
		world.addAtEmptyLocation(ring, z);
		return ring;
	}

}
