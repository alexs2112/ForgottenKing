package assembly;

import creatures.Creature;
import creatures.Tag;
import items.Item;
import items.ItemTag;
import items.ItemType;
import world.World;

public class ItemFactory implements java.io.Serializable {
	private static final long serialVersionUID = 7769423305067121315L;
	private World world;
	private ConsumableFactory consumable;
	private WeaponFactory weapon;
	private ArmorFactory armor;
	private BookFactory book;
	private TrinketFactory trinket;
	private AmmoFactory ammo;
	public ConsumableFactory consumable() { return consumable; }
	public WeaponFactory weapon() { return weapon; }
	public ArmorFactory armor() { return armor; }
	public BookFactory book() { return book; }
	public TrinketFactory trinket() { return trinket; }
	public AmmoFactory ammo() { return ammo; }
	public void setCreatureFactory(CreatureFactory f) { 
		book.setCreatureFactory(f);
		weapon.setCreatureFactory(f);
	}	//For summons and stuff

	public ItemFactory(World world) {
		this.world = world;
		consumable = new ConsumableFactory(world);
		weapon = new WeaponFactory(world);
		armor = new ArmorFactory(world);
		book = new BookFactory(world);
		trinket = new TrinketFactory(world);
		ammo = new AmmoFactory(world);
	}
	
	public Item newRandomWeapon(int z) { return weapon.newRandomWeapon(z); }
	public Item newRandomArmor(int z) { return armor.newRandomArmor(z); }
	public Item newRandomPotion(int z) { return consumable.newRandomPotion(z); }
	
	public Item newVictoryItem(int z) {
		Item item = new Item("Glowing Runestone", ItemType.RUNESTONE, Loader.victoryItemIcon);
		item.addTag(ItemTag.VICTORYITEM);
		item.setDescription("What you need to complete your quest. All that you have to do now is return it to the surface");
		item.setWeight(0);
		world.addAtEmptyLocation(item, z);
		return item;
	}
	
	public void equipPlayer(Creature player) {
		if (player.is(Tag.FIGHTER)) {
			player.addEquipment(weapon().newMace(-1));
			player.addEquipment(armor().newLeatherArmor(-1));
		}
		else if (player.is(Tag.RANGER)) {
			player.addEquipment(weapon().newDagger(-1));
			player.addEquipment(weapon().newShortbow(-1));
			for (int i = 0; i < 8; i++)
				player.addItemToInventory(ammo().newArrow(-1));
		}
		else if (player.is(Tag.BERSERKER))
			player.addEquipment(weapon().newHandaxe(-1));
		else if (player.is(Tag.ELEMENTALIST)) {
			player.addItemToInventory(book().newBookOfKindling(-1));
			player.addEquipment(weapon().newDagger(-1));
		} else {	//Adventurer
			player.addEquipment(weapon().newDagger(-1));
			player.addEquipment(armor().newLeatherArmor(-1));
			for (int i = 0; i < 5; i++)
				player.addItemToInventory(ammo().newDart(-1));
		}
	}

}
