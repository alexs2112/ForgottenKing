package assembly;

import items.Item;
import items.ItemTag;
import items.ItemType;
import world.World;

public class ItemFactory {
	private World world;
	private ConsumableFactory consumable;
	private EquipmentFactory equipment;
	private BookFactory book;
	private TrinketFactory trinket;
	private AmmoFactory ammo;
	public ConsumableFactory consumable() { return consumable; }
	public EquipmentFactory equipment() { return equipment; }
	public BookFactory book() { return book; }
	public TrinketFactory trinket() { return trinket; }
	public AmmoFactory ammo() { return ammo; }

	public ItemFactory(World world) {
		this.world = world;
		consumable = new ConsumableFactory(world);
		equipment = new EquipmentFactory(world);
		book = new BookFactory(world);
		trinket = new TrinketFactory(world);
		ammo = new AmmoFactory(world);
	}
	
	public Item newRandomWeapon(int z) { return equipment.newRandomWeapon(z); }
	public Item newRandomArmor(int z) { return equipment.newRandomArmor(z); }
	public Item newRandomPotion(int z) { return consumable.newRandomPotion(z); }
	
	public Item newVictoryItem(int z) {
		Item item = new Item("Glowing Runestone", ItemType.RUNESTONE, Loader.victoryItemIcon);
		item.addTag(ItemTag.VICTORYITEM);
		world.addAtEmptyLocation(item, z);
		return item;
	}

}
