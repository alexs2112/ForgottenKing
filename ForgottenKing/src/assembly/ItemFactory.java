package assembly;

import creatures.Creature;
import creatures.Tag;
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
	public void setCreatureFactory(CreatureFactory f) { 
		book.setCreatureFactory(f);
	}	//For summons and stuff

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
		item.setDescription("What you need to complete your quest. All that you have to do now is return it to the surface");
		world.addAtEmptyLocation(item, z);
		return item;
	}
	
	public void equipPlayer(Creature player) {
		if (player.is(Tag.FIGHTER)) {
			player.addEquipment(equipment().newHandaxe(-1));
			player.addEquipment(equipment().newLeatherArmor(-1));
		}
		else if (player.is(Tag.RANGER)) {
			player.addEquipment(equipment().newDagger(-1));
			player.addEquipment(equipment().newShortbow(-1));
			for (int i = 0; i < 8; i++)
				player.addItemToInventory(ammo().newArrow(-1));
		}
		else if (player.is(Tag.BERSERKER))
			player.addEquipment(equipment().newHandaxe(-1));
		else {	//Adventurer
			player.addEquipment(equipment().newDagger(-1));
			player.addEquipment(equipment().newLeatherArmor(-1));
			for (int i = 0; i < 5; i++)
				player.addItemToInventory(ammo().newDart(-1));
		}
	}

}
