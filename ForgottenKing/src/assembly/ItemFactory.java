package assembly;

import creatures.Creature;
import creatures.Tag;
import features.Chest;
import items.Item;
import items.ItemTag;
import items.ItemType;

public class ItemFactory implements java.io.Serializable {
	private static final long serialVersionUID = 7769423305067121315L;
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

	public ItemFactory() {
		consumable = new ConsumableFactory();
		weapon = new WeaponFactory();
		armor = new ArmorFactory();
		book = new BookFactory();
		trinket = new TrinketFactory();
		ammo = new AmmoFactory();
	}
	
	public Item newRandomWeapon(int z) { return weapon.newRandomWeapon(z); }
	public Item newRandomArmor(int z) { return armor.newRandomArmor(); }
	public Item newRandomPotion(int z) { return consumable.newRandomPotion(); }
	
	public Item newVictoryItem(int z) {
		Item item = new Item("Glowing Runestone", ItemType.RUNESTONE, Loader.victoryItemIcon);
		item.addTag(ItemTag.VICTORYITEM);
		item.setDescription("What you need to complete your quest. All that you have to do now is return it to the surface");
		item.setWeight(0);
		return item;
	}
	
	public void equipPlayer(Creature player) {
		if (player.is(Tag.FIGHTER)) {
			player.addEquipment(weapon().newMace());
			player.addEquipment(armor().newLeatherArmor());
		}
		else if (player.is(Tag.RANGER)) {
			player.addEquipment(weapon().newDagger());
			player.addEquipment(weapon().newShortbow());
			for (int i = 0; i < 8; i++)
				player.addItemToInventory(ammo().newArrow(-1));
		}
		else if (player.is(Tag.BERSERKER))
			player.addEquipment(weapon().newHandaxe());
		else if (player.is(Tag.ELEMENTALIST)) {
			player.addItemToInventory(book().newBookOfKindling());
			player.addEquipment(weapon().newDagger());
		} else {	//Adventurer
			player.addEquipment(weapon().newDagger());
			player.addEquipment(armor().newLeatherArmor());
			for (int i = 0; i < 5; i++)
				player.addItemToInventory(ammo().newDart(-1));
		}
	}
	
	/**
	 * A method that fills a chest full of necessary items
	 */
	public void fillChest(Chest c, int z) {
		if (c.chestType() == Chest.ChestType.BARREL) {
			if (Math.random() < 0.5)
				return;
			Item item = ammo.newRandomAmmo(z);
			for (int i = 0; i < 2 + (int)(Math.random()*3); i++)
				c.addItem(item);
		} else {
			Item item = weapon.newRandomWeapon(z, true);
			c.addItem(item);
		}
	}

}
