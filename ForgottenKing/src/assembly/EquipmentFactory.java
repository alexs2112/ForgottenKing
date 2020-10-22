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
	private Image rocksIconFull = new Image(this.getClass().getResourceAsStream("resources/items/rocks_full.png"));
	private Image rockIconA = tools.ImageCrop.cropImage(rocksIconFull, 32, 32, 32, 32);
	private Image rockIconB = tools.ImageCrop.cropImage(rocksIconFull, 96, 0, 32, 32);
	
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

	public Item newRock(int z) {
		Item rock = new Item("Rock", ItemType.STONE, rockIconB);
		rock.modifyThrownAttackValue(1);
		rock.addTag(ItemTag.THROWING);
		world.addAtEmptyLocation(rock,z);
		return rock;
	}
	
	public Item newDagger(int z) {
		Item dagger = new Item("Dagger", ItemType.WEAPON, daggerIcon);
		dagger.modifyAttackValue(2);
		dagger.setDamage(1,2);
		dagger.modifyWeaponDelay(-2);
		dagger.addTag(ItemTag.DAGGER);
		dagger.addTag(ItemTag.LIGHT);
		dagger.setDamageType(Type.PIERCING);
		world.addAtEmptyLocation(dagger,z);
		return dagger;
	}
	
	public Item newShortSword(int z) {
		Item sword = new Item("Short Sword", ItemType.WEAPON, shortSwordIcon);
		sword.modifyAttackValue(3);
		sword.setDamage(2, 4);
		sword.addTag(ItemTag.SWORD);
		sword.addTag(ItemTag.LIGHT);
		sword.setDamageType(Type.SLASHING);
		world.addAtEmptyLocation(sword,z);
		return sword;
	}
	
	public Item newStaff(int z) {
		Item staff = new Item("Staff", ItemType.WEAPON, staffIcon);
		staff.modifyAttackValue(1);
		staff.modifyArmorValue(1);
		staff.setDamage(1, 3);
		staff.addTag(ItemTag.POLEARM);
		world.addAtEmptyLocation(staff,z);
		return staff;
	}
	
	public Item newShortbow(int z) {
		Item bow = new Item("Shortbow", ItemType.WEAPON, shortbowIcon);
		bow.modifyRangedAttackValue(3);
		bow.setDamage(1, 3);			
		bow.setRangedDamage(2, 4);		//Standard Arrows typically add 2 damage
		bow.addTag(ItemTag.BOW);
		world.addAtEmptyLocation(bow,z);
		return bow;
	}
	public Item newHandaxe(int z) {
		Item axe = new Item("Handaxe", ItemType.WEAPON, handaxeIcon);
		axe.modifyAttackValue(1);
		axe.setDamage(2, 4);
		axe.addTag(ItemTag.AXE);
		axe.addTag(ItemTag.CLEAVING);
		axe.setDamageType(Type.SLASHING);
		world.addAtEmptyLocation(axe,z);
		return axe;
	}
	
	public Item newLeatherArmor(int z) {
		Item armor = new Item("Leather Armor", ItemType.ARMOR, leatherArmorIcon);
		armor.modifyArmorValue(1);
		world.addAtEmptyLocation(armor,z);
		return armor;
	}
	public Item newStuddedLeatherArmor(int z) {
		Item armor = new Item("Studded Leather Armor", ItemType.ARMOR, studdedLeatherArmorIcon);
		armor.modifyArmorValue(2);
		world.addAtEmptyLocation(armor,z);
		return armor;
	}
	public Item newCopperBreastplate(int z) {
		Item armor = new Item("Copper Breastplate", ItemType.ARMOR, copperBreastplateIcon);
		armor.modifyArmorValue(3);
		world.addAtEmptyLocation(armor,z);
		return armor;
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
