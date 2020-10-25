package assembly;

import creatures.Type;
import items.Item;
import items.ItemTag;
import items.ItemType;
import javafx.scene.image.Image;
import world.World;

public class EquipmentFactory {
	private World world;
	
	//Images
	private Image shortWeaponFull = new Image(this.getClass().getResourceAsStream("resources/items/short_weapon_full.png"));
	private Image daggerIcon = tools.ImageCrop.cropImage(shortWeaponFull, 0, 0, 32, 32);
	
	private Image medWeaponFull = new Image(this.getClass().getResourceAsStream("resources/items/med_weapon_full.png"));
	private Image shortSwordIcon = tools.ImageCrop.cropImage(medWeaponFull, 0, 0, 32, 32);
	private Image handaxeIcon = tools.ImageCrop.cropImage(medWeaponFull, 0, 32, 32, 32);
	
	private Image longWeaponFull = new Image(this.getClass().getResourceAsStream("resources/items/long_weapon_full.png"));
	private Image staffIcon = tools.ImageCrop.cropImage(longWeaponFull, 160, 0, 32, 32);
	
	private Image rangedFull = new Image(this.getClass().getResourceAsStream("resources/items/ranged_full.png"));
	private Image shortbowIcon = tools.ImageCrop.cropImage(rangedFull, 64, 32, 32, 32);

	private Image armorFull = new Image(this.getClass().getResourceAsStream("resources/items/armor_full.png"));
	private Image leatherArmorIcon = tools.ImageCrop.cropImage(armorFull, 0, 0, 32, 32);
	private Image studdedLeatherArmorIcon = tools.ImageCrop.cropImage(armorFull, 32, 0, 32, 32);
	private Image copperBreastplateIcon = tools.ImageCrop.cropImage(armorFull, 0, 192, 32, 32);
	
	private Image devSwordIcon = tools.ImageCrop.cropImage(longWeaponFull, 160, 32, 32, 32);
	private Image devBreastplateIcon = tools.ImageCrop.cropImage(armorFull, 224, 192, 32, 32);
	
	public EquipmentFactory(World world) {
		this.world = world;
	}
	
	public Item newRandomMeleeWeapon(int z) {
		switch((int)(Math.random()*4)) {
		case 0: return newDagger(z);
		case 1: return newShortSword(z);
		case 3: return newHandaxe(z);
		default: return newStaff(z);
		}
	}
	
	public Item newRandomWeapon(int z) {
		switch((int)(Math.random()*5)) {
		case 0: return newDagger(z);
		case 1: return newShortSword(z);
		case 2: return newShortbow(z);
		case 3: return newHandaxe(z);
		default: return newStaff(z);
		}
	}
	public Item newRandomArmor(int z) {
		switch((int)(Math.random()*3)) {
		case 0: return newLeatherArmor(z);
		case 1: return newStuddedLeatherArmor(z);
		default: return newCopperBreastplate(z);
		}
	}

	
	
	public Item newDagger(int z) {
		Item item = new Item("Dagger", ItemType.WEAPON, daggerIcon);
		item.modifyAttackValue(2);
		item.setDamage(1,2);
		item.modifyWeaponDelay(-2);
		item.addTag(ItemTag.DAGGER);
		item.addTag(ItemTag.LIGHT);
		item.setDamageType(Type.PIERCING);
		item.setDescription("A short double-edged fighting knife with a sharp point. Ideal for quick strikes or stabbing unaware foes.");
		world.addAtEmptyLocation(item,z);
		return item;
	}
	
	public Item newShortSword(int z) {
		Item item = new Item("Short Sword", ItemType.WEAPON, shortSwordIcon);
		item.modifyAttackValue(3);
		item.setDamage(2, 4);
		item.addTag(ItemTag.SWORD);
		item.addTag(ItemTag.LIGHT);
		item.setDamageType(Type.SLASHING);
		item.setDescription("A short and light blade. Perfect for your off hand, or for slashing an enemy.");
		world.addAtEmptyLocation(item,z);
		return item;
	}
	
	//To be removed or changed in the future
	public Item newStaff(int z) {
		Item item = new Item("Staff", ItemType.WEAPON, staffIcon);
		item.modifyAttackValue(1);
		item.modifyArmorValue(1);
		item.setDamage(1, 3);
		item.addTag(ItemTag.POLEARM);
		item.setDescription("A stout walking stick.");
		world.addAtEmptyLocation(item,z);
		return item;
	}
	
	public Item newShortbow(int z) {
		Item item = new Item("Shortbow", ItemType.WEAPON, shortbowIcon);
		item.modifyRangedAttackValue(3);
		item.setDamage(1, 3);			
		item.setRangedDamage(2, 4);		//Standard Arrows typically add 2 damage
		item.addTag(ItemTag.BOW);
		item.setDescription("A short piece of rope attached to curved wood designed to fire arrows.");
		world.addAtEmptyLocation(item,z);
		return item;
	}
	public Item newHandaxe(int z) {
		Item item = new Item("Handaxe", ItemType.WEAPON, handaxeIcon);
		item.modifyAttackValue(1);
		item.setDamage(2, 4);
		item.addTag(ItemTag.AXE);
		item.addTag(ItemTag.CLEAVING);
		item.setDamageType(Type.SLASHING);
		item.setDescription("A small and mundane axe, it is still perfectly serviceable for cleaving enemies in two.");
		world.addAtEmptyLocation(item,z);
		return item;
	}
	
	public Item newLeatherArmor(int z) {
		Item item = new Item("Leather Armor", ItemType.ARMOR, leatherArmorIcon);
		item.modifyArmorValue(2);
		item.addTag(ItemTag.LIGHTARMOR);
		item.setDescription("A suit made from layers of tanned animal hide, this light armour provides basic protection with almost no hindrance to movement.");
		world.addAtEmptyLocation(item,z);
		return item;
	}
	public Item newStuddedLeatherArmor(int z) {
		Item item = new Item("Studded Leather Armor", ItemType.ARMOR, studdedLeatherArmorIcon);
		item.modifyArmorValue(4);
		item.addTag(ItemTag.MEDIUMARMOR);
		item.setDescription("A set of leather armor enhanced with metal studs providing additional defense, at the sake of swift movement.");
		world.addAtEmptyLocation(item,z);
		return item;
	}
	public Item newCopperBreastplate(int z) {
		Item item = new Item("Copper Breastplate", ItemType.ARMOR, copperBreastplateIcon);
		item.modifyArmorValue(6);
		item.addTag(ItemTag.HEAVYARMOR);
		item.setDescription("A set of interlocking copper plates. It is decent armor for the dungeon, if a bit heavy.");
		world.addAtEmptyLocation(item,z);
		return item;
	}
	
	public Item newDevSword(int z) {
		Item sword = new Item("Sword of the Dev", ItemType.WEAPON, devSwordIcon);
		sword.modifyAttackValue(400);
		sword.setDamage(2, 4);
		sword.setEffectOnHit(Effects.poisoned(5, 2, 0));
		world.addAtEmptyLocation(sword,z);
		sword.addTag(ItemTag.SWORD);
		sword.addTag(ItemTag.CLEAVING);
		sword.setDamageType(Type.SLASHING);
		return sword;
	}
	public Item newDevBreastplate(int z) {
		Item armor = new Item("Armor of the Dev", ItemType.ARMOR, devBreastplateIcon);
		armor.modifyArmorValue(500);
		world.addAtEmptyLocation(armor,z);
		return armor;
	}
	
	
}
