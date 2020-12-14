package assembly;

import items.Item;
import items.ItemTag;
import items.ItemType;
import javafx.scene.image.Image;
import world.World;

public class ArmorFactory implements java.io.Serializable {
	private static final long serialVersionUID = 7769423305067121315L;
	private World world;
	
	private Image armorFull = new Image(this.getClass().getResourceAsStream("resources/items/armor_full.png"));
	private Image leatherArmorIcon = tools.ImageCrop.cropImage(armorFull, 0, 0, 32, 32);
	private Image studdedLeatherArmorIcon = tools.ImageCrop.cropImage(armorFull, 32, 0, 32, 32);
	private Image copperBreastplateIcon = tools.ImageCrop.cropImage(armorFull, 0, 192, 32, 32);
	private Image paddedLeatherArmorIcon = tools.ImageCrop.cropImage(armorFull, 128, 0, 32, 32);
	private Image ironCuirassIcon = tools.ImageCrop.cropImage(armorFull, 128, 160, 32, 32);
	private Image chainMailIcon = tools.ImageCrop.cropImage(armorFull, 0, 256, 32, 32);
	
	private Image bootFull = new Image(this.getClass().getResourceAsStream("resources/items/boot_full.png"));
	private Image leatherBootIcon = tools.ImageCrop.cropImage(bootFull, 160, 0, 32, 32);
	
	private Image gloveFull = new Image(this.getClass().getResourceAsStream("resources/items/gloves_full.png"));
	private Image gloveIcon = tools.ImageCrop.cropImage(gloveFull, 0, 0, 32, 32);
	
	private Image hatFull = new Image(this.getClass().getResourceAsStream("resources/items/hat_full.png"));
	private Image capIcon = tools.ImageCrop.cropImage(hatFull, 0, 32, 32, 32);
	
	private Image shieldFull = new Image(this.getClass().getResourceAsStream("resources/items/shield_full.png"));
	private Image bucklerIcon = tools.ImageCrop.cropImage(shieldFull, 0, 0, 32, 32);
	private Image shieldIcon = tools.ImageCrop.cropImage(shieldFull, 64, 0, 32, 32);
	
	private Image devBreastplateIcon = tools.ImageCrop.cropImage(armorFull, 224, 192, 32, 32);
	
	public ArmorFactory(World world) {
		this.world = world;
	}
	
	public Item newRandomArmor(int z) {
		switch((int)(Math.random()*7)) {
		case 0: return newLeatherArmor(z);
		case 1: return newStuddedLeatherArmor(z);
		case 2: return newLeatherBoot(z);
		case 3: return newGlove(z);
		case 4: return newCap(z);
		case 5: return newBuckler(z);
		default: return newCopperBreastplate(z);
		}
	}
	
	public Item newLeatherArmor(int z) {
		Item item = new Item("Leather Armor", ItemType.ARMOR, leatherArmorIcon);
		item.modifyArmorValue(2);
		item.addTag(ItemTag.LIGHTARMOR);
		item.setDescription("A suit made from layers of tanned animal hide, this light armour provides basic protection with almost no hindrance to movement.");
		item.setWeight(2);
		world.addAtEmptyLocation(item,z);
		return item;
	}
	public Item newStuddedLeatherArmor(int z) {
		Item item = new Item("Studded Leather Armor", ItemType.ARMOR, studdedLeatherArmorIcon);
		item.modifyArmorValue(4);
		item.addTag(ItemTag.MEDIUMARMOR);
		item.setWeight(2);
		item.setDescription("A set of leather armor enhanced with metal studs providing additional defense, at the sake of swift movement.");
		world.addAtEmptyLocation(item,z);
		return item;
	}
	public Item newCopperBreastplate(int z) {
		Item item = new Item("Copper Breastplate", ItemType.ARMOR, copperBreastplateIcon);
		item.modifyArmorValue(6);
		item.addTag(ItemTag.HEAVYARMOR);
		item.setWeight(3);
		item.setDescription("A set of interlocking copper plates. It is decent armor for the dungeon, if a bit heavy.");
		world.addAtEmptyLocation(item,z);
		return item;
	}
	
	public Item newLeatherBoot(int z) {
		Item item = new Item("Leather Boots", ItemType.BOOTS, leatherBootIcon);
		item.modifyArmorValue(1);
		item.setDescription("A pair of leather boots. They mildly increases your armor without hindering your movement.");
		world.addAtEmptyLocation(item,z);
		return item;
	}
	
	public Item newGlove(int z) {
		Item item = new Item("Gloves", ItemType.GLOVES, gloveIcon);
		item.modifyArmorValue(1);
		item.setDescription("A pair of leather gloves. They mildly increases your armor without hindering your movement.");
		world.addAtEmptyLocation(item,z);
		return item;
	}
	
	public Item newCap(int z) {
		Item item = new Item("Cap", ItemType.HEAD, capIcon);
		item.modifyArmorValue(1);
		item.setDescription("A small leather cap. It mildly increases your armor without hindering your movement.");
		world.addAtEmptyLocation(item,z);
		return item;
	}

	public Item newBuckler(int z) {
		Item item = new Item("Buckler", ItemType.OFFHAND, bucklerIcon);
		item.modifyArmorValue(2);
		item.modifyAttackValue(-2);
		item.setDescription("A small wooden shield. Effective for blocking attacks while slightly impeding your offensive capabilities.");
		item.addTag(ItemTag.SHIELD);
		world.addAtEmptyLocation(item,z);
		return item;
	}
	
	public Item newPaddedLeatherArmor(int z) {
		Item item = new Item("Padded Leather Armor", ItemType.ARMOR, paddedLeatherArmorIcon);
		item.modifyArmorValue(4);
		item.addTag(ItemTag.LIGHTARMOR);
		item.setDescription("Layers of cloth padding to increase the potency of standard leather.");
		item.setWeight(2);
		world.addAtEmptyLocation(item,z);
		return item;
	}
	
	public Item newIronCuirass(int z) {
		Item item = new Item("Iron Cuirass", ItemType.ARMOR, ironCuirassIcon);
		item.modifyArmorValue(6);
		item.addTag(ItemTag.MEDIUMARMOR);
		item.setDescription("A small piece of iron plate, strapped together and worn around the chest. Protects your vitals without hindering movement too much.");
		item.setWeight(3);
		world.addAtEmptyLocation(item,z);
		return item;
	}
	
	public Item newChainMail(int z) {
		Item item = new Item("Chain Mail", ItemType.ARMOR, chainMailIcon);
		item.modifyArmorValue(8);
		item.addTag(ItemTag.HEAVYARMOR);
		item.setDescription("A suit made entirely out of tiny metal rings. Offering strong protection against weapons, claws and carnassials, chain mail hinders quick and stealthy movement.");//*
		item.setWeight(4);
		world.addAtEmptyLocation(item,z);
		return item;
	}
	
	public Item newShield(int z) {
		Item item = new Item("Shield", ItemType.OFFHAND, shieldIcon);
		item.modifyArmorValue(4);
		item.modifyAttackValue(-3);
		item.setDescription("A piece of wood and metal, to be strapped on one arm for defence. It is cumbersome to wear."); //*
		item.addTag(ItemTag.SHIELD);
		world.addAtEmptyLocation(item,z);
		return item;
	}
	
	
	public Item newDevBreastplate(int z) {
		Item armor = new Item("Armor of the Dev", ItemType.ARMOR, devBreastplateIcon);
		armor.modifyArmorValue(500);
		world.addAtEmptyLocation(armor,z);
		return armor;
	}
}
